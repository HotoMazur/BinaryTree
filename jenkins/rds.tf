resource "aws_db_instance" "default" {
  allocated_storage    = 10
  db_name              = "binaryTree"
  engine               = "postgres"
  engine_version       = "11.22"
  instance_class       = "db.t3.micro"
  db_subnet_group_name = aws_db_subnet_group.default.name
  vpc_security_group_ids = [aws_security_group.jenkins_sg.id]
  username             = "postgres"
  password             = "postgres"
  skip_final_snapshot  = true
  multi_az             = false
}

resource "aws_s3_bucket" "jenkins_s3_bucket" {
  bucket = "my-unique-jenkins-s3-bucket-2023"

  tags = {
    Name = "Jenkins-Server"
  }
}

resource "aws_db_subnet_group" "default" {
  name = "jenkins-db-subnet-group"
  subnet_ids = [
    aws_subnet.public_subnet[0].id,
    aws_subnet.public_subnet[1].id,
    aws_subnet.public_subnet[2].id
  ]

  tags = {
    Name = "Jenkins-DB-Subnet-Group"
  }
}

resource "aws_s3_bucket_acl" "s3_bucket_acl" {
  bucket = aws_s3_bucket.jenkins_s3_bucket.id
  acl    = "private"

  depends_on = [aws_s3_bucket_ownership_controls.s3_bucket_acl_ownership]
}

resource "aws_s3_bucket_ownership_controls" "s3_bucket_acl_ownership" {
  bucket = aws_s3_bucket.jenkins_s3_bucket.id

  rule {
    object_ownership = "ObjectWriter"
  }
}

resource "aws_s3_bucket" "liquibase_bucket" {
  bucket        = "my-liquibase-migrations-${random_string.suffix.result}"
  force_destroy = true
}

resource "aws_s3_bucket_versioning" "versioning" {
  bucket = aws_s3_bucket.liquibase_bucket.id
  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_s3_object" "db_files" {
  for_each = fileset("../db", "**")
  bucket = aws_s3_bucket.liquibase_bucket.id
  key    = "db/${each.value}"
  source = "../db/${each.value}"
  etag = filemd5("../db/${each.value}")
}
