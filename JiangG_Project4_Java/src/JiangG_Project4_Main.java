import java.io.*;
import java.util.*;


public class JiangG_Project4_Main{
    private static class treeNode {
        int key1; //- (int) key1
        int key2; //- (int) key2

        int rank; //- (int) rank // -1, 5, 1 or 2 or 3:
        // -1 means no father;
        // 5 means not yet know;
        // 1 means the 1st child of its father, 2 means 2nd child; etc.
        treeNode child1; //- (treeNode) child1
        treeNode child2; //- (treeNode) child2
        treeNode child3; //- (treeNode) child3
        treeNode father; // - (treeNode) father

        // Constructor
        public treeNode(int key1, int key2, int rank, treeNode child1, treeNode child2, treeNode child3, treeNode father) {
            //- constructor (…) // Create treeNode with given parameters
            this.key1 = key1;
            this.key2 = key2;
            this.rank = rank;
            this.child1 = child1;
            this.child2 = child2;
            this.child3 = child3;
            this.father = father;
        }

        private static void printNode(treeNode Tnode, PrintWriter treeFile) {
            if (Tnode == null) {
                return;
            }

            if (Tnode.child3 == null) {
                // Tnode is not a 3-node
                treeFile.print("(" + Tnode.key1 + ",-1," + Tnode.rank + ",");

                if (Tnode.child1 != null) {
                    treeFile.print(Tnode.child1.key1 + ",");
                } else {
                    treeFile.print("null,");
                }

                if (Tnode.child2 != null) {
                    treeFile.print(Tnode.child2.key1 + ",");
                } else {
                    treeFile.print("null,");
                }

                treeFile.print("null,");

                if (Tnode.father != null) {
                    treeFile.print(Tnode.father.key1 + ")\n");
                } else {
                    treeFile.print("-1)\n");
                }
            } else {
                // Tnode is a 3-node
                treeFile.print("(" + Tnode.key1 + "," + Tnode.key2 + "," + Tnode.rank + ",");

                if (Tnode.child1 != null) {
                    treeFile.print(Tnode.child1.key1 + ",");
                } else {
                    treeFile.print("null,");
                }

                if (Tnode.child2 != null) {
                    treeFile.print(Tnode.child2.key1 + ",");
                } else {
                    treeFile.print("null,");
                }

                if (Tnode.child3 != null) {
                    treeFile.print(Tnode.child3.key1 + ",");
                } else {
                    treeFile.print("null,");
                }

                if (Tnode.father != null) {
                    treeFile.print(Tnode.father.key1 + ")\n");
                } else {
                    treeFile.print("-1)\n");
                }
            }

            // 递归打印子节点
            printNode(Tnode.child1, treeFile);
            printNode(Tnode.child2, treeFile);
            printNode(Tnode.child3, treeFile);
        }


    }

    private static class Trees {
        static treeNode Root;

        // constructor
        public Trees() {
            this.Root = null;
        }

        // initialTree method
        private static treeNode initialTree(Scanner inFile, PrintWriter deBugFile) {
            // Step 0: deBugFile <- write "Entering initialTree () method"
            deBugFile.println("Entering initialTree () method");

            // Step 1: Root <- get a treeNode (-1, -1, -1, null, null, null, null)
            treeNode Root = new treeNode(-1, -1, -1, null, null, null, null);

            // Step 2: data1 <- read one data item from inFile
            //         data2 <- read one data item from inFile
            if (!inFile.hasNextInt()) {
                throw new IllegalArgumentException("Input file must contain at least two integers.");
            }
            int data1 = inFile.nextInt();
            if (!inFile.hasNextInt()) {
                throw new IllegalArgumentException("Input file must contain at least two integers.");
            }
            int data2 = inFile.nextInt();

            // deBugFile <- write "before swap data1 and data2 are: " // output data1 data2
            deBugFile.println("before swap data1 and data2 are: " + data1 + " " + data2);

            // if data2 < data1
            //     swap (data1, data2)

            if (data2 < data1) {
                int[] swapped = Trees.swap(data1, data2);
                data1 = swapped[0];
                data2 = swapped[1];
            }
            // deBugFile <- write "after swap data1 and data2 are " data1 data2 // to see if you swapped.
            deBugFile.println("after swap data1 and data2 are: " + data1 + " " + data2);

            // Step 3: newNode1 <- get a treeNode (data1, -1, 1, null, null, null, Root)
            treeNode newNode1 = new treeNode(data1, -1, 1, null, null, null, Root);

            // Step 4: newNode2 <- get a treeNode (data2, -1, 2, null, null, null, Root)
            treeNode newNode2 = new treeNode(data2, -1, 2, null, null, null, Root);

            // Step 5: Root.child1 <- newNode1
            //         Root.child2 <- newNode2
            //         Root.key1 <- data2
            Root.child1 = newNode1;
            Root.child2 = newNode2;
            Root.key1 = data2;

            // Step 6: printNode (Root, deBugFile) // write a caption indicating this is the Root node.
            treeNode.printNode(Root, deBugFile);

            // Step 7: deBugFile <- write "Exiting initialTree () method"
            deBugFile.println("Exiting initialTree () method");

            // Step 8: return Root
            return Root;

        }

