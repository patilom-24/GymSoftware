pipeline {
  agent any

  environment {
    IMAGE_NAME = 'springboot-app'
    DOCKERHUB_CREDENTIALS_ID = 'dckr_pat_PVuA4GohPRYWjp_N_0fPi02v7YQ'
  }

  tools {
    maven 'MAVEN_HOME'
    jdk 'JDK21'
  }

  stages {
    stage('Checkout') {
      steps {
        git 'https://github.com/patilom-24/GymSoftware.git'
      }
    }

    stage('Build') {
      steps {
        sh 'mvn clean package -DskipTests'
      }
    }

    stage('Docker Build') {
      steps {
        sh 'docker build -t $IMAGE_NAME .'
      }
    }

    stage('Docker Compose Up') {
      steps {
        sh 'docker-compose down'
        sh 'docker-compose up -d'
      }
    }
  }

  post {
    success {
      echo "✅ Deployment successful!"
    }
    failure {
      echo "❌ Deployment failed."
    }
  }
}
