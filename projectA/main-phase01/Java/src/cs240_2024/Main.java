package cs240_2024;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.jws.soap.SOAPBinding;

/*
 * Main.java: CS240 2024-2025
 * Original author: Antonis Papaioannou
 */
public class Main {
    public static district[] Districs= new district[56];//array of district objects
    public static party[] Parties=new party[5];//array if party objects
    public static parliament Parliament;//new Parliamnet
    private static int nextavailable=0;//static flag that helps with knowing which districts we are working with
    
    public static boolean DEBUG = false;
    /**
     * @brief Initializes the system.
     *
     * @return true on success
     *         false on failure
     */
    public static boolean announce_elections(){
        //initializes the objects
        for(int i=0; i<56; i++){
            Districs[i]=new district();
        }
        for(int i=0; i<5; i++){
            Parties[i]=new party();
        }
        Parliament=new parliament();

        return true;
    }
    
    /**
    * @brief Create a new district and adds it to the districts array
    *
    * @param did The new district's id
    * @param seats The number of elected seats of district
    *
    * @return true on success
    *         false on failure
    */
    public static boolean create_district(int did, int seats){
       district newDistrict=new district(did,seats);//we create the object
       if(nextavailable>=56){//O(1) complexity (I could have use for because we know that districts are constant 56)
        return false;//if we are past our capacity
       }
       Districs[nextavailable]=newDistrict;//we add to the next spot in the array the object
       nextavailable++;//we move to the next spot
       return true; 
    }
    
    /**
    * @brief Create a new station and adds it to the districts array
    *
    * @param sid The new station's id
    * @param did The district's id to add the new station
    *
    * @return true on success
    *         false on failure
    */    
    public static boolean create_station(int sid, int did){
        station newStation=new station(sid);//create a station
        for(int i=0; i<56; i++){
            if((Districs[i].did==did) && (Districs[i]!=null)){//we find the correct district
                Districs[i].addStation(newStation);//we add the station
                return true;
            }
        }
        return false;//if we didnt add a station then it didnt work
    }
    
    /**
    * @brief Create a new party and adds it to the parties array
    *
    * @param pid The new party's id
    *
    * @return true on success
    *         false on failure
    */
    public static boolean create_party(int pid) {
        party newParty=new party(pid);//we create a party
        for(int i=0; i<5; i++){
            if(Parties[i].pid==-1){//we check that we havent initialize it yet
                Parties[i]=newParty;//we add the party object in the array
                return true;
            }
        }
        return false;//if we didnt add a party then it didnt work
    }
    
    /**
    * @brief Create a new candidate and insert him to the appropriate district
    *
    * @param cid The new candidate's id
    * @param did The district's id to add the new candidate
    * @param pid The party id that represents the candidate
    *
    * @return true on success
    *         false on failure
    */
    public static boolean register_candidate(int cid, int did, int pid) {
        candidate newCandidate=new candidate(cid,pid);//create a candidate
        for(int i=0; i<56; i++){
            if(Districs[i].did==did){//we find the district
                Districs[i].addCandidates(newCandidate);//we add the candidate
                return true;
            }
        }

        return false;//if we didnt add a candidate then it didnt work
    }
    
    /**
    * @brief Create a new voter and adds him to the appropriate station
    *
    * @param vid The new voter's id
    * @param sid The station id to add the new voter
    * @param did The district's id that belongs the station
    *
    * @return true on success
    *         false on failure
    */
    public static boolean register_voter(int vid, int did, int sid) {
        voter newVoter=new voter(vid);//we create a voter
        int index=-1;//index helps with finding the district
        for(int i=0; i<56; i++){//we search for the district
            if(Districs[i].did==did){
                index=i;
                break;//we break if we find it
            }
        }        
        if(index==-1){//if we didnt find it then something wrong
            return false;
        }
        station sid_Station=Districs[index].findStation(sid);
        if(sid_Station!=null){
            sid_Station.addVoter(newVoter);//we add the voter
            sid_Station.registered++;//we add 1 in the registered
            return true;
        }
        return false;//if we didnt add then something wrong
    }
    
