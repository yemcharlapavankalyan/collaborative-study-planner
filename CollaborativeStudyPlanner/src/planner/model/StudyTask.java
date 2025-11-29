package planner.model;

import java.util.*;

public class StudyTask {
    private String title;
    private String description;
    private int estimatedHours;
    private int difficulty;
    private List<StudyTask> dependencies = new ArrayList<>();
    private User assignedTo;

    public StudyTask(String title, String description, int estimatedHours, int difficulty, User assignedTo) {
        this.title = title;
        this.description = description;
        this.estimatedHours = estimatedHours;
        this.difficulty = difficulty;
        this.assignedTo = assignedTo;
    }

    public void addDependency(StudyTask t) {
        dependencies.add(t);
    }

    public String getTitle() { return title; }
    public User getAssignedTo() { return assignedTo; }
    public List<StudyTask> getDependencies() { return dependencies; }

    @Override
    public String toString() {
        return "StudyTask{" +
                "title='" + title + '\'' +
                ", desc='" + description + '\'' +
                ", hours=" + estimatedHours +
                ", diff=" + difficulty +
                ", assignedTo=" + (assignedTo != null ? assignedTo.getName() : "None") +
                '}';
    }
}
