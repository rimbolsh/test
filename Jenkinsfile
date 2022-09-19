def version
pipeline {
    agent any

    // triggers {
    //     pollSCM('*/3 * * * *')
    // }
    parameters {
        string(name: 'projectName', defaultValue: 'pipeline-docker-image')
        string(name: 'profile', defaultValue: 'dev')
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
                        def customImage = docker.build("healthcare.kr.ncr.ntruss.com/${params.projectName}:${params.profile}-${version}")
                        customImage.push()
                    }
                }
            }
            
            
        }

        stage('ssh docker pull & run') {
            agent any
            steps {
                echo 'docker pull & run'
                
                sshagent(['jenkins-deploy']) {
                    sh "ssh -o StrictHostKeyChecking=no root@10.41.152.227 docker ps -a"
                    sh "ssh -o StrictHostKeyChecking=no root@10.41.152.227 docker ps -q --filter name=${params.projectName} | grep -q . && docker rm -f \$(docker ps -aq --filter name=${params.projectName}) | docker rmi \$(docker images --filter 'dangling=true' -q --no-trunc) || echo Not Found"
                    sh "ssh -o StrictHostKeyChecking=no root@10.41.152.227 docker run -p 8888:8888 -d --restart=always -e USE_PROFILE=${params.profile} --name ${params.projectName} healthcare.kr.ncr.ntruss.com/${params.projectName}:${params.profile}-${version}"
                }
            }
            
            
        }
    }
}
