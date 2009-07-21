package dukebox

import dukebox.Track
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import javazoom.jl.player.advanced.AdvancedPlayer
import javazoom.jl.player.advanced.PlaybackEvent
import javazoom.jl.player.advanced.PlaybackListener
import org.apache.commons.lang.math.RandomUtils
import org.springframework.beans.factory.DisposableBean

class PlayerService implements DisposableBean {

	static transactional = false

	private final AtomicReference<Track> currentTrackRef = new AtomicReference()
	private final AtomicReference<AdvancedPlayer> playerRef = new AtomicReference()
	private final listeners = []

	private final AtomicBoolean aliveFlag = new AtomicBoolean(false)
	private CountDownLatch aliveLatch

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
				aliveLatch = new CountDownLatch(1)
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
					currentTrackRef.set(track)
					fireListeners()

					log.debug "starting playback..."
					try {
						track.withInputStream {istream ->
							def player = new AdvancedPlayer(istream)
							if (log.isDebugEnabled()) {
								player.playBackListener = new LoggingPlaybackListener(log)
							}
							player.play()
							playerRef.set(player)
						}
					} catch (IOException e) {
						log.error "problem reading file...", e
					} finally {
						playerRef.set(null)
					}
				}
			}

			log.info "stopping..."
			currentTrackRef.set(null)
			aliveLatch.countDown()
		}
	}

	void stop() {
		aliveFlag.set(false)
		playerRef.get()?.stop()
	}

	void stopAndWait() {
		stop()
		if (aliveLatch) {
			aliveLatch.await()
			log.info "stopped..."
		}
	}

	boolean isAlive() { aliveFlag.get() }

	void destroy() {
		stop()
	}

	void addListener(TrackChangeListener listener) {
		listeners << listener
	}

	boolean removeListener(TrackChangeListener listener) {
		listeners.remove(listener)
	}

	void removeAllListeners() {
		listeners.clear()
	}

	void fireListeners() {
		listeners*.trackChanged()
	}

}

interface TrackChangeListener {
	void trackChanged()
}

class LoggingPlaybackListener extends PlaybackListener {

	private final log

	LoggingPlaybackListener(log) {
		this.log = log
	}

	void playbackStarted(PlaybackEvent evt) {
		log.debug "playback started..."
	}

	void playbackFinished(PlaybackEvent evt) {
		log.debug "playback finished..."
	}
}

class RandomSelectionCategory {
	static randomElement(List self) {
		if (!self || self.empty) return null
		return self[RandomUtils.nextInt(self.size())]
	}
}
