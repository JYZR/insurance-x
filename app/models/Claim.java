package models;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Claim extends Model {

	@Id
	public Long id;
	@Required
	@ManyToOne(optional = false)
	public Insurance insurance;
	@Required
	@NotNull
	public String damage;

	public Claim(Insurance insurance, String damage) {
		this.insurance = insurance;
		this.damage = damage;
	}

	/*
	 * Static methods
	 */

	public static Finder<Long, Claim> find = new Finder<Long, Claim>(Long.class, Claim.class);

	public static Claim fetch(Long id) {
		return find.byId(id);
	}

	public static List<Claim> all() {
		return find.all();
	}

	public static List<Claim> ofCustomer(CustomerUser customer) {
		return find.where().in("insurance", customer.insurances).findList();
	}

	public static void create(Claim claim) {
		claim.save();
	}

	public static int count() {
		return find.findRowCount();
	}

	public static int count(CustomerUser customer) {
		return ofCustomer(customer).size();
	}

}
