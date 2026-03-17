# Election System Simulation - Phase 01

This project implements a simulation of the Greek electoral system for the allocation of parliamentary seats. It was developed as part of the **HY240: Data Structures** course (Winter Semester 2024-25). It constitutes the 1st part of the programming assignment.

---

## Data Structures

* **Electoral Districts (Districts):** Static array of 56 positions. Each record contains information about the seats, blank/invalid votes, and pointers to the lists of stations and candidates.
* **Polling Stations (Stations):** Unsorted, singly-linked list within each electoral district.
* **Voters:** Unsorted, singly-linked list with a sentinel node for each polling station.
* **Parties:** Static array of 5 positions. Each party maintains a sorted, singly-linked list of its elected members (in descending order based on votes).
* **Candidates:** Doubly-linked list for each district, sorted in descending order based on the number of votes.
* **Parliament:** A sorted, singly-linked list of Members of Parliament (MPs), in descending order based on their vote count.

---

## ⚙️ Program Operations (Events)

| Command | Description |
| :--- | :--- |
| `A` | Announce elections. Initializes the main structures (Districts, Parties, Parliament). |
| `D <did> <seats>` | Create an electoral district. |
| `S <sid> <did>` | Create a polling station within the respective district's list. |
| `P <pid>` | Establish a new party and insert it into the parties array. |
| `C <cid> <did> <pid>`| Register a candidate in the district's doubly-linked candidates list. |
| `R <vid> <did> <sid>`| Register a voter in the polling station's list. |
| `U <vid>` | Unregister a voter by searching across the entire territory. |
| `E` | Eliminate empty polling stations in O(n) time. |
| `V <vid> <sid> <cid>`| Voting process; uses position swaps to maintain the sorted candidate list. |
| `M <did>` | Count votes for a district and calculate the electoral quota. |
| `G` | Allocate undistributed district seats. |
| `N` | Form parliament (merge the lists of elected candidates into the final list) in O(n) time. |
