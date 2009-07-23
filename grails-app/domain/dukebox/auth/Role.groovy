package dukebox.auth

class Role {

	static hasMany = [people: User]

	String authority
	String description

	static constraints = {
		authority blank: false, unique: true
		description()
	}

	boolean equals(o) {
		if (this.is(o)) return true
		if (!o.instanceOf(Role)) return false
		return authority == o.authority
	}

	int hashCode() {
		return authority?.hashCode() ?: 0
	}

	String toString() {
		return authority
	}
}
