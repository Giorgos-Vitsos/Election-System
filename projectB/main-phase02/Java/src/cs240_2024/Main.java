package cs240_2024;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.tree.TreeNode;

public class Main{
    public static int  MaxStationsCount;
    public static int MaxSid;
    public static int[] Primes = {
        0, 1, 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223, 1229, 1231, 1237, 1249, 1259, 1277, 1279, 1283, 1289, 1291, 1297, 1301, 1303, 1307, 1319, 1321, 1327, 1361, 1367, 1373, 1381, 1399, 1409, 1423, 1427, 1429, 1433, 1439, 1447, 1451, 1453, 1459, 1471, 1481, 1483, 1487, 1489, 1493, 1499, 1511, 1523, 1531, 1543, 1549, 1553, 1559, 1567, 1571, 1579, 1583, 1597, 1601, 1607, 1609, 1613, 1619, 1621, 1627, 1637, 1657, 1663, 1667, 1669, 1693, 1697, 1699, 1709, 1721, 1723, 1733, 1741, 1747, 1753, 1759, 1777, 1783, 1787, 1789, 1801, 1811, 1823, 1831, 1847, 1861, 1867, 1871, 1873, 1877, 1879, 1889, 1901, 1907, 1913, 1931, 1933, 1949, 1951, 1973, 1979, 1987, 1993, 1997, 1999, 2003, 2011, 2017, 2027, 2029, 2039, 2053, 2063, 2069, 2081, 2083, 2087, 2089, 2099, 2111, 2113, 2129, 2131, 2137, 2141, 2143, 2153, 2161, 2179, 2203, 2207, 2213, 2221, 2237, 2239, 2243, 2251, 2267, 2269, 2273, 2281, 2287, 2293, 2297, 2309, 2311, 2333, 2339, 2341, 2347, 2351, 2357, 2371, 2377, 2381, 2383, 2389, 2393, 2399, 2411, 2417, 2423, 2437, 2441, 2447, 2459, 2467, 2473, 2477, 2503, 2521, 2531, 2539, 2543, 2549, 2551, 2557, 2579, 2591, 2593, 2609, 2617, 2621, 2633, 2647, 2657, 2659, 2663, 2671, 2677, 2683, 2687, 2689, 2693, 2699, 2707, 2711, 2713, 2719, 2729, 2731, 2741, 2749, 2753, 2767, 2777, 2789, 2791, 2797, 2801, 2803, 2819, 2833, 2837, 2843, 2851, 2857, 2861, 2879, 2887, 2897, 2903, 2909, 2917, 2927, 2939, 2953, 2957, 2963, 2969, 2971, 2999, 3001, 3011, 3019, 3023, 3037, 3041, 3049, 3061, 3067, 3079, 3083, 3089, 3109, 3119, 3121, 3137, 3163, 3167, 3169, 3181, 3187, 3191, 3203, 3209, 3217, 3221, 3229, 3251, 3253, 3257, 3259, 3271, 3299, 3301, 3307, 3313, 3319, 3323, 3329, 3331, 3343, 3347, 3359, 3361, 3371, 3373, 3389, 3391, 3407, 3413, 3433, 3449, 3457, 3461, 3463, 3467, 3469, 3491, 3499, 3511, 3517, 3527, 3529, 3533, 3539, 3541, 3547, 3557, 3559, 3571, 3581, 3583, 3593, 3607, 3613, 3617, 3623, 3631, 3637, 3643, 3659, 3671, 3673, 3677, 3691, 3697, 3701, 3709, 3719, 3727, 3733, 3739, 3761, 3767, 3769, 3779, 3793, 3797, 3803, 3821, 3823, 3833, 3847, 3851, 3853, 3863, 3877, 3881, 3889, 3907, 3911, 3917, 3919, 3923, 3929, 3931, 3943, 3947, 3967, 3989, 4001, 4003, 4007, 4013, 4019, 4021, 4027, 4049, 4051, 4057, 4073, 4079, 4091, 4093, 4099, 4111, 4127, 4129, 4133, 4139, 4153, 4157, 4159, 4177, 4201, 4211, 4217, 4219, 4229, 4231, 4241, 4243, 4253, 4259, 4261, 4271, 4273, 4283, 4289, 4297, 4327, 4337, 4339, 4349, 4357, 4363, 4373, 4391, 4397, 4409, 4421, 4423, 4441, 4447, 4451, 4457, 4463, 4481, 4483, 4493, 4507, 4513, 4517, 4519, 4523, 4547, 4549, 4561, 4567, 4583, 4591, 4597, 4603, 4621, 4637, 4639, 4643, 4649, 4651, 4657, 4663, 4673, 4679, 4691, 4703, 4721, 4723, 4729, 4733, 4751, 4759, 4783, 4787, 4789, 4793, 4799, 4801, 4813, 4817, 4831, 4861, 4871, 4877, 4889, 4903, 4909, 4919, 4931, 4933, 4937, 4943, 4951, 4957, 4967, 4969, 4973, 4987, 4993, 4999, 5003, 5009, 5011, 5021, 5023, 5039, 5051, 5059, 5077, 5081, 5087, 5099, 5101, 5107, 5113, 5119, 5147, 5153, 5167, 5171, 5179, 5189, 5197, 5209, 5227, 5231, 5233, 5237, 5261, 5273, 5279, 5281, 5297, 5303, 5309, 5323, 5333, 5347, 5351, 5381, 5387, 5393, 5399, 5407, 5413, 5417, 5419, 5431, 5437, 5441, 5443, 5449, 5471, 5477, 5479, 5483, 5501, 5503, 5507, 5519, 5521, 5527, 5531, 5557, 5563, 5569, 5573, 5581, 5591, 5623, 5639, 5641, 5647, 5651, 5653, 5657, 5659, 5669, 5683, 5689, 5693, 5701, 5711, 5717, 5737, 5741, 5743, 5749, 5779, 5783, 5791, 5801, 5807, 5813, 5821, 5827, 5839, 5843, 5849, 5851, 5857, 5861, 5867, 5869, 5879, 5881, 5897, 5903, 5923, 5927, 5939, 5953, 5981, 5987, 6007, 6011, 6029, 6037, 6043, 6047, 6053, 6067, 6073, 6079, 6089, 6091, 6101, 6113, 6121, 6131, 6133, 6143, 6151, 6163, 6173, 6197, 6199, 6203, 6211, 6217, 6221, 6229, 6247, 6257, 6263, 6269, 6271, 6277, 6287, 6299, 6301, 6311, 6317, 6323, 6329, 6337, 6343, 6353, 6359, 6361, 6367, 6373, 6379, 6389, 6397, 6421, 6427, 6449, 6451, 6469, 6473, 6481, 6491, 6521, 6529, 6547, 6551, 6553, 6563, 6569, 6571, 6577, 6581, 6599, 6607, 6619, 6637, 6653, 6659, 6661, 6673, 6679, 6689, 6691, 6701, 6703, 6709, 6719, 6733, 6737, 6761, 6763, 6779, 6781, 6791, 6793, 6803, 6823, 6827, 6829, 6833, 6841, 6857, 6863, 6869, 6871, 6883, 6899, 6907, 6911, 6917, 6947, 6949, 6959, 6961, 6967, 6971, 6977, 6983, 6991, 6997, 7001, 7013, 7019, 7027, 7039, 7043, 7057, 7069, 7079, 7103, 7109, 7121, 7127, 7129, 7151, 7159, 7177, 7187, 7193, 7207, 7211, 7213, 7219, 7229, 7237, 7243, 7247, 7253, 7283, 7297, 7307, 7309, 7321, 7331, 7333, 7349, 7351, 7369, 7393, 7411, 7417, 7433, 7451, 7457, 7459, 7477, 7481, 7487, 7489, 7499, 7507, 7517, 7523, 7529, 7537, 7541, 7547, 7549, 7559, 7561, 7573, 7577, 7583, 7589, 7591, 7603, 7607, 7621, 7639, 7643, 7649, 7669, 7673, 7681, 7687, 7691, 7699, 7703, 7717, 7723, 7727, 7741, 7753, 7757, 7759, 7789, 7793, 7817, 7823, 7829, 7841, 7853, 7867, 7873, 7877, 7879, 7883, 7901, 7907, 7919, 7927, 7933, 7937, 7949, 7951, 7963, 7993, 8009, 8011, 8017, 8039, 8053, 8059, 8069, 8081, 8087, 8089, 8093, 8101, 8111, 8117, 8123
    };
    public static District[] Districts=new District[56];
    public static Party[] Parties=new Party[5];
    public static HashTable StationTable;
    public static ElectedCandidate Parliament;

