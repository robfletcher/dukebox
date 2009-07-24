package dukebox.auth

import dukebox.auth.Role

class User {
	static transients = ['pass']
	static hasMany = [authorities: Role]
	static belongsTo = Role

	String username
	String userRealName
	String passwd
	boolean enabled
	String email
	boolean emailShow

	/** plain password to create a MD5 password */
	String pass = '[secret]'

	static constraints = {
		username blank: false, unique: true
		userRealName blank: false
		passwd blank: false
		email blank: false, email: true
		enabled()
	}

	static mapping = {
		authorities cache: true
		cache true
	}

	boolean equals(o) {
		if (this.is(o)) return true
		if (!(o instanceof User)) return false
		return username == o.username
	}

	int hashCode() {
		return username?.hashCode() ?: 0
	}

	String toString() {
		return "User[$username]"
	}
}
