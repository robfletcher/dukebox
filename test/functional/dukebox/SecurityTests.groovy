package dukebox

import static javax.servlet.http.HttpServletResponse.*

class SecurityTests extends AbstractFunctionalTestCase {

	void setUp() {
		super.setUp()

		createUser()
		createAdmin()
	}

	void testLoggedOutMenuItems() {
		get "/player"
		assertEquals "Home", byXPath("//div[@class='nav']/ul/li[1]/a")?.textContent?.trim()
		assertEquals "Player", byXPath("//div[@class='nav']/ul/li[2]/a")?.textContent?.trim()
		assertEquals "Login", byXPath("//div[@class='nav']/ul/li[3]/a")?.textContent?.trim()
		assertEquals "Register", byXPath("//div[@class='nav']/ul/li[4]/a")?.textContent?.trim()
		assertNull byXPath("//div[@class='nav']/ul/li[5]/a")
	}

	void testLoggedInMenuItems() {
		login()
		get "/player"
		assertEquals "Home", byXPath("//div[@class='nav']/ul/li[1]/a")?.textContent?.trim()
		assertEquals "Player", byXPath("//div[@class='nav']/ul/li[2]/a")?.textContent?.trim()
		assertEquals "Track", byXPath("//div[@class='nav']/ul/li[3]/a")?.textContent?.trim()
		assertEquals "Logout", byXPath("//div[@class='nav']/ul/li[4]/a")?.textContent?.trim()
		assertNull byXPath("//div[@class='nav']/ul/li[5]/a")
	}

	void testAdminMenuItems() {
		adminLogin()
		get "/player"
		assertEquals "Home", byXPath("//div[@class='nav']/ul/li[1]/a")?.textContent?.trim()
		assertEquals "Player", byXPath("//div[@class='nav']/ul/li[2]/a")?.textContent?.trim()
		assertEquals "User", byXPath("//div[@class='nav']/ul/li[3]/a")?.textContent?.trim()
		assertEquals "Logout", byXPath("//div[@class='nav']/ul/li[4]/a")?.textContent?.trim()
		assertNull byXPath("//div[@class='nav']/ul/li[5]/a")
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