    //initializes the elections
    public static boolean announce_elections(){
        for(int i=0; i<56; i++){
            Districts[i]=new District();
        }
        StationTable=new HashTable(MaxStationsCount, MaxSid, Primes);//creates the hashtable
        for(int pid=0; pid<5; pid++){
            Parties[pid]=new Party(pid);
        }
        Parliament=null;
        return true;
    }
    

    //creates a new distric by adding a did and the number of seats
    public static boolean create_district(int did, int seats){
        int index=findFirstEmpty(0, 56);
        Districts[index].did=did;
        Districts[index].seats=seats;
        return true;
    }

    //finds the first empty spot in the District array
    private static int findFirstEmpty(int start,int end){
        if(start>end){
            return -1;
        }
        int mid=start+(end-start)/2;
        if(Districts[mid].did==-1){//if the middle is empty
            if(mid==0 || Districts[mid-1].did!=-1){//if the one before isnt empty then mid is the first empty
                return mid;
            }else{//otherwise we search the left side
                return findFirstEmpty(start, mid-1);
            }
        }else{//if middle is not empty we search the right side
            return findFirstEmpty(mid+1, end);
        }
    }
    
    //creates a new station and adds it to the hashtable
    public static boolean create_station(int sid, int did){
        Station newStation =new Station(sid, did);
        StationTable.insert(newStation);
        return true;
    }
    
