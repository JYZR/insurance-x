package models;

import org.junit.*;

import com.avaje.ebean.Ebean;

import static org.junit.Assert.*;
import play.libs.Yaml;
import play.test.WithApplication;
import static play.test.Helpers.*;
import java.util.List;


public class TasksAndProjectsTest extends WithApplication {
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
    public void findProjectsInvolving() {
        new User("Bobby", "bobby", "secret").save();
        new User("Janny", "janny", "secret").save();

        Project.create("Play 2", "bobby");
        Project.create("Play 1", "janny");

        List<Project> results = Project.findInvolving("bobby");
        assertEquals(1, results.size());
        assertEquals("Play 2", results.get(0).name);
    }

    @Test
    public void loadYamlAndCountTasks() {
        Ebean.save((List<?>) Yaml.load("test-data.yml"));
        assertEquals(2, Task.find.findRowCount());
    }

    @Test
    public void loadYamlAndCountProjects() {
        Ebean.save((List<?>) Yaml.load("test-data.yml"));
        assertEquals(1, Project.find.findRowCount());
    }

    @Test
    public void loadYamlAndCountMembersInProject() {
        Ebean.save((List<?>) Yaml.load("test-data.yml"));
        List<Project> jimmysProjects = Project.findInvolving("jimmy");
        assertEquals(2, jimmysProjects.get(0).members.size());
    }

    @Test
    public void fullTest() {
        Ebean.save((List<?>) Yaml.load("test-data.yml"));

        // Count things
        assertEquals(2, User.find.findRowCount());
        assertEquals(1, Project.find.findRowCount());
        assertEquals(2, Task.find.findRowCount());

        // Find all Jimmy's projects
        List<Project> jimmysProjects = Project.findInvolving("jimmy");
        assertEquals(1, jimmysProjects.size());

        // Find all Jeppe's todo tasks
        List<Task> jeppesTasks = Task.findTasks(User.fetch("jeppe"));
        assertEquals(1, jeppesTasks.size());
    }
}