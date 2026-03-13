package cs240_2024;

public class District {
    int did;
    int seats;
    int blanks;
    int invalids;
    int[] partyVotes=new int[5];

    public District(){
        did=-1;
        seats=0;
        blanks=0;
        invalids=0;
        for(int i=0; i<5; i++){
            partyVotes[i]=0;
        }
    }

}
