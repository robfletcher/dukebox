package dukebox

import dukebox.Track
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.UnsupportedAudioFileException
import org.cmc.music.myid3.MyID3
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.web.multipart.MultipartFile
import org.springframework.context.MessageSourceResolvable

class LibraryService {

	static transactional = false

	File getBasedir() {
		new File(ConfigurationHolder.config.library.basedir)
	}

	Track add(MultipartFile multipartFile) {
		if (multipartFile.empty) throw new LibraryException("File is empty")
		def istream = new BufferedInputStream(multipartFile.inputStream)
		try {
			AudioSystem.getAudioFileFormat(istream)
		} catch (UnsupportedAudioFileException e) {
			throw new LibraryException("File is invalid", e)
		} finally {
			istream.close()
		}

		Track.withTransaction {
			def track = new Track()
			track.filepath = "${UUID.randomUUID()}.mp3"

			def file = new File(basedir, track.filepath)
			multipartFile.transferTo file

			def metadata = new MyID3().read(file).simplified
			track.title = metadata.getSongTitle()
			track.artist = metadata.getArtist()
			track.album = metadata.getAlbum()
			track.trackNo = metadata.getTrackNumberNumeric()
			track.year = metadata.getYear()

			track.save()
			return track
		}
	}

}

class LibraryException extends RuntimeException {

	LibraryException(String message) {
		super(defaultMessage)
	}

	LibraryException(String message, Throwable cause) {
		super(message, cause)
	}

}