    //searches in the table for the sid and then adds a new voter to the CBT
    public static boolean register_voter(int vid, int sid) {
        Voter newVoter=new Voter(vid);
        StationTable.search(sid).insert(newVoter);
        StationTable.search(sid).registered++;
        return true;
    }

    //prints in-order the CBT
    public static void printInOrder(Voter root,StringBuffer Rstring){
        if(root==null){
            return;
        }
        printInOrder(root.lc,Rstring);
        Rstring.append("<"+root.vid+">, ");
        printInOrder(root.rc,Rstring);
    }

    //registers a new candidate to a party
    public static boolean register_candidate(int cid,int pid,int did) {
        Candidate newCandidate=new Candidate(cid, did);
        Parties[pid].insertCandidate(newCandidate);
        return true;
    }

    //prints in-order the SBT
    public static void printInOrder(Candidate root,StringBuffer Cstring){
        if(root==null){
            return;
        }
        printInOrder(root.lc,Cstring);
        Cstring.append("  <"+root.cid+"> <"+root.did+">,\n");
        printInOrder(root.rc,Cstring);
    }
    
    //add one vote to a candidate
    public static boolean vote(int vid, int sid, int cid,int pid){
        Station currentStation=StationTable.search(sid);//finds the station
        Voter currentVoter=currentStation.findVoter(vid);//finds the voter
        if(currentVoter==null){
            return false;
        }
        currentVoter.voted=true;
        for(int i=0; i<56; i++){//finds the District of that station
            if(currentStation.did==Districts[i].did){
                if(cid==-2){//-2 means invalid
                    Districts[i].invalids++;
                }else if(cid==-1){//-1 means blank
                    Districts[i].blanks++;
                }else{//all others (Have made sure cid is >=-2)
                    Candidate currentCandidate=Parties[pid].findCandidate(Parties[pid].candidates, cid);//finds the candidate
                    if(currentCandidate==null){
                        return false;
                    }
                    currentCandidate.votes++;//adds one vote
                    Districts[i].partyVotes[pid]++;//adds one vote for the party
                }
                break;
            }
        }

        return true;
    }
    
