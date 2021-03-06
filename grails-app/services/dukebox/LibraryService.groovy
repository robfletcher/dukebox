package dukebox

import dukebox.Track
import org.apache.commons.io.IOUtils
import org.cmc.music.myid3.MyID3
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class LibraryService {

	static transactional = false

	File getBasedir() {
		new File(ConfigurationHolder.config.library.basedir)
	}

	Track add(InputStream istream) {
		istream = new BufferedInputStream(istream)
		Track.withTransaction {
			def track = new Track()
			track.filepath = "${UUID.randomUUID()}.mp3"

			def file = new File(basedir, track.filepath)
			file.withOutputStream { ostream ->
				use(IOUtils) {
					istream.copy(ostream)
				}
			}

			def metadata = new MyID3().read(file).simplified
			track.title = metadata.getSongTitle()
			track.artist = metadata.getArtist()
			track.album = metadata.getAlbum()
			track.trackNo = metadata.getTrackNumberNumeric()
			track.year = metadata.getYear()

			if (!track.save()) {
				file.delete()
			}
			return track
		}
	}

}
