# Election System Simulation - Phase 02

This project is a simulation of the Greek parliamentary election system. It was developed as the second phase of the **HY240: Data Structures** course assignment (Computer Science Department, University of Crete).

---

##  Data Structures

To achieve optimal time complexity, the system implements the following advanced data structures:

* **Districts:** A static array of 56 cells. It stores seats, blanks, invalids, and an array of party votes.
* **Stations:** A Hash Table utilizing universal hashing. Collisions are resolved using sorted linked lists (chaining).
* **Voters:** A complete, unsorted, doubly-linked binary tree, maintained within each station.
* **Parties:** A static array of 5 cells.
* **Candidates:** A simply-linked Binary Search Tree (BST) for each party, sorted by candidate ID.
* **Parliament:** A singly-linked list of elected members, sorted in descending order by candidate ID.
* **Election Mechanism (MinHeap):** A dynamically allocated Minimum Heap is used during the vote-counting phase to efficiently find the candidates with the most votes.

---

## ⚙️ Supported Events

The program parses an input file containing specific commands. The operations are:

| Command | Description | Time Complexity |
| :--- | :--- | :--- |
| `A` | **Announce Elections:** Initializes all primary data structures. | - |
| `D <did> <seats>` | **Create District:** Inserts a new district into the first empty array slot. | O(log n) |
| `S <sid> <did>` | **Create Station:** Inserts a station into the appropriate hash table chain. | - |
| `R <vid> <sid>` | **Register Voter:** Inserts a voter into the complete binary tree. | O(log n) |
| `C <cid> <pid> <did>`| **Register Candidate:** Inserts a candidate into the party's BST. | O(log n) |
| `V <vid> <sid> ...`| **Vote:** Records a valid, blank, or invalid vote and updates counters. | - |
| `M <did>` | **Count Votes:** Calculates the electoral quota and elects candidates using the MinHeap. | - |
| `N` | **Form Parliament:** Merges the elected candidates into the final sorted list. | O(n) |
| `I`, `J`, `K`, `L` | **Print:** Outputs the state of districts, stations, parties, and the parliament. | - |
| `BU <vid> <sid>` | *(Bonus)* **Unregister Voter:** Deletes a voter while maintaining the complete tree structure. | - |
| `BF` | *(Bonus)* **Free Memory:** Safely deallocates all dynamically allocated memory. | - |
