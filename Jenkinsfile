def gv
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

        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }

        stage("build") {
            steps {
                script {
                    gv.buildApp(NEW_VERSION)
                }
            }
        }

        stage("test") {
            when {
                expression {
                     BRANCH_NAME == 'master' && params.executeTests
                }
            }
            steps {
                script {
                    gv.testApp(SERVER_CREDENTIALS)
                }
            }
        }

        stage("deploy") {
            input {
                message "Select the env to deploy to: "
                ok "Done"
                parameters {
                     choice(name: 'ENV', choices: ['dev', 'staging', 'prod'], description: '')
                }
            }
            steps {
                script {
                    gv.deployApp(params.VERSION)
                    echo "Deploying to ${ENV}"
                }
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
