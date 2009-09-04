package dukebox

import grails.test.GrailsUnitTestCase
import net.sf.ehcache.Element

class AlbumArtServiceTests extends GrailsUnitTestCase {

	File successResponse, notFoundResponse
	def mockCacheStore
	AlbumArtService service

	void setUp() {
		super.setUp()

		successResponse = new File("test/data/amazon/success.xml")
		notFoundResponse = new File("test/data/amazon/notfound.xml")
		assert successResponse.isFile()

		registerMetaClass URL

		mockConfig '''
			amazon.aws.endpoint="localhost:8080"
			amazon.aws.keys.access="abc"
			amazon.aws.keys.secret="abc"
		'''

		mockCacheStore = [:]
		def mockCache = new Expando()
		mockCache.get = {key -> mockCacheStore[key] }
		mockCache.put = {Element element -> mockCacheStore[element.key] = element }

		mockLogging AlbumArtService, true
		service = new AlbumArtService()
		service.albumArtCache = mockCache
	}

	void testNoOpIfArtistOrAlbumNull() {
		assertNull service.getAlbumArt("Kim Carnes", null)
		assertNull service.getAlbumArt(null, "Live It Out")
	}

	void testRequestsAlbumArtFromAmazon() {
		// stub out Amazon response
		URL.metaClass.withInputStream = {Closure c ->
			successResponse.withInputStream {istream ->
				c(istream)
			}
		}

		def albumart = service.getAlbumArt("Handsome Furs", "Face Control")
		assertEquals "http://ecx.images-amazon.com/images/I/51ujdalJ8qL._SL160_.jpg", albumart.url
		assertEquals "160", albumart.width
		assertEquals "160", albumart.height
	}

	void testSubsequentRequestsAreCached() {
		// stub out Amazon response
		URL.metaClass.withInputStream = {Closure c ->
			successResponse.withInputStream {istream ->
				c(istream)
			}
		}

		service.getAlbumArt("Handsome Furs", "Face Control")

		assertEquals 1, mockCacheStore.size()

		URL.metaClass.withInputStream = {Closure c ->
			fail "2nd call to same URL should have gone to cache"
		}

		service.getAlbumArt("Handsome Furs", "Face Control")
	}

	void testReturnsNullIfNotFoundOnAmazon() {
		// stub out Amazon response
		URL.metaClass.withInputStream = {Closure c ->
			notFoundResponse.withInputStream {istream ->
				c(istream)
			}
		}

		assertNull service.getAlbumArt("Handsome Furs", "Face Control")
	}

	void testReturnsOtherSizesIfRequested() {
		// stub out Amazon response
		URL.metaClass.withInputStream = {Closure c ->
			successResponse.withInputStream {istream ->
				c(istream)
			}
		}

		def albumart = service.getAlbumArt("Handsome Furs", "Face Control", ImageSize.LARGE)
		assertEquals "http://ecx.images-amazon.com/images/I/51ujdalJ8qL.jpg", albumart.url
		assertEquals "500", albumart.width
		assertEquals "500", albumart.height
	}

	void testSizesAreCachedSeparately() {
		// stub out Amazon response
		URL.metaClass.withInputStream = {Closure c ->
			successResponse.withInputStream {istream ->
				c(istream)
			}
		}

		service.getAlbumArt("Handsome Furs", "Face Control", ImageSize.LARGE)
		service.getAlbumArt("Handsome Furs", "Face Control", ImageSize.MEDIUM)
		service.getAlbumArt("Handsome Furs", "Face Control", ImageSize.SMALL)

		assertEquals 3, mockCacheStore.size()
	}

}