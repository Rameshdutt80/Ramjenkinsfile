node {
    stage('clone') {
        // git 'https://github.com/playframework/play-java-starter-example.git'
        git 'https://github.com/Rameshdutt80/gradle-simple.git'
    }
    dir('game-of-life') {
        stage('build') {
        // sh label: '', script: 'gradlew.bat -b migration-job/build.gradle build'
            def gradle_Home = tool name: 'gradle5.6', type: 'gradle'
			def gradleCMD = "${gradle_Home}/bin/gradle"
            sh "${gradleCMD} tasks"
        }
    }
}