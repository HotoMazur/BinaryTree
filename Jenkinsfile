pipeline {
    agent any

        environment {
            DOCKER_IMAGE = 'binaryTree'
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
                    sh 'docker build -t $DOCKER_IMAGE .'
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
