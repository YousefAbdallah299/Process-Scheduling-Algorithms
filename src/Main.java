import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("************ Welcome to the Scheduling Simulator ************");
        Scanner scanner = new Scanner(System.in);
        SchedulingManager manager;
        while (true){
            System.out.println("**************** Choose scheduling strategy ****************");
            System.out.println("1. Shortest- Job First (SJF)");
            System.out.println("2. Shortest- Remaining Time First (SRTF)");
            System.out.println("3. Non-preemptive Priority Scheduling");
            System.out.println("4. AG Scheduling");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            int numberOfProcesses;
            int quantumTime;
            int contextSwitchingTime;
            List<Process> processes;
            switch (choice) {
                case 1:
                    System.out.print("Enter number of processes: ");
                    numberOfProcesses = scanner.nextInt();
                    System.out.print("Enter the quantum time: ");
                    quantumTime = scanner.nextInt();
                    System.out.print("Enter context switching time: ");
                    contextSwitchingTime = scanner.nextInt();
                    processes = new ArrayList<>();
                    System.out.println("***************** Entering Processes Details *****************");
                    takeProcessInput(scanner, numberOfProcesses, quantumTime, processes);

                    manager = new SchedulingManager(new SJFScheduling(contextSwitchingTime));
                    manager.performScheduling(processes);
                    break;
                case 2:
                    System.out.print("Enter number of processes: ");
                    numberOfProcesses = scanner.nextInt();
                    System.out.print("Enter the quantum time: ");
                    quantumTime = scanner.nextInt();
                    System.out.print("Enter context switching time: ");
                    contextSwitchingTime = scanner.nextInt();
                    processes = new ArrayList<>();
                    System.out.println("***************** Entering Processes Details *****************");
                    takeProcessInput(scanner, numberOfProcesses, quantumTime, processes);

                    manager = new SchedulingManager(new SRTFScheduling(contextSwitchingTime));
                    manager.performScheduling(processes);
                    break;
                case 3:
                    System.out.print("Enter number of processes: ");
                    numberOfProcesses = scanner.nextInt();
                    System.out.print("Enter the quantum time: ");
                    quantumTime = scanner.nextInt();
                    System.out.print("Enter context switching time: ");
                    contextSwitchingTime = scanner.nextInt();
                    processes = new ArrayList<>();
                    System.out.println("***************** Entering Processes Details *****************");
                    takeProcessInput(scanner, numberOfProcesses, quantumTime, processes);

                    manager = new SchedulingManager(new PriorityScheduling(contextSwitchingTime));
                    manager.performScheduling(processes);
                    break;
                case 4:
                    System.out.print("Enter number of processes: ");
                    numberOfProcesses = scanner.nextInt();
                    System.out.print("Enter the quantum time: ");
                    quantumTime = scanner.nextInt();
                    System.out.print("Enter context switching time: ");
                    contextSwitchingTime = scanner.nextInt();
                    processes = new ArrayList<>();
                    System.out.println("***************** Entering Processes Details *****************");
                    takeProcessInput(scanner, numberOfProcesses, quantumTime, processes);

                    manager = new SchedulingManager(new AGScheduling(contextSwitchingTime));
                    manager.performScheduling(processes);
                    break;
                case 0:
                    System.out.println("****************** Good bye! ******************");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void takeProcessInput(Scanner scanner, int numberOfProcesses, int quantumTime, List<Process> processes) {
        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("**************** Process " + (i + 1) + " ****************");
            System.out.print("Enter process name: ");
            String processName = scanner.next();
            System.out.print("Enter arrival time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time: ");
            int burstTime = scanner.nextInt();
            System.out.print("Enter priority: ");
            int priority = scanner.nextInt();
            Process process = new Process(processName, arrivalTime, burstTime, priority, quantumTime);
            processes.add(process);
            System.out.println("--------------------------------------------------------------");
        }
    }
}