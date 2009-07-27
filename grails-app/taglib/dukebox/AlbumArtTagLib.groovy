package dukebox

class AlbumArtTagLib {

	static namespace = "amazon"

	def albumArtService

	def albumArt = { attrs ->
		def image = albumArtService.getAlbumArt(attrs.artist, attrs.album)
		out << """<img src="${image.url}" width="${image.width}" height="${image.height}" class="albumart"/>"""
	}

}