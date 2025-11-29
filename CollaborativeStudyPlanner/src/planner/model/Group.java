package planner.model;

import java.util.*;

public class Group {
    private String name;
    private List<User> members = new ArrayList<>();

    public Group(String name) { this.name = name; }

    public void addMember(User u) { members.add(u); }
    public List<User> getMembers() { return members; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name + " " + members.toString();
    }
}
