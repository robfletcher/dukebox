package dukebox

class AlbumArtTagLib {

	static namespace = "amazon"

	def albumArtService

	def albumArt = {attrs ->
		def image = albumArtService.getAlbumArt(attrs.artist, attrs.album)
		if (!image) {
			image = [url: resource(dir: "images", file: "defaultAlbumArt.png"), width: 160, height: 160]
		}
		out << """<img src="${image.url}" width="${image.width}" height="${image.height}" class="albumart"/>"""
	}

}