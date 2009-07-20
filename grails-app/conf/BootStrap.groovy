import org.codehaus.groovy.grails.commons.ConfigurationHolder
import dukebox.auth.Role

class BootStrap {

	def init = {servletContext ->
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
	}

	def destroy = {
	}
} 