pipeline {
    agent any

    options {
        timestamps()
    }

    environment {
        CI = 'true'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Backend') {
            steps {
                dir('backend') {
                    bat 'mvnw.cmd -B -DskipTests clean package'
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    bat 'npm ci'
                    bat 'npm run build'
                }
            }
        }

        stage('Test Backend') {
            steps {
                dir('backend') {
                    bat 'mvnw.cmd -B test'
                }
            }
        }

        stage('Test Frontend') {
            steps {
                dir('frontend') {
                    bat 'npm run lint'
                }
            }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                echo 'Deploy stage: add your server/docker/k8s deployment commands here'
            }
        }
    }

    post {
        always {
            junit testResults: 'backend/target/surefire-reports/*.xml', allowEmptyResults: true
        }
        success {
            archiveArtifacts artifacts: 'backend/target/*.jar', fingerprint: true
        }
    }
}