    /**
    * @brief Create a new district and adds it to the districts array
    *
    * @param vid The voter's id to unregister
    *
    * @return true on success
    *         false on failure
    */
    public static boolean unregister_voter(int vid) {
        return true;
    }
    
    /**
    * @brief Delete stations that don't have registered voters
    *
    * @return true on success
    *         false on failure
    */
    public static boolean delete_empty_stations() {
        StringBuffer Estring = new StringBuffer();//buffer for the string
        for(int i=0; i<56; i++){
            if(Districs[i]==null || Districs[i].stations==null){//if its already empty
                continue;
            }
            station current=Districs[i].stations;
            station prev=null;
            while(current!=null){//move through the list
                if(current.registered==0){//if none is registered
                    if(prev==null){//and its the head
                        Estring.append("\n <"+current.sid+"> <"+Districs[i].did+">");
                        Districs[i].stations=current.next;
                    }else{//otherwise
                        Estring.append("\n <"+current.sid+"> <"+Districs[i].did+">");
                        prev.next=current.next;//we delete by combining the prev with the next
                    }
                }else{//if its not empty
                    prev=current;
                }
                current=current.next;//we move to the next one
            }     
        }
        System.out.print("E");
        if(Estring.toString().equals("")){//if buffer is empty then nothing got deleted
            System.out.println("\nNothing got deleted");
        }else{
            System.out.println(Estring.toString());
        }
        System.out.println("DONE");
        
        return true;
    }
    
    /**
    * @brief Handle the vote of a voter
    *
    * @param vid The voter's id
    * @param sid The station's id that the voter is registered
    * @param cid The candidate's id that got the vote
    *
    * @return true on success
    *         false on failure
    */
    public static boolean vote(int vid, int sid, int cid) {
        int index=-1;
        station currStation=null;
        for(int i=0; i<56; i++){
            if(Districs[i].findStation(sid)==null){
                continue;
            }else{
                index=i;
                currStation=Districs[index].findStation(sid);
            }
        }
        if(index==-1){
            return false;
        }

        voter currentVoter=currStation.findVoter(vid);
        if(currentVoter==null){
            return false;
        }
        currentVoter.voted=1;//the current voted
        if(cid==0){//if vote is 0
            Districs[index].voids++;//then its void
        }else if(cid==1){//if vote is 1
            Districs[index].blanks++;//then its blank
        }else{//all else 
            candidate currentCandidate = Districs[index].findCandidate(cid);//we find who he vote for
            if (currentCandidate == null) {
                return false;
            }
            currentCandidate.votes++;//and we add the vote
            Districs[index].sort();//we sort so that it always stays sorted
        }
        
        System.out.println("V <"+vid+"> <"+sid+"> <"+cid+">");
        System.out.println(" District = <"+Districs[index].did+">");
        StringBuffer CVsting = new StringBuffer(" Candidate votes = ");
        candidate current=Districs[index].candidates;
        while(current!=null){//we move through all candidates
            CVsting.append("(<"+current.cid+">, <"+current.votes+">)");
            if(current.next!=null){//if there are more
                CVsting.append(", ");
            }
            current=current.next;
        }
        System.out.println(CVsting.toString());
        System.out.println(" Blanks = <"+Districs[index].blanks+">");
        System.out.println(" Voids = <"+Districs[index].voids+">");
        System.out.println("DONE");
        return true;
    }
    
