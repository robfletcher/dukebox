package dukebox

import org.joda.time.DateTime
import org.joda.time.contrib.hibernate.PersistentDateTime
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class Track implements Serializable {

	String title
	String artist
	String album
	String trackNo
	String year

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
		title blank: false
		artist blank: false
		album nullable: true, blank: true
		trackNo nullable: true, blank: true, matches: /^\d+$/
		year nullable: true, blank: true, matches: /^\d{4}$/
		lastPlayed nullable: true
		filepath unique: true
    }

	static mapping = {
		dateCreated type: PersistentDateTime
		lastUpdated type: PersistentDateTime
		cache true
	}

	static transients = ["file", "inputStream"]

	File getFile() {
		if (!filepath) return null
		def basedir = new File(ConfigurationHolder.config.library.basedir)
		def file = new File(basedir, filepath)
		return file
	}

	InputStream getInputStream() {
		def file = getFile()
		if (!file?.isFile()) return null
		return new FileInputStream(file)
	}

	int hashCode() {
		filepath?.hashCode() ?: 0
	}

	boolean equals(o) {
		if (this.is(o)) return true
		if (!(o instanceof Track)) return false
		return filepath == o.filepath
	}

	String toString() {
		"$title by $artist"
	}
}
