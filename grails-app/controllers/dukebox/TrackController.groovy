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

	def save = {TrackUploadCommand command ->
		if (command.hasErrors()) {
			render(view: 'create', model: [command: command])
		} else {
			def trackInstance = libraryService.add(command.file.inputStream)
			flash.message = "track.created"
			flash.args = [trackInstance.title, trackInstance.artist]
			flash.defaultMessage = "$trackInstance uploaded"
			redirect(action: show, id: trackInstance.id)
		}
	}
}

class TrackUploadCommand {

	MultipartFile file

	static constraints = {
		file nullable: false, validator: {MultipartFile self ->
			if (self.empty) return false
			try {
				def istream = new BufferedInputStream(self.inputStream)
				AudioSystem.getAudioFileFormat(istream)
			} catch (UnsupportedAudioFileException e) {
				return false
			}
			return true
		}
	}
}