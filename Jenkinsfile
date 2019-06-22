pipeline {
    agent any
    tools {
        jdk 'jdk8'
    }
    stages {
        stage('Test') {
            steps {
                sh "mvn clean test"
            }
        }
        stage('Build') {
            steps {
               sh "mvn clean package"
            }
        }
        stage('Build image') {
            steps {
               script {
                    def image = docker.build "localhost:5000/angular2-spring:0.1"
                    image.push()
               }
            }
        }
    }
}