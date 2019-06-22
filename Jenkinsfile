node('docker') {

    stage ('Checkout') {
        checkout scm
    }
    stage ('Build & UnitTest') {
        mvn test
    }
}