import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class StudentGui extends JFrame {

	JTextArea studentTextArea		= new JTextArea (); 

JLabel 		studentLabel 		= new JLabel (" Name ");
JLabel 		student1Label 		= new JLabel (" Name ");
JTextField 	studenttextField 	= new JTextField (15);
JLabel 		idLabel 			= new JLabel (" ID ");
JTextField 	idtextField 		= new JTextField (4);


JButton		addButton 			= new JButton ("Add"); 
JButton		deleteButton 		= new JButton ("Delete"); 
JButton		displayAllButton 	= new JButton ("View All"); 
JButton		exitButton 			= new JButton ("Exit"); 
JButton     editButton        	= new JButton ("Edit");
JButton    editSaveButton    	= new JButton ("Save");
JButton		saveDataButton 		= new JButton ("File Save"); 
JButton		openDataButton 		= new JButton ("File Open"); 

private LinkedList <Student> studentLinkedList = new LinkedList <Student> (); 
private int editIndex;



public StudentGui () {
	setTitle("Student System");
	
	JPanel flow1Panel = new JPanel (new FlowLayout (FlowLayout.CENTER));
	JPanel flow2Panel = new JPanel (new FlowLayout (FlowLayout.CENTER));
	JPanel gridPanel = new JPanel (new GridLayout(2,1)); 
	
	 studentTextArea.setEditable (false);
	
	flow1Panel.add(studentLabel);
	flow1Panel.add(studenttextField);
	flow1Panel.add(idLabel);
	flow1Panel.add(idtextField);
	flow2Panel.add(addButton);
	flow2Panel.add(deleteButton);
	flow2Panel.add(displayAllButton);
	flow2Panel.add(exitButton);
	flow2Panel.add(editButton);
	flow2Panel.add(editSaveButton);
	flow1Panel.add(saveDataButton);
	flow1Panel.add(openDataButton);
	
	
	gridPanel.add(flow1Panel); 
	gridPanel.add(flow2Panel); 
	add (gridPanel, 			BorderLayout.SOUTH);
	add (studentTextArea,		BorderLayout.CENTER);
	
	addButton.addActionListener(event -> addStudent ());
	displayAllButton.addActionListener (event -> displayAll ());
    editButton.addActionListener       (event -> editStudent ());
    editSaveButton.addActionListener   (event -> editSaveStudent ());
    exitButton.addActionListener       (event -> exitApplication ());
    deleteButton.addActionListener     (event -> deleteStudent ());
    saveDataButton.addActionListener   (event -> saveData ());
    openDataButton.addActionListener   (event -> openData ());
   
}

private boolean isStudentIdInLinkedList (String idStr)
{
   boolean inList = false;

   for (Student stud : studentLinkedList)
   {
      if (stud.getId ().compareToIgnoreCase (idStr) == 0)
      {
         inList = true;
      }
   }

   return inList;
}


public void saveData () {
	JFileChooser fileChooser = new JFileChooser(); 
	fileChooser.setCurrentDirectory(new File("."));
	
	int saved = fileChooser.showSaveDialog(null);
	if(saved == JFileChooser.APPROVE_OPTION) {
		File file; 
		PrintWriter fileOut = null;
		file = new File(fileChooser.getSelectedFile().getAbsolutePath()); 
		try {
			fileOut = new PrintWriter(file);
			fileOut.println(studentTextArea.getText());
		} 
		
		catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		}
		finally {
			fileOut.close(); 
		}
	}
	
}


public void openData () {
	JFileChooser fileChooser = new JFileChooser(); 
	fileChooser.setCurrentDirectory(new File("."));
	
	int open = fileChooser.showOpenDialog(null);
	
	if(open == JFileChooser.APPROVE_OPTION) {
		File file = new File(fileChooser.getSelectedFile().getAbsolutePath()); 
		Scanner fileIn = null;
		try {
			fileIn = new Scanner(file);
			if(file.isFile()) {
				while(fileIn.hasNextLine()) {
					String Line = fileIn.nextLine()+"\n"; 
					studentTextArea.append(Line);
					
				}
				
			}
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		}
		finally {
			fileIn.close();
		}
		
		
				
	}
}


