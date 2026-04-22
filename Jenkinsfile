pipeline {
    agent any

    options {
        timestamps()
    }

    environment {
        CI = 'true'
        NODE_PATH = 'C:\\Program Files\\nodejs'
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
                    bat '''
                    echo Setting up Node environment...
                    set "PATH=C:\\Program Files\\nodejs;%PATH%"

                    echo Cleaning old dependencies...
                    if exist node_modules rmdir /s /q node_modules

                    echo Installing dependencies...
                    npm ci

                    echo Building frontend...
                    npm run build
                    '''
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
                    bat '"%NODE_PATH%\\npm.cmd" run lint'
                }
            }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                echo 'Deploy stage: add your deployment commands here'
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
        failure {
            echo 'Build failed. Check logs for details.'
        }
    }
}