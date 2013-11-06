package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.mvc.Http.Context;

@Entity
public class Insurance extends Model {

	@Id
	public Long id;
	@Required
	@ManyToOne(optional = false)
	public CustomerUser customer;
	@Required
	@NotNull
	public InsuranceLevel level;
	@Required
	@Pattern(value = "[A-Z]{3}[0-9]{3}", message = "For example: ABC123")
	public String regNumber;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "insurance")
	public List<Claim> claims;

	public Insurance(CustomerUser customer, InsuranceLevel level, String regNumber) {
		this.customer = customer;
		this.level = level;
		this.regNumber = regNumber;
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

	public static List<Insurance> ofCustomer(CustomerUser customer) {
		return find.where().eq("customer", customer).findList();
	}

	public static void create(Insurance insurance) {
		insurance.save();
	}

	public static int count() {
		return find.findRowCount();
	}

	public static int count(CustomerUser customer) {
		return ofCustomer(customer).size();
	}

	public static Map<String, String> options() {
		HashMap<String, String> options = new HashMap<String, String>();
		for (Insurance insurance : all()) {
			options.put(insurance.id.toString(), insurance.regNumber);
		}
		return options;
	}

	public static Map<String, String> options(Context ctx) {
		if (User.isEmployee(ctx))
			return options();
		HashMap<String, String> options = new HashMap<String, String>();
		for (Insurance insurance : ofCustomer(CustomerUser.fetch(ctx))) {
			options.put(insurance.id.toString(), insurance.regNumber);
		}
		return options;
	}

	/*
	 * Insurance level enumerator
	 */

	public static enum InsuranceLevel {
		Basic, Half, Full;

		public static Map<String, String> options() {
			HashMap<String, String> options = new HashMap<String, String>();
			for (InsuranceLevel level : InsuranceLevel.values()) {
				options.put(level.toString(), level.toString());
			}
			return options;
		}
	}
}
