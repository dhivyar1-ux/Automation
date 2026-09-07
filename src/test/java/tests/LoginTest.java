package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.LoginModalPage;
import pages.LoginPage;
import pages.Dashboard.Users.DashboardPage;
import utils.DataStore;
import utils.PageFactory;

public class LoginTest extends BaseTest {
        private LoginPage login;
        private DashboardPage dashboard;
        private LoginModalPage loginModalPage;
        SoftAssert softAssert = new SoftAssert();

        private PageFactory pf;

        @BeforeClass
        public void setUpPages() {
                pf = new PageFactory();
                login = pf.loginPage();
                dashboard = pf.dashboardPage();
                loginModalPage = pf.loginModalPage();
        }

        @Test
        @Parameters({ "username", "password" })
        public void verifyLoggedInUser(@Optional("admin") String username, @Optional("admin") String password) {

                login.login(username, password);

                // Check #1 – Welcome banner
                softAssert.assertEquals(dashboard.getWelcomeUsername(),username,"Welcome banner username mismatch!");

                // Check #2 – Account menu
                softAssert.assertEquals(dashboard.getAccountMenuUsername(),username,"Account menu username mismatch!");
        }

        @Test(dependsOnMethods = "verifyLoggedInUser")
        public void verifyLogout() {

                dashboard.logout();

                softAssert.assertEquals(loginModalPage.getSignInText(), "Sign in","Sign in text not displayed after logout");
                
                // IMPORTANT: Collect results
                softAssert.assertAll();
        }
}
