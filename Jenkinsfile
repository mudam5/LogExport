pipeline {
    agent any

    options {
        timestamps() // adds timestamps to console output
    }

    environment {
        DOCKER_REGISTRY = "mudam5"
        IMAGE_NAME = "log-export"
        HOST_PORT = "9097"
        CONTAINER_PORT = "9097"
        CONTAINER_NAME = "log-export"
    }

    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main',
                    changelog: false,
                    poll: false,
                    url: 'https://github.com/mudam5/LogExport.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                script {
                    // Get short commit ID for tagging
                    COMMIT_ID = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
                    DOCKER_TAG = "${COMMIT_ID}"

                    // Build Docker image with commit tag and latest
                    sh """
                        docker build -t ${DOCKER_REGISTRY}/${IMAGE_NAME}:${DOCKER_TAG} \
                                     -t ${DOCKER_REGISTRY}/${IMAGE_NAME}:latest .
                    """

                    // Login and push Docker images
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-id', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                        sh "docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}:${DOCKER_TAG}"
                        sh "docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}:latest"
                    }
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    sh """
                        echo "Stopping old deployment..."
                        docker compose -f docker-compose.yml down || true

                        echo "Starting new deployment..."
                        docker compose -f docker-compose.yml up -d
                    """
                }
            }
        }
    }

    post {
        success {
            echo "✅ Deployment successful! UI is live at http://<EC2-Public-IP>:${HOST_PORT}"
        }
        failure {
            echo "❌ Deployment failed. Check Jenkins logs."
        }
    }
}
