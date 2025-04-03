provider "aws" {
  region = "eu-central-1"
}

resource "aws_instance" "jenkins_ec2" {
  ami           = "ami-0bade3941f267d2b8"
  instance_type = "t2.medium"
  key_name      = "terraformkp"
  subnet_id     = aws_subnet.public_subnet[0].id
  vpc_security_group_ids = [aws_security_group.jenkins_sg.id]

  iam_instance_profile = aws_iam_instance_profile.ec2_s3_instance_profile.name

  root_block_device {
    volume_type           = "gp3"
    volume_size           = 20
    delete_on_termination = true
    encrypted             = true
  }

  associate_public_ip_address = true

  user_data = templatefile("install_jenksin.sh", {
    rds_endpoint = aws_db_instance.default.endpoint
    s3_bucket    = aws_s3_bucket.liquibase_bucket.bucket
  })

  tags = {
    Name = "Jenkins"
  }

  depends_on = [aws_internet_gateway.jenkins_gw, aws_db_instance.default, aws_s3_bucket.liquibase_bucket]
}