package planner;

import planner.model.*;
import planner.util.*;
import planner.tracker.*;

import java.time.*;
import java.util.*;

public class Menu {
    private List<StudyTask> tasks = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<StudySession> sessions = new ArrayList<>();
    private ConflictDetector conflictDetector = new ConflictDetector();
    private ProgressTracker tracker = new ProgressTracker();
    private Analytics analytics = new Analytics();

    private Scanner sc = new Scanner(System.in);

    public void showMainMenu() {
        loop: while (true) {
            System.out.println("\n=== COLLABORATIVE STUDY PLANNER ===");
            System.out.println("1. Add User");
            System.out.println("2. Create/Edit Group");
            System.out.println("3. Add Study Task");
            System.out.println("4. Schedule Study Session");
            System.out.println("5. Log Revision/Progress");
            System.out.println("6. Show Analytics");
            System.out.println("7. Detect Conflicts");
            System.out.println("0. Exit");
            System.out.print("Select: ");
            int ch = Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 1: addUser(); break;
                case 2: createOrEditGroup(); break;
                case 3: addTask(); break;
                case 4: scheduleSession(); break;
                case 5: logRevision(); break;
                case 6: showAnalytics(); break;
                case 7: detectConflicts(); break;
                case 0: break loop;
                default: System.out.println("Invalid!"); break;
            }
        }
    }

    private void addUser() {
        System.out.print("User name: ");
        String name = sc.nextLine();
        System.out.print("Role (Leader/Member): ");
        String role = sc.nextLine();
        users.add(new User(name, role));
    }

    private void createOrEditGroup() {
        System.out.print("New group name: ");
        String name = sc.nextLine();
        Group group = new Group(name);
        System.out.println("Add group members (names, comma separated): ");
        String[] names = sc.nextLine().split(",");
        for (String uname : names) {
            for (User u : users) {
                if (u.getName().equalsIgnoreCase(uname.trim())) {
                    group.addMember(u);
                }
            }
        }
        groups.add(group);
        System.out.println("Created group: " + group);
    }

    private void addTask() {
        System.out.print("Task title: ");
        String title = sc.nextLine();
        System.out.print("Description: ");
        String desc = sc.nextLine();
        System.out.print("Estimated hours: ");
        int hours = Integer.parseInt(sc.nextLine());
        System.out.print("Difficulty (1-5): ");
        int diff = Integer.parseInt(sc.nextLine());
        System.out.print("Assign to (username, or leave blank): ");
        String uname = sc.nextLine();
        User assigned = null;
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(uname.trim())) assigned = u;
        }
        StudyTask task = new StudyTask(title, desc, hours, diff, assigned);

        System.out.print("Dependencies (comma separated task titles, blank if none): ");
        String depLine = sc.nextLine();
        if (!depLine.trim().isEmpty()) {
            String[] tnames = depLine.split(",");
            for (String depTitle : tnames) {
                for (StudyTask t : tasks) {
                    if (t.getTitle().equalsIgnoreCase(depTitle.trim()))
                        task.addDependency(t);
                }
            }
        }
        tasks.add(task);
        // Check for circular dependency
        if (conflictDetector.hasCircularDependency(task, new HashSet<>())) {
            System.out.println("ERROR: Circular dependency detected! Task not added.");
            tasks.remove(task);
        }
    }

    private void scheduleSession() {
        if (tasks.isEmpty() || users.isEmpty()) {
            System.out.println("Add at least one task and user first."); return;
        }
        System.out.println("Choose a task:");
        for (int i = 0; i < tasks.size(); i++)
            System.out.println((i+1)+". "+tasks.get(i));
        int tindex = Integer.parseInt(sc.nextLine())-1;

        System.out.print("Session Start (yyyy-MM-ddTHH:mm): ");
        LocalDateTime start = LocalDateTime.parse(sc.nextLine());
        System.out.print("Session End (yyyy-MM-ddTHH:mm): ");
        LocalDateTime end = LocalDateTime.parse(sc.nextLine());

        System.out.print("Participants (comma usernames): ");
        String[] names = sc.nextLine().split(",");
        List<User> parts = new ArrayList<>();
        for (String uname : names) {
            for (User u : users)
                if (u.getName().equalsIgnoreCase(uname.trim())) parts.add(u);
        }
        StudySession sess = new StudySession(tasks.get(tindex), start, end, parts);
        sessions.add(sess);
    }

    private void logRevision() {
        System.out.println("Select task to log progress:");
        for (int i = 0; i < tasks.size(); i++)
            System.out.println((i+1)+". "+tasks.get(i));
        int tindex = Integer.parseInt(sc.nextLine())-1;

        tracker.logRevision(tasks.get(tindex), LocalDateTime.now());
        System.out.println("Revision/progress logged!");
    }

    private void showAnalytics() {
        analytics.showHeatmap(tracker);
    }

    private void detectConflicts() {
        System.out.println("Sessions with conflicts:");
        for (int i = 0; i < sessions.size(); i++)
        for (int j = i+1; j < sessions.size(); j++)
            if (conflictDetector.hasSessionConflict(sessions.get(i), sessions.get(j))) {
                System.out.println("Conflict between:");
                System.out.println(sessions.get(i));
                System.out.println(sessions.get(j));
            }
    }
}