    //counts the votes of a district
    public static boolean count_votes(int did,StringBuffer Mstring) {
        for(int i=0; i<56; i++){
            if(Districts[i].did==did){//finds the district
                int sumVotes=0;//sum of all votes in the district
                double electoralQuota; 
                for(int j=0; j<5; j++){//finds the sum
                    sumVotes+=Districts[i].partyVotes[j];
                }
                if(Districts[i].seats==0){
                    electoralQuota=0;
                }else{
                    electoralQuota=sumVotes/(double) Districts[i].seats;//casts it as double
                }
                int[] partyElected=new int[5];//helper array 
                for(int j=0; j<5; j++){
                    if(electoralQuota==0){
                        partyElected[j]=0;
                    }else{
                        partyElected[j]=(int) (Districts[i].partyVotes[j]/electoralQuota);//casts as int
                    }
                    int numOfCand=Parties[j].getNumOfCand(Parties[j].candidates);//we get the number of Candidates in a party
                    if(partyElected[j]>numOfCand){
                        partyElected[j]=numOfCand;
                    }
                    Parties[j].electedCount=partyElected[j];
                    Districts[i].seats-=partyElected[j];
                }
                 for(int j=0; j<5; j++){
                    ElectPartyCandidatesInDistrict(j, i, partyElected[j],Mstring);
                }
                break;
            }
        }
        
        return true;
    }

    public static void ElectPartyCandidatesInDistrict(int pid, int did, int partyElected,StringBuffer Mstring){
        if(partyElected==0){
            return;
        }
        minHeap HeapTable=new minHeap(partyElected);//we initialize a min-Heap
        int[] heapsize={0};//we use an array cause its a primitive value and I dont want to use a wrapper
        Traverse(Parties[pid].candidates,HeapTable,heapsize,partyElected,did);//we traverse throught the BST
        HeapTable.elect(Mstring,pid);//we elect everyone on the heap

    }

    //in-order traversal
    public static void Traverse(Candidate node,minHeap HeapTable,int[] size,int partyElected,int did) {
        if (node == null) return;
        Traverse(node.lc,HeapTable,size,partyElected,did);
        if(node.did==did){//if we find a candidate with the same district
            if(size[0]<partyElected){//if the heap is not full
                HeapTable.HeapInsert(node);//we add in the heap
            }else{//if the heap is full
                HeapTable.HeapDeleteMin();//we delete the smallest
                HeapTable.HeapInsert(node);//and add the new one
            }
            
        }
        Traverse(node.rc,HeapTable,size,partyElected,did);
    }

    //merges the trees to form a parliament
    public static boolean form_parliament(){
        ElectedCandidate[] mergedArray=mergeBSTs(Parties[0].candidates, Parties[1].candidates, Parties[2].candidates, Parties[3].candidates, Parties[4].candidates);//an array with all elected candidates
        for(int i=0; i<mergedArray.length-1; i++){//we make the array a list
            mergedArray[i].next=mergedArray[i+1];
        }
        Parliament=mergedArray[0];

        return true;
    }

    //in-order traversal
    private static void inOrderTraversal(Candidate node, ElectedCandidate[] arr, int[] index,int pid) {
        if (node == null) {
            return;
        }

        inOrderTraversal(node.lc, arr, index,pid);

        if(node.isElected){//if the candidate is elected
            ElectedCandidate newNode=new ElectedCandidate(node, pid);//we make him electedCandidate
            arr[index[0]] = newNode;//and add him to an array
            index[0]++;
        }
        
        inOrderTraversal(node.rc, arr, index,pid);
    }

    //merges all 5 BSTs
    public static ElectedCandidate[] mergeBSTs(Candidate root0, Candidate root1,Candidate root2,Candidate root3,Candidate root4) {

        //we make an array with ElectedCandidates for each tree
        ElectedCandidate[] tree0 = new ElectedCandidate[Parties[0].electedCount];
        int[] index0 = {0}; //we again use an array so that we dont use a wrapper
        if(Parties[0].electedCount!=0){
            inOrderTraversal(root0, tree0, index0,0);
        }
        

        ElectedCandidate[] tree1 = new ElectedCandidate[Parties[1].electedCount];
        int[] index1 = {0};
        
        if(Parties[1].electedCount!=0){
            inOrderTraversal(root1, tree1, index1,1);
        }

        ElectedCandidate[] tree2 = new ElectedCandidate[Parties[2].electedCount];
        int[] index2 = {0};
        
        if(Parties[2].electedCount!=0){
            inOrderTraversal(root2, tree2, index2,2);
        }

        ElectedCandidate[] tree3 = new ElectedCandidate[Parties[3].electedCount];
        int[] index3 = {0};
        
        if(Parties[3].electedCount!=0){
            inOrderTraversal(root3, tree3, index3,3);
        }

        // Second BST
        ElectedCandidate[] tree4 = new ElectedCandidate[Parties[4].electedCount];
        int[] index4 = {0};
        
        if(Parties[4].electedCount!=0){
            inOrderTraversal(root4, tree4, index4,4);
        }

        //we return the merge of all those 5 arrays
        return mergeSortedArrays(tree0, tree1,tree2,tree3,tree4);
    }

