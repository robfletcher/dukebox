package dukebox

import static javax.servlet.http.HttpServletResponse.*

class SecurityTests extends AbstractFunctionalTestCase {

	void setUp() {
		super.setUp()

		createUser()
	}

	void testCannotAccessUserAdminPagesWhenNotLoggedIn() {
		get "/user"
		assertTitle "Login"
		get "/role"
		assertTitle "Login"
	}

	void testCannotAccessUserAdminPagesWhenNotAdmin() {
		login()
		get "/user"
		assertTitle "Denied"
		get "/role"
		assertTitle "Denied"
	}

	void testUserCanRegister() {
		get "/captcha"
		get "/register"
		form {
			username = "roundhouse"
			userRealName = "Chuck Norris"
			passwd = "password"
			repasswd = "password"
			email = "roundhouse@energizedwork.com"
			captcha = "CAPTCHA"
			click "Create"
		}
		assertTitle "Welcome to Dukebox"

		get "/track"
		assertTitle "Track List"

		get "/logout"
		get "/track"
		assertTitle "Login"

		form {
			j_username = "roundhouse"
			j_password = "password"
			click "Login"
		}
		get "/track"
		assertTitle "Track List"
	}

}