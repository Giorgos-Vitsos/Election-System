package cs240_2024;

public class Voter {
    int vid;
    boolean voted;
    Voter parent;
    Voter lc;
    Voter rc;

    public Voter(int vid){
        this.vid=vid;
        voted=false;
        parent=null;
        lc=null;
        rc=null;
    }


   
}
