package dukebox

import dukebox.auth.Role
import dukebox.auth.User
import functionaltestplugin.FunctionalTestCase
import org.codehaus.groovy.grails.commons.ApplicationHolder

abstract class AbstractFunctionalTestCase extends FunctionalTestCase {

	File testFile

	void setUp() {
		super.setUp()

		def port = System.properties."server.port" ?: 8080
		baseURL = "http://localhost:${port}/dukebox"

		testFile = new File("sample.mp3")
	}

	void tearDown() {
		super.tearDown()

		Role.withTransaction {
			Role.list().each {role ->
				role.people = []
				role.save()
			}
			User.list().each {user ->
				user.delete()
			}
			Track.list().each {track ->
				track.delete()
			}
		}
	}

	void createUser() {
		def authenticateService = ApplicationHolder.application.mainContext.getBean("authenticateService")

		User.withTransaction {
			def user = new User(username: "blackbeard", userRealName: "Edward Teach", enabled: true, email: "blackbeard@energizedwork.com")
			user.passwd = authenticateService.encodePassword("password")
			user.addToAuthorities Role.findByAuthority("ROLE_USER")
			assert user.save(), user.errors
		}
	}

	void login() {
		get "/login"
		form {
			j_username = "blackbeard"
			j_password = "password"
			click "Login"
		}
	}

	void logout() {
		get "/logout"
	}

	void uploadTrack() {
		get "/track/create"
		assertTitle "Create Track"

		byId("file").valueAttribute = testFile.absolutePath
		form {
			click "Create"
		}

		assertTitle "Track Uploaded"
		assertContentContains "Fake French by Le Tigre uploaded"
	}

}