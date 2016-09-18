node {
    // uncomment these 2 lines and edit the name 'node-4.4.7' according to what you choose in configuration
    def nodeHome = tool name: 'node-4.4.5', type: 'jenkins.plugins.nodejs.tools.NodeJSInstallation'
    env.PATH = "${nodeHome}/bin:${env.PATH}"

    stage('check tools') {
        sh "node -v"
        sh "npm -v"
        sh "bower -v"
        //sh "gulp -v"
    }

    stage('checkout') {
        checkout scm
    }

    stage('npm install') {
        sh "npm install"
        sh "npm install -g gulp-cli"
        sh "bower install"
    }

    stage('clean') {
        sh "./mvnw clean"
    }

    stage('backend tests') {
        //sh "./mvnw test"
    }

    stage('frontend tests') {
        //sh "gulp test"
    }

    stage('packaging') {
        //sh "./mvnw package -Pprod -DskipTests"
	sh "./mvnw -X package -Pprod docker:build"
	//sh "docker-compose -f src/main/docker/app.yml up"
    }
    stage('deployment'){
        sh "pkill docker-compose || true"
        sh "/usr/local/bin/docker-compose -f src/main/docker/app.yml up &"
    }
}
