module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "~> 20.31"

  cluster_name    = "binary-tree"
  cluster_version = "1.32"

  cluster_endpoint_public_access = true

  # Optional: Adds the current caller identity as an administrator via cluster access entry
  enable_cluster_creator_admin_permissions = true

  eks_managed_node_groups = {
    binary-tree = {
      instance_types = ["t3.medium"]
      min_size       = 1
      max_size       = 3
      desired_size   = 1
    }
  }

  vpc_id     = aws_vpc.jenkins_vpc.id
  subnet_ids = aws_subnet.public_subnet.*.id

  tags = {
    Environment = "dev"
    Terraform   = "true"
  }
}

resource "aws_ecr_repository" "binary-tree-ecr" {
  name                 = "binary-tree"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

resource "aws_s3_object" "k8s_manifest" {
  bucket = aws_s3_bucket.liquibase_bucket.id
  key    = "k8s/deployment.yaml"
  content = replace(
    file("kube/deployment.yaml"),
    "ECR_REPOSITORY_URL",
    aws_ecr_repository.binary-tree-ecr.repository_url
  )
  depends_on = [aws_s3_bucket.liquibase_bucket]
}