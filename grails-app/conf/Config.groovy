// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [html: ['text/html', 'application/xhtml+xml'],
		xml: ['text/xml', 'application/xml'],
		text: 'text/plain',
		js: 'text/javascript',
		rss: 'application/rss+xml',
		atom: 'application/atom+xml',
		css: 'text/css',
		csv: 'text/csv',
		all: '*/*',
		json: ['application/json', 'text/json'],
		form: 'application/x-www-form-urlencoded',
		multipartForm: 'multipart/form-data'
]
// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true

// set per-environment serverURL stem for creating absolute links
environments {
	production {
		grails.serverURL = "http://www.changeme.com"
	}
	development {
		grails.serverURL = "http://localhost:8080/${appName}"
		jawr.debug.on = true
	}
	test {
		grails.serverURL = "http://localhost:8080/${appName}"
		captcha.disabled = true
	}

}

// log4j configuration
log4j = {
	// Example of changing the log pattern for the default console
	// appender:
	//
	//appenders {
	//    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
	//}

	error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
			'org.codehaus.groovy.grails.web.pages', //  GSP
			'org.codehaus.groovy.grails.web.sitemesh', //  layouts
			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			'org.codehaus.groovy.grails.web.mapping', // URL mapping
			'org.codehaus.groovy.grails.commons', // core / classloading
			'org.codehaus.groovy.grails.plugins', // plugins
			'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
			'org.springframework',
			'org.hibernate'

	warn 'org.mortbay.log'

	debug 'grails.app.service.dukebox.PlayerService',
			'grails.app.service.dukebox.AlbumArtService',
			'dukebox.Player'
}

jawr {
	js {
		bundle.names = 'dukebox,jquery'
		bundle {
			dukebox.id = '/bundles/dukebox.js'
			dukebox.mappings = '/js/application.js'
		}
		bundle {
			jquery.id = '/bundles/jquery.js'
			jquery.mappings = '/js/jquery/jquery-1.3.2.js'
		}
	}
	css {
		bundle.names = 'dukebox'
		bundle.dukebox.id = '/bundles/dukebox.css'
		bundle.dukebox.mappings = '/css/**'
	}
}

springcache {
	provider = "ehcache"
	cachingModels {
		albumArtCachingModel = "cacheName=ALBUM_ART_CACHE"
	}
}

amazon {
	aws {
		endpoint = 'ecs.amazonaws.co.uk'
		keys {
			access = 'AKIAICJEHIOQKWTPDEBA'
			secret = '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'
		}
	}
}

library.basedir = "${System.properties.'java.io.tmpdir'}/dukebox/library"

//log4j.logger.org.springframework.security='off,stdout'
