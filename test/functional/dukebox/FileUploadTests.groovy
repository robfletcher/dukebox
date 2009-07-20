package dukebox

import functionaltestplugin.FunctionalTestCase
import com.gargoylesoftware.htmlunit.html.HtmlFileInput

class FileUploadTests extends FunctionalTestCase {

	File testFile

	void setUp() {
		super.setUp()

		def port = System.properties."server.port" ?: 8080
		baseURL = "http://localhost:${port}/dukebox"

		testFile = new File("sample.mp3")
	}

	void testFileIsMandatory() {
		get "/track/create"

		form {
			click "Create"
		}

		assertTitle "Create Track"
		assertContentContains "File is not a valid MP3 file" 
	}

	void testSuccessfulFileUpload() {
		get "/track/create"

		byId("file").valueAttribute = testFile.absolutePath
		form {
			click "Create"
		}

		assertTitle "Show Track"
		assertContentContains "Fake French by Le Tigre uploaded"
	}

}