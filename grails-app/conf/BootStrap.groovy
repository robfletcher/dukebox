import org.codehaus.groovy.grails.commons.ConfigurationHolder
import dukebox.auth.Role
import grails.util.GrailsUtil
import dukebox.auth.User
import org.springframework.web.context.support.WebApplicationContextUtils
import org.springframework.validation.Errors
import org.springframework.validation.ObjectError
import org.springframework.validation.FieldError

class BootStrap {

	def init = {servletContext ->

		Errors.metaClass.toString = {->
			def buffy = new StringBuilder()
			buffy << "$delegate.objectName: $delegate.errorCount errors"
			delegate.globalErrors.each {error ->
				buffy << "\n   $error.code"
			}
			delegate.fieldErrors.each {error ->
				buffy << "\n   $error.field = $error.code"
			}
			return buffy.toString()
		}

		def libraryBasedir = new File(ConfigurationHolder.config.library.basedir)
		libraryBasedir.mkdirs()

		[ROLE_USER: "A standard user", ROLE_ADMIN: "An administrator"].each {auth, desc ->
			def role = Role.findByAuthority(auth)
			if (!role) {
				println "Creating $auth..."
				role = new Role(authority: auth, description: desc)
				assert role.save()
			}
		}

		// TODO: remove?
		if (GrailsUtil.environment == "development") {
			def authenticateService = WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean("authenticateService")

			println "Creating default user..."
			def user = new User(username: "blackbeard", userRealName: "Edward Teach", email: "blackbeard@energizedwork.com", enabled: true)
			user.passwd = authenticateService.encodePassword("password")
			user.addToAuthorities Role.findByAuthority("ROLE_USER")
			assert user.save()
		}
	}

	def destroy = {
	}
} 