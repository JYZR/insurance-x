import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

import java.util.List;

import org.junit.Test;

import com.avaje.ebean.Ebean;

import play.libs.Yaml;
import play.libs.F.Callback;
import play.test.TestBrowser;

public class Acceptance1Test {

	@Test
	public void test() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), FIREFOX,
				new Callback<TestBrowser>() {
					public void invoke(TestBrowser browser) {
						browser.goTo("http://localhost:3333/login");
						assertThat(browser.pageSource()).contains("Please sign in");
					}
				});
	}

	@Test
	public void acceptanceTest1() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), FIREFOX,
				new Callback<TestBrowser>() {
					public void invoke(TestBrowser browser) {
						// Load initial data
						Ebean.save((List<?>) Yaml.load("initial-data.yml"));

						// Go to start page
						browser.goTo("http://localhost:3333/login");
						assertThat(browser.pageSource()).contains("Please sign in");

						// Login
						browser.fill("#login-username").with("kungen");
						browser.fill("#login-password").with("pwd");
						browser.submit("#login-button");
						assertThat(browser.pageSource()).contains(
								"Signed in as Carl Gustaf Bernadotte");

						// Click on 'Claims'
						browser.click("#nav-claims");

						int numClaimsBefore = browser.find("tr").size();

						// Add claims
						// Click on the first item in the list, 0 is just 'Choose registration
						// number'
						browser.find("select").find("option", 1).click();
						browser.fill("#damage").with("Hello world");
						browser.submit("#claims-button");

						// Verify that claim have been added
						int numClaimsAfter = browser.find("tr").size();
						assertEquals(1, numClaimsAfter - numClaimsBefore);
					}
				});
	}

}
