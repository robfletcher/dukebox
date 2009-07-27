import org.springframework.cache.ehcache.EhCacheFactoryBean

beans = {

	albumArtCache(EhCacheFactoryBean) {
		timeToLive = 300
	}

}