    /**
    * @brief Count votes of a district
    *
    * @param did The district's id to count votes
    *
    * @return true on success
    *         false on failure
    */
    public static boolean count_votes(int did) {
        int index=-1;
        int totalvotesindistric=0;//the total votes of a district
        int[] totalVotes = new int[5];//votes of a district
        for(int i=0; i<56; i++){//we find the district
            if(Districs[i].did==did){
                index=i;
                break;
            }
        }
        if(index==-1){
            return false;
        }
        for(int i=0; i<5; i++){//we initialize the array
            totalVotes[i]=0;
        }
        candidate search_cur=Districs[index].candidates;
        while(search_cur!=null){//we move through the candidates of this district

            totalvotesindistric+=search_cur.votes;//we add their votes to the total
            totalVotes[search_cur.pid]+=search_cur.votes;//and also to the district total   
            search_cur=search_cur.next;//we move
        }
        double EklogikoMetro=totalvotesindistric/(double) Districs[index].seats;//we cast double to be sure that its accurate
        int[] ElectedSeats={0,0,0,0,0};//array of the seats that its district can have
        for(int i=0; i<5; i++){
            double electedseats=0.0;
            if( EklogikoMetro!=0){//we cant divide with 0
                electedseats=totalVotes[i]/ EklogikoMetro;
            }
            ElectedSeats[i]=(int) electedseats;//we cast it as an int so that we can round it down
        }
        search_cur=Districs[index].candidates;
        StringBuffer PEstring = new StringBuffer();
        while(search_cur!=null){//we move through the candidates of the district
            if(ElectedSeats[search_cur.pid]!=0 && Districs[index].alloted<=Districs[index].seats){//if they can elected someone
                search_cur.elected=1;//the current one get elected(already sorted)
                candidate newCandidate = new candidate(search_cur);//we make a copy
                Parties[search_cur.pid].addelected(newCandidate);//we add to the Party
                Parties[search_cur.pid].nelected++;//we increase this variable
                Districs[index].alloted++;//and also the number of seats that we gave
                PEstring.append(" <"+search_cur.cid+"> <"+search_cur.pid+"> <"+search_cur.votes+">\n");
                ElectedSeats[search_cur.pid]--;//we have less seats now
            }
            search_cur=search_cur.next;//we move to the next
        }      
        System.out.println("M <"+did+">");
        System.out.println(" Seats =");
        if(PEstring.toString().equals("")){
            System.out.println(" <Not enough seats>");
        }else{
            System.out.print(PEstring.toString());

        }
        System.out.println("DONE");
        return true;
    }
    
    /**
    * @brief Distribute the rest of available seats
    *
    * @return true on success
    *         false on failure
    */
    public static boolean form_government(){
        int maxIndex=Parties[0].pid;//pid of the first Party
        int max=Parties[0].nelected;//number of the first Party of elected people
        for(int i=1; i<5; i++){//we find the one with the most elections
            if(Parties[i].nelected>max){
                max=Parties[i].nelected;
                maxIndex=Parties[i].pid;
            }
        }
        StringBuffer gstring = new StringBuffer();
        for(int i=0; i<56; i++){//for every district
            int remaining =Districs[i].seats-Districs[i].alloted;//remaining seats
            candidate current = Districs[i].candidates;
            while(current!=null && remaining!=0){//we move inside the candidate list if we can and if we still need to
                if(current.pid==maxIndex && current.elected!=1){//first we get the one who didnt get elected form the winning party
                    current.elected=1;
                    gstring.append(" <"+i+"> <"+current.cid+"> <"+current.votes+">\n");
                    candidate newCandidate = new candidate(current);
                    Parties[maxIndex].addelected(newCandidate);
                    Parties[maxIndex].nelected++;
                    Districs[i].alloted++;
                    remaining--;
                }
                current=current.next;
            }
            if(remaining!=0){//afterwards we search the rest of the candidates
                candidate current_nomax = Districs[i].candidates;
                while (current_nomax != null && remaining!=0) {
                    if (current_nomax.pid != maxIndex && current_nomax.elected != 1) {
                        gstring.append(" <"+i+"> <"+current_nomax.cid+"> <"+current_nomax.votes+">\n");
                        current_nomax.elected=1;
                        candidate newCandidate = new candidate(current_nomax);
                        Parties[current_nomax.pid].addelected(newCandidate);
                        Parties[current_nomax.pid].nelected++;
                        Districs[i].alloted++;
                        remaining--;
                    }
                    current_nomax = current_nomax.next;
                }
            }
            if(remaining!=0){//if we still have seats then candidates wherent enough
                System.out.println("Not enough canditates in district");
                return false;
            }
        }
        System.out.println("G");
        System.out.println("Seats =");
        System.out.print(gstring.toString());
        System.out.println("DONE");
        return true;
    }
    
