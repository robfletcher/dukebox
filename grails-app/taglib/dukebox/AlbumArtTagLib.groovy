package dukebox

class AlbumArtTagLib {

	static namespace = "amazon"

	def albumArtService

	def albumArt = {attrs ->
		def size = attrs.size ? ImageSize.valueOf(attrs.size) : ImageSize.MEDIUM
		def artist = attrs.track?.artist ?: attrs.artist
		def album = attrs.track?.album ?: attrs.album

		def image = albumArtService.getAlbumArt(artist, album, size)

		if (!image) {
			image = [url: resource(dir: "images", file: "defaultAlbumArt.png"), width: 160, height: 160]
		}
		out << """<img src="${image.url}" width="${image.width}" height="${image.height}" class="albumart"/>"""
	}

	def albumArtCell = {attrs ->
		def trackList = attrs.trackList
		def index = attrs.index

		def track = trackList[index]
		def previous = index == 0 ? null : trackList[index - 1]
		if (!previous || track.artist != previous.artist || track.album != previous.album) {
			def rowsToSpan = 1
			def done = false
			for (def i = index + 1; !done && i < trackList.size(); i++) {
				if (track.artist == trackList[i].artist && track.album == trackList[i].album) {
					rowsToSpan++
				} else {
					done = true
				}
			}

			out << "<td"
			if (rowsToSpan > 1) out << """ rowspan="$rowsToSpan" """
			out << ">"
			out << albumArt(track: track, size: "SMALL")
			out << "</td>"
		}
	}
}