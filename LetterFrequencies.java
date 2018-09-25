/**
 * Programming AE2
 * Processes report on letter frequencies
 */
public class LetterFrequencies
{
	/** Size of the alphabet */
	private final int SIZE = 26;
	
	/** Count for each letter */
	private int [] alphaCounts;
	
	/** The alphabet */
	private char [] alphabet; 
												 	
	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};

	/** Character that occurs most frequently */
	private char maxCh;

	/** Total number of characters encrypted/decrypted */
	private int totChars;
	
	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies()
	{
		// Fill alphabet array
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++) {
			alphabet[i] = (char)('A' + i);
		}
		// Instantiate alpha count array with zeros
		alphaCounts = new int [SIZE];
		for (int i = 0; i < SIZE; i++) {
			alphaCounts[i] = 0;
		}
		// Instantiate other variables
		maxCh = 0;
		totChars = 1;
	}
		
	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public void addChar(char ch)
	{
	    for (int i = 0; i < SIZE; i++) {
	    		if (ch == alphabet[i]) {
	    			alphaCounts[i]++;
	    			// Increment total character count 
	    			totChars++;
	    		}
	    }
	}
	
	/**
	 * Gets the maximum frequency
	 * @return the maximum frequency
	 */
	private double getMaxPC()
    {
		// Variable to store max frequency
		int maxFreq = 0;
		// Loop 
		for (int i = 0; i < SIZE; i++) {
			if (alphaCounts[i] > maxFreq) {
				maxFreq = alphaCounts[i];
				maxCh = alphabet[i];
			}
		}
	    return maxFreq; 
	}
	
	/**
	 * Returns a String consisting of the full frequency report
	 * @return the report
	 */
	public String getReport()
	{
		// Calls the get max freq method
		getMaxPC();
		// String builder to build the output to file
		StringBuilder report = new StringBuilder();
		// Add headers for columns and title
		report.append("LETTER ANALYSIS\n\rLetter\tFreq\tFreq%\tAvgFreq%\tDiff\r\n");
		// Loop to add info to report
		for (int i = 0; i < SIZE; i++) {
			// Work out frequency for the particular letter
			double freq = ((double)alphaCounts[i] / totChars) * 100;
			// Work out difference between frequency and average frequency
			double diff = freq - avgCounts[i];
			// Append all to the report
			report.append(alphabet[i] + "\t" + alphaCounts[i] + "\t" + String.format("%5.1f", freq) + "\t" + avgCounts[i] + "\t" + String.format("%5.1f", diff) + "\r\n\r\n");		
		}
		// Most frequent letter
		report.append(String.format("The most frequent letter is %c at %5.1f%%", maxCh, (getMaxPC() / totChars) * 100));
		// Return the report 
		return report.toString();  
	}
}