    /**
    * @brief Form the parliament
    *
    * @return true on success
    *         false on failure
    */
    public static boolean form_parliament() {
        parliament Vouli=Parliament;//I dont need this I just liked it
        StringBuffer Pstring = new StringBuffer();
        for(int i=0; i<5; i++){//for every party
            candidate current=Parties[i].elected;
            while(current!=null){//we add the elected candidates to a new list
                candidate newCandidate = new candidate(current);
                Vouli.addMembers(newCandidate);//sorted
                current=current.next;
            }
        }
        candidate curmember=Vouli.members;
        while (curmember!=null) {
            Pstring.append("  <"+curmember.cid+"> <"+curmember.pid+"> <"+curmember.votes+">\n");
            curmember=curmember.next;  
        }
        System.out.println("N");
        System.out.println("Members =");
        System.out.print(Pstring.toString());
        System.out.println("DONE");

        return true;
    }
    


    /**
    * @brief Print a district
    *
    * @param did The district's id
    *
    * @return true on success
    *         false on failure
    */
    public static boolean print_district(int did) {
        int index=-1;
        for(int i=0; i<56; i++){
            if(Districs[i].did==did){
                index=i;
                break;
            }
        }
        candidate current=Districs[index].candidates;
        StringBuffer stirng =new StringBuffer();
        while (current!=null) {
            stirng.append("  <"+current.cid+"> <"+current.pid+"> <"+current.votes+">\n");
            current=current.next;  
        }
        StringBuffer string2 =new StringBuffer();
        station currentStation=Districs[index].stations;
        while (currentStation!=null){
            string2.append("  <"+currentStation.sid+">");
            if(currentStation.next!=null){
                string2.append(", ");
            }
            currentStation=currentStation.next;  
        }

        System.out.println("I <"+did+">");
        System.out.println(" Seats = <"+Districs[index].seats+">");
        System.out.println(" Blanks = <"+Districs[index].blanks+">");
        System.out.println(" Voids = <"+Districs[index].voids+">");
        System.out.println(" Canditates =");
        System.out.print(stirng.toString());
        System.out.print(" Stations = ");
        System.out.println(string2.toString());
        System.out.println("DONE");
        return true;
    }

    
    /**
    * @brief Print a station
    *
    * @param sid The station's id
    * @param did The district id that the station belongs to
    *
    * @return true on success
    *         false on failure
    */
    public static boolean print_station(int sid, int did) {
        
        int index = -1;
        for (int i = 0; i < 56; i++) {
            if (Districs[i].did == did) {
                index = i;
                break;
            }
        }
        if(index==-1){
            return false;
        }
        station current=Districs[index].findStation(sid);
        if(current==null){
            return false;
        }
        System.out.println("J <"+sid+">");
        System.out.println(" Registered = <"+current.registered+">");
        StringBuffer string=new StringBuffer();
        voter currentVoter=current.voters;
        while(currentVoter!=current.vsentinel){
            string.append("  <"+currentVoter.vid+"> <"+currentVoter.voted+">\n");
            currentVoter=currentVoter.next;
        }
        System.out.println( " Voters =");
        System.out.print(string.toString());
        System.out.println("DONE");
        
        return true;
    }
    
    /**
    * @brief Print a party
    *
    * @param pid The party's id
    *
    * @return true on success
    *         false on failure
    */
    public static boolean print_party(int pid) {
        int index =-1;
        for(int i=0; i<5; i++){
            if(Parties[i].pid==pid){
                index =i;
                break;
            }
        }
        if(index==-1){
            return false;
        }
        candidate current=Parties[index].elected;
        StringBuffer string=new StringBuffer();
        while (current!=null) {
            string.append("    <"+current.cid+"> <"+current.votes+">\n");
            current=current.next;  
        }
        System.out.println("K <"+pid+">");
        System.out.println("  Elected =");
        System.out.print(string.toString());
        System.out.println("DONE");
        
        return true;
    }

