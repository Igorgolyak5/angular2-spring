node('docker') {

    stage ('Checkout') {
        checkout scm
    }
    stage ('Build & UnitTest') {
        sh "mvn test"
    }
}