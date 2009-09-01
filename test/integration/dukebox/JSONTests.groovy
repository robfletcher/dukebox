package dukebox

import dukebox.Track
import grails.converters.JSON
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat

class JSONTests extends GroovyTestCase {

	static final JSON_DATE_TIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ")

	void testJSONRendering() {
		def track = Track.build(
				title: "This Wheel's On Fire",
				artist: "Siouxsie and the Banshees",
				album: "Through the Looking Glass",
				trackNo: 4,
				year: 1987,
				lastPlayed: new DateTime(2009, 9, 1, 9, 24, 0, 0).withZone(DateTimeZone.forID("America/Vancouver"))
		)

		def sw = new StringWriter()
		(track as JSON).render(sw)
		def jsonObject = JSON.parse(sw.toString())

		assertEquals track.title, jsonObject.title
		assertEquals track.artist, jsonObject.artist
		assertEquals track.album, jsonObject.album
		assertEquals track.trackNo, jsonObject.trackNo
		assertEquals track.year, jsonObject.year
		assertEquals track.dateCreated.toString(JSON_DATE_TIME_FORMAT), jsonObject.dateCreated
		assertEquals track.lastUpdated.toString(JSON_DATE_TIME_FORMAT), jsonObject.lastUpdated
		assertEquals track.lastPlayed.toString(JSON_DATE_TIME_FORMAT), jsonObject.lastPlayed
		assertEquals track.playCount, jsonObject.playCount
	}

}