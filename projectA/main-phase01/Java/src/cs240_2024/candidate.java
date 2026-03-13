package cs240_2024;

//class that represents a candidate
public class candidate {
    int cid;//id
    int pid;//party's id
    int votes;//number of votes
    int elected;//0-->not elected,1-->elected
    candidate prev;//previus node
    candidate next;//next node     
    
    //constructor
    public candidate(int cid,int pid){
        this.cid=cid;
        this.pid=pid;
        votes=0;
        elected=0;
        prev=null;
        next=null;

    }

    //method constructing the same object (copy)
    public candidate(candidate other){
        this.cid=other.cid;
        this.pid=other.pid;
        votes=other.votes;
        elected=other.elected;
        prev=null;
        next=null;

    }

}
