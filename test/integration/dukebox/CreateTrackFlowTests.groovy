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

	void tearDown() {
		super.tearDown()

		Track.list().each {
			it.file.delete()
		}
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

		assertEquals 1, Track.count()
		def track = Track.findByTitle("Fake French")
		assertNotNull track
		assertEquals "Le Tigre", track.artist
		assertTrue track.file.isFile()
	}

	void testRequestsMissingTagDataIfUploadedFileHasNoTags() {
		startFlow()

		controller.params.file = new MockMultipartFile(mp3File.name, new FileInputStream(noTagsFile))
		signalEvent "next"

		assertCurrentStateEquals "enterDetails"
		def track = flowScope.trackInstance
		assertNotNull track
		assertEquals "nullable", track.errors.getFieldError("title").code
		assertEquals "nullable", track.errors.getFieldError("artist").code
		assertFalse track.file.isFile()
	}

}