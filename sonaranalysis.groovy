node {
    stage('git clone'){
        git 'https://github.com/Rameshdutt80/gradle-simple.git'
    }
    dir('gradle-simple') {
        stage('build') {
        // sh label: '', script: 'gradlew.bat -b migration-job/build.gradle build'
            def gradle_Home = tool name: 'gradle5.6', type: 'gradle'
			def gradleCMD = "${gradle_Home}/bin/gradle"
            sh "${gradleCMD} tasks"
        }
    }
    stage('Sonarqube Analysis') {
        def scannerHome = tool name: 'SonarQube Scanner 2.8'
        withSonarQubeEnv('SonarQube') {
            sh "${scannerHome}/bin/sonar-scanner -D sonar.login=admin -D sonar.password=admin"
        }
    }
}