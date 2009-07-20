package dukebox

import dukebox.Track
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.UnsupportedAudioFileException
import org.springframework.web.multipart.MultipartFile

class TrackController {

	static scaffold = Track

	// the delete, save and update actions only accept POST requests
	static allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

	def libraryService

	def create = {
		TrackUploadCommand command = new TrackUploadCommand()
		return [command: command]
	}

	def save = { TrackUploadCommand command ->
		if (command.hasErrors()) {
			render(view: 'create', model: [command: command])
		} else {
			def trackInstance = new Track(title: command.title, artist: command.artist, album: command.album)
			trackInstance.filepath = "${UUID.randomUUID()}.mp3"
			// TODO: move to libraryService
			File libraryFile = new File(libraryService.basedir, trackInstance.filepath)
			command.file.transferTo libraryFile

			if (trackInstance.save()) {
				flash.message = "Track ${trackInstance.id} created"
				redirect(action: show, id: trackInstance.id)
			}
			else {
				render(view: 'create', model: [trackInstance: trackInstance])
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
		file nullable: false, validator: { MultipartFile self ->
			if (self.empty) return false
			try {
				def istream = new BufferedInputStream(self.inputStream)
				AudioSystem.getAudioFileFormat(istream)
			} catch (UnsupportedAudioFileException e) {
				return false
			}
			return true
		}
		title nullable: false, blank: false
		artist nullable: false, blank: false
	}
}