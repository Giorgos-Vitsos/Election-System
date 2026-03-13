package cs240_2024;

public class party {
    int pid;
    int nelected;
    candidate elected;

    //constructor with values
    public party(int pid){
        this.pid=pid;
        nelected=0;
        elected=null;
    }

    //initializing constructor
    public party(){
        pid=-1;
        nelected=-1;
        elected=null;
    }

    //adds an elected candidate sorted (same as the others)
    void addelected(candidate newCandidate){
        if(elected==null){
            elected=newCandidate;
        }else if(newCandidate.votes>=elected.votes){
            elected.prev=newCandidate;
            newCandidate.next=elected;
            elected=newCandidate;
        }else{
            candidate current=elected;
            while(current.next!=null && current.next.votes>newCandidate.votes){
                current=current.next;
            }
            newCandidate.next=current.next;
            if(current.next!=null){
                current.next.prev=newCandidate;
            }
            current.next=newCandidate;
            newCandidate.prev=current;
        }
    }


}
