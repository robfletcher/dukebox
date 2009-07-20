package dukebox

import dukebox.auth.Role
import dukebox.auth.User
import functionaltestplugin.FunctionalTestCase
import org.codehaus.groovy.grails.commons.ApplicationHolder

abstract class AbstractFunctionalTestCase extends FunctionalTestCase {

	void setUp() {
		super.setUp()

		def port = System.properties."server.port" ?: 8080
		baseURL = "http://localhost:${port}/dukebox"
	}

	void tearDown() {
		super.tearDown()

		Role.withTransaction {
			Role.list().each {role ->
				role.people.each {
					role.removeFromPeople it
					it.delete()
				}
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
}