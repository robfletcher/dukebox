package dukebox

import grails.test.WebFlowTestCase
import org.springframework.mock.web.MockMultipartFile

class CreateTrackFlowTests extends WebFlowTestCase {

	TrackController controller
	def libraryService
	File mp3File
	File noTagsFile

	void setUp() {
		super.setUp()

		mp3File = new File("sample.mp3")
		noTagsFile = new File("notags.mp3")

		controller = new TrackController()
		controller.libraryService = libraryService
	}

	def getFlow() {
		return controller.createFlow
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

		controller.params.file = new MockMultipartFile(mp3File.name, new FileInputStream(mp3File))
		signalEvent "next"

		assertFlowExecutionEnded()
		assertFlowExecutionOutcomeEquals "showTrack"
	}

	void testRequestsMissingTagDataIfUploadedFileHasNoTags() {
		startFlow()

		controller.params.file = new MockMultipartFile(mp3File.name, new FileInputStream(noTagsFile))
		signalEvent "next"

		assertCurrentStateEquals "enterDetails"
	}

}