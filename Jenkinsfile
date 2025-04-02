pipeline {
    agent any

        environment {
            AWS_ACCOUNT_ID="864899830569"
            AWS_DEFAULT_REGION="eu-central-1"
            DOCKER_IMAGE = 'binary-tree'
            IMAGE_REPO_NAME="binary-tree"
            IMAGE_TAG="latest"
            REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
        }

    stages {
        stage('Checkout Code') {
            steps {
                // Checkout the code from your Git repository
                git branch: 'main', url: 'https://github.com/HotoMazur/BinaryTree.git'
            }
        }

        stage('Build Maven Project') {
            steps {
                script {
                    // Run Maven to clean and build the project
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        stage('Build Docker image') {
            steps{
                script{
                     dockerImage = docker.build "${IMAGE_REPO_NAME}:${IMAGE_TAG}"
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
    }

    post {
        success {
            echo "Maven build completed successfully!"
        }

        failure {
            echo "Maven build failed!"
        }
    }
}
