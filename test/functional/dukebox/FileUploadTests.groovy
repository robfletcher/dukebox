package dukebox

import dukebox.AbstractFunctionalTestCase

class FileUploadTests extends AbstractFunctionalTestCase {

	void setUp() {
		super.setUp()
		createUser()
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

}