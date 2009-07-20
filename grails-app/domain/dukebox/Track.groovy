package dukebox

import org.joda.time.DateTime
import org.joda.time.contrib.hibernate.PersistentDateTime
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class Track {

	String title
	String artist
	String album
	DateTime dateCreated
	DateTime lastUpdated
	DateTime lastPlayed
	int playCount = 0

//	User uploadedBy
//	User lastModifiedBy
//	User lastPlayedBy

	// path to file on disk relative to root defined in Config.groovy
	String filepath

    static constraints = {
		title()
		artist()
		album nullable: true, blank: true
		lastPlayed nullable: true
    }

	static mapping = {
		dateCreated type: PersistentDateTime
		lastUpdated type: PersistentDateTime
	}

	static transients = ["inputStream"]

	InputStream getInputStream() {
		def basedir = new File(ConfigurationHolder.config.library.basedir)
		assert basedir.isDirectory()
		def file = new File(basedir, filepath)
		assert file.isFile()
		return new FileInputStream(file)
	}

	def withInputStream(Closure closure) {
		def istream
		try {
			istream = getInputStream()
			closure(istream)
		} finally {
			istream?.close()
		}
	}

	String toString() {
		"$title by $artist"
	}
}
