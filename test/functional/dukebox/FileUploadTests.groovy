package dukebox

import dukebox.AbstractFunctionalTestCase

class FileUploadTests extends AbstractFunctionalTestCase {

	File noTagsFile

	void setUp() {
		super.setUp()
		createUser()
		noTagsFile = new File("notags.mp3")
		assertTrue noTagsFile.isFile()
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

		assertTitle "Track Information"
		assertContentContains "Title is required"
		assertContentContains "Artist is required"

		form {
			album = "From the Desk of Mr. Lady"
			year = "2004"
			click "Update"
		}

		assertTitle "Track Information"
		assertContentContains "Title is required"
		assertContentContains "Artist is required"

		form {
			assertEquals "From the Desk of Mr. Lady", album
			assertEquals "2004", year

			title = "Fake French"
			artist = "Le Tigre"
			click "Update"
		}

		assertTitle "Fake French by Le Tigre"
		assertContentContains "Fake French by Le Tigre uploaded"
	}

}