    /**
    * @brief Print a canditate
    *
    * @param pid The party's id
    * @param cid The canditate's id
    * @param did The district id that the station belongs to
    *
    * @return true on success
    *         false on failure
    */
    public static boolean print_canditate(int cid,int did,int pid){
        int index=-1;
        for(int i=0; i<56; i++){
            if(Districs[i].did==did){
                System.out.println("C <"+Districs[i].candidates.cid+"> <"+Districs[i].did+">"+" <"+pid+">");
                index=i;
                break;
            }
        }
        if(index==-1){
            return false;
        }
        StringBuffer cstring = new StringBuffer("Canditates = ");
        if(Districs[index].candidates==null){
            return false;
        }
        candidate current = Districs[index].candidates;
        while(current!=null){
            cstring.append("<"+current.cid+">");
            if(current.next!=null){
                cstring.append(", ");
            }
            current=current.next;
        }
        System.out.println(cstring.toString());
        System.out.println("DONE");
        return true;


    }

    /**
    * @brief Print all voters
    *
    * @param sid The station's id
    * @param vid The voters's id
    * @param did The district id that the station belongs to
    *
    * @return true on success
    *         false on failure
    */
    public static boolean print_votes(int vid,int did,int sid){
        int index=-1;
        for(int i=0; i<56; i++){
            if(Districs[i].did==did){
                System.out.println("R <"+Districs[i].findStation(sid).voters.vid+"> <"+Districs[i].did+">"+" <"+Districs[i].findStation(sid).sid+">");
                index=i;
                break;
            }
        }
        StringBuffer vstring = new StringBuffer("Voters = ");
        if(Districs[index].findStation(sid).voters==null){
            return false;
        }
        voter current = Districs[index].findStation(sid).voters;
        while(current!=Districs[index].findStation(sid).vsentinel){
            vstring.append("<"+current.vid+">");
            if(current.next!=Districs[index].findStation(sid).vsentinel){
                vstring.append(", ");
            }
            current=current.next;
        }
        System.out.println(vstring.toString());
        System.out.println("DONE");
        return true;

    }





    
    /**
    * @brief Print the parliament
    *
    * @return true on success
    *         false on failure
    */
    public static boolean print_parliament() { 
        System.out.println("L");
        System.out.println("  Members =");
        StringBuffer string=new StringBuffer();
        candidate current=Parliament.members;
        while (current!=null) {
            string.append("    <"+current.cid+"> <"+current.pid+"> <"+current.votes+">\n");
            current=current.next;     
        }
        System.out.print(string.toString());
        System.out.println("DONE");
        return true;
    }


////////////////////////////////////////////////////////////////////////////////

    public static void DPRINT(String s) {
        if (DEBUG) { System.out.println(s); }
    } 
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader inputFile;
        String line;
        String [] params;

        

                
	/* Check command buff arguments */       
        if (args.length != 1) {
            System.err.println("Usage: <executable-name> <input_file>");
            System.exit(0);
        }

	/* Open input file */        
        inputFile = new BufferedReader(new FileReader(args[0]));

