import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/** 
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener
{
	//instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;

	//application instance variables
	//including the 'core' part of the text file filename
	//some way of indicating whether encoding or decoding is to be done
	private MonoCipher mcipher;
	private VCipher vcipher;
	
	private String keyword, fileName;
	private boolean encode;
	
	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{
		this.setSize(400,150);
		this.setLocation(100,100);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
		
		keyword = "";
		fileName = "";
		encode = false;
	}
	
	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{
		//top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		top.add(keyField);
		this.add(top,BorderLayout.NORTH);
		
		//middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);
		
		//bottom panel is green and contains 2 buttons
		
		bottom = new JPanel();
		//create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}
	
	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e)
	{
		// boolean returns on the methods mean that the program will not be able to continue to next stage unless everything is okay
		if (getKeyword()) {
			if (processFileName()) {
			    if (e.getSource() == monoButton) {
		    			processFile(false);
		    			System.exit(0);
			    }
			    else if (e.getSource() == vigenereButton) {
		    			processFile(true);
		    			System.exit(0);
			    }
			}
		}
	}
	
	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	private boolean getKeyword()
	{
		// boolean to be returned if a valid keyword has been entered
		boolean wordValid = true;
		// Gets the keyword from the field
		keyword = keyField.getText();
		// Error message if needed
		String errorMessage = "";
		// Checks a keyword was entered
		if (keyword.equals("")) {
			errorMessage = "You have not entered a keyword.";
			wordValid = false;
		}
		else {
			// Checks if the keyword is all upper case
			for (int i = 0; i < keyword.length(); i++) {
				if (!Character.isUpperCase(keyword.charAt(i))) {
					errorMessage = "The keyword needs to be all upper case.";
					wordValid = false;
				}
			}
			// Checks that there are no duplicate letters in the keyword
			for (int i = 0; i < keyword.length(); i++) {
				for (int a = i+1; a < keyword.length(); a++) {
					if (keyword.charAt(i) == keyword.charAt(a)) {
						errorMessage = "The keyword cannot have duplicate letters.";
						wordValid = false;
					}
				}
			}
		}
		// Clears text field
		keyField.setText("");
		// Error message if word is not valid
		if (!wordValid) {
			JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
		}
		// Returns boolean value
	    return wordValid; 
	}
	
	/** 
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are extracted
	 * If the filename is invalid, a message is produced 
	 * The details obtained from the filename must be remembered
	 * @return whether a valid filename was entered
	 */
	private boolean processFileName()
	{
		// boolean for if everything is valid
		boolean fileValid = true;
		// Variables for file name and the lines that will be read
		fileName = messageField.getText();
		int fileNameLength = fileName.length();
		// Error message variable
		String errorMessage = "";
		// char variable for the file type
		char fileType = 0;
		// If a file name hasn't been entered error message
		if (fileName.equals("")) {
			errorMessage = "You have not entered a file name.";
			fileValid = false;
		}
		else {
			fileType = fileName.charAt(fileNameLength-1);
			// Checks the end of the file is a P or C. 
			if (fileType == 'P') {
				encode = true;
			}
			else if (fileType == 'C') {
				encode = false;
			}
			else {
				errorMessage = "The file does not end in a P or a C.";
				fileValid = false;
			}
		}
		// Clear text field
		messageField.setText("");
		// J Option Error if file is not valid 
		if (!fileValid) {
			JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
		}
		// Return value
	    return fileValid; 
	}
	
	/** 
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * and written to the output text file
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return whether the I/O operations were successful
	 */
	private boolean processFile(boolean vigenere)
	{
		// boolean for operation success
		boolean processSuccess = true;
		// message for if there is an error
		String errorMessage = "";
		// Adding .txt to the file name
		String fullFile = fileName + ".txt";
		// Letter frequency object for report
		LetterFrequencies letterReport = new LetterFrequencies();
		// Create relevant cipher object
		if (vigenere) {
			vcipher = new VCipher(keyword);
		}
		else {
			mcipher = new MonoCipher(keyword);
		}
		// Name out put file
		String outputFile = "";
		if (encode) {
			outputFile = fileName.substring(0, fileName.length() - 1) + "C.txt";
		}
		else {
			outputFile = fileName.substring(0, fileName.length() - 1) + "D.txt";
		}
		//Read through buffered reader and writer
		try {
			FileReader fr = new FileReader(fullFile);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(outputFile);
			// Variable to check if there is another character in the file 
			int nextChar = 0;
			// While loop for reading and writing
			while (nextChar != -1) {
				nextChar = br.read();
				// Current character in file
				char readChar = (char) nextChar;
				// If the file is to be encoded or decoded 
				if (encode) {
					if (vigenere) {
						readChar = vcipher.encode(readChar);
					}
					else {
						readChar = mcipher.encode(readChar);
					}	
				}
				else {
					if (vigenere) {
						readChar = vcipher.decode(readChar);
					}
					else {
						readChar = mcipher.decode(readChar);
					}
				}
				// Adds to report
				letterReport.addChar(readChar);
				// Writes to file
				fw.write(readChar);
			}
			System.out.println("\nEnd of file");
			// Close readers and writers
			fr.close();
			br.close();
			fw.close();
		}	
		// File not found
		catch (FileNotFoundException e) {
			processSuccess = false;
			errorMessage = "File not found.";
		}
		catch (IOException e) {
			processSuccess = false;
			errorMessage = "File read error.";
		}
		// Makes sure everything is okay up until this point
		if (processSuccess) {
			// File write the report on letter frequencies
			try {
				FileWriter report = new FileWriter(fileName.substring(0, fileName.length() - 1) + "F.txt");
				report.write(letterReport.getReport());
				report.close();
			}
			catch (IOException e) {
				processSuccess = false;
				errorMessage = "Error writing frequency file.";
			}
		}
		// If there is an error
		if (!processSuccess) {
			JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
		}
		// Return value
	    return processSuccess;  
	}
}
