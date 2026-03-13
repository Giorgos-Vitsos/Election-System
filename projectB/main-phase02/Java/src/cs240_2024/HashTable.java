package cs240_2024;

import java.util.Random;

/**
 * This class represents a HashTable that stores a collection of Station objects using 
 * separate chaining to handle collisions. The hash table uses a simple linear 
 * congruential method for generating hash values and provides methods to insert, 
 * search, print chains, and free up the table's memory.
 */
public class HashTable{

    Station[] stations;

    /**
     * The capacity of the hash table, which is the number of slots in the array 
     * that holds the stations.
     */
    int m;
    int p;
    int a,b;


    /**
     * Constructs a new hash table with specified maximum number of stations, maximum
     * station ID, and an array of prime numbers. It initializes the hash table with
     * an appropriate size and generates random values for the hash function coefficients.
     * 
     * @param MaxStationsCount The maximum number of stations that can be inserted into the table.
     * @param MaxSid The maximum station ID that can be stored in the table.
     * @param Primes An array of prime numbers to choose from when selecting 'p'.
     */
    public HashTable(int MaxStationsCount,int MaxSid,int[] Primes){

        Random rand=new Random();
        double loadFactor = 0.75;

        for (int prime : Primes) {//find p>MaxSid
            if(prime > MaxSid){
                p=prime;
                break;
            }
        }

        //generates random values
        a=rand.nextInt(p-1)+1;
        b=rand.nextInt(p);

        //Calculates the initial capacity of the hashtable based on loadFactor
        int tempCap=(int) (MaxStationsCount/loadFactor);
        if((MaxStationsCount%loadFactor)!=0){
            tempCap++;
        }
        m=tempCap;

        stations=new Station[m];
        for(int i=0; i<m; i++){
            stations[i]=null;
        }

    }

    /**
     * Hash function that computes the hash value for a given station ID (sid).
     * It uses the formula: ((a * key + b) % p) % m.
     * 
     * @param key The station ID to hash.
     * @return The hash value corresponding to the station ID.
     */
    public int hash(int key){
        return ((a * key + b) % p) % m;
    }

    /**
     * Inserts a station into the hash table. If there is a collision (the hash 
     * value is already occupied), it uses separate chaining (a linked list) to store 
     * the new station in the corresponding index.
     * 
     * @param station The station object to insert into the hash table.
     */
    public void insert(Station station){

        int index=hash(station.sid);//find the h

        //if the station is empty insert
        if (stations[index] == null) {
            stations[index] = station;
        } else {//otherwise add it to the linked list

            Station current = stations[index];
            Station prev=null;
            while (current != null && current.sid < station.sid) {
                prev=current;
                current = current.next;
            }
            if(prev==null){
                station.next=current;
                stations[index]=station;
            }else{
                prev.next=station;
                station.next=current;
            }
        }
    }

    /**
     * Prints the linked list of stations at the given hash index.
     * 
     * @param h The hash index for which the linked list of stations should be printed.
     */
    public void printChain(int h){
        Station current=stations[h];
        StringBuffer Sstring=new StringBuffer();
        while(current!=null){
            Sstring.append("<"+current.sid+">, ");
            current=current.next;
        }
        if(Sstring.length()>=2){
            Sstring.delete(Sstring.length()-2, Sstring.length());
        }
        System.out.println(Sstring.toString());
    }

    /**
     * Searches for a station by its station ID. If the station is found, it returns 
     * the corresponding station object; otherwise, it returns null.
     * 
     * @param sid The station ID to search for.
     * @return The station object with the specified ID, or null if not found.
     */
    public Station search(int sid) {
        int index = hash(sid); 
        Station current = stations[index];
    
        while (current != null) {
            if (current.sid == sid) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Frees up the memory used by the hash table by iterating over each slot in the
     * `stations` array and removing the linked list of stations stored at each
     * index.
     * It clears the `next` references of each station node to help with garbage
     * collection,
     * and finally sets each slot in the `stations` array to `null`.
     * <p>
     * This method effectively releases any resources allocated for the hash table's
     * linked lists,
     * ensuring that no memory is being held unnecessarily.
    */
    public void freeHash(){
        for(int i=0; i<m; i++){
            Station current=stations[i];
            while(current!=null) {
                Station temp = current.next;
                current.next = null;
                current = temp;
            }
            stations[i]=null;
        }
    }

}
