package cs240_2024;

/**
 * This class represents a political party, which manages a set of candidates. 
 * Each party has a unique party ID (`pid`) and a count of elected candidates (`electedCount`). 
 * The candidates are stored in a binary search tree (BST) structure, where each node represents 
 * a candidate, and the candidates are inserted and searched based on their candidate IDs (`cid`).
 */
public class Party {
    int pid;
    int electedCount;
    Candidate candidates;

    /**
     * Constructs a new Party with the specified party ID. The initial number of elected candidates 
     * is set to 0, and the candidates tree is empty (null).
     * 
     * @param pid The unique ID of the party.
     */
    public Party(int pid){
        this.pid=pid;
        electedCount=0;
        candidates=null;
    }

    /**
     * Inserts a new candidate into the binary search tree of candidates. 
     * The candidates are inserted based on their candidate ID (`cid`), with smaller `cid` values 
     * being placed in the left subtree and larger `cid` values in the right subtree.
     * 
     * @param root The root of the tree (or subtree) where the candidate should be inserted.
     * @param newCandidate The candidate to be inserted into the tree.
     * @return The root of the (possibly updated) tree.
     */
    private Candidate insert(Candidate root,Candidate newCandidate){
        if(root==null){
            return newCandidate;
        }
        if(newCandidate.cid<root.cid){
           root.lc=insert(root.lc,newCandidate);
        }else{
            root.rc=insert(root.rc, newCandidate);
        }
        return root;
    }

    /**
     * Public method to insert a candidate into the party's candidate tree.
     * This method is called externally to add a new candidate to the party.
     * 
     * @param newCandidate The candidate to be inserted.
     */
    public void insertCandidate(Candidate newCandidate){
        candidates=insert(candidates, newCandidate);
    }

    /**
     * Searches for a candidate in the binary search tree by their candidate ID (`cid`).
     * 
     * @param root The root of the tree (or subtree) to search in.
     * @param key The candidate ID (`cid`) to search for.
     * @return The candidate with the specified ID if found, otherwise null.
     */
    public Candidate findCandidate(Candidate root, int key){
        if(root==null || root.cid==key){
            return root;
        }
        if(key<root.cid){
            return findCandidate(root.lc, key);
        }
        return findCandidate(root.rc, key);
    }

    /**
     * Recursively counts the number of candidates in the tree.
     * 
     * @param node The root of the tree (or subtree) to count the candidates in.
     * @return The number of candidates in the tree (or subtree).
     */
    public int getNumOfCand(Candidate node){
        if(node==null){
            return 0;
        }
        return 1+getNumOfCand(node.lc)+getNumOfCand(node.rc);
    }

     /**
     * Recursively frees the memory used by the candidates in the party's tree. This method 
     * removes all references to the left and right children and nullifies the current node.
     * 
     * @param root The root of the tree (or subtree) to be cleaned.
     */
    private void cleanCandidates(Candidate root){
        if (root == null) {
            return;
        }
        cleanCandidates(root.lc);
        cleanCandidates(root.rc); 
        root.lc = null;  
        root.rc = null;  
        root = null;     
    }

    /**
     * Frees the memory used by the party's candidates by calling the `cleanCandidates` 
     * method to clean up the entire tree of candidates.
     */
    public void freeParty(){
        cleanCandidates(candidates);
    }

}
