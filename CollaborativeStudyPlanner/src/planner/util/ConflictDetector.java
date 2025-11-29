package planner.util;

import planner.model.*;
import java.util.*;

public class ConflictDetector {
    public boolean hasSessionConflict(StudySession a, StudySession b) {
        if (!Collections.disjoint(a.getParticipants(), b.getParticipants())) {
            return a.getStart().isBefore(b.getEnd()) && a.getEnd().isAfter(b.getStart());
        }
        return false;
    }

    // Simple circular dependency check (DFS)
    public boolean hasCircularDependency(StudyTask task, Set<StudyTask> visited) {
        if (visited.contains(task)) return true;
        visited.add(task);
        for (StudyTask dep : task.getDependencies()) {
            if (hasCircularDependency(dep, visited)) return true;
        }
        visited.remove(task);
        return false;
    }
}
