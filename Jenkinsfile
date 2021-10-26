def gv

pipeline {

    agent any

    tools {
        maven 'maven-3.8'
    }

    stages {

        stage("init scripts") {
            steps {
                script {
                    gv = load 'script.groovy'
                }
            }
        }
        
        stage("build jar") {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    gv.buildImage()
                }
          
            }
        }
        stage("deploy image") {
            steps {
                script {
                    def dockerCmd = 'docker run -p 8080:8081 -d zmitser/my-repo:2.1'
                    sshagent(['ec2-server']) {
                        sh "ssh -o StrictHostKeyChecking=no ec2-user@3.122.254.206 ${dockerCmd}"
                   }
                }
                echo "Deploying image to the Nexus repository"
            }
        }
    }
}
