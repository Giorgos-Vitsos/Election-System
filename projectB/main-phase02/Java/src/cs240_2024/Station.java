package cs240_2024;

import java.util.LinkedList;
import java.util.Queue;


/**
 * The Station class represents a voting station, which holds a list of voters and their associated data.
 * Each station has a unique station ID (`sid`), a district ID (`did`), and keeps track of the registered voters.
 * The voters are stored in a complete binary tree, where each node represents a voter.
 */
public class Station {
    int sid;
    int did;
    int registered;
    Voter voters;
    Station next;

    public Station(int sid,int did){
        this.sid=sid;
        this.did=did;
        registered=0;
        voters=null;
        next=null;
    }


    /**
     * Inserts a new voter into the complete binary tree of voters. The voter is added as a child of an existing voter node.
     * The insertion is performed using a level-order traversal (BFS).
     * If the left child of a node is null, the new voter is inserted there, otherwise, the right child is checked.
     * 
     * @param voter The voter to be inserted into the tree.
     */
    public void insert(Voter voter){
        if(voters==null){
            voters=voter;
            return;
        }
        Queue<Voter> q=new LinkedList<>();//uses level-order traversal
        q.add(voters);
        while(!q.isEmpty()){
            Voter current=q.poll();
            if(current.lc==null){
                current.lc=voter;
                voter.parent=current;
                return;
            }else{
                q.add(current.lc);
            }
            if(current.rc==null){
                current.rc=voter;
                voter.parent=current;
                return;
            }else{
                q.add(current.rc);
            }

        }
    }

    /**
     * Finds a voter by their unique voter ID (`vid`) in the complete binary tree of voters.
     * A level-order traversal is used to search for the voter.
     * 
     * @param key The voter ID (`vid`) of the voter to find.
     * @return The voter with the specified ID, or null if no such voter exists.
     */
    public Voter findVoter(int key){
        if (voters == null) {
            return null;
        }
    
        Queue<Voter> q = new LinkedList<>();
        q.add(voters);
    
        while (!q.isEmpty()) {
            Voter current = q.poll();
    
            if (current.vid == key) {
                return current;
            }
    
            if (current.lc != null) {
                q.add(current.lc);
            }
    
            if (current.rc != null) {
                q.add(current.rc);
            }
        }
        return null;
    }

    /**
     * Recursively prints all voters in the binary tree in an in-order traversal.
     * Each voter's ID and voting status (`voted`) are appended to the provided StringBuffer.
     * 
     * @param root The root of the binary tree of voters.
     * @param Jstring The StringBuffer to which the voter data is appended.
     */
    public void printVoters(Voter root,StringBuffer Jstring){
        if(root==null){
            return;
        }
        printVoters(root.lc,Jstring);
        Jstring.append("  <"+root.vid+"> <"+root.voted+">,\n");
        printVoters(root.rc,Jstring);
    }

    /**
     * Deletes a voter from the binary tree by their unique voter ID (`vid`).
     * The deletion is performed by replacing the target node with the last node in the tree and then deleting
     * that last node.
     * 
     * @param key The voter ID (`vid`) of the voter to delete.
     */
    public void deleteNode( int key) {
        if (voters == null) {
            return;
        }

        Voter targetNode = null;
        Voter lastNode = null;
        Queue<Voter> queue = new LinkedList<>();//we search for the last and targetNode
        queue.add(voters);

        while (!queue.isEmpty()) {
            lastNode = queue.poll();

            if (lastNode.vid == key) {
                targetNode = lastNode;
            }

            if (lastNode.lc != null) {
                queue.add(lastNode.lc);
            }

            if (lastNode.rc != null) {
                queue.add(lastNode.rc);
            }
        }

        if (targetNode != null) {
            if(targetNode==voters){//if the target is the root
                if((voters.lc == null && voters.rc == null)){//and its the only node
                    voters=null;//we remove the root
                }else{//otherwise we swap and delete the last
                    targetNode.vid = lastNode.vid;
                    deleteLastNode(lastNode);
                }  
            }else{//we swap and delete the last
                targetNode.vid = lastNode.vid;
                deleteLastNode(lastNode);
            }
            
        }
    }

    /**
     * Deletes the last node in the binary tree of voters. This node is found using a level-order traversal,
     * and the node is removed by adjusting the parent's references.
     * 
     * @param lastNode The last node (voter) to be deleted.
     */
    public void deleteLastNode(Voter lastNode) {
        if (voters == null || lastNode == null) {
            return;
        }

        Queue<Voter> queue = new LinkedList<>();
        queue.add(voters);
        Voter parent = null;
        
        while (!queue.isEmpty()) {
            parent = queue.poll();
            if (parent.lc == lastNode) {//checks if the left child of the current parent is the last node to be deleted.
                parent.lc = null;//deletes the child
                return;
            } else if (parent.rc == lastNode) {//checks if the right child of the current parent is the last node to be deleted.
                parent.rc= null;
                return;
            }

            if (parent.lc != null) {//we traverse
                queue.add(parent.lc);
            }

            if (parent.rc != null) {
                queue.add(parent.rc);
            }
        }
    }
}
