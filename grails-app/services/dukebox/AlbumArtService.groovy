package dukebox

import com.amazon.advertising.api.sample.SignedRequestsHelper
import net.sf.ehcache.Element
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.apache.commons.lang.WordUtils

class AlbumArtService {

	static transactional = false

	def albumArtCache

	def getAlbumArt(String artist, String album, ImageSize size = ImageSize.MEDIUM) {
		def key = new AlbumArtKey(artist: artist, album: album, size: size)
		withCache(key) {
			def config = ConfigurationHolder.config.amazon.aws
			def signedRequestsHelper = SignedRequestsHelper.getInstance(config.endpoint, config.keys.access, config.keys.secret)
			def params = [
					Service: "AWSECommerceService",
					Version: "2009-03-31",
					Operation: "ItemSearch",
					SearchIndex: "Music",
					Artist: artist,
					Title: album,
					ResponseGroup: "Images"]
			def requestUrl = signedRequestsHelper.sign(params)
			log.debug "Making request to URL: $requestUrl"
			new URL(requestUrl).withInputStream {InputStream istream ->
				def response = new XmlSlurper().parse(istream)
				def imageNode = response.Items.Item[0]."${size}Image"
				if (imageNode.URL?.text()) {
					return [url: imageNode.URL.text(), height: imageNode.Height.text(), width: imageNode.Width.text()]
				} else {
					log.debug "No image found in response"
					return null
				}
			}
		}
	}

	private def withCache(AlbumArtKey key, Closure closure) {
		def value = albumArtCache.get(key)?.value
		if (!value) {
			log.debug "cache miss for $key"
			value = closure(key)
			albumArtCache.put(new Element(key, value))
		}
		return value
	}

}

class AlbumArtKey implements Serializable {
	String artist
	String album
	ImageSize size

	boolean equals(o) {
		if (this.is(o)) return true
		if (!(o instanceof AlbumArtKey)) return false
		return artist == o.artist && album == o.album && size == o.size
	}

	int hashCode() {
		int result = artist?.hashCode() ?: 0
		result = 31 * result + (album?.hashCode() ?: 0)
		result = 31 * result + (size?.hashCode() ?: 0)
		return result
	}

	String toString() {
		"AlbumArtKey[artist='$artist', album='$album', size='$size']"
	}
}

enum ImageSize {
	SMALL, MEDIUM, LARGE

	String toString() {
		WordUtils.capitalizeFully(name())
	}

}