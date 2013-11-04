package models;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import play.db.ebean.Model;

@Entity
public class Insurance extends Model {

	@Id
	public Long id;
	@ManyToOne(optional = false)
	public CustomerUser customer;
	@NotNull
	public InsuranceLevel level;

	public Insurance(CustomerUser customer, InsuranceLevel level) {
		this.customer = customer;
		this.level = level;
	}

	/*
	 * Static methods
	 */

	public static Finder<Long, Insurance> find = new Finder<Long, Insurance>(Long.class,
			Insurance.class);

	public static Insurance fetch(Long id) {
		return find.byId(id);
	}
	
	public static List<Insurance> all() {
		return find.all();
	}

	public static void create(Insurance insurance) {
		insurance.save();
	}

	public static enum InsuranceLevel {
		Basic, Half, Full;
	}
}
