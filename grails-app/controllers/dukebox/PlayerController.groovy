package dukebox

class PlayerController {

	def playerService

	def beforeInterceptor = {
		log.fatal actionName
	}

	def index = {
		return [currentTrack: playerService.currentTrack]
	}

	def play = {
		playerService.play()
		redirect action: "index"
	}

	def stop = {
		playerService.stop()
		redirect action: "index"
	}

}