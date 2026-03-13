package cs240_2024;

public class station {
    int sid;
    int registered;
    voter voters;
    voter vsentinel;
    station next;

    //constructor
    public station(int sid){
        this.sid=sid;
        registered=0;
        next=null;
        voters=null;
        vsentinel=new voter(-1);//sentinel node with value that means nothing

    }

    //method that adds a new voter
    void addVoter(voter newVoter){
        if(voters==null){
            newVoter.next=vsentinel;//vsentinel show at the last node
            voters=newVoter;
        }else{
            newVoter.next = voters;
            voters = newVoter;
        }
    }

    //method that finds a voter (like the rest find methods)
    voter findVoter(int vid){
        voter search_cur=voters;
        while (search_cur!=null && search_cur!=vsentinel){
            if(search_cur.vid==vid){
                return search_cur;
            }
            search_cur=search_cur.next;
        }
        return null;
    }
}
    