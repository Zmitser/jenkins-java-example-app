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
                echo "Deploying image to the Nexus repository"
            }
        }
    }
}
