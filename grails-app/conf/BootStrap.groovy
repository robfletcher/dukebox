import org.codehaus.groovy.grails.commons.ConfigurationHolder

class BootStrap {

	def init = {servletContext ->
		def libraryBasedir = new File(ConfigurationHolder.config.library.basedir)
		libraryBasedir.mkdirs()
	}

	def destroy = {
	}
} 