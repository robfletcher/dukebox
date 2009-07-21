package dukebox

import dukebox.PlayerService
import dukebox.Track
import dukebox.TrackChangeListener
import grails.test.GrailsUnitTestCase
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

class PlayerServiceTests extends GrailsUnitTestCase {

	PlayerService playerService

	void setUp() {
		super.setUp()

		mockConfig '''
			library.basedir = System.properties.'java.io.tmpdir'
		'''

		mockLogging(PlayerService, true) 
		playerService = new PlayerService()
	}

	void tearDown() {
		playerService.stopAndWait()
		super.tearDown()
	}

	void testPlaybackStarts() {
		def track1 = new Track(title: "Slapped Actress", artist: "The Hold Steady", filepath: randomAlphanumeric(8))
		mockDomain(Track, [track1])

		def latch = new CountDownLatch(1)
		playerService.addListener([trackChanged: {-> latch.countDown() }] as TrackChangeListener)

		playerService.play()

		assertLatchHitsZero(latch)
		assertEquals track1, playerService.currentTrack
	}

	void testPlaysRandomTracks() {
		def tracks = []
		tracks << new Track(title: "Slapped Actress", artist: "The Hold Steady", filepath: randomAlphanumeric(8))
		tracks << new Track(title: "Gimme Sympathy", artist: "Metric", filepath: randomAlphanumeric(8))
		tracks << new Track(title: "Dog Days Are Over", artist: "Florence & The Machine", filepath: randomAlphanumeric(8))
		tracks << new Track(title: "Heads Will Roll", artist: "Yeah Yeah Yeahs", filepath: randomAlphanumeric(8))
		mockDomain(Track, tracks)

		def playedTracks = [] as Set
		def latch = new CountDownLatch(2)
		playerService.addListener([trackChanged: {->
			playedTracks << playerService.currentTrack
			latch.countDown()
		}] as TrackChangeListener)

		playerService.play()

		assertLatchHitsZero(latch)
		assertTrue playedTracks.size() > 1
	}

	static void assertLatchHitsZero(CountDownLatch latch, long timeout = 3, TimeUnit timeoutUnit = TimeUnit.SECONDS) {
		assertTrue "Timed out wating for latch", latch.await(timeout, timeoutUnit)
	}
}