        private static int[] swap(int data1, int data2) {
            int temp;
            temp = data1;
            data1 = data2;
            data2 = temp;
            return new int[]{data1, data2};
        }

        // build23Tree method
        private static void build23Tree(Scanner inFile, treeNode Root, PrintWriter deBugFile) {
            // Step 0: deBugFile ← write “Entering build23Tree () method”
            deBugFile.println("Entering build23Tree() method");

            // Step 1: data ← read one data item from inFile
            while (inFile.hasNext()) {
                int data = inFile.nextInt();

                // Step 2: Spot ← findSpot (Root, data, deBugFile)
                treeNode Spot = findSpot(Root, data, deBugFile);

                // Step 3: repeat step 1 to step 2 while Spot == null
                while (Spot == null) {
                    data = inFile.nextInt();
                    Spot = findSpot(Root, data, deBugFile);
                }

                // Step 4: deBugFile ← write “in build23Tree (), printing Spot info.”
                deBugFile.println("In build23Tree(), printing Spot info:");
                treeNode.printNode(Spot, deBugFile);

                // Step 5: leafNode ← get a treeNode (data, -1, 5, null, null, null, null)
                treeNode leafNode = new treeNode(data, -1, 5, null, null, null, null);

                // Step 6: treeInsert (Spot, leafNode, deBugFile)
                treeInsert(Spot, leafNode, deBugFile);

                // Step 7: deBugFile ← write “In build23Tree; printing preOrder () after one treeInsert”
                deBugFile.println("In build23Tree; printing preOrder() after one treeInsert");
                preOrder(Root, deBugFile);
            }

            // Step 8: repeat step 1 to step 6 until inFile is empty

            // Step 9: close all files
            inFile.close();
            deBugFile.close();
            System.out.println("Done building 2-3 Tree!");
        }

        private static treeNode findSpot(treeNode Spot, int data, PrintWriter deBugFile) {
            //step0: deBugFile <- write "Entering findSpot() method
            //       deBugFile <- write "Spot's key1 and key2 and data are"
            deBugFile.println("Entering findSpot() method");
            deBugFile.println("Spot's key1 and key2 and data are" + " " + Spot.key1 + " " + Spot.key2 + "  " + data); // output Spot's key1, spot's key2, and data

            // Step 1: if Spot.child1 == null // meaning Spot is a leaf node. this is a boundary case, but it should not occur.
            // deBugFile  write " In findSpot () You are at leaf level, you are too far down the tree!! "
            // return NULL
            if (Spot.child1 == null) {
                // meaning Spot is a leaf node. This is a boundary case, but it should not occur
                deBugFile.println("In findSpot() You are at leaf level, you are too far down the tree!");
                return null;
            }
            // Step 2: if (data == Spot.key1) or (data == Spot. key2)
            // deBugFile  write "In findSpot (): data is already in Spot’s keys, no need to search further!"
            // return NULL
            if (data == Spot.key1 || data == Spot.key2) {
                deBugFile.println("In findSpot(): data is already in Spot's keys, no need to search further!");
                return null;
            }


            //Step 3: if isLeaf (Spot.child1) // This is the level Spot should be.
            if (isLeaf(Spot.child1)) {
                if (data == Spot.child1.key1 || data == Spot.child2.key1) {
                    deBugFile.println("In findSpot(): data is already in a leaf node.");
                    return null;
                } else {
                    return Spot;
                }
            } else { // Spot is not yet at the level above the leaf, search needs to continue.
                if (data < Spot.key1) {
                    return findSpot(Spot.child1, data, deBugFile); // go down to 1st subtree
                } else if (Spot.key2 == -1 || data < Spot.key2) {
                    return findSpot(Spot.child2, data, deBugFile); // go down to 2nd subtree
                } else if (Spot.key2 != -1 && data >= Spot.key2) {
                    return findSpot(Spot.child3, data, deBugFile); // go down to 3rd subtree
                } else {
                    deBugFile.printf("In findSpot(), something is wrong about data: %d%n", data); // output data here.
                    return null;
                }
            }


        }

