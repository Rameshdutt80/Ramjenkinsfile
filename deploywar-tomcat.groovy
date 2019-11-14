node {
    stage('git checkout'){
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/Rameshdutt80/hello-world.git']]])
    }
    stage('build the code') {
        sh label: '', script: 'mvn clean install package'
    }
    stage('Deploy to tomcat') {
        withCredentials([usernamePassword(credentialsId: 'ansadmin', passwordVariable: 'anspwd', usernameVariable: 'ansadmin')]) {
            sshagent(['tomcat-ram']) {
                sh 'scp -o StrictHostKeyChecking=no /var/lib/jenkins/workspace/tomcat-deployment/webapp/target/*.war ansadmin@ip-172-31-20-227.us-east-2.compute.internal:/opt/tomcat/webapps'
            }
        }
    }
}