job('Aplicacion Node.js Docker DSL') {
    description('Aplicación Node JS Docker DSL para el curso de Jenkins')
    scm {
        git('https://github.com/gussaints/nodejsapp.git', 'master') { node ->
            node / gitConfigName('gussaints')
            node / gitConfigEmail('qwertygussaints@gmail.com')
        }
    }
    triggers {
        scm('H/7 * * * *')
    }
    wrappers {
        nodejs('nodejs')
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('gussaintsofficial/nodejsapp01')
            tag('${GIT_REVISION,length=7}')
            registryCredentials('docker-hub')
            forcePull(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
    publishers {
	// Add conditional post-build actions.
        flexiblePublish {
            // Adds a conditional action.
            conditionalAction {
                // Specifies the condition to evaluate before executing the build steps.
                condition {
                    // Runs a shell script for checking the condition.
                    shell('echo hello0000!')
                    // Runs the build steps if the current build status is within the configured range.
                    status('FAILURE', 'SUCCESS')
                }
                steps {
                    shell('echo hello1111!')
                }
            }
        }
    }
}
