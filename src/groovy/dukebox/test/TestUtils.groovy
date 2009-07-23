package dukebox.test

import dukebox.auth.User
import org.springframework.security.GrantedAuthorityImpl
import org.springframework.security.GrantedAuthority
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUserImpl
import org.springframework.security.providers.UsernamePasswordAuthenticationToken
import org.springframework.security.context.SecurityContextHolder

class TestUtils {

	static void login(String username) {
		User user = User.findByUsername(username)
		assert user, "User '$username' not found"
		login user
	}

	static void login(User user) {
		def authorities = user.authorities.collect {role -> new GrantedAuthorityImpl(role.authority) } as GrantedAuthority[]
		def userDetails = new GrailsUserImpl(user.username, user.passwd, true, true, true, true, authorities, user)
		SecurityContextHolder.context.authentication = new UsernamePasswordAuthenticationToken(userDetails, user.passwd, authorities)
	}

	static void logout() {
		SecurityContextHolder.context.authentication = null
	}

}