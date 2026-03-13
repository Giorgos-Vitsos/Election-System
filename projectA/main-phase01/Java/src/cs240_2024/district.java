package cs240_2024;

//class that represents a district
public class district {
    int did;//id
    int seats;//number of seats
    int alloted;//number of seats allocated
    station stations;//head of the list
    int voids;//number of void votes
    int blanks;//number of blank votes
    candidate candidates;//head of the doublelinked list

    //initializing custroctor
    district(){
        did=-1;
        seats=-1;
        alloted=-1;
        stations=null;
        voids=-1;
        blanks=-1;
        candidates=null;
    }

    //constructing with data
    district(int did,int seats){
        this.did=did;
        this.seats=seats;
        this.alloted=0;
        stations=null;
        voids=0;
        blanks=0;
        candidates=null;

    }

    //method that add a new station to the list
    void addStation(station newstation){
        
        if(stations==null){//if the list is empty
            stations=newstation;//newstation is head
        }else{//if it has nodes
            newstation.next = stations;//we put the newstation at the start of the list
            stations = newstation;
        }

    }

    //method that returns a station given its sid
    station findStation(int sid){

        station search_cur=stations;
        while (search_cur!=null){//we go through all the stations until we find it or until the list ends
            if(search_cur.sid==sid){//if we find it
                return search_cur;//we return it
            }
            search_cur=search_cur.next;//we move inside the list
        }
        return null;//if we dont find it we return null
    }

    //method that adds a new candidate sorted in the doublylinked list
    void addCandidates(candidate newCandidate){

        if(candidates==null){//if the list is empty
            candidates=newCandidate;//we initialize it
        }else if(newCandidate.votes>=candidates.votes){//otherwise we check if head has less or same votes
            candidates.prev=newCandidate;
            newCandidate.next=candidates;
            candidates=newCandidate;
        }else{//otherwise we move inside the list
            candidate current=candidates;
            while(current.next!=null && current.next.votes>newCandidate.votes){
                current=current.next;//we move forward in the list while the next's votes are bigger
            }
            newCandidate.next=current.next;//connects the nexts
            if(current.next!=null){//if we are not at the last node in the list
                current.next.prev=newCandidate;//update the prev of the next one to show at the new one
            }
            current.next=newCandidate;//sets the current next to the new candidate
            newCandidate.prev=current;//sets the new candidate's prev to the current node
        }

    }


    //method that swap two nodes
    void swapCandidates(candidate a,candidate b){

        if (a == null || b == null){//if its empty we dont need to swap
            return;
        }


        a.next = b.next;//points a's next to the node after b
        if (b.next != null) {//if that node exists
            b.next.prev = a;//chanhe the previous node
        }
        b.prev = a.prev;//points b's prev to the node before a
        if (a.prev != null) {//if it exists
            a.prev.next = b;//we update its next
        }
        b.next = a;//now the node after b is a
        a.prev = b;//and the other way around
        if (a.prev == null) {//if a its the head
            candidates = a;//we update
        } else if (b.prev == null) {//same for b
            candidates = b;
        }
    }

    //method for sorting the list
    void sort(){

        //if the list is empty we cant sort
        if (candidates == null){
            return;
        } 


        boolean swapped;//flag to know if we swapped
        candidate current;
        do {
            swapped = false;
            current = candidates;

            while (current != null && current.next != null) {//we move throught the list

                if (current.votes < current.next.votes) {//if currents is smaller than the next one
                    swapCandidates(current, current.next);//we swap them
                    swapped = true;//we update the flag
                }
                current = current.next;//we move
            }
        } while (swapped);//we repeat until we are sorted
    }

    //method for finding a candidate given his cid (returns a candidate)
    candidate findCandidate(int cid){

        candidate search_cur=candidates;
        while (search_cur!=null){//we move through the list
            if(search_cur.cid==cid){//if we find it 
                return search_cur;//we return it
            }
            search_cur=search_cur.next;
        }
        return null;//otherwise null
    }
   
}
