package dukebox

import org.joda.time.DateTime
import grails.converters.JSON

class JSONTests extends GroovyTestCase {

	void testJSONRendering() {
		def track = Track.build(
				title: "This Wheel's On Fire",
				artist: "Siouxsie and the Banshees",
				trackNo: 4,
				year: 1987,
				dateCreated: new DateTime(),
				lastUpdated: new DateTime(),
				lastPlayed: new DateTime()
		)

		println track as JSON
	}

}