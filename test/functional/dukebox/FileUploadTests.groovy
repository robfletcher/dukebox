package dukebox

import dukebox.AbstractFunctionalTestCase

class FileUploadTests extends AbstractFunctionalTestCase {

	File testFile

	void setUp() {
		super.setUp()

		createUser()

		testFile = new File("sample.mp3")
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

		get "/track/create"

		byId("file").valueAttribute = testFile.absolutePath
		form {
			click "Create"
		}

		assertTitle "Show Track"
		assertContentContains "Fake French by Le Tigre uploaded"
	}

}