#!/usr/bin/env groovy


@Library("jenkins-shared-library")_

pipeline {

    agent any

    tools {
        maven 'maven-3.8'
    }

    stages {

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
                    buildImage "zmitser/my-repo:2.1"
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
