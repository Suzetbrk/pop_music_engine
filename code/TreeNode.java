import java.util.*;

public class TreeNode {
	char nodevalue;
	String titlelist;
	LinkedList<TreeNode> childList = null;
	
	// TreeNode contains:
	// nodevalue: the character associated with the node
	// childList: A list of its children
	// titlelist: a list of all titles for the artist, delimited by commas 
	public TreeNode(char n, String mytitle){
		nodevalue = n;
		childList = new LinkedList<TreeNode>();
		titlelist = mytitle;
	}
	
    public String getTitlelist() {
        return titlelist;
    }

    public void appendTitle(String title) {
        titlelist = titlelist.concat("; ");
        titlelist = titlelist.concat(title);
    }
}