        private static boolean isLeaf(treeNode Tnode) {

            if (Tnode == null) {
                return false;
            }
            return Tnode.child1 == null && Tnode.child2 == null && Tnode.child3 == null;
        }

        private static void treeInsert(treeNode Spot, treeNode newNode, PrintWriter deBugFile) {
            //Step 0: deBugFile <- write " Entering treeInsert() method
            deBugFile.println("\n=============================================================");
            deBugFile.println("Entering treeInsert () method");

            if (Spot == null) {
                deBugFile.println("In treeInsert (), Spot is null, something is wrong");
                return;
            } else {
                deBugFile.println("In treeInsert () -> Printing Spot  info");
                treeNode.printNode(Spot, deBugFile);
                deBugFile.println("\nIn treeInsert () -> Printing newNode info\n");
                treeNode.printNode(newNode, deBugFile);
            }
            // Step 1: if Spot.key2 == -1
            // count  2
            // else
            // count  3
            int count;
            if (Spot.key2 == -1) {
                count = 2;
            } else {
                count = 3;
            }
            // Step 2: deBugFile  write " In treeInsert () method; Spot kids count is " // output count
            deBugFile.printf("In treeInsert () method; Spot kids count is  %d%n ", count);

            if (count == 2) {
                spotHash2kidCase(Spot, newNode, deBugFile);
            } else if (count == 3) {
                spotHas3kidsCase(Spot, newNode, deBugFile);
            }
            deBugFile.println("Newly inserted node:");
            preOrder(newNode, deBugFile);
            deBugFile.println(" ");


            deBugFile.println("Leaving treeInsert () method");
            deBugFile.println("=============================================================");


        }


        private static void spotHash2kidCase(treeNode Spot, treeNode newNode, PrintWriter deBugFile) {
            // Step 1: deBugFile  write "Entering spotHas2kidCase () method"
            // deBugFile  write "In spotHas2kidCase () method; Spot’s rank is " // write Spot’s rank here.
            deBugFile.println("=============================================================");
            deBugFile.println("Entering spotHas2kidCase() method");
            deBugFile.printf("In spotHas2kidCase() method; Spot's rank is %d%n", Spot.rank); // write Spot's rank here.
            // Step 2: if newNode.key1 < Spot.child2.key1
            // Spot.child3  Spot.child2
            // Spot.child2  newNode
            // else
            // Spot.child3  newNode
            if (newNode.key1 < Spot.key1) {
                Spot.child3 = Spot.child2;
                Spot.child2 = newNode;
            } else Spot.child3 = newNode;
            // Step 3: if Spot.child2.key1 < Spot.child1.key1 // then swap the key1 of the two nodes.
            // tmpNode  Spot.child1
            // Spot.child1  Spot.child2
            // Spot.child2  tmpNode
            if (Spot.child2.key1 < Spot.child1.key1) { // swap the key1 of the two nodes
                treeNode tmpNode = Spot.child1;
                Spot.child1 = Spot.child2;
                Spot.child2 = tmpNode;


            }
            // Step 4: Spot.child1.father  Spot
            // Spot.child1.rank  1
            // Spot.child2.father Spot
            // Spot.child2.rank  2
            // Spot.child3.father  Spot
            // Spot.child3.rank  3
            Spot.child1.father = Spot;
            Spot.child1.rank = 1;
            Spot.child2.father = Spot;
            Spot.child2.rank = 2;
            Spot.child3.father = Spot;
            Spot.child3.rank = 3;

            // Step 5: updateKeys (Spot, deBugFile)
            updateKeys(Spot, deBugFile);
            // Step 6: if Spot.rank > 1
            // updateKeys (Spot.father, deBugFile)
            if (Spot.rank > 1) {
                updateKeys(Spot.father, deBugFile);
            }
            //Step 7: deBugFile  write "Leaving spotHas2kidCase () method"
            deBugFile.println("Leaving spotHash2kidCase() method ");
            deBugFile.println("=============================================================");
        }

