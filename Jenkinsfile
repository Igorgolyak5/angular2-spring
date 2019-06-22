pipeline {
    agent any
    tools {
        jdk 'jdk8'
    }
    stages {
        stage('Install') {
            steps {
                sh "mvn clean test"
            }
        }
    }
}