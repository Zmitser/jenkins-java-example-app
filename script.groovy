def buildApp (version){
    echo 'Building application ...'
    echo "Building version ${version}"
}

def testApp (credentials) {
    echo 'Testing application...'
    echo "Deploying with ${credentials}"
}

def deployApp(paramVersion){
    echo 'Deploying application...'
    echo "Deploying version ${paramVersion}"
}

return this