    //merges 5 arrays
    public static ElectedCandidate[] mergeSortedArrays(ElectedCandidate[] tree0,ElectedCandidate[] tree1,ElectedCandidate[] tree2,ElectedCandidate[] tree3,ElectedCandidate[] tree4){
        ElectedCandidate[] mergedArray=mergeTwo(tree0,tree1);//merges the first 2
        mergedArray=mergeTwo(mergedArray, tree2);//merges the already merged one with the third array etc...
        mergedArray=mergeTwo(mergedArray,tree3);
        mergedArray=mergeTwo(mergedArray, tree4);
        return mergedArray;
       
    }

    //merges two arrays
    public static ElectedCandidate[] mergeTwo(ElectedCandidate[] array1,ElectedCandidate[] array2){
        int i = 0, j = 0, k = 0;
        ElectedCandidate[] mergedArray=new ElectedCandidate[array1.length+array2.length];
        while (i < array1.length && j < array2.length) {
            if (array1[i].cid<=array2[j].cid) {
                mergedArray[k++] = array1[i++];
            } else {
                mergedArray[k++] = array2[j++];
            }
        }

        //copies any remaining elements from array1
        while (i < array1.length) {
            mergedArray[k++] = array1[i++];
        }

        //copies any remaining elements from array2
        while (j < array2.length) {
            mergedArray[k++] = array2[j++];
        }

        return mergedArray;

    }
    

    //prints a district
    public static boolean print_district(int did) {
        System.out.println("I "+did);
        int index=-1;
        for(int i=0; i<56; i++){
            if(Districts[i].did==did){
                index=i;
                break;
            }
        }
        if(index==-1){
            return false;
        }
        System.out.println("  seats <"+Districts[index].seats+">");
        System.out.println("  blanks <"+Districts[index].blanks+">");
        System.out.println("  invalids <"+Districts[index].invalids+">");
        System.out.println("  partyVotes");
        for(int i=0; i<5; i++){
            
            if(i!=4){
                System.out.println("  <"+i+"> <"+Districts[index].partyVotes[i]+">,");
            }else{
                System.out.println("  <"+i+"> <"+Districts[index].partyVotes[i]+">");
            }
        }
        System.out.println("DONE");

        return true;
    }
    

    //prints a station
    public static boolean print_station(int sid) {
        Station current=StationTable.search(sid);
        System.out.println("J <"+current.sid+">");
        System.out.println("  registered "+current.registered);
        System.out.println("  voters");
        StringBuffer Jstring=new StringBuffer();
        current.printVoters(current.voters, Jstring);
        if(Jstring.length()>=2){
            Jstring.delete(Jstring.length()-2, Jstring.length());
        }
        System.out.println(Jstring.toString());
        System.out.println("DONE");
        
        return true;
    }
    
    //prints a party
    public static boolean print_party(int pid) {
        System.out.println("K <"+pid+">");
        System.out.println("  elected");
        ElectedCandidate current=Parliament;
        StringBuffer Kstring=new StringBuffer();
        while (current!=null) {
            if(current.pid==pid){
                Candidate currentCandidate=Parties[pid].findCandidate(Parties[pid].candidates, current.cid);
                Kstring.append("  <"+currentCandidate.cid+"> <"+currentCandidate.votes+">,\n");
            }
            current=current.next;
        }
        if(Kstring.length()>=2){
            Kstring.delete(Kstring.length()-2, Kstring.length());
        }
        System.out.println(Kstring.toString());
        System.out.println("DONE");
        return true;
    }
    

    //prints the parliament
    public static boolean print_parliament() {
        System.out.println("  Members");
        ElectedCandidate current = Parliament;
        while (current != null) {
            System.out.print("  <" + current.cid + "> <" + current.pid + "> <" + current.did+">");
            if(current.next!=null){
                System.out.println(",");
            }
            current = current.next;
        }
        System.out.println("\nDONE");

        return true;
    }

