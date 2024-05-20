import java.util.List;

public class SchedulingManager {
    private SchedulingStrategy strategy;

    public SchedulingManager(SchedulingStrategy strategy) {
        this.strategy = strategy;
    }

    public void performScheduling(List<Process> processes) {
        strategy.schedule(processes);
    }
}
