package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import models.Insurance.InsuranceLevel;

import org.junit.Before;
import org.junit.Test;

import play.test.WithApplication;

public class ClaimTest extends WithApplication {

	CustomerUser bobby;

	@Before
	public void setUp() {
		start(fakeApplication(inMemoryDatabase()));
		new CustomerUser("Bobby", "12345", "bobby", "pwd").save();
	}

	public void CreateAndfetch() {
		bobby = CustomerUser.fetch("bobby");
		Insurance insurance1 = new Insurance(bobby, InsuranceLevel.Basic, "AAA111");
		insurance1.save();

		String damage = "Bad bad accident, the car exploded!";
		new Claim(insurance1, damage).save();
		Claim claim = insurance1.claims.get(0);
		assertNotNull(claim);
		assertEquals(claim.damage, damage);
	}

	@Test
	public void createAndCount() {
		bobby = CustomerUser.fetch("bobby");
		Insurance insurance1 = new Insurance(bobby, InsuranceLevel.Basic, "AAA111");
		insurance1.save();
		Insurance insurance2 = new Insurance(bobby, InsuranceLevel.Full, "ZZZ999");
		insurance2.save();

		String damage1 = "Bad bad accident, the car exploded!";
		String damage2 = "The mafia took my car!";
		String damage3 = "Found my car in the pool this morning, don't know what happened yesterday...";

		insurance1.claims.add(new Claim(insurance1, damage1));
		insurance1.save();

		new Claim(insurance2, damage2).save();
		new Claim(insurance2, damage3).save();

		insurance2 = bobby.insurances.get(1);

		assertEquals(1, insurance1.claims.size());
		assertEquals(2, insurance2.claims.size());
		assertEquals(3, Claim.count(bobby));
	}

	@Test
	public void createAndCountAfterRemovalOfUser() {
		bobby = CustomerUser.fetch("bobby");
		Insurance insurance1 = new Insurance(bobby, InsuranceLevel.Basic, "AAA111");
		insurance1.save();
		Insurance insurance2 = new Insurance(bobby, InsuranceLevel.Full, "ZZZ999");
		insurance2.save();

		String damage1 = "Bad bad accident, the car exploded!";
		String damage2 = "The mafia took my car!";
		String damage3 = "Found my car in the pool this morning, don't know what happened yesterday...";

		insurance1.claims.add(new Claim(insurance1, damage1));
		insurance1.save();

		new Claim(insurance2, damage2).save();
		new Claim(insurance2, damage3).save();

		bobby.insurances.get(1).delete();

		assertEquals(1, Claim.count(bobby));
	}

}