	/* Read input file and handle the events */
        while ( (line = inputFile.readLine()) != null ) {

           // System.out.println("Event: " + line);
            params = line.split(" ");
            char eventID = line.charAt(0);

            switch(eventID) {

		/* Comment */
		case '#':
			break;

		/* Announce elections
		 * A */
		case 'A':
		{
			if ( announce_elections() ) {
                            DPRINT("A succeeded\n");
			} else {
                            System.err.println("A failed");
			}

			break;
		}

		/* Create a new District
		 * D <did> <seats> */
		case 'D':
		{
                    int did = Integer.parseInt(params[1]);
                    int seats = Integer.parseInt(params[2]);
                    assert(did >= 0);
                        DPRINT(eventID + " " + did + " " + seats);

			if ( create_district(did, seats) ) {
                int index = -1;
                for (int i = 0; i < 56; i++) {
                    if (Districs[i].did == did) {
                        System.out.println("D <" + Districs[i].did + "> <" + Districs[i].seats + ">");
                        index = i;
                    }
                }
                StringBuffer dstring = new StringBuffer("Districts = ");
                for (int i = 0; i <= index; i++) {
                    dstring.append("<" + Districs[i].did + ">");
                    if (i != index) {
                        dstring.append(", ");
                    }
                }
                System.out.println(dstring.toString());
                System.out.println("DONE");


                DPRINT(eventID + " " + did + " " + seats + " succeeded\n");

			} else {
                            System.err.println(eventID + " " + did + " " + seats + " failed");
			}

			break;
		}

		/* Create a new Station
		 * S <sid> <did> */
	 	case 'S':
		{
                    int sid = Integer.parseInt(params[1]);
                    int did = Integer.parseInt(params[2]);
                    assert(sid >= 0);
                    assert(did >= 0);
                    DPRINT(eventID + " " + sid + " " + did);

                    if ( create_station(sid, did) ) {


                        int index = -1;
                        for (int i = 0; i < 56; i++) {
                            if (Districs[i].did == did) {
                                System.out.println("S <" + Districs[i].stations.sid + "> <" + Districs[i].did + ">");
                                index = i;
                                break;
                            }
                        }
                        if (index == -1) {
                            System.out.println("Error: couldnt find did");
                            return;
                        }
                        StringBuffer sstring = new StringBuffer("Stations = ");
                        if (Districs[index].stations == null) {
                            break;
                        }
                        station current = Districs[index].stations;
                        while (current != null) {
                            sstring.append("<" + current.sid + ">");
                            if (current.next != null) {
                                sstring.append(", ");
                            }
                            current = current.next;
                        }
                        System.out.println(sstring.toString());
                        System.out.println("DONE");


                        DPRINT(eventID + " " + sid + " " + did + " succeeded\n");
                    } else {
                        System.err.println(eventID + " " + sid + " " + did + " failed");
                    }

                    break;
		}

		/* Create a new Party
		 * P <pid> */
		case 'P':
		{
                    int pid = Integer.parseInt(params[1]);
                    assert(pid >= 0);
                    DPRINT(eventID + " " + pid);

                    if ( create_party(pid) ) {

                        
                        int index=-1;
                        for(int i=0; i<5; i++){
                            if(Parties[i].pid==pid){
                                System.out.println("P <"+Parties[i].pid+">");
                                index=i;
                                break;
                            }
                        }
                        if(index==-1){
                            System.out.println("Error: couldnt find did");
                            break;
                        }
                        StringBuffer pstring = new StringBuffer("Parties = ");
                        for(int i=0; i<=index; i++){
                            pstring.append("<"+Parties[i].pid+">");
                            if(i!=index){
                                pstring.append(", ");
                            }
                        }
                        System.out.println(pstring.toString());
                        System.out.println("DONE");







                        DPRINT(eventID + " " + pid + " succeeded\n");
                    } else {
                        System.err.println(eventID + " " + pid + " failed");
                    }

                    break;
		}

		/* Register Candidate
		 * C <cid> <did> <pid> */
		case 'C':
		{
                    int cid = Integer.parseInt(params[1]);
                    int did = Integer.parseInt(params[2]);
                    int pid = Integer.parseInt(params[3]);
                    assert(cid >= 0);
                    assert(did >= 0);
                    assert(pid >= 0);
                    DPRINT(eventID + " " + cid + " " + did + " " + pid);

                    if ( register_candidate(cid, did, pid) ) {

                        print_canditate(cid, did, pid);

                        DPRINT(eventID + " " + cid + " " + did + " " + pid + " succeeded\n");
                    } else {
                        System.err.println(eventID + " " + cid + " " + did + " " + pid + " failed");
                    }

                    break;
		}

		/* Register voter
		 * R <vid> <sid> <did> */
	 	case 'R':
		{
                    int vid = Integer.parseInt(params[1]);
                    int did = Integer.parseInt(params[2]);
                    int sid = Integer.parseInt(params[3]);
                    assert(vid >= 0);
                    assert(sid >= 0);
                    assert(did >= 0);
                    DPRINT(eventID + " " + vid + " " + sid + " " + did);

                    if ( register_voter(vid, did, sid) ) {

                        print_votes(vid, did, sid);
                        DPRINT(eventID + " " + vid + " " + sid + " " + did + " succeeded\n");
                    } else {
                        System.err.println(eventID + " " + vid + " " + sid + " " + did + " failed");
                    }
                    
                    break;
		}

		/* Uregister voter
		 * U <vid> */
	/*   	case 'U':
		{
                    int vid = Integer.parseInt(params[1]);
                    assert(vid >= 0);
                    DPRINT(eventID + " " + vid);

                    if ( unregister_voter(vid) ) {
                        DPRINT(eventID + " " + vid + " succeeded\n");
                    } else {
                        System.err.println(eventID + " " + vid + " failed");
                    }

                    break;
		}

		/* Delete empty stations
		 * E */
	 	case 'E':
		{
                    DPRINT(eventID + "");

                    if ( delete_empty_stations() ) {
                        DPRINT(eventID + " succeeded\n");
                    } else {
                        System.err.println(eventID + " failed");
                    }

                    break;
		}
                
                /* Vote
                 * V <vid> <sid> <cid> */
        case 'V':
        {
                    int vid = Integer.parseInt(params[1]);
                    int sid = Integer.parseInt(params[2]);
                    int cid = Integer.parseInt(params[3]);
                    assert(vid >= 0);
                    assert(sid >= 0);
                    assert(cid >= 0);
                    
                    DPRINT(eventID + " " + vid + " " + sid + " " + cid);

                    if ( vote(vid, sid, cid) ) {

                        DPRINT(eventID + " " + vid + " " + sid + " " + cid + " succeeded\n");
                    } else {
                        System.err.println(eventID + " " + vid + " " + sid + " " + cid + " failed");
                    }

                    break;
                    
                }
                
                /* Count Votes
		 * M <did> */
 		case 'M':
		{
                    int did = Integer.parseInt(params[1]);
                    assert(did >= 0);
                    DPRINT(eventID + " " + did);

                    if ( count_votes(did) ) {
                        DPRINT(eventID + " " + did + " succeeded\n");
                    } else {
                        System.err.println(eventID + " " + did + " failed");
                    }
                    
                    break;
		}

                /* Form government
		 * G */
 		case 'G':
		{
                    DPRINT("G");

                    if ( form_government() ) {
                        DPRINT(eventID + " succeeded\n");
                    } else {
                        System.err.println(eventID + " failed");
                    }
                    
                    break;
		}
                
                /* Form parliament
		 * N */
 		case 'N':
		{
                    DPRINT(eventID + "");

                    if ( form_parliament() ) {
                        DPRINT(eventID + " succeeded\n");
                    } else {
                        System.err.println(eventID + " failed");
                    }
                    
                    break;
		}
                
		/* Print a district
		 * I <did> */
 		case 'I':
		{
                    int did = Integer.parseInt(params[1]);
                    assert(did >= 0);
                    DPRINT(eventID + " " + did);

                    if ( print_district(did) ) {
                        DPRINT(eventID + " " + did + " succeeded\n");
                    } else {
                        System.err.println(eventID + " " + did + " failed");
                    }

                    break;
		}

		/* Print a station
		 * J <sid> <did> */
 		case 'J':
		{
                    int sid = Integer.parseInt(params[1]);
                    int did = Integer.parseInt(params[2]);
                    assert(sid >= 0);
                    assert(sid >= 0);
                
                    DPRINT(eventID + " " + sid + " " + did);

                    if ( print_station(sid,did) ) {
                        DPRINT(eventID + " " + sid + " " + did + " succeeded\n");
                    } else {
                        System.err.println(eventID + " " + sid  + " " + did + " failed");
                    }

                    break;
		}

		/* Print party
		 * K <pid> */
 		case 'K':
		{
                    int pid = Integer.parseInt(params[1]);
                    assert(pid >= 0);
                    DPRINT(eventID + " " + pid);
                    
                    if ( print_party(pid) ) {
			DPRINT(eventID + " succeeded\n");
                    } else {
                        System.err.println(eventID + " failed");
                    }

                    break;
		}

		/* Print parliament
		 * L */
 	 	case 'L':
		{
                    if ( print_parliament() ) {
			DPRINT(eventID + " succeeded\n");
                    } else {
                        System.err.println(eventID + " failed");
                    }

                    break;
		} 

		/* Empty line */
		case '\n':
			break;

		/* Ignore everything else */
		default:
                    DPRINT("Ignoring " + line);

                    break;
		}
	}
    }
    
    
           
}
