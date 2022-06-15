def version
pipeline {
    agent any

    // triggers {
    //     pollSCM('*/3 * * * *')
    // }
    parameters {
        string(name: 'projectName', defaultValue: 'pipeline-docker-image')
    }  

    stages {
        // 레포지토리를 다운로드 받음
        stage('Repository Prepare') {
            agent any
            steps {
                echo 'Clonning Repository'
                checkout scm
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    echo 'Successfully Cloned Repository'
                }
                always {
                    echo "i tried..."
                }
                cleanup {
                    echo "after all other post condition"
                }
            }
        }

        stage('Build version') {
            agent any
            steps {
                script {
                    // echo "** version init : ${params.version} **"
                    version = sh( returnStdout: true, script: "cat build.gradle | grep -o 'version = [^,]*'" ).trim()
                    echo "** version temp : ${version} **"
                    
                    version = version.split(/=/)[1]
                    version = version.replaceAll("'","").trim()
                    // params.put("version", tempSplit)
                    echo "** version load : ${version} **"
                }
            }
        }
        
        stage('Bulid gradle') {
            agent any
            steps {
                echo 'Build Backend'
                dir ('./'){
                    sh """
                    echo final version: ${version}

                    chmod +x gradlew
                    ./gradlew clean build
                    ls -al ./build

                    """
                }
            }

            post {
                success {
                    echo 'Successfully Image Build'
                }

                failure {
                  error 'This pipeline stops here...'
                }
            }
        }

        stage('docker Image build & push') {
            agent any
            steps {
                echo 'build & registry push'
                
                script {
                    docker.withRegistry("https://healthcare.kr.ncr.ntruss.com", 'dockerRegistry') {
                        def customImage = docker.build("${params.projectName}:${version}")
                        customImage.push()
                    }
                }
            }
            
            
        }
    }
}
