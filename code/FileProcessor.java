// package fileRead;

import java.io.BufferedReader;
import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class FileProcessor {

	// The TreeNode contains the information for each node in the tree
	// A TreeNode should contain a LinkedList<TreeNode> to its children,
	//    and a character. 
	// A leaf TreeNode should contain a String which lists the songs.
	public static TreeNode root = new TreeNode('@', "");

	// Return the song list associated with an artist. 
	// If the artist is not found, then return a null String
    public String FindArtist(String currentPattern)
    {
    	TreeOperation newop = new TreeOperation();
    	return (newop.searchsong(root, currentPattern));
    }

    // Look for all names which include "inputname" 
    // For example "La" would match "Lady GaGa" and 
    // "Lady Antebellum". Return all matching Strings in a 
    // LinkedList<String>
	public LinkedList<String> search(String inputname){
		TreeOperation newop = new TreeOperation();
	
		// Implement the search method in the TreeOperation 
		// class because it has more access to the data 
		LinkedList<String> result=newop.searchnode(root, inputname);
		return result;
		
	}

	// Read in the song file and build the tree
	public void ReadSongFile(String filename, int numsongs){
		File file = new File(filename);
		TreeOperation op = new TreeOperation();

		int i=0;
		try{
			FileReader file_reader=new FileReader(file);
			BufferedReader buf_reader = new BufferedReader (file_reader);
			while(i<numsongs){
				String line=buf_reader.readLine();
				String[] data=line.split(",");// in csv file, attributes are seperate using ","
				//transfer string to float and int

				data[1].replaceAll("\\s+", "");
				data[2].replaceAll("\\s+", "");
				
				// Add a new name to the tree
 				op.addnewname(data[2], data[1], root);
				i++;
			}
            //op.traverse(root);
			buf_reader.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}








