package cs240_2024;

public class parliament {
    candidate members;

    public parliament(){
        members=null;
    }

    //method that add sorted members (same as the one in district)
    void addMembers(candidate newCandidate){
        if(members==null){
            members=newCandidate;
        }else if(newCandidate.votes>=members.votes){
            members.prev=newCandidate;
            newCandidate.next=members;
            members=newCandidate;
        }else{
            candidate current=members;
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
