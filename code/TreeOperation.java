import java.util.*;

public class TreeOperation {
	
  
	// This method traverses the tree starting from the root. All name
	// Strings that are found are returned in a LinkedList of Strings.
	// The method uses recursion to simplify the traversal.
	// Each call to this method adds one character (of node) and all the child characters
	public LinkedList<String> traverse(String partialName, LinkedList<String> nameList, TreeNode node) {
			// Make a copy of the current subname since we will be
		    // adding more characters to it.
		
        	String newPartialName = new String(partialName);

        	// Add the character of the current node
        	newPartialName += node.nodevalue;

        	// Iterate over all the children of this node. 
        	// Each child will have newPartialName as the front part
        	// of its name
        	LinkedList<TreeNode> childlist = node.childList;

            if (childlist.isEmpty()) {
                // If node is a leaf then add the String to the nameArray and return
                nameList.add(newPartialName); 
            }
            else {
                // Else iterate through the children in the node and recursively call newTraverse
                Iterator<TreeNode> it = childlist.iterator();
                while (it.hasNext()){
                    traverse(newPartialName, nameList, it.next());
                }
            }
            return nameList;
        }

	// This method provides a cleaner interface to the Display class
	public LinkedList<String> traverse(TreeNode root){
            String partialName = "";
            LinkedList<String> nameList = new LinkedList<String>();
            return (traverse(partialName, nameList, root));
        }

	// Add a new name to the tree. The method traverses the current
	// tree. If the artist name is already included, then the song title
	// is added to the existing song list. Otherwise, new nodes are added 
	// to the tree and the title is added to the leaf node
	public void addnewname(String artist, String title, TreeNode root){
            final int ROOT = -1;

            // Convert the artist name to a character array
            char[] chararray = artist.toCharArray();

            // If artist name is null, then return
            if (chararray.length == 0)
            	return;
            
            // Start at the current location
            TreeNode current = root;
            int position = ROOT;
            int i;
            TreeNode anode = null;

            // This loop determines how much of the artist name
            // is already in the tree. If a character in the name 
            // are found, try the next one. Continue until the whole
            // name is tried or until a character isn't found
            for(i=0; i<chararray.length;i++){
                boolean findmatch = false;

                // Iterate over the tree looking for nodes which 
                // match the next character in the name
                Iterator<TreeNode> it = current.childList.iterator();
                while(it.hasNext()){
                    anode = it.next();
                    if (anode.nodevalue==chararray[i]){
                        current = anode;
                        findmatch = true;
                        break;
                    }
                }

                // If artist name isn't found, add it to the tree at the current location
                if (findmatch == false){
                	position = i;
                	break;
                }
            }

            // If the name is already present in the tree, add the title to the stored title
            if (i == chararray.length) 
            	anode.appendTitle(title);
            
            // Test to make sure the character string isn't a null string
            else if (position != ROOT){// add left char in chararray into the tree
            	// Repetitively add nodes to the tree for each character in 
            	// in artist name which wasn't previously in the tree
              	for (i = position; i<chararray.length;i++) {
                	TreeNode newnode;
                	// If last node, then include song title in node
                	if ((i+1) == chararray.length)
                		// Add the title to the leaf node
                		newnode = new TreeNode(chararray[i], title);
                	else 
                		newnode = new TreeNode(chararray[i], "");
                	current.childList.add(newnode);
                    current = newnode;
                }
            }
	}


	// This method returns the list of songs for a specific artist
	// The method traverses the tree until the leaf is found for the 
	// artist. If the artist is not found, a null String is returned
	public String searchsong(TreeNode root, String artistname){
        char[] chararray = artistname.toCharArray();
        TreeNode current = root;
        boolean findmatch = false;
        int i;
        TreeNode anode = null;
        
        // If the artist name is null, return an empty String
        if (chararray.length == 0)
        	return ("");

        // Iterate until all the characters in the artist's
        // name have been found in the tree.
        for(i=0; i<chararray.length; i++){
            findmatch = false;
            Iterator<TreeNode> it = current.childList.iterator();
            while(it.hasNext()){
                anode = it.next();
                if (anode.nodevalue==chararray[i]){
                    current = anode;
                    findmatch = true;
                    break;
                }
            }

            if (findmatch == false){ // if no match found return ""
                return ("");
            }	
        }

        // We must be at the leaf. Return its title list
        return (anode.getTitlelist());
  
    }
	
    // This method returns a LinkedList of artist Strings which
	// name the substring in "substring". For example, if substring 
	// is "La", all names which being with "La" will be returned.
	public LinkedList<String> searchnode(TreeNode root, String substring){
            char[] chararray = substring.toCharArray();
            TreeNode current = root;
            boolean findmatch = false;
		
            // Traverse the tree looking for the node at the bottom of the substring
            // If character i doesn't match, then all the characters
            // before it must have matched. 
            
            // current indicates the last node which successfully matched
            // a character in the input substring
            for(int i=0; i<chararray.length; i++){
                findmatch = false;
                Iterator<TreeNode> it = current.childList.iterator();
                while(it.hasNext()){
                    TreeNode anode = it.next();
                    if (anode.nodevalue==chararray[i]){
                        current = anode;
                        findmatch = true;
                        break;
                    }
                }

                // A character mismatch has occurred. 
                if (findmatch == false){ 
                    break;
                }	
            }

            // search result
            // Variable "current" contains the target node

            LinkedList<String> searchresult = new LinkedList<String>();

            // Locate all the names that have the common
            // substring which was found above. Traverse the tree
            // and add each new name to the LinkedList of Strings
            if (current.nodevalue!=root.nodevalue && findmatch){
                LinkedList<String> namelist = new LinkedList<String>();
                namelist = traverse(current);

                // Prune out the leading character			
                substring = substring.substring(0, substring.length()-1);
			
                Iterator<String> itname = namelist.iterator();
                String aname = new String();
                while(itname.hasNext()){
                	aname = itname.next();
                	aname = substring.concat(aname);
                    searchresult.add(aname);
                }
            }
            return searchresult;
        }
}