        private static void spotHas3kidsCase(treeNode Spot, treeNode newNode, PrintWriter deBugFile) {
            deBugFile.println("=============================================================");
            deBugFile.println("Entering spotHas3kidsCase() method ");
            deBugFile.println("In spotHas3kidsCase() method； Spot's rank is " + Spot.rank); // write Spot's rank here.
            treeNode sibling = new treeNode(-1, -1, 5, null, null, null, null);

            // Step 2: if newNode.key1 > Spot.child3.key1, then swap the key1 of the two nodes.
            if (newNode.key1 > Spot.child3.key1) {
                sibling.child2 = newNode;
                sibling.child1 = Spot.child3;
                Spot.child3 = null;
            } else if (newNode.key1 < Spot.child3.key1) {
                sibling.child2 = Spot.child3;
                Spot.child3 = newNode;
            } else {
                sibling.child2 = newNode;
                Spot.child3 = null;
            }

            // Step 3:
            if (Spot.child3 != null) {
                if (Spot.child3.key1 > Spot.child2.key1) {
                    sibling.child1 = Spot.child3;
                    Spot.child3 = null;
                } else {
                    sibling.child1 = Spot.child2;
                    Spot.child2 = newNode;
                }
            } else if (Spot.child2.key1 < Spot.child1.key1) {
                treeNode tmpNode = Spot.child1;
                Spot.child1 = Spot.child2;
                Spot.child2 = tmpNode;
            }

            // Step 4:
            Spot.child1.father = Spot;
            Spot.child1.rank = 1;
            Spot.child2.father = Spot;
            Spot.child2.rank = 2;
            Spot.child3 = null;

            // Step 5:
            sibling.child1.father = sibling;
            sibling.child1.rank = 1;
            sibling.child2.father = sibling;
            sibling.child2.rank = 2;
            sibling.child3 = null;

            // Step 6:
            updateKeys(Spot, deBugFile);
            updateKeys(sibling, deBugFile);

            // Step 7:
            if (Spot.rank == -1 && Spot.father == null) {
                Root = makeNewRoot(Spot, sibling, deBugFile);
            } else {
                treeInsert(Spot.father, sibling, deBugFile);
            }

            // Step 8:
            if (Spot.rank > 1) {
                updateKeys(Spot.father, deBugFile);
            }

            deBugFile.println("Leaving spotHas3kidsCase() method ");
            deBugFile.println("=============================================================");
        }


