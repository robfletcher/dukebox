package dukebox

class PlaybackTests extends AbstractFunctionalTestCase {

	void tearDown() {
		get "/player/stop"
		super.tearDown()
	}

	void testNothingPlaying() {
		get "/player"
		assertContentContains "Nothing playing right now. 0 tracks in the database."
		click "Play"
		assertContentContains "Nothing playing right now. 0 tracks in the database."
	}

	void testPlay() {
		createUser()
		login()
		uploadTrack()

		get "/player"
		assertContentContains "Nothing playing right now. 1 tracks in the database."

		click "Play"
		assertTitle "Dukebox Player"
		refreshUntilContentContains "Now playing: Fake French by Le Tigre", 10

		Thread.sleep 10000

		click "Stop"
		assertTitle "Dukebox Player"
		refreshUntilContentContains "Nothing playing right now", 10
	}

	void refreshUntilContentContains(String expected, int retries) {
		def remaining = retries
		while (!response.contentAsString.contains(expected)) {
			if (remaining == 0) fail "Expected content '$expected' not found in $retries page refreshes"
			Thread.sleep 100
			page.refresh()
			remaining--
		}
	}

}