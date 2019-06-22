pipeline {
    agent any
    tools {
        jdk 'jdk8'
        maven 'mvn-3.5.2'
    }
    stages {
        stage('Install') {
            steps {
                sh "mvn clean test"
            }
        }
    }
}