package dukebox

import dukebox.Track
import grails.converters.JSON

class PlayerController {

	def index = {
		def playlist = Track.list(sort: "lastPlayed")
		return [playlist: playlist]
	}

	def nextTrack = {
		def track = Track.list(sort: "lastPlayed", max: 1)[0]
		render track as JSON
	}

	def stream = {
		Track track = Track.findByFilepath(params.id)
		response.setContentType "audio/mpeg"
		response.setContentLength track.file.size().toInteger()
		track.file.withInputStream {istream ->
			response.outputStream << istream
		}
		track.incrementPlayCount()
	}

}