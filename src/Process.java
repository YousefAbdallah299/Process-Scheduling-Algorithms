import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Process {
    private String name;

    public int agFactor;
    public int arrivalTime;
    public boolean isarrivalset=false;
    public int burstTime;
    public int priorityNumber;
    public boolean isFinished = false;
    public int startime;
    public int endtime;
    public int waitingTime;
    public int turnAroundTime;
    public int quantumTime;
    public int length;

    private HashSet<Integer> Qtimes = new HashSet<>();

    public Process(String name, int arrivalTime, int burstTime, int priorityNumber, int quantumTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priorityNumber;
        this.quantumTime = quantumTime;
        this.Qtimes.add(quantumTime);
        this.length = burstTime;
    }


    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriorityNumber() {
        return priorityNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setPriorityNumber(int priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public String toString() {
        return "Process Name: " + name + "\nProcess Arrival Time: " + arrivalTime + "\nProcess Burst Time: " + burstTime + "\nProcess Priority Number: " + priorityNumber;
    }


    public int getQuantumTime() {
        return quantumTime;
    }

    public void setQuantumTime(int quantumTime) {
        this.quantumTime = quantumTime;
        this.Qtimes.add(quantumTime);
    }

    public int getAgFactor() {
        return agFactor;
    }

    public void setAgFactor(int agFactor) {
        this.agFactor = agFactor;
    }

    public int getLength() {
        return length;
    }

    public HashSet<Integer> getQtimes() {
        return Qtimes;
    }

    public void setQtimes(HashSet<Integer> qtimes) {
        Qtimes = qtimes;
    }
}
