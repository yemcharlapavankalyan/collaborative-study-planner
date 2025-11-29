package planner.model;

public class User {
    private String name;
    private String role; // Leader, Member, etc.

    public User(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getName() { return name; }
    public String getRole() { return role; }

    @Override
    public String toString() {
        return name + " (" + role + ")";
    }
}
