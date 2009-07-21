package dukebox

import dukebox.Track
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import javazoom.jl.player.advanced.AdvancedPlayer
import javazoom.jl.player.advanced.PlaybackEvent
import javazoom.jl.player.advanced.PlaybackListener
import org.apache.commons.lang.math.RandomUtils
import org.springframework.beans.factory.DisposableBean

class PlayerService implements DisposableBean {

	static transactional = false

	private final AtomicBoolean aliveFlag = new AtomicBoolean(false)
	private final AtomicReference<Track> currentTrackRef = new AtomicReference()
	private AdvancedPlayer player

	Track getCurrentTrack() { currentTrackRef.get() }

	void play() {
		aliveFlag.set(true)
		Thread.start {
			def trackCount = Track.count()
			if (trackCount == 0) {
				log.warn "nothing to play..."
				aliveFlag.set(false)
			} else {
				log.info "starting..."
				while (aliveFlag.get()) {
					def tracks = Track.list()
					log.debug "found ${tracks.size()} tracks..."
					def track = null
					use(RandomSelectionCategory) {
						track = tracks.randomElement()
						if (tracks.size() > 1) {
							while (track == currentTrack) {
								track = tracks.randomElement()
							}
						}
					}

					log.debug "playing $track..."
					createPlayer(track)
					player.play()
				}
			}

			log.info "stopping..."
			currentTrackRef.set(null)
		}
	}

	private synchronized void createPlayer(Track track) {
		player = new AdvancedPlayer(track.inputStream)
		player.playBackListener = [
				playbackStarted: {PlaybackEvent event ->
					log.debug "playback started..."
					currentTrackRef.set(track)
				},
				playbackFinished: {PlaybackEvent event ->
					log.debug "playback finished..."
					currentTrackRef.set(null)
				}
		] as PlaybackListener
	}

	void stop() {
		aliveFlag.set(false)
		try {
			player?.stop()
		} catch (Exception e) {
			log.error "Caught exception stopping playback: $e.message"
		}
	}

	boolean isAlive() { aliveFlag.get() }

	void destroy() {
		stop()
	}

}

class RandomSelectionCategory {
	static randomElement(List self) {
		if (!self || self.empty) return null
		return self[RandomUtils.nextInt(self.size())]
	}
}
