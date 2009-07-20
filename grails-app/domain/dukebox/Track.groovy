package dukebox

import org.joda.time.DateTime
import org.joda.time.contrib.hibernate.PersistentDateTime
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class Track {

	String title
	String artist
	String album
	Integer trackNo
	Integer year

//	User uploadedBy
//	User lastModifiedBy
	DateTime dateCreated
	DateTime lastUpdated

//	User lastPlayedBy
	DateTime lastPlayed
	int playCount = 0


	// path to file on disk relative to root defined in Config.groovy
	String filepath

    static constraints = {
		title()
		artist()
		album nullable: true, blank: true
		trackNo nullable: true
		year nullable: true
		lastPlayed nullable: true
    }

	static mapping = {
		dateCreated type: PersistentDateTime
		lastUpdated type: PersistentDateTime
	}

	static transients = ["file", "inputStream"]

	File getFile() {
		def basedir = new File(ConfigurationHolder.config.library.basedir)
		def file = new File(basedir, filepath)
		return file
	}

	InputStream getInputStream() {
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
