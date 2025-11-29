package planner.util;

import planner.tracker.ProgressTracker;

public class Analytics {
    public void showHeatmap(ProgressTracker tracker) {
        System.out.println("[Progress Heatmap]");
        tracker.showTaskRevisions();
    }
}