    //it removes a voter
    public static boolean unregister_voter(int vid,int sid){
       Station currentStation=StationTable.search(sid);
       currentStation.deleteNode(vid);
        return true;
    }

    //prints the CBT after we removed
    public static void printUnregVoters(Voter root,StringBuffer BUstring){
        if(root==null){
            return;
        }
        printUnregVoters(root.lc,BUstring);
        BUstring.append("<"+root.vid+">, ");
        printUnregVoters(root.rc,BUstring);
    }

    //it frees memory for the system
    public static boolean free_memory(){
        for(int i=0; i<56; i++){
            Districts[i]=null;
        }
        StationTable.freeHash();
        for(int i=0; i<5; i++){
            Parties[i].freeParty();
            Parties[i]=null;
        }
        Parliament.freeParliament();
        System.gc();//we suggest the garbage collector to act
        return true;
    }


////////////////////////////////////////////////////////////////////////////////

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

            params = line.split(" ");
            char eventID = line.charAt(0);

            switch(eventID) {

		/* Comment */
		case '#':
			break;

		case 'A':
		{
            MaxStationsCount= Integer.parseInt(params[1]);
            MaxSid = Integer.parseInt(params[2]);

			if ( announce_elections() ){
                
                System.out.println("A <"+MaxStationsCount+"> <"+MaxSid+">");
                System.out.println("DONE");
			} else {
                System.err.println("A failed");
			}

			break;
		}

		case 'D':
	    {
            int did = Integer.parseInt(params[1]);
            int seats = Integer.parseInt(params[2]);
            assert (did >= 0);

            if (create_district(did, seats)) {
                System.out.println("D <"+did+"> <"+seats+">\n  Districs");
                StringBuffer dstring=new StringBuffer("  ");
                int i=0;
                while(Districts[i].did!=-1 && i<56){
                    dstring.append("<"+Districts[i].did+">, ");
                    i++;
                }
                dstring.delete(dstring.length()-2, dstring.length());
                System.out.println(dstring.toString());
                System.out.println("DONE");



            } else {
                System.err.println(eventID + " " + did + " " + seats + " failed");
            }

            break;
        }

	    case 'S': 
        {
            int sid = Integer.parseInt(params[1]);
            int did = Integer.parseInt(params[2]);
            assert (sid >= 0);
            assert (did >= 0);

            if (create_station(sid, did)) {
                System.out.println("S <"+sid+"> <"+did+">");
                System.out.println("Stations["+StationTable.hash(sid)+"]");
                StationTable.printChain(StationTable.hash(sid));
                System.out.println("DONE");
            } else {
                System.err.println(eventID + " " + sid + " " + did + " failed");
            }

            break;
        }

		case 'R':
		{
            int vid = Integer.parseInt(params[1]);
            int sid = Integer.parseInt(params[2]);
            assert (vid >= 0);
            assert (sid >= 0);

            if (register_voter(vid, sid)) {
                System.out.println("R <" + vid + "> <" + sid + ">");
                System.out.println("  Voters[" + sid + "]");
                StringBuffer Rstring=new StringBuffer("  ");
                printInOrder(StationTable.search(sid).voters,Rstring);
                Rstring.delete(Rstring.length()-2, Rstring.length());
                System.out.println(Rstring);
                System.out.println("DONE");

            } else {
                System.err.println(eventID + " " + vid + " " + sid + " failed");
            }

            break;
        }

		case 'C':
		{
            int cid = Integer.parseInt(params[1]);
            int pid = Integer.parseInt(params[2]);
            int did = Integer.parseInt(params[3]);
            assert (cid >= 0);
            assert (did >= 0);
            assert (pid >= 0);

            if (register_candidate(cid, pid, did)) {
                System.out.println("C <"+cid+"> <"+pid+"> <"+did+">");
                System.out.println("  Candidates["+pid+"]");
                StringBuffer Cstring=new StringBuffer();
                printInOrder(Parties[pid].candidates, Cstring);
                Cstring.delete(Cstring.length()-2, Cstring.length());
                System.out.println(Cstring);
                System.out.println("DONE");

            } else {
                System.err.println(eventID + " " + cid + " " + did + " " + pid + " failed");
            }

            break;
        }

        case 'V': 
        {
            int vid = Integer.parseInt(params[1]);
            int sid = Integer.parseInt(params[2]);
            int cid = Integer.parseInt(params[3]);
            int pid = Integer.parseInt(params[4]);
            assert (vid >= 0);
            assert (sid >= 0);
            assert (cid >= -2);
            assert (pid >= 0);


            if (vote(vid, sid, cid, pid)){
                System.out.println("V <"+vid+"> <"+sid+"> <"+cid+"> <"+pid+">");
                for(int i=0; i<56; i++){
                    if(StationTable.search(sid).did==Districts[i].did){
                        System.out.println("  District["+Districts[i].did+"]");
                        System.out.println("  blanks "+Districts[i].blanks);
                        System.out.println("  invalids "+Districts[i].invalids);
                        System.out.println("  partyVotes");
                        for(int j=0; j<5; j++){
                            if(j==4){
                                System.out.println("  "+j+" "+Districts[i].partyVotes[j]);
                            }else{
                                System.out.println("  "+j+" "+Districts[i].partyVotes[j]+",");
                            }
                        }
                        break;
                    }
                }
                System.out.println("DONE");


            } else {
                System.err.println(eventID + " " + vid + " " + sid + " " + cid + " " + pid + " failed");
            }

            break;

        }
                
		case 'M':
		{
            int did = Integer.parseInt(params[1]);
            assert (did >= 0);
            StringBuffer Mstring=new StringBuffer();

            if (count_votes(did,Mstring)) {
                System.out.println("M <"+did+">");
                System.out.println("  seats");
                if(Mstring.length()>=2){
                    Mstring.delete(Mstring.length()-2, Mstring.length());
                }
                System.out.println(Mstring.toString());
                System.out.println("DONE");

            } else {
                System.err.println(eventID + " " + did + " failed");
            }

            break;
        }
      
        case 'N': {

            if (form_parliament()) {
                System.out.println("N");
                print_parliament();

                
            } else {
                System.err.println(eventID + " failed");
            }

            break;
        }

		case 'I':
		{
            int did = Integer.parseInt(params[1]);
            assert (did >= 0);

            if (print_district(did)) {
            } else {
                System.err.println(eventID + " " + did + " failed");
            }

            break;
        }

		case 'J':
		{
                    int sid = Integer.parseInt(params[1]);
                    assert(sid >= 0);
                

                    if ( print_station(sid) ) {
                 //       DPRINT(eventID + " " + sid + " " + did + " succeeded\n");
                    } else {
                        System.err.println(eventID + " " + sid  + " failed");
                    }

                    break;
		}

		case 'K':
		{
            int pid = Integer.parseInt(params[1]);
            assert (pid >= 0);

            if (print_party(pid)) {
            } else {
                System.err.println(eventID + " failed");
            }

            break;
        }

		case 'L':
		{
            if (print_parliament()) {
                // DPRINT(eventID + " succeeded\n");
            } else {
                System.err.println(eventID + " failed");
            }

            break;
        }

		case 'B':
		{
            if(line.charAt(1)=='U'){
                int vid = Integer.parseInt(params[1]);
                int sid = Integer.parseInt(params[2]);
               

                if (unregister_voter(vid,sid)) {
                    System.out.println("BU <"+vid+"> <"+sid+">");
                    System.out.println("  Voters["+sid+"]");
                    StringBuffer BUstring=new StringBuffer("  ");
                    printUnregVoters(StationTable.search(sid).voters,BUstring);
                    if(BUstring.length()>=2){
                        BUstring.delete(BUstring.length()-2, BUstring.length());
                    }
                    System.out.println(BUstring.toString());
                    System.out.println("DONE");
                } else {
                    System.err.println(eventID + " failed");
                }
    
                break;

            }else{
                if (free_memory()) {
                    System.out.println("BF");
                    System.out.println("DONE");
                } else {
                    System.err.println(eventID + " failed");
                }
    
                break;
            }
            
        }

        /* Empty line */
        case '\n':
            break;

        /* Ignore everything else */
        default:

            break;
    }
}

    }
           
}
