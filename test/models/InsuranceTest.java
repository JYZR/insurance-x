package models;

import play.test.*;
import static play.test.Helpers.*;
import models.Insurance.InsuranceLevel;

import org.junit.*;

import static org.junit.Assert.*;

public class InsuranceTest extends WithApplication {
	@Before
	public void setUp() {
		start(fakeApplication(inMemoryDatabase()));
	}

	@Test
	public void createAndFetch() {
		new CustomerUser("Bobby", "12345", "bobby", "pwd").save();
		CustomerUser bobby = CustomerUser.fetch("bobby");
		Insurance in1 = new Insurance(bobby, InsuranceLevel.Basic);
		in1.save();
		Insurance in2 = Insurance.fetch(in1.id);
		assertNotNull(in2);
		assertEquals(in2.customer, bobby);
		assertEquals(in2.level, InsuranceLevel.Basic);
	}

	@Test
	public void createAndCount() {
		new CustomerUser("Bobby", "12345", "bobby", "pwd").save();
		CustomerUser bobby = CustomerUser.fetch("bobby");
		new Insurance(bobby, InsuranceLevel.Basic).save();
		new Insurance(bobby, InsuranceLevel.Full).save();
		assertEquals(2, Insurance.all().size());
	}

	@Test
	public void createAndCountAfterRemovalOfUser() {
		new CustomerUser("Bobby", "12345", "bobby", "pwd").save();
		CustomerUser bobby = CustomerUser.fetch("bobby");
		new Insurance(bobby, InsuranceLevel.Basic).save();
		new Insurance(bobby, InsuranceLevel.Full).save();
		bobby.delete();
		assertEquals(0, Insurance.all().size());
	}
}
