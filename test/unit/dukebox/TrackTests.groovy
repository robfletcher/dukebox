package dukebox

import grails.test.*

class TrackTests extends GrailsUnitTestCase {

	def libraryService

    void setUp() {
        super.setUp()

		mockConfig '''
			library.basedir = "${System.properties.'java.io.tmpdir'}/testlibrary"
		'''
		libraryService = new LibraryService()
		libraryService.basedir.mkdirs()
    }

    void tearDown() {
        super.tearDown()
    }

	void testMandatoryFields() {
		mockDomain Track

		def track = new Track()
		assertFalse track.validate()
		assertEquals 3, track.errors.errorCount
		assertEquals "nullable", track.errors.title
		assertEquals "nullable", track.errors.artist
		assertEquals "nullable", track.errors.filepath

		track.title = ""
		track.artist = ""
		assertFalse track.validate()
		assertEquals "blank", track.errors.title
		assertEquals "blank", track.errors.artist
	}

	void testFilepathMustBeUnique() {
		mockDomain(Track, [new Track(filepath: "foo.mp3")])

		def track = new Track(filepath: "foo.mp3")
		assertFalse track.validate()
		assertEquals "unique", track.errors.filepath
	}

	void testYearMustBeCorrectFormat() {
		mockDomain Track

		def track = new Track(title: "Bulletproof", artist: "La Roux", filepath: "bulletproof.mp3", year: "foo")
		assertFalse track.validate()
		assertEquals "matches", track.errors.year

		track.year = "000"
		assertFalse track.validate()
		assertEquals "matches", track.errors.year

		track.year = "00000"
		assertFalse track.validate()
		assertEquals "matches", track.errors.year

		track.year = "0000"
		assertTrue track.validate()
	}

	void testTrackNoMustBeNumeric() {
		mockDomain Track

		def track = new Track(title: "Bulletproof", artist: "La Roux", filepath: "bulletproof.mp3", trackNo: "foo")
		assertFalse track.validate()
		assertEquals "matches", track.errors.trackNo

		track.trackNo = "0"
		assertTrue track.validate()
	}
}
