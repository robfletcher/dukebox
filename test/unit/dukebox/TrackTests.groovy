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

}
