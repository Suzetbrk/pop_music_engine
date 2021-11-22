import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;


/* ComboBoxDemo2.java requires no other files. */
public class Display extends JPanel
                           implements ActionListener {
    static JFrame frame;
    JLabel result;
    String currentPattern;
    JComboBox patternList;
    JPanel patternPanel;
    LinkedList<String> dropdown = new LinkedList<String>();
    
    // The file processor reads in the songs and contains
    // high level interfaces to methods which manipulate the tree
    FileProcessor fp = new FileProcessor();

    public Display() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        Vector<String> suggest_list = new Vector<String>();

        //Set up the UI for selecting a pattern.
        JLabel patternLabel1 = new JLabel("Enter a singer name or");
        JLabel patternLabel2 = new JLabel("Select one from the suggested list:");

        patternList = new JComboBox(suggest_list);
        patternList.setEditable(true);
        patternList.addActionListener(this);

        //Create the UI for displaying result.
        JLabel resultLabel = new JLabel("Song list: ",
                                        JLabel.LEADING); //== LEFT
        result = new JLabel(" ");
        result.setForeground(Color.black);
        result.setBorder(BorderFactory.createCompoundBorder(
             BorderFactory.createLineBorder(Color.black),
             BorderFactory.createEmptyBorder(5,5,5,5)
        ));

        //Lay out everything.
        patternPanel = new JPanel();
        patternPanel.setLayout(new BoxLayout(patternPanel,
                               BoxLayout.PAGE_AXIS));
        patternPanel.add(patternLabel1);
        patternPanel.add(patternLabel2);
        patternList.setAlignmentX(Component.LEFT_ALIGNMENT);
        patternPanel.add(patternList);

        JPanel resultPanel = new JPanel(new GridLayout(0, 1));
        resultPanel.add(resultLabel);
        resultPanel.add(result);

        patternPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(patternPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(resultPanel);

        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

    } //constructor

    public void actionPerformed(ActionEvent e) {
    	
        JComboBox cb = (JComboBox)e.getSource();
        String newSelection = (String)cb.getSelectedItem();
        currentPattern = newSelection;//current Pattern is the string you get from input
        //System.out.println(currentPattern);
        reformatsuggestion();
    }

    /** Formats and displays suggestions. */
    public void reformatsuggestion() {
        try {
        	
        	// If the user types "clean" in the input window,
        	// clear out the saved artist list
        	if (currentPattern.equals("clean")){
        		dropdown.clear();
        		patternList.removeAllItems();
                patternList.setAlignmentX(Component.LEFT_ALIGNMENT);
                patternPanel.add(patternList);
                result.setForeground(Color.black);
                result.setText("clean");
        		
        	}
        	else
        	{
        		// currentPattern is the input pattern typed by the user
        		
        		// * todo* - Create a linked list of Strings to store search results
                LinkedList<String> searchresult = new LinkedList<String>();

                // * todo * - Perform a search in the tree for the desired
                //            set of artists name based on the input pattern
                //            A linked list of Strings which match the input String are returned
                searchresult = fp.search(currentPattern);

                // *todo * - If currentPattern is an artist's name, return the song list
                //           for the artist. Otherwise return an empty String
                String songlist = fp.FindArtist(currentPattern);
                Iterator<String> it = searchresult.iterator();
                String astring = new String();
                while (it.hasNext()){
                    astring = it.next();
                    if(!dropdown.contains(astring)){
                        patternList.addItem(astring);
                        dropdown.add(astring);
                    }
                }
           	
                patternList.setAlignmentX(Component.LEFT_ALIGNMENT);
                patternPanel.add(patternList);
                result.setForeground(Color.black);
                result.setText(songlist);
            
        	}
            
        } catch (IllegalArgumentException iae) {
            result.setForeground(Color.red);
            result.setText("Error: " + iae.getMessage());
        }
    }
    

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Searching Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new Display();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	Display newdisplay = new Display();
    	newdisplay.run();
    }
    	
    public void run(){

    // ** todo ** - Modify the location of your file
	final int NUMSONGS = 115840;
	fp.ReadSongFile("c:\\users\\tessier\\desktop\\hundredKsongs.txt", NUMSONGS);
  
    	
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        
    }
}