import java.util.*;

public class SRTFScheduling implements SchedulingStrategy{
    int contextSwitchingTime;
    int time=0;
    int maxage=10; //TO solve starvation
    public SRTFScheduling(int contextSwitchingTime){
        this.contextSwitchingTime = contextSwitchingTime;
    }
    public Process chceage(List<Process> processes,int time){
        for(int i=0;i<processes.size();i++){
            if(processes.get(i).getWaitingTime()>=maxage&&processes.get(i).isFinished!=true){
                return processes.get(i);
            }
        }
        return null;
    }
    public Process anotherarrived(List<Process> processes,int time){
        List<Process> Avillablprocesses=new ArrayList<>();
        for(int i=0;i<processes.size();i++){
            if(processes.get(i).arrivalTime<=time&&processes.get(i).isFinished!=true){
               Avillablprocesses.add(processes.get(i));
            }

        }
        Collections.sort(Avillablprocesses, Comparator.comparingInt(Process::getBurstTime));
        if(Avillablprocesses.size()==0){
            return new Process("null",0,0,0, 0);
        }
        if(Avillablprocesses.get(0).isarrivalset==false){
            Avillablprocesses.get(0).startime=time;
            Avillablprocesses.get(0).isarrivalset=true;
        }
        for(int i=1;i<Avillablprocesses.size();i++){
           Avillablprocesses.get(i).waitingTime+=1;
        }
        if(chceage(Avillablprocesses,maxage)!=null){
            //TO solve starvation
            return chceage(Avillablprocesses,maxage);
        }
        return Avillablprocesses.get(0);
    }
    boolean end(List<Process> processes){
        for(int i=0;i<processes.size();i++){
            if(!processes.get(i).isFinished()){
                return false;
            }
        }
        return true;
    }
    @Override
    public void schedule(List<Process> processes) {
        Process p=new Process("null",0,0,0, 0);
        ArrayList<Process>procsscopy = new ArrayList<>();
        ArrayList<Process> History = new ArrayList<>();
        HashMap<String,Integer>mp=new HashMap<>();
        Collections.sort(processes, Comparator.comparingInt(Process::getArrivalTime));
        for(int i=0;i<processes.size();i++){
            procsscopy.add(processes.get(i));
            mp.put(processes.get(i).getName(),processes.get(i).burstTime);
        }
        while(!end(procsscopy)){
                    if(p.getName()!="null"&&p.getName()==anotherarrived(procsscopy,time).getName()){
                        p.burstTime-=1;
                        time+=1;
                    }
                    else{
                        p=anotherarrived(procsscopy,time);
                        if(p.getName()=="null"){
                            time+=1;
                            continue;
                        }
                        History.add(p);
                        p.burstTime-=1;
                        time+=1;
                    }

            if(p.burstTime==0){
                p.isFinished=true;
                p.endtime=time;

            }
        }
        for(int i=0;i<procsscopy.size();i++){
            processes.get(i).startime=procsscopy.get(i).startime;
            processes.get(i).endtime=procsscopy.get(i).endtime;
        }
        System.out.println("***************** Process execution order *****************");
        for(int i=0;i<History.size();i++){
            System.out.print("Process " + History.get(i).getName()+" ");
        }
        System.out.println("");
        double avgturnaround=0;
        double avgwaiting=0;
        System.out.println("***************** Process waiting time *****************");
        for(int i=0;i<processes.size();i++){
            int waitingtime=(processes.get(i).endtime-processes.get(i).arrivalTime)-mp.get(processes.get(i).getName());
            avgwaiting+=waitingtime;
            System.out.println("Process " + processes.get(i).getName()+" : "+waitingtime);
        }
        System.out.println("***************** Average waiting time *****************");

        System.out.println("Average waiting time : "+(avgwaiting/processes.size()));
        System.out.println("***************** Process turn around time *****************");
        for(int i=0;i<processes.size();i++){
            int Turnaround=(processes.get(i).endtime-processes.get(i).arrivalTime);
            avgturnaround+=Turnaround;
            System.out.println("Process " + processes.get(i).getName()+" : "+Turnaround);
        }
        System.out.println("***************** Average turn around time *****************");
        System.out.println("Average turn around time : "+(avgturnaround/processes.size()));
        System.out.println("******** Note these results are printed by considering the aging to solve starvation ********");
    }
}

/*
4
1
0
p1
0
7
1
p2
2
4
1
p3
4
1
1
p4
5
4
1
*/