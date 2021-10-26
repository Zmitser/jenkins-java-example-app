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
                    -DnewVersions=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                    versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
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
    }
}
