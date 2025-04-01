#!/bin/bash

# Accept rds_endpoint as an environment variable (passed by Terraform)

# Update system packages
sudo yum update -y

# Download and install Jenkins
sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
sudo yum upgrade -y
sudo dnf install java-17-amazon-corretto -y
sudo yum install jenkins -y
sudo systemctl enable jenkins
sudo systemctl start jenkins

# Install additional dependencies
sudo yum install maven -y
sudo yum install -y amazon-linux-extras
sudo yum install docker -y
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker jenkins
sudo systemctl restart jenkins

# Install Liquibase
wget https://github.com/liquibase/liquibase/releases/download/v4.17.0/liquibase-4.17.0.tar.gz
tar -xvzf /liquibase-4.17.0.tar.gz -C /home/ec2-user/
cd /home/ec2-user/ || exit

mkdir -p /home/ec2-user/db
aws s3 sync "s3://${s3_bucket}/db" /home/ec2-user/db/

# Run Liquibase to apply changes to the RDS database
./liquibase --changeLogFile=db/changelog/db-changelog-master.yaml --url="jdbc:postgresql://${rds_endpoint}/binaryTree" --username=postgres --password=postgres --driver=org.postgresql.Driver update --log-level=info

#test resources !!!DELETE AND CLOSE ACL IN AWS
sudo yum install postgresql15 -y