        private static treeNode makeNewRoot(treeNode Spot, treeNode Sibling, PrintWriter deBugFile) {
            //Step 0: deBugFile ← “Entering makeNewRoot () method.”
            deBugFile.println("=============================================================");
            deBugFile.println("Entering makeNewRoot () method.");

            //step 1
            treeNode newRoot = new treeNode(-1, -1, -1, null, null, null, null);
            //newRoot ← get a treeNode (-1, -1, -1, null, null, null, null)
            newRoot.child1 = Spot; //  newRoot.child1 ← Spot
            newRoot.child2 = Sibling;//  newRoot.child2 ← Sibling
            newRoot.child3 = null;//  newRoot.child3 ← null

            // Set new keys for newRoot
            newRoot.key1 = findMinLeaf(Sibling);//  newRoot.key1 ← findMinLeaf(Sibling)
            newRoot.key2 = -1;//  newRoot.key2 ← -1

            // Set father and rank for Spot and Sibling
            Spot.father = newRoot;//  Spot.father ← newRoot
            Spot.rank = 1; //  Spot.rank ←1
            Sibling.father = newRoot;//  Sibling.father ← newRoot
            Sibling.rank = 2;//  Sibling.rank ← 2
            //Step 2: deBugFile ← “Leaving makeNewRoot () method.”
            deBugFile.println("Leaving makeNewRoot () method. ");
            deBugFile.println("=============================================================");
            return newRoot;//Step 3: return newRoot
        }

        private static int findMinLeaf(treeNode Tnode) {
            if (Tnode == null) { // Step 1: if Tnode is null
                return -1; // return -1
            }
            treeNode current = Tnode;
            while (current.child1 != null) { // Step 2: while current has a child1
                current = current.child1; // set current to its child1
            }
            return current.key1; // Step 3: return the key of the leaf node
        }





        private static void preOrder(treeNode Tnode, PrintWriter treeFile) {
            if (Tnode == null) {
                return;
            }
            // print current node information
            treeNode.printNode(Tnode, treeFile);

            // recursively print left child subtree
            preOrder(Tnode.child1, treeFile);

            // recursively print middle child subtree
            preOrder(Tnode.child2, treeFile);

            // recursively print right child subtree
            preOrder(Tnode.child3, treeFile);
        }


        private static void print23Tree(treeNode root, PrintWriter treeFile) {
            Queue<treeNode> q = new LinkedList<>();
            q.add(root);

            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    treeNode node = q.poll();
                    if (node.child1 != null) {
                        q.add(node.child1);
                    }
                    if (node.child2 != null) {
                        q.add(node.child2);
                    }
                    if (node.child3 != null) {
                        q.add(node.child3);
                    }
                    if (node.father != null) {
                        treeFile.print("(" + node.key1 + "," + node.key2 + "),");
                    }
                }
                treeFile.println();
            }
        }

        private static void preOrderList(treeNode node, List<treeNode> list) {
            if (node == null) {
                return;
            }
            list.add(node);
            preOrderList(node.child1, list);
            preOrderList(node.child2, list);
            preOrderList(node.child3, list);
        }



        private static void updateKeys(treeNode Tnode, PrintWriter deBugFile) {
            deBugFile.println("=============================================================");
            deBugFile.print("Entering updateKeys() method ");
            if (Tnode == null) {
                return;
            }
            Tnode.key1 = Trees.findMinLeaf(Tnode.child2);
            Tnode.key2 = Trees.findMinLeaf(Tnode.child3);
            deBugFile.print(Tnode.key1 + " " + Tnode.key2 + "\n");

            if (Tnode.rank > 1) {
                updateKeys(Tnode.father, deBugFile);
            }
            deBugFile.println("Leaving updateKeys() method");
            deBugFile.println("=============================================================");
        }

    }




    public static void main(String[] args) throws IOException {
        // Step 0: open files
        Scanner data1 = new Scanner(new File(args[0]));
        PrintWriter treeFile = new PrintWriter(new FileWriter(args[1]));
        PrintWriter deBugFile = new PrintWriter(new FileWriter(args[2]));

        // Step 1: build initial tree with first two items

        treeNode root = Trees.initialTree(data1, deBugFile);

        // Step 2: build the 2-3 tree

       // if (root.father != null) {
            treeNode.printNode(root.father, treeFile);
      //  }
        Trees.build23Tree(data1, root, deBugFile);
        // Trees.updateKeys(root,treeFile);

        // Step 3: print tree to treeFile using preOrder traversal
        //  treeNode.printNode(root, treeFile);

        //Trees.preOrder(root, treeFile);

        Trees.print23Tree(root,treeFile);
        // Step 4: close all files
        data1.close();
        treeFile.close();
        deBugFile.close();
    }

}