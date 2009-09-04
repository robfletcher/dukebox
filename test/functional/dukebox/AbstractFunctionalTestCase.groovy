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

		testFile = new File("test/data/mp3/sample.mp3")
	}

	void tearDown() {
		get "/logout"

		super.tearDown()

		Role.withTransaction {
			Track.list().each {track ->
				track.delete()
			}
			Role.list().each {role ->
				role.people = []
				role.save()
			}
			User.list().each {user ->
				user.delete()
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

	void createAdmin() {
		def authenticateService = ApplicationHolder.application.mainContext.getBean("authenticateService")

		User.withTransaction {
			def user = new User(username: "admin", userRealName: "System Administrator", enabled: true, email: "admin@energizedwork.com")
			user.passwd = authenticateService.encodePassword("password")
			user.addToAuthorities Role.findByAuthority("ROLE_ADMIN")
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

	void adminLogin() {
		get "/login"
		form {
			j_username = "admin"
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

		assertTitle "Fake French by Le Tigre"
		assertContentContains "Fake French by Le Tigre uploaded"
	}

}