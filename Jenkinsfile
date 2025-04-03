pipeline {
    agent any

        environment {
            AWS_ACCOUNT_ID = "864899830569"
            AWS_DEFAULT_REGION = "eu-central-1"
            DOCKER_IMAGE = 'binary-tree'
            IMAGE_REPO_NAME = "binary-tree"
            IMAGE_TAG = "latest"
            REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
            CLUSTER_NAME = "binary-tree"
            KUBE_NAMESPACE = "default"
            S3_BUCKET = "my-liquibase-migrations-pr5o7a6x"
            SECURITY_GROUP_ID = "sg-084b66406d2e7b2a6"
        }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/HotoMazur/BinaryTree.git'
            }
        }

        stage('Build Maven Project') {
            steps {
                script {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        stage('Build Docker image') {
            steps{
                script{
                     def dockerImage = docker.build "${IMAGE_REPO_NAME}:${IMAGE_TAG}"
                }
            }
        }
        stage("Log in to ECR"){
            steps{
                script{
                    sh "aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com"
                }
            }
        }
        stage("Push to ECR"){
              steps{
                    script{
                        sh "docker tag ${IMAGE_REPO_NAME}:${IMAGE_TAG} ${REPOSITORY_URI}:$IMAGE_TAG"
                        sh "docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}:${IMAGE_TAG}"
                    }
              }
        }

        stage('Configure AWS Credentials') {
            steps {
                script {
                    sh "aws eks update-kubeconfig --region ${AWS_DEFAULT_REGION} --name ${CLUSTER_NAME}"
                    sh "aws configure set region eu-central-1"
                    def publicIp = sh(script: "curl -s ifconfig.me", returnStdout: true).trim()
                    echo "Public IP: ${publicIp}"
                    env.PUBLIC_IP = publicIp
                    sh """
                        aws ec2 authorize-security-group-ingress --group-id $SECURITY_GROUP_ID --protocol tcp --port 443 --cidr ${env.PUBLIC_IP}/32 --region $AWS_REGION
                    """
                }
            }
        }

        stage('Find deployment'){
            steps{
                script{
                    sh "aws ec2 describe-route-tables --filters 'Name=vpc-id,Values=vpc-0bf49f16776cc166c'"
                    sh "kubectl get nodes"
                }
            }
        }

        stage('Update Deployment YAML') {
            steps {
                script {
                    sh """
                        sed -i 's|ECR_REPOSITORY_URL|${REPOSITORY_URI}|g' jenkins/kube/deployment.yaml
                    """
                }
            }
        }

        stage('Deploy to EKS') {
            steps {
                script {
                    sh """
                        kubectl apply -f jenkins/kube/deployment.yaml
                    """
                }
            }
        }
    }

    post {
        success {
            echo "Maven build completed successfully!"
        }

        failure {
            echo "Maven build failed!"
        }
        always {
                    // Cleanup Docker images
                    sh "docker rmi ${REPOSITORY_URI}:${IMAGE_TAG} || true"
                    sh "docker rmi ${IMAGE_REPO_NAME}:${IMAGE_TAG} || true"
                    sh "rm -f deployment.yaml || true"
                }
    }
}
