package dukebox

import dukebox.Track

class PlayerController {

	def index = {
		def playlist = Track.list(sort: "lastPlayed")
		return [playlist: playlist]
	}

	def stream = {
		Track track = Track.findByFilepath(params.id)
		response.setContentType "audio/mpeg"
		track.file.withInputStream {istream ->
			response.outputStream << istream
		}
		track.incrementPlayCount()
	}

}