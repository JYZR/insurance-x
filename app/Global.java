import play.*;
import play.libs.*;
import com.avaje.ebean.Ebean;
import models.*;
import java.util.*;

public class Global extends GlobalSettings {

	public void onStart(Application app) {
		// If there yet are no users
		if (User.find.findRowCount() == 0) {
			Ebean.save((List<?>) Yaml.load("initial-data.yml"));
		}
	}
}
