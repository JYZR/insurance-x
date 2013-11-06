package models;

import org.junit.*;

import com.avaje.ebean.Ebean;

import static org.junit.Assert.*;
import play.libs.Yaml;
import play.test.WithApplication;
import static play.test.Helpers.*;
import java.util.List;

public class TaskTest extends WithApplication {
	@Before
	public void setUp() {
		start(fakeApplication(inMemoryDatabase()));
	}

	@Test
	public void createAndRetrieveTask() {
		new User("Jimmy", "jimmy", "secret").save();
		Task task = new Task();
		task.label = "Buy milk";
		task.owner = User.fetch("jimmy");
		int numOfTasksBefore = Task.findTasks(User.fetch("jimmy")).size();
		task.save();
		int numOfTasksAfter = Task.findTasks(User.fetch("jimmy")).size();
		assertEquals(numOfTasksBefore, numOfTasksAfter - 1);
		task = Task.fetch(task.id);
		assertNotNull(task);
		assertEquals("Buy milk", task.label);
	}

	@Test
	public void loadAndCountTest() {
		Ebean.save((List<?>) Yaml.load("test-data.yml"));

		assertEquals(2, User.find.findRowCount());
		assertEquals(2, Task.find.findRowCount());

		List<Task> jesperTasks = Task.findTasks(User.fetch("jesper"));
		assertEquals(1, jesperTasks.size());
	}
}
