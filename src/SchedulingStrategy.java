import java.util.List;

public interface SchedulingStrategy {
    void schedule(List<Process> processes);
}