private void addStudent ()
{
   if (isStudentIdInLinkedList (idtextField.getText()) == true)
   {
      JOptionPane.showMessageDialog (StudentGui.this,
                           "Error: student ID is already in the database.");
   }
   else
   {
      try
      {
    	  Student stud = new Student (studenttextField.getText(),
    			  idtextField.getText());

         studentLinkedList.add (stud);

         displayAll ();
         JOptionPane.showMessageDialog (StudentGui.this,"Has Been Added " + 
                 stud);
         studenttextField.setText("");
         idtextField.setText("");


      }
      catch (StudentException error)
      {
         JOptionPane.showMessageDialog (StudentGui.this, error.toString ());
        


      }

   }
}

private void deleteStudent ()
{
   if (studentLinkedList.size() == 0)
   {
      JOptionPane.showMessageDialog (StudentGui.this,
                                     "Error: Database is empty.");
   }
   else if (isStudentIdInLinkedList (idtextField.getText()) == false)
   {
      JOptionPane.showMessageDialog (StudentGui.this,
                                    "Error: student ID is not in the database.");
   }
   else
   {
      for (int s = 0; s < studentLinkedList.size(); s++)
      {
         String currId = studentLinkedList.get (s).getId ();

         if (currId.compareToIgnoreCase (idtextField.getText()) == 0)
         {
            studentLinkedList.remove (s);
         }
      }

      displayAll ();

      studenttextField.setText(" ");
      idtextField.setText("");
   }
}

private void editStudent ()
{
   if (studentLinkedList.size() == 0)
   {
      JOptionPane.showMessageDialog (StudentGui.this,
                                     "Error: Database is empty.");
   }
   else if (isStudentIdInLinkedList (idtextField.getText()) == false)
   {
      JOptionPane.showMessageDialog (StudentGui.this,
                                     "Error: student ID is not in the database.");
   }
   else
   {
      editIndex = -1;

      for (int s = 0; s < studentLinkedList.size(); s++)
      {
         String currId = studentLinkedList.get (s).getId ();

         if (currId.compareToIgnoreCase (idtextField.getText()) == 0)
         {
            editIndex = s;
            s = studentLinkedList.size(); 
         }
      }

      if (editIndex >= 0)
      {
    	 editSaveButton.setEnabled   (true);
    	 editButton.setEnabled       (false);
         addButton.setEnabled        (false);
         deleteButton.setEnabled     (false);
         displayAllButton.setEnabled (false);
         exitButton.setEnabled       (false);

         studenttextField.setText (studentLinkedList.get (editIndex).getName () );
     
      }


   }

}

private void editSaveStudent ()
{

   studentLinkedList.get (editIndex).setName (studenttextField.getText() );
   studentLinkedList.get (editIndex).setId   (idtextField.getText() );

   displayAll ();

   studenttextField.setText ("");
   idtextField.setText   ("");

   editSaveButton.setEnabled   (false);
   editButton.setEnabled       (true);
   addButton.setEnabled        (true);
   deleteButton.setEnabled     (true);
   displayAllButton.setEnabled (true);
   exitButton.setEnabled       (true);
}


private void displayAll ()
{
   studentTextArea.setText ("");

   for (Student stud : studentLinkedList)
   {
studentTextArea.append (stud + "\n");
      JOptionPane.showMessageDialog (StudentGui.this,"Students " + 
              stud);
         }
}

private void exitApplication ()
{
   System.exit (0); 
}


public static void main (String[] args) {
	StudentGui gooey = new StudentGui (); 
	gooey.setVisible	(true);
	gooey.setSize	    (500,500);
	gooey.setLocation	(500,500);
	gooey.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	
}
}

