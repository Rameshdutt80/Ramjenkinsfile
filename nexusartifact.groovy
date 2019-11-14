node {
   def mvnHome
   stage('Preparation') {
      git 'https://github.com/Rameshdutt80/game-of-life.git'
      mvnHome = tool name: 'maven', type: 'maven'
   }
   stage('Build') {
      if (isUnix()) {
         sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
      } else {
         bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
      }
   }
   stage('Results') {
      junit '**/target/surefire-reports/TEST-*.xml'
      archive 'target/*.jar'
   }
   stage('Publish') {
	 # copied code
     nexusPublisher nexusInstanceId: 'localNexus',
	 nexusRepositoryId: 'releases', 
	 packages: [[$class: 'MavenPackage',
	 mavenAssetList: [[classifier: '', extension: '', 
	 filePath: 'war/target/jenkins.war']], 
	 mavenCoordinate: [artifactId: 'jenkins-war', 
	 groupId: 'org.jenkins-ci.main', 
	 packaging: 'war', version: '2.23']]]
	 # created one
	 nexusArtifactUploader credentialsId: 'nexus_credentials', groupId: '\'org.jenkins-ci.main\'', nexusUrl: '192.168.1.10:8081', nexusVersion: 'nexus2', protocol: 'http', repository: 'latest_gameofliife1', version: '3'
	 
   }
}
