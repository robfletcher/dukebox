package dukebox

import dukebox.Track
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.UnsupportedAudioFileException
import org.springframework.web.multipart.MultipartFile
import org.cmc.music.myid3.MyID3
import org.cmc.music.metadata.MusicMetadataSet
import org.cmc.music.metadata.MusicMetadata
import org.cmc.music.metadata.IMusicMetadata

class TrackController {

	static scaffold = Track

	// the delete, save and update actions only accept POST requests
	static allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

	def libraryService

	def create = {
		TrackUploadCommand command = new TrackUploadCommand()
		return [command: command]
	}

	def save = {TrackUploadCommand command ->
		try {
			def trackInstance = libraryService.add(params.file)
			flash.message = "Track ${trackInstance.id} created"
			redirect(action: show, id: trackInstance.id)
		} catch (LibraryException e) {
			flash.error =
				render(view: 'create')
		}
	}
}
}

class TrackUploadCommand {

	MultipartFile file
	String title
	String artist
	String album

	static constraints = {
		file nullable: false, validator: {MultipartFile self ->
			if (self.empty) return false
			try {
				def istream = new BufferedInputStream(self.inputStream)
				AudioSystem.getAudioFileFormat(istream)

				File tmp = File.createTempFile(self.originalFilename, ".mp3")
				self.transferTo tmp
				IMusicMetadata metadata = new MyID3().read(tmp).simplified
				println metadata.getArtist()
				println metadata.getSongTitle()
				println metadata.getAlbum()
				println metadata.getYear()
			} catch (UnsupportedAudioFileException e) {
				return false
			}
			return true
		}
		title nullable: false, blank: false
		artist nullable: false, blank: false
	}
}