package cs240_2024;

public class ElectedCandidate {
    int cid;
    int did;
    int pid;
    ElectedCandidate next;

    public ElectedCandidate(Candidate c,int pid){
        cid=c.cid;
        did=c.did;
        this.pid=pid;
        next=null;
    }

    //method that frees the list by breaking the references
    public void freeParliament(){
        ElectedCandidate current=this;
        while (current!=null) {
            ElectedCandidate temp=current.next;
            current.next=null;
            current=temp; 
        }
    }

    
}
