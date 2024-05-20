import java.util.*;

public class SJFScheduling implements SchedulingStrategy{

    private int contextSwitchingTime;
    public static final Comparator<Process> BURST_TIME_COMPARATOR = Comparator.comparingInt(process -> process.getBurstTime());

    public SJFScheduling(int contextSwitchingTime){
        this.contextSwitchingTime = contextSwitchingTime;
    }
    @Override
    public void schedule(List<Process> processes) {
        int currentTime = 0;
        int numberOfPrecessedProcesses = 0;
        int numberOfProcesses = processes.size();
        Process currentProcess;
        List<Process> currentProcesses = new ArrayList<>();
        List<Process> finishedProcesses = new ArrayList<>();
        while (numberOfPrecessedProcesses < numberOfProcesses) {
            for (Process process : processes) {
                if (!process.isFinished()) {
                    if (!currentProcesses.contains(process)){
                        if (process.getArrivalTime() <= currentTime) {
                            currentProcesses.add(process);
                        }
                    }
                }
            }
            if (currentProcesses.size() > 0) {
                currentProcesses.sort(BURST_TIME_COMPARATOR);
                currentProcess = currentProcesses.get(0);
                currentProcesses.remove(currentProcess);
                currentProcess.setFinished(true);
                currentTime += currentProcess.getBurstTime();
                currentProcess.setTurnAroundTime(currentTime - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getBurstTime());
                finishedProcesses.add(currentProcess);
                numberOfPrecessedProcesses++;
                currentTime += this.contextSwitchingTime;
            }
            else{
                currentTime++;
            }
        }
        System.out.println("***************** Process execution order *****************");
        for (Process process : finishedProcesses) {
            System.out.print("Process " + process.getName() + "  ");
        }
        System.out.println();
        double totalWaitingTime = 0;
        System.out.println("***************** Process waiting time *****************");
        for (Process process : finishedProcesses) {
            System.out.println("Process " + process.getName() + " : " + process.getWaitingTime());
            totalWaitingTime += process.getWaitingTime();
        }
        System.out.println("***************** Average waiting time *****************");
        System.out.println("Average waiting time : " + (totalWaitingTime / numberOfProcesses));

        double totalTurnAroundTime = 0;
        System.out.println("***************** Process turn around time *****************");
        for (Process process : finishedProcesses) {
            System.out.println("Process " + process.getName() + " : " + process.getTurnAroundTime());
            totalTurnAroundTime += process.getTurnAroundTime();
        }
        System.out.println("***************** Average turn around time *****************");
        System.out.println("Average turn around time : " + (totalTurnAroundTime / numberOfProcesses));
    }
}
