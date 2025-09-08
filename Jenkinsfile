pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = "mudam5"
        IMAGE_NAME = "logexport"
        HOST_PORT = "9097"
        CONTAINER_PORT = "9097"    // match with SERVER_PORT or 8080 if default
        CONTAINER_NAME = "logexport"
    }

    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'master',
                    changelog: false,
                    poll: false,
                    url: 'https://github.com/mudam5/Log-Processing.git'
            }
        }
        stage('Build') {
      steps {
        // build the project and create a JAR file
        sh 'mvn clean package -DskipTests'
      }
    }
            
stage('Build & Push Backend') {
            steps {
                script {
                    sh 'docker build -t $DOCKER_REGISTRY/log-exporter:latest .'
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-id', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                        sh 'docker push $DOCKER_REGISTRY/log-exporter:latest'
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
