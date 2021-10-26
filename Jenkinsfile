#!/usr/bin/env groovy


@Library("jenkins-shared-library")_

pipeline {

    agent any

    tools {
        maven 'maven-3.8'
    }

    stages {
        
        stage('increment version'){
         
            steps {
                script {
                   echo 'incrementing app version...'
                   sh 'mvn build-helper:parse-version versions:set \
                    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                    versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "zmitser/my-repo:$version-$BUILD_NUMBER"
                }
            }
        }

        stage("build jar") {
            steps {
                script {
                    buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    buildImage env.IMAGE_NAME
                }

            }
        }
        stage("deploy image") {
           steps {
                script {
                    def dockerCmd = "docker run -p 8080:8081 -d ${env.IMAGE_NAME}"
                    sshagent(['ec2-server']) {
                        sh "ssh -o StrictHostKeyChecking=no ec2-user@3.122.254.206 ${dockerCmd}"
                   }
                }
                echo "Deploying image to the Nexus repository"
            }
        }
        
        stage("commit new version") {
         
            steps{
                script {
                  withCredentials([usernamePassword(credentialsId: 'docker-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]){
                      sh "git remote set-url origin https://${USER}:${PASS}@github.com/Zmitser/jenkins-java-example-app.git"
                      sh 'git add .'
                      sh 'git commit -m "ci: version bump"'
                      sh 'git push origin HEAD:jenkins-shared-library'
    }   
                }
            }
        }
    }
}
