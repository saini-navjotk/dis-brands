pipeline {
    agent any
        
    
   
	 environment{
        DOCKER_USERNAME = credentials('NAVJOT_DOCKER_USERNAME')
        DOCKER_PASSWORD = credentials('NAVJOT_DOCKER_PASSWORD')
    }
	
    stages {
        
      
        
           stage('Checkout the code') {
            steps{
               sh script: '''
	       rm -rf .git
	       cd ..
                rm -rf dis-brands
		
                '''
            }
        }
		
   
}
}
