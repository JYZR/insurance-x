import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.FIREFOX;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import java.util.List;

import org.junit.Test;

import play.libs.Yaml;
import play.libs.F.Callback;
import play.test.*;

import com.avaje.ebean.Ebean;

public class Acceptance2Test {
	@Test
	public void acceptanceTest2() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), FIREFOX,
				new Callback<TestBrowser>() {
					public void invoke(TestBrowser browser) throws InterruptedException {
						// Load initial data
						Ebean.save((List<?>) Yaml.load("initial-data.yml"));

						// Go to start page
						browser.goTo("http://localhost:3333/login");
						assertThat(browser.pageSource()).contains("Please sign in");

						// Login as employee
						browser.fill("#login-username").with("jimmy");
						browser.fill("#login-password").with("pwd");
						browser.submit("#login-button");
						assertThat(browser.pageSource()).contains("Signed in as Jimmy");

						// Click on 'Customers'
						browser.click("#nav-customers");
						browser.await();

						int numCustomersBefore = browser.find("tr").size();

						// Add customer
						browser.fill("#name").with("John Doe");
						browser.fill("#phone").with("3141592653");
						browser.fill("#username").with("john");
						browser.fill("#password").with("pwd");
						browser.submit("#customers-button");

						int numCustomersAfter = browser.find("tr").size();

						// Verify that the customer have been added
						assertEquals(1, numCustomersAfter - numCustomersBefore);

						// Click on 'Insurances'
						browser.click("#nav-insurances");
						browser.await();
						

						int numInsurancesBefore = browser.find("tr").size();

						browser.fill("#regNumber").with("CAF384");
						// Last added customer will be first
						browser.find("select").find("option", 1).click();
						browser.find("#level_Full").click();
						browser.submit("#insurances-button");

						int numInsurancesAfter = browser.find("tr").size();

						// Verify that the insurances have been added
						assertEquals(1, numInsurancesAfter - numInsurancesBefore);
					}
				});
	}
}
