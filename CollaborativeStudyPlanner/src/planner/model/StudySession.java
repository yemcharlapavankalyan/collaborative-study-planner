package planner.model;

import java.time.*;
import java.util.*;

public class StudySession {
    private StudyTask task;
    private LocalDateTime start;
    private LocalDateTime end;
    private List<User> participants;

    public StudySession(StudyTask task, LocalDateTime start, LocalDateTime end, List<User> participants) {
        this.task = task;
        this.start = start;
        this.end = end;
        this.participants = participants;
    }

    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
    public List<User> getParticipants() { return participants; }
    public StudyTask getTask() { return task; }

    @Override
    public String toString() {
        return "Session for: " + task.getTitle() +
                " from " + start +
                " to " + end +
                ", users=" + participants;
    }
}
