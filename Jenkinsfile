pipeline {
    agent any
    tools {
        jdk 'jdk8'
        maven 'Maven 3.3.9'
    }
    stages {
        stage('Install') {
            steps {
                sh "mvn clean test"
            }
        }
    }
}