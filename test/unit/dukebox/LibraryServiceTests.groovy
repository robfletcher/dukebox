package dukebox

import grails.test.GrailsUnitTestCase
import org.apache.commons.lang.RandomStringUtils
import org.springframework.mock.web.MockMultipartFile
import org.apache.commons.io.FileUtils

class LibraryServiceTests extends GrailsUnitTestCase {

	LibraryService libraryService
	File basedir
	File mp3File
	File noTagsFile

	void setUp() {
		super.setUp()

		basedir = new File(System.properties.'java.io.tmpdir', RandomStringUtils.randomAlphanumeric(8))
		basedir.mkdirs()
		mockConfig "library.basedir='$basedir.absolutePath'"

		mockDomain(Track)
		Track.metaClass.static.withTransaction = {Closure closure -> closure() }

		mp3File = new File("sample.mp3")
		noTagsFile = new File("notags.mp3")

		libraryService = new LibraryService()
	}

	void tearDown() {
		super.tearDown()

		basedir.deleteDir()
	}

	void testAddCreatesTrackAndFile() {
		def track
		mp3File.withInputStream {istream ->
			track = libraryService.add(istream)
		}

		assertEquals("Fake French", track.title)
		assertEquals("Le Tigre", track.artist)
		assertEquals("The Wired Cd: Rip. Sample. Mash. Share. ", track.album)
		assertEquals(9, track.trackNo)
		assertEquals(2004, track.year)

		assertTrue track.file.isFile()
		use(FileUtils) {
			assertEquals mp3File.checksumCRC32(), track.file.checksumCRC32()
		}

		assertEquals 1, Track.count()
	}

	void testAddReturnsTrackWithErrorsWhenTagsMissingFromFile() {
		def track
		noTagsFile.withInputStream {istream ->
			track = libraryService.add(istream)
		}

		assertTrue track.hasErrors()
		assertEquals "nullable", track.errors.title
		assertEquals "nullable", track.errors.artist

		assertFalse track.file.isFile()

		assertEquals 0, Track.count()
	}

}