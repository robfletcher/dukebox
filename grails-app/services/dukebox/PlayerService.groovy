package dukebox

import javax.sound.sampled.AudioSystem

import javazoom.jl.player.advanced.AdvancedPlayer
import javazoom.jl.player.advanced.PlaybackEvent
import javazoom.jl.player.advanced.PlaybackListener

class PlayerService {

	static transactional = false

	void play() {
		Thread.start {
			def tracks = Track.list()
			tracks.each {track ->
				println "Playing $track"
				track.withInputStream {istream ->
					def player = new AdvancedPlayer(istream)
					player.playBackListener = new PlaybackListenerImpl()
					player.play()
				}
			}
		}
	}

}

class PlaybackListenerImpl extends PlaybackListener {

	void playbackStarted(PlaybackEvent evt) {
		println "Playback started"
	}

	void playbackFinished(PlaybackEvent evt) {
		println "Playback finished"
	}

}