def catroidroot = new JobsBuilder(this).pipeline({new CatroidData()})

catroidroot.job("lab/Michael_Musenbrock/Build-Standalone") {
    htmlDescription(['Builds a Catroid APP as a standalone APK.'])

    // !! DO NOT give Anonymous-Users read permission, otherwise the upload-token would be spoiled
    jenkinsUsersPermissions(Permission.JobRead)

    job.parameters {
        stringParam('DOWNLOAD', 'https://share.catrob.at/pocketcode/download/821.catrobat', 'Enter the Project ID you want to build as standalone')
        stringParam('SUFFIX', 'standalone', '')
        password {
            name('UPLOAD')
            defaultValue('')
            description('upload url for webserver\n\nSyntax of the upload value is of the form\n' +
                        'https://pocketcode.org/ci/upload/1?token=UPLOADTOKEN')
        }
    }

    // The authentication token should not be on github.
    // That means it cannot be hardcode here.
    // At the same time this information should be visible in the job itself.
    // A workaround to achieve this is to store the information in global properties on jenkins master.
    def token = GLOBAL_STANDALONE_AUTH_TOKEN
    token = 'mytest'

    authenticationToken(token)
	//configure { Node project ->
	//	project / authToken("myToken")
	//}
    git(branch: 'master', jenkinsfile: 'Jenkinsfile.BuildStandalone')
}
