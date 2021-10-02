pipeline {

    agent any

    parameters {
        string(name: 'APP_NAME', defaultValue: '', description: 'version to deploy')
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        booleanParam(name: 'executeTests', defaultValue: true)
    }

    tools {
        maven 'maven-3.8'
    }

    environment {
        NEW_VERSION = '1.3.0'
        SERVER_CREDENTIALS = credentials('server-credentials')
    }

    stages {
        stage("build") {
            steps {
                echo 'Building application ...'
                echo "Building version ${NEW_VERSION}"
            }
        }

        stage("test") {
            when {
                expression {
                     BRANCH_NAME == 'master' && params.executeTests
                }
            }
            steps {
                echo 'Testing application...'
                echo "Deploying with ${SERVER_CREDENTIALS}"
            }
        }

        stage("deploy") {
            steps {
                echo 'Deploying application...'
                echo "Deploying version ${params.VERSION}"
            }
        }
    }

    post {
        always {
            echo 'Deployment process is finished'
        }

        success {
            echo 'Deploying application was successful'
        }

        failure {
            echo 'Deploying application was failed'
        }
    }
}
