package dukebox

import dukebox.Track
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.UnsupportedAudioFileException
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletResponse

class TrackController {

	static scaffold = Track

	// the delete, save and update actions only accept POST requests
	static allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

	def libraryService

	def createFlow = {
		upload {
			on("next") {TrackUploadCommand command ->
				println "upload next..."
				if (command.hasErrors()) {
					println "upload validation failed..."
					return error()
				}
				flow.file = File.createTempFile("upload", ".mp3")
				println "created temp file $flow.file.absolutePath"
				command.file.transferTo flow.file
			}.to("createTrack")
		}
		createTrack {
			action {
				println "createTrack action..."
				flow.file.withInputStream {istream ->
					def track = libraryService.add(istream)
					if (track.hasErrors()) {
						failure()
					} else {
						flow.trackId = track.id
						flash.message = "track.created"
						flash.args = [track.title, track.artist]
						flash.defaultMessage = "$track uploaded"
						success()
					}
				}
			}
			on("success") {
				println "createTrack success..."
			}.to("finished")
			on("failure") {
				println "createTrack failure..."
			}.to("upload")
		}
		finished {
			redirect(controller: "track", action: "show", id: flow.trackId)
		}
	}

	def save = {
		log.error "Should not be here..."
		response.sendError HttpServletResponse.SC_BAD_REQUEST
	}
//	def save = {TrackUploadCommand command ->
//		if (command.hasErrors()) {
//			render(view: 'create', model: [command: command])
//		} else {
//			def trackInstance = libraryService.add(command.file.inputStream)
//			flash.message = "track.created"
//			flash.args = [trackInstance.title, trackInstance.artist]
//			flash.defaultMessage = "$trackInstance uploaded"
//			redirect(action: show, id: trackInstance.id)
//		}
//	}
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