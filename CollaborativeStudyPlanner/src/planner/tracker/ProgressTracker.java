package planner.tracker;

import planner.model.StudyTask;
import java.time.*;
import java.util.*;

public class ProgressTracker {
    private Map<StudyTask, List<LocalDateTime>> revisionLog = new HashMap<>();

    public void logRevision(StudyTask task, LocalDateTime when) {
        revisionLog.computeIfAbsent(task, k -> new ArrayList<>()).add(when);
    }

    public void showTaskRevisions() {
        for (StudyTask t : revisionLog.keySet()) {
            System.out.println(t.getTitle() + ": " + revisionLog.get(t));
        }
    }
}
