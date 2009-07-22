package dukebox

import dukebox.Track
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.UnsupportedAudioFileException
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletResponse

class TrackController {

	static scaffold = Track

	// the delete, save and update actions only accept POST requests
	static allowedMethods = [delete: 'POST', update: 'POST']

	def libraryService

	def createFlow = {
		upload {
			on("next") {TrackUploadCommand command ->
				flow.command = command
				if (command.hasErrors()) {
					return error()
				}
			}.to("createTrack")
		}
		createTrack {
			action {
				flow.trackInstance = libraryService.add(flow.command.file.inputStream)
				if (flow.trackInstance.hasErrors()) {
					failure()
				} else {
					println "id = $flow.trackInstance.id"
					flash.message = "track.created"
					flash.args = [flow.trackInstance.title, flow.trackInstance.artist]
					flash.defaultMessage = "$flow.trackInstance uploaded"
					success()
				}
			}
			on("success").to("finished")
			on("failure").to("enterDetails")
		}
		enterDetails {
			on("next") {
				flow.trackInstance.properties = params
				if (!flow.trackInstance.save()) {
					return error()
				}
				println "id = $flow.trackInstance.id"
			}.to("finished")
		}
		finished {
			redirect(controller: "track", action: "show", id: flow.trackInstance.id)
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

class TrackUploadCommand implements Serializable {

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