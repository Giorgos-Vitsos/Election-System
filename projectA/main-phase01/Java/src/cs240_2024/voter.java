package cs240_2024;

public class voter {
    int vid;
    int voted;
    voter next;

    voter(int vid){
        this.vid=vid;
        voted=0;
        next=null;                                                                                                          
    }

}
