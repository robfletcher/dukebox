package dukebox

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.amazon.advertising.api.sample.SignedRequestsHelper
import org.springmodules.cache.annotations.Cacheable

class AlbumArtService {

	static transactional = false

//	@Cacheable(modelId="albumArtCachingModel")
	def getAlbumArt(String artist, String album) {
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
			def imageNode = response.Items.Item[0].MediumImage
			return [url: imageNode.URL, height: imageNode.Height, width: imageNode.Width]
		}
	}

}