import java.util.*;

public class AGScheduling implements SchedulingStrategy{

    private int contextSwitch;

    AGScheduling(int contextSwitch){
        this.contextSwitch = contextSwitch;
    }
    @Override
    public void schedule(List<Process> processes) {

        int time = 0;
        List<Process> copyProcesses = new ArrayList<>();
        copyProcesses.addAll(processes);
        List<Process> readyQueue = new ArrayList<>();
        List<Process> deadList = new ArrayList<>();
        List<Process> ganttChart = new ArrayList<>();
        calculateAGFactor(copyProcesses);
//        int i = 0;
//        for(Process p:copyProcesses){
//            if(i == 0){
//                p.setAgFactor(20);
//            } else if (i == 1) {
//                p.setAgFactor(17);
//            } else if (i == 2) {
//                p.setAgFactor(16);
//            } else if (i == 3) {
//                p.setAgFactor(43);
//            }
//            i++;
//        }
        //p1,p2,p3
        //p1,p2,p3
        System.out.println("***************** Process execution order *****************");
        while (deadList.size() != copyProcesses.size()) {
            updateReadyQueue(copyProcesses, readyQueue, time);


            if (!readyQueue.isEmpty()) {

                Process currentProcess = readyQueue.get(0);


//                ganttChart.add(currentProcess);
                System.out.print("Process "+currentProcess.getName() +" ");
                int quantum = currentProcess.getQuantumTime();


                int ceilQuantum = (quantum + 1) / 2;

                //6 -- 3
                //3 2
                if (ceilQuantum >= currentProcess.getBurstTime()){
                    //case 3
                    time += currentProcess.getBurstTime();
                    currentProcess.setBurstTime(0);
                    currentProcess.setQuantumTime(0);
                    currentProcess.endtime = time;
                    currentProcess.isFinished = true;
                    int index = copyProcesses.indexOf(currentProcess);
                    copyProcesses.get(index).setQuantumTime(currentProcess.getQuantumTime());
                    readyQueue.remove(currentProcess);
                    time += contextSwitch;
                    deadList.add(currentProcess);
                    continue;
                }
                else {
                    time += ceilQuantum;
                    currentProcess.setBurstTime(currentProcess.getBurstTime() - ceilQuantum);
                }

                updateReadyQueue(copyProcesses, readyQueue, time);


                if(currentProcess.equals(getLeast(readyQueue))){

                    int remainingQuantum = quantum - ceilQuantum;

                    while(remainingQuantum > 0){
                        time++;
                        currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);

                        updateReadyQueue(copyProcesses, readyQueue, time);
                        if(currentProcess.getBurstTime() == 0){
                            break;
                        }

                        remainingQuantum--;
                        if(!currentProcess.equals(getLeast(readyQueue))){
                            // case 2
                            currentProcess.setQuantumTime(currentProcess.getQuantumTime() + remainingQuantum);
                            readyQueue.remove(currentProcess);
                            readyQueue.add(currentProcess);
                            Process least = getLeast(readyQueue);
                            readyQueue.remove(least);
                            readyQueue.add(0, least);
                            int index = copyProcesses.indexOf(currentProcess);
                            copyProcesses.get(index).setQuantumTime(currentProcess.getQuantumTime());
                            time += contextSwitch;
                            break;
                        }
                    }


                    if(currentProcess.getBurstTime() == 0){
                        // case 3
                        currentProcess.setQuantumTime(0);
                        currentProcess.endtime = time;
                        currentProcess.isFinished = true;
                        int index = copyProcesses.indexOf(currentProcess);
                        copyProcesses.get(index).setQuantumTime(currentProcess.getQuantumTime());
                        readyQueue.remove(currentProcess);
                        time += contextSwitch;
                        deadList.add(currentProcess);
                    }
                    else if (remainingQuantum == 0){

                        // case 1
                        int avg = calcQuantumMean(copyProcesses);
                        currentProcess.setQuantumTime(currentProcess.getQuantumTime() + avg);
                        int index = copyProcesses.indexOf(currentProcess);
                        copyProcesses.get(index).setQuantumTime(currentProcess.getQuantumTime());
                        readyQueue.remove(currentProcess);
                        readyQueue.add(currentProcess);
                        time += contextSwitch;

//p2,p3,p1
                    }
                }
                else{
                    // case 2
                    currentProcess.setQuantumTime(currentProcess.getQuantumTime() + (quantum - ceilQuantum));
                    int index = copyProcesses.indexOf(currentProcess);
                    copyProcesses.get(index).setQuantumTime(currentProcess.getQuantumTime());
                    readyQueue.remove(currentProcess);


                    readyQueue.add(currentProcess);

                    Process least = getLeast(readyQueue);
                    readyQueue.remove(least);
                    readyQueue.add(0, least);

                    time += contextSwitch;
                }

            }
            else {
                time++;
            }
        }
        System.out.println();

