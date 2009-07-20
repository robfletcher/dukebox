package dukebox

import grails.test.ControllerUnitTestCase
import org.springframework.mock.web.MockMultipartFile
import org.apache.commons.lang.RandomStringUtils

class TrackControllerTests extends ControllerUnitTestCase {

	void setUp() {
		super.setUp()
		mockCommandObject(TrackUploadCommand)
	}

	void testMandatoryFields() {
		def command = new TrackUploadCommand()

		assertFalse command.validate()

		assertEquals "nullable", command.errors.file
	}

	void testEmptyFileIsInvalid() {
		def command = new TrackUploadCommand(title: "Bulletproof", artist: "La Roux")
		command.file = new MockMultipartFile("larouxbulletproof.mp3", new byte[0])

		assertFalse command.validate()

		assertEquals "validator", command.errors.file
	}

	void testNonMp3FileIsInvalid() {
		def command = new TrackUploadCommand(title: "Bulletproof", artist: "La Roux")
		command.file = new MockMultipartFile("larouxbulletproof.mp3", RandomStringUtils.random(1024).bytes)

		assertFalse command.validate()

		assertEquals "validator", command.errors.file
	}

}
