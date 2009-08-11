package dukebox

import dukebox.Track
import grails.test.ControllerUnitTestCase
import org.apache.commons.lang.RandomStringUtils
import org.joda.time.DateTime
import org.joda.time.DateTimeUtils

class PlayerControllerTests extends ControllerUnitTestCase {

	def now
	def tracks

	void setUp() {
		super.setUp()

		def basedir = new File(System.properties.'java.io.tmpdir', RandomStringUtils.randomAlphanumeric(8))
		basedir.mkdirs()
		mockConfig "library.basedir='$basedir.absolutePath'"

		now = new DateTime()
		DateTimeUtils.setCurrentMillisFixed now.millis

		tracks = []
		tracks << new Track(title: "This Wheel's On Fire", artist: "Siouxsie and the Banshees", playCount: 1, lastPlayed: now.minusDays(1), filepath: "${UUID.randomUUID()}.mp3")
		tracks << new Track(title: "Gimme Back My Bullets", artist: "Lynyrd Skynyrd", playCount: 1, lastPlayed: now.minusWeeks(1), filepath: "${UUID.randomUUID()}.mp3")
		tracks << new Track(title: "Consoler Of The Lonely", artist: "The Racounteurs", playCount: 1, lastPlayed: now.minusHours(1), filepath: "${UUID.randomUUID()}.mp3")
		mockDomain(Track, tracks)

		tracks.each { track ->
			track.file.withOutputStream { ostream ->
				ostream << RandomStringUtils.random(10).bytes
			}
		}
	}

	void tearDown() {
		Track.list().each {
			it.file.delete()
		}

		super.tearDown()

		DateTimeUtils.setCurrentMillisSystem()
	}

	void testIndexReturnsTracksInLastPlayedOrder() {
		def tracksInLastPlayedOrder = tracks.sort { it.lastPlayed }
		def model = controller.index()
		assertEquals tracksInLastPlayedOrder, model.playlist
	}

	void testStreamOutputsMp3Data() {
		controller.params.id = tracks[0].filepath
		controller.stream()
		assertEquals "audio/mpeg", controller.response.contentType
		assertEquals 10, controller.response.contentLength
		assertEquals tracks[0].file.readBytes(), controller.response.contentAsByteArray
	}

	void testStreamIncrementsPlayCountAndLastPlayed() {
		controller.params.id = tracks[0].filepath
		controller.stream()
		assertEquals 2, tracks[0].playCount
		assertEquals now, tracks[0].lastPlayed
	}

}