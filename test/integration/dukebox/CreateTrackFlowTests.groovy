package dukebox

import grails.test.WebFlowTestCase
import org.springframework.mock.web.MockMultipartFile

class CreateTrackFlowTests extends WebFlowTestCase {

	File mp3File
	File noTagsFile

	void setUp() {
		super.setUp()

		mp3File = new File("sample.mp3")
		noTagsFile = new File("notags.mp3")
	}

	def getFlow() {
		return new TrackController().createFlow
	}

	void testStartsOnUploadForm() {
		startFlow()
		
		assertFlowExecutionActive()
		assertCurrentStateEquals "upload"
	}

	void testUploadFormRenderedWithErrorsIfUploadedFileInvalid() {
		startFlow()

		signalEvent "next"

		assertCurrentStateEquals "upload"
		def command = getFlowAttribute("command")
		assertTrue command.hasErrors()
		assertEquals "nullable", command.errors.getFieldError("file").code
	}

	void testGoesDirectlyToEndIfUploadedFileHasTags() {
		startFlow()

		mockRequest.file = new MockMultipartFile(mp3File.name, new FileInputStream(mp3File))
		signalEvent "next"

		assertCurrentStateEquals "showTrack"
	}

	void testRequestsMissingTagDataIfUploadedFileHasNoTags() {
		startFlow()

		mockRequest.file = new MockMultipartFile(mp3File.name, new FileInputStream(noTagsFile))
		signalEvent "next"

		assertCurrentStateEquals "enterDetails"
	}

}