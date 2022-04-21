pipeline {
	agent { 
        kubernetes{
            label 'jenkins-slave'     
	}
   
     }
	
 environment{
        DOCKER_USERNAME = credentials('NAVJOT_DOCKER_USERNAME')
        DOCKER_PASSWORD = credentials('NAVJOT_DOCKER_PASSWORD')
    }
	
    stages {
        
      
        
           stage('Checkout the code') {
            steps{
                sh(script: """
                    git clone https://github.com/saini-navjotk/dis-brands.git
                """, returnStdout: true) 
            }
        }

		  stage('Build the code') {
            steps {
			      sh script: '''
                #!/bin/bash
		echo "This is start $(pwd)"
                cd $WORKSPACE/dis-brands/ 
                export M2_HOME=/usr/share/maven
                export PATH=$PATH:$M2_HOME/bin
                mvn --version
                mvn install:install-file -Dfile=./KafkaService.jar -DgroupId=org.tcs.dis -DartifactId=dis-kafka -Dversion=1.0-SNAPSHOT -Dpackaging=jar
                mvn install
                '''
              }
        }
        

					
			
         stage('docker build') {
            steps{
                sh script: '''
                #!/bin/bash
               cd $WORKSPACE/dis-brands/ 
                docker build -t navjotdis/dis-brands:${BUILD_NUMBER} .
                '''
            }
        }
        
         stage('docker login') {
            steps{
                sh(script: """
                    docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD
                """, returnStdout: true) 
            }
        }     
	
             stage('Push docker image') {
            steps{
                sh(script: """
                    docker push navjotdis/dis-brands:${BUILD_NUMBER}
                """)
            }
        }
       
           stage('Deploy microservice') {
				steps{
					sh script: '''
						#!/bin/bash
					cd $WORKSPACE/dis-brands/ 	
					#get kubectl for this demo
					curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl
					chmod +x ./kubectl
					./kubectl  apply -f ./configmap.yaml
					./kubectl   apply -f ./secret.yaml
					
					cat ./deployment.yaml | sed s/changeMePlease/${BUILD_NUMBER}/g | ./kubectl   apply -f -
					 ./kubectl    apply -f ./service.yaml
					'''
				}
			}

		
   
}
}
