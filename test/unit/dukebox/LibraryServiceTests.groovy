package dukebox

import grails.test.GrailsUnitTestCase
import org.apache.commons.lang.RandomStringUtils
import org.springframework.mock.web.MockMultipartFile
import org.apache.commons.io.FileUtils

class LibraryServiceTests extends GrailsUnitTestCase {

	LibraryService libraryService
	File basedir
	File mp3File
	MockMultipartFile multipartFile

	void setUp() {
		super.setUp()

		basedir = new File(System.properties.'java.io.tmpdir', RandomStringUtils.randomAlphanumeric(8))
		basedir.mkdirs()
		mockConfig "library.basedir='$basedir.absolutePath'"

		mockDomain(Track)
		Track.metaClass.static.withTransaction = {Closure closure -> closure() }

		mp3File = new File("larouxbulletproof.mp3")
		mp3File.withInputStream {istream ->
			multipartFile = new MockMultipartFile(mp3File.name, istream)
		}

		libraryService = new LibraryService()
	}

	void tearDown() {
		super.tearDown()

		basedir.deleteDir()
	}

	void testAddFailsForEmptyFile() {
		def emptyMultipartFile = new MockMultipartFile(mp3File.name, new byte[0])
		shouldFail(LibraryException) {
			libraryService.add(emptyMultipartFile)
		}
	}

	void testAddFailsForInvalidFile() {
		def emptyMultipartFile = new MockMultipartFile(mp3File.name, RandomStringUtils.random(1024).bytes)
		shouldFail(LibraryException) {
			libraryService.add(emptyMultipartFile)
		}
	}

	void testAddCreatesTrackAndFile() {
		def track = libraryService.add(multipartFile)

		assertEquals("04 Bulletproof - Album Version", track.title)
		assertEquals("La Roux", track.artist)
		assertEquals("Bulletproof Remixes", track.album)
		assertEquals(4, track.trackNo)
		assertEquals(2009, track.year)

		assertTrue track.file.isFile()
		use(FileUtils) {
			assertEquals mp3File.checksumCRC32(), track.file.checksumCRC32()
		}

		assertEquals 1, Track.count()
	}

}