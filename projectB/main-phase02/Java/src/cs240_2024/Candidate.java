package cs240_2024;

public class Candidate {
    int cid;
    int did;
    int votes;
    boolean isElected;
    Candidate lc;
    Candidate rc;

    public Candidate(int cid,int did){
        this.cid=cid;
        this.did=did;
        votes=0;
        isElected=false;
        lc=null;
        rc=null;

    }

}
