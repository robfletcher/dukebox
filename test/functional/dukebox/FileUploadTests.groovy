package dukebox

import dukebox.AbstractFunctionalTestCase

class FileUploadTests extends AbstractFunctionalTestCase {

	File noTagsFile

	void setUp() {
		super.setUp()
		createUser()
		noTagsFile = new File("notags.mp3")
	}

	void testMustBeLoggedInToUpload() {
		get "/track/create"
		assertTitle "Login"
	}

	void testFileIsMandatory() {
		login()
		
		get "/track/create"

		form {
			click "Create"
		}

		assertTitle "Create Track"
		assertContentContains "File is not a valid MP3 file" 
	}

	void testSuccessfulFileUpload() {
		login()
		uploadTrack()
	}

	void testUploadOfFileWithNoTags() {
		login()

		get "/track/create"
		assertTitle "Create Track"

		byId("file").valueAttribute = noTagsFile.absolutePath
		form {
			click "Create"
		}

		assertTitle "Create Track"
		assertContentContains "Please provide missing data"
	}

}