        double avgTurnAround;
        int sumTurnAround = 0;
        System.out.println("***************** Process turn around time *****************");
        for (Process p : deadList){
            p.setTurnAroundTime(p.endtime - p.getArrivalTime());
            System.out.println("Process "+p.getName() + " : " + p.getTurnAroundTime());
            sumTurnAround += p.getTurnAroundTime();
        }
        avgTurnAround = sumTurnAround/deadList.size();
        System.out.println("***************** Average turn around time *****************");
        System.out.println("Average turn around time : " + avgTurnAround);

        double avgWaitingTime;
        int sumWaitingTime = 0;
        System.out.println("***************** Process waiting time *****************");
        for (Process p : deadList){
            p.setWaitingTime(p.getTurnAroundTime() - p.getLength());
            System.out.println("Process "+p.getName() + " : " + p.getWaitingTime());
            sumWaitingTime += p.getWaitingTime();
        }
        avgWaitingTime = sumWaitingTime/deadList.size();

        System.out.println("***************** Average waiting time *****************");
        System.out.println("Average waiting time : " + avgWaitingTime);

        System.out.println("***************** Quantum values *****************");
        for (Process p : deadList){
            System.out.print("Process " + p.getName() + " : " + "(");
            for (int x =1; x<p.getQtimes().size();x++){
                System.out.print(p.getQtimes().toArray()[x] + " , ");
            }
            System.out.println(")");
        }
    }

    public static void calculateAGFactor(List<Process> processes) {


        for (Process p : processes) {
            Random random = new Random();
            int randomValue = random.nextInt(21);
            int agFactor = p.arrivalTime + p.burstTime;

            if (randomValue < 10){
                agFactor += randomValue;
            } else if (randomValue > 10) {
                agFactor += 10;
            }else {
                agFactor += p.getPriorityNumber();
            }

            p.setAgFactor(agFactor);
        }

    }



    public void updateReadyQueue(List<Process> copyProcesses, List<Process> readyQueue, int time){
        for (Process process : copyProcesses) {
            if (process.getArrivalTime() <= time && !process.isFinished() && !readyQueue.contains(process))
            {
                process.isarrivalset = true;
                readyQueue.add(process);
                readyQueue.sort(Comparator.comparingInt(Process::getAgFactor));
            }
        }

    }

    public Process getLeast(List<Process> readyQueue){
        Process least = readyQueue.get(0);
        for(Process p : readyQueue){
            if(p.getAgFactor() < least.getAgFactor()){
                least = p;
            }
        }
        return least;
    }


    public int calcQuantumMean(List<Process> copyProcesses){
        double avgQuantum;
        int sum = 0;

        for(Process p : copyProcesses){
            sum += p.getQuantumTime();
        }
        avgQuantum = Math.ceil((sum/copyProcesses.size())* 0.1);

        return (int) avgQuantum;

    }

}
