package dukebox

import dukebox.Track
import javax.servlet.http.HttpServletResponse
import org.apache.commons.io.FileUtils
import org.springframework.web.multipart.MultipartFile
import javax.sound.sampled.UnsupportedAudioFileException
import javax.sound.sampled.AudioSystem

class TrackController {

	static scaffold = Track

	// the delete, save and update actions only accept POST requests
	static allowedMethods = [delete: 'POST', update: 'POST']

	def libraryService

	def createFlow = {
		upload {
			on("next") {TrackUploadCommand command ->
				if (command.hasErrors()) {
					log.debug "upload error: ${command.errors.allErrors.collect { "$it.field: $it.code" } }"
					flow.command = command
					return error()
				} else {
					flow.tempfile = session.createTempFile("upload", ".mp3")
					command.file.transferTo flow.tempfile
				}
			}.to("createTrack")
		}
		createTrack {
			action {
				flow.trackInstance = libraryService.add(flow.tempfile.newInputStream())
				if (flow.trackInstance.hasErrors()) {
					log.debug "createTrack failure - ${flow.trackInstance.errors.allErrors.collect { it.field + ":" + it.code} }"
					failure()
				} else {
					log.debug "createTrack success"
					success()
				}
			}
			on("success").to("confirmUpload")
			on("failure").to("enterDetails")
		}
		enterDetails {
			on("next") {
				log.debug "enterDetails form submitted"
				flow.trackInstance.properties = params
				if (!flow.trackInstance.save()) {
					log.debug "enterDetails track invalid"
					return error()
				}
				use(FileUtils) {
					flow.tempfile.copyFile flow.trackInstance.file
				}
			}.to("confirmUpload")
		}
		confirmUpload() {
			action {
				log.debug "Everything ok"
				flash.message = "track.created"
				flash.args = [flow.trackInstance.title, flow.trackInstance.artist]
				flash.defaultMessage = "$flow.trackInstance uploaded"
				flow.tempfile.delete()
				success()
			}
			on("success").to("showTrack")
		}
		showTrack()
	}

	def save = {
		log.error "Should not be here..."
		response.sendError HttpServletResponse.SC_BAD_REQUEST
	}

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