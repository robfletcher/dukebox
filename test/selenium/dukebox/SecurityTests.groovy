package dukebox

class SecurityTests extends BaseSeleneseTestCase {

	void setUp() {
		super.setUp()
		createUser()
		createAdmin()
	}

	void testLoggedOutMenuItems() {
		selenium.open "$rootURL/player"
		assertEquals "Home", selenium.getText("//div[@class='nav']/ul/li[1]/a")
		assertEquals "Player", selenium.getText("//div[@class='nav']/ul/li[2]/a")
		assertEquals "Login", selenium.getText("//div[@class='nav']/ul/li[3]/a")
		assertEquals "Register", selenium.getText("//div[@class='nav']/ul/li[4]/a")
		assertFalse selenium.isElementPresent("//div[@class='nav']/ul/li[5]/a")
	}

	void testLoggedInMenuItems() {
		login()
		selenium.open "$rootURL/player"
		assertEquals "Home", selenium.getText("//div[@class='nav']/ul/li[1]/a")
		assertEquals "Player", selenium.getText("//div[@class='nav']/ul/li[2]/a")
		assertEquals "Track", selenium.getText("//div[@class='nav']/ul/li[3]/a")
		assertEquals "Logout", selenium.getText("//div[@class='nav']/ul/li[4]/a")
		assertFalse selenium.isElementPresent("//div[@class='nav']/ul/li[5]/a")
	}

	void testAdminMenuItems() {
		adminLogin()
		selenium.open "$rootURL/player"
		assertEquals "Home", selenium.getText("//div[@class='nav']/ul/li[1]/a")
		assertEquals "Player", selenium.getText("//div[@class='nav']/ul/li[2]/a")
		assertEquals "User", selenium.getText("//div[@class='nav']/ul/li[3]/a")
		assertEquals "Logout", selenium.getText("//div[@class='nav']/ul/li[4]/a")
		assertFalse selenium.isElementPresent("//div[@class='nav']/ul/li[5]/a")
	}

	void testCannotAccessUserAdminPagesWhenNotLoggedIn() {
		selenium.open "$rootURL/user"
		assertEquals "Login", selenium.getTitle()
		selenium.open "$rootURL/role"
		assertEquals "Login", selenium.getTitle()
	}

	void testCannotAccessUserAdminPagesWhenNotAdmin() {
		login()
		selenium.open "$rootURL/user"
		assertEquals "Denied", selenium.getTitle()
		selenium.open "$rootURL/role"
		assertEquals "Denied", selenium.getTitle()
	}

	void testUserCanRegister() {
		selenium.open "$rootURL/register"

		selenium.type "username", "roundhouse"
		selenium.type "userRealName", "Chuck Norris"
		selenium.type "passwd", "password"
		selenium.type "repasswd", "password"
		selenium.type "email", "roundhouse@energizedwork.com"
		selenium.type "captcha", "CAPTCHA"
		selenium.clickAndWait "css=.buttons input[type='submit']"
		assertEquals "Welcome to Dukebox", selenium.getTitle()

		selenium.open "$rootURL/track"
		assertEquals "Track List", selenium.getTitle()

		selenium.open "$rootURL/logout"
		selenium.open "$rootURL/track"
		assertEquals "Login", selenium.getTitle()

		// ensure registered user can log in again
		selenium.type "j_username", "roundhouse"
		selenium.type "j_password", "password"
		selenium.clickAndWait "css=#loginForm input[type='submit']"

		selenium.open "$rootURL/track"
		assertEquals "Track List", selenium.getTitle()
	}

}