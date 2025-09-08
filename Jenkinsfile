pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = "mudam5"
        IMAGE_NAME = "logexport"
         HOST_PORT = "9097"
        CONTAINER_PORT = "9097"
        CONTAINER_NAME = "logexport"
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

        stage('Build Backend') {
            steps {
                script {
                    def services = ['LogExport']
                    services.each { svc ->
                        dir("BACKEND/${svc}") {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
            }
        }
        stage('Build & Push Docker Image') {
            steps {
                script {
                    COMMIT_ID = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
                    DOCKER_TAG = "${COMMIT_ID}"

                    sh """
                        docker build -t ${DOCKER_REGISTRY}/${IMAGE_NAME}:${DOCKER_TAG} \
                                     -t ${DOCKER_REGISTRY}/${IMAGE_NAME}:latest .
                    """

                    withCredentials([usernamePassword(credentialsId: 'docker', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                        sh "docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}:${DOCKER_TAG}"
                        sh "docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}:latest"
                    }
                }
            }
        }
    }
}
