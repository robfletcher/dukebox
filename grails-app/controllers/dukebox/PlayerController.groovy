package dukebox

class PlayerController {

	def playerService

	def index = {
		render {
			html {
				head {
					title("Player")
				}
				body {
					a(href: "play", "Play")
				}
			}
		}
	}

	def play = {
		playerService.play()
		forward action: "index"
	}

}