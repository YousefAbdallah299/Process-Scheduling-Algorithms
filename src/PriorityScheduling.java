import java.util.*;

/*
5
1
1
p1
0
11
2
p2
5
28
0
p3
12
2
3
p4
2
10
1
p5
9
16
4


*/
public class PriorityScheduling implements SchedulingStrategy {
    private static final double AGE_FACTOR = 2;
    private static int currentTime = 0;
    private int contextSwitch;

    PriorityScheduling(int contextSwitch){
        this.contextSwitch = contextSwitch;
    }
    @Override
    public void schedule(List<Process> allProcesses) {
        if (allProcesses.isEmpty()) {
            System.out.println("No processes to schedule.");
            return;
        }

        allProcesses.sort(Comparator.comparingInt(Process::getArrivalTime));
        PriorityQueue<Process> currentProcesses = new PriorityQueue<>(Comparator.comparingDouble(this::getPriority));

        while (!allProcesses.isEmpty() || !currentProcesses.isEmpty()) {
            if (!allProcesses.isEmpty())
                while (allProcesses.get(0).getArrivalTime() <= currentTime) {
                    currentProcesses.add(allProcesses.remove(0));
                    if (allProcesses.isEmpty()) break;
                }

            if (currentProcesses.isEmpty() && !allProcesses.isEmpty()) {
                currentProcesses.add(allProcesses.get(0));
                currentTime = allProcesses.get(0).getArrivalTime();
                allProcesses.remove(0);
                if (allProcesses.isEmpty()) break;

                while (allProcesses.get(0).getArrivalTime() <= currentTime) {
                    currentProcesses.add(allProcesses.get(0));
                    allProcesses.remove(0);
                    if (allProcesses.isEmpty()) break;
                }
            }

            var currentProcess = currentProcesses.poll();
            var finishTime = currentTime + currentProcess.getBurstTime();
            System.out.println(currentProcess.getName() + " with priority of ("+ currentProcess.getPriorityNumber() +")" + " and its arrival time is ("+ currentProcess.getArrivalTime() +")" + " has entered the CPU at " + currentTime + " and finished at " + finishTime+" + " + contextSwitch + " (context switch time)");
            currentTime = finishTime+contextSwitch;
        }
    }

    private double getPriority(Process process) {
        return  (process.getPriorityNumber() - AGE_FACTOR * (currentTime - process.getArrivalTime()));

    }
}


/*
5
0
0
p1
1
10
3
p2
1
1
1
p3
1
2
4
p4
1
1
5
p5
1
5
2
*/
