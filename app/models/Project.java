package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;

@Entity
public class Project extends Model {

    @Id
    public Long id;
    public String name;
    // This will also remove users when a project is removed, not smart!
    @ManyToMany(cascade = CascadeType.REMOVE)
    public List<User> members = new ArrayList<User>();

    public static Finder<Long,Project> find = new Finder<Long, Project>(Long.class, Project.class);

    public Project(String name, User member) {
        this.name = name;
        this.members.add(member);
    }

    public static Project create(String name, String member_username) {
        Project project = new Project(name, User.find.ref(member_username));
        project.save();
        project.saveManyToManyAssociations("members");
        return project;
    }

    public static List<Project> findInvolving(String member_username) {
        return find.where()
            .eq("members.username", member_username)
            .findList();
    }
}
