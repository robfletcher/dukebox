security {

	// see DefaultSecurityConfig.groovy for all settable/overridable properties

	active = true

	loginUserDomainClass = "dukebox.auth.User"
	authorityDomainClass = "dukebox.auth.Role"

	useRequestMapDomainClass = false

	requestMapString = '''
		CONVERT_URL_TO_LOWERCASE_BEFORE_COMPARISON
		PATTERN_TYPE_APACHE_ANT
		/track/**=ROLE_USER
		/role/**=ROLE_ADMIN
		/user/**=ROLE_ADMIN
	'''
}
