package dukebox

import dukebox.auth.Role
import dukebox.auth.User
import org.codehaus.groovy.grails.commons.ApplicationHolder
import grails.plugins.selenium.GrailsSeleneseTestCase

abstract class BaseSeleneseTestCase extends GrailsSeleneseTestCase {

	void tearDown() {
		selenium.open "$rootURL/logout"
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
		selenium.open "$rootURL/login"
		selenium.type "j_username", "blackbeard"
		selenium.type "j_password", "password"
		selenium.clickAndWait "css=#loginForm input[type='submit']"
	}

	void adminLogin() {
		selenium.open "$rootURL/login"
		selenium.type "j_username", "admin"
		selenium.type "j_password", "password"
		selenium.clickAndWait "css=#loginForm input[type='submit']"
	}

	void logout() {
		selenium.open "$rootURL/logout"
	}

}
