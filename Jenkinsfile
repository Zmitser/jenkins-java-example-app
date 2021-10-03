pipeline {

    agent any

    tools {
        maven 'maven-3.8'
    }

    stages {
        stage("build jar") {
            steps {
                script {
                    echo "Building application"
                    sh 'mvn package'
                }
            }
        }
        stage("build image") {
            steps {
                echo "Building the docker image"
                withCredentials([usernamePassword(credentialsId: 'docker-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]){
                    sh 'mvn -Pprod jib:dockerBuild -Dimage=zmitser/my-repo:1.0'
                    sh "echo $PASS | docker login -u $USER --password-stdin"
                    sh "docker push zmitser/my-repo:1.0"
                }
            }
        }
        stage("deploy image") {
            steps {
                echo "Deploying image to the Nexus repository"

            }
        }
    }
}
