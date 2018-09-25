import java.util.Arrays;

/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */
public class VCipher
{
	private char [] alphabet;   //the letters of the alphabet
	private final int SIZE = 26;
	private char [][] cipher;
	private int encode, decode; // counters for encoding and decoding
	private int kwLength;
  
	/** 
	 * The constructor generates the cipher
	 * @param keyword the cipher keyword
	 */
	public VCipher(String keyword)
	{
		// Instantiate counters
		encode = 0;
		decode = 0;
		// Keyword length
		kwLength = keyword.length();
		// String to check 2D array
		String check = "";
		// Fill alphabet array
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++) {
			alphabet[i] = (char)('A' + i);
		}
		// Keyword variable, making sure its upper case
		String keyWord = keyword.toUpperCase();
		// Fill cipher 2D array
		// keyword length rows, alphabet columns
		cipher = new char [kwLength][SIZE];
		// For loop cycle through length of keyword
		for (int i = 0; i < kwLength; i++) {
			// Makes the first letter of every row a letter of the keyword
			cipher[i][0] = keyWord.charAt(i);
			check += cipher[i][0];
			// Cycle trough rest of the alphabet
			for (int a = 1; a < SIZE; a++) {
				// When the row gets to Z need to make it go back to A
				if (cipher[i][0] + a <= 'Z') {
					// Adds the letters following the letter of the keyword
					cipher[i][a] = (char)(cipher[i][0] + a);	
				}
				else {
					// Loop to find out where in the array Z is
					int zPos = 0;
					for (int b = 0; b < SIZE - 1; b++) {
						if (cipher[i][b] == 'Z') {
							zPos = b;
						}
					}
					// Start the cipher array putting in again
					cipher[i][a] = (char)('A' + a - (zPos + 1));
				}
				check += cipher[i][a];
			}
			check += "\r\n";
		}
		// Prints out arrays
		System.out.println(alphabet);
		System.out.println(check);
	}
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */	
	public char encode(char ch)
	{
		// returned character 
		char encodedReturn = 0;
		// Makes sure only encoding letters and not punctuation etc. 
		if (ch < 'A' && ch < 'Z') {
			return ch;
		}
		else {
			// Reset counter if at the end of cipher
			if (encode >= kwLength) {
				encode = 0;
			}	
			// Loop through alphabet and assign the code
			for (int i = 0; i < SIZE; i++) {
				if (ch == alphabet[i]) {
					encodedReturn = cipher[encode][i];
					encode++;
				}
			}
		}
	    return encodedReturn;
	}
	
	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch)
	{
		// returned character 
		char decodedReturn = 0;
		// Makes sure only encoding letters and not punctuation etc. 
		if (ch < 'A' && ch < 'Z') {
			return ch;
		}
		else {
			// Reset counter at end of cipher
			if (decode >= kwLength) {
				decode = 0;
			}
			// Loop through cipher
			for (int i = 0; i < SIZE; i++) {
				if (ch == cipher[decode][i]) {
					decodedReturn = alphabet[i];
					decode++;
				}
			}
		}
	    return decodedReturn;
	}
}
