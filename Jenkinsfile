pipeline {
    agent any

    tools {
        maven "MavenNew" // Tên maven đã cấu hình trong Global Tool Configuration
    }

    environment {
        GITHUB_URL = "https://github.com/DongNguyen20/udacity-java-web-project-4.git"
        // GITHUB_CREDENTIALS = credentials("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")
    }

    stages {
        stage('Clone') {
            steps {
                git branch: 'dev', url: GITHUB_URL
            }
        }

        stage('Build') {
            steps {
                dir('starter_code') {
                    sh "mvn clean install"
                }
            }
        }
    }
}
