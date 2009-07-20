package dukebox

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class LibraryService {

	static transactional = false

	File getBasedir() {
		new File(ConfigurationHolder.config.library.basedir)
	}

}