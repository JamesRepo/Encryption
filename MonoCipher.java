/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods to encode and decode a character.
 */
public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char [] alphabet;
	
	/** The cipher array. */
	private char [] cipher;

	/**
	 * Instantiates a new mono cipher.
	 * Keyword must not contain duplicate letters. 
	 * @param keyword the cipher keyword
	 */
	public MonoCipher(String keyword)
	{
		//create alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
		
		// create first part of cipher from keyword
		// create remainder of cipher from the remaining characters of the alphabet
		cipher = new char [SIZE];
		// New variable to store the keyword as all upper case and one to store the keywords length
		String keyWord = keyword.toUpperCase();
		int kwLength = keyWord.length();
		// put the key word in the array first
		for (int i = 0; i < kwLength; i++) {
			cipher[i] = keyWord.charAt(i);
		}
		// another loop to put the rest of the alphabet in
		for (int i = SIZE-1; i >= 0; i--) {
			// index of used to check if the characters are already there, if not put in array
			if (keyWord.indexOf(alphabet[i]) == -1) {
				cipher[kwLength] = alphabet[i];
				kwLength++;
			}
		}
		// Prints both arrays in console to check
		System.out.println(alphabet);
		System.out.println(cipher);
	}
	
	/**
	 * Encode a character
	 * All text from files will be upper case
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)
	{
		// returned character
		char encoded = 0;
		// Makes sure only encoding letters and not punctuation etc. 
		if (ch < 'A' && ch < 'Z') {
			return ch;
		}
		else {
			// Loops through alphabet to determine letter and encodes
			for (int i = 0; i < SIZE; i++) {
				if (ch == alphabet[i]) {
					encoded = cipher[i];
				}
			}
		}
		// returns the encoded character
		return encoded;
	}

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{
		// Returned character
		char decoded = 0;
		// Avoid punctuation
		if (ch < 'A' && ch <'Z') {
			return ch;
		}
		else {
			// Loops through cipher to determine which letter it is in actual alphabet
			for (int i = 0; i < SIZE; i++) {
				if (ch == cipher[i]) {
					decoded = alphabet[i];
				}
			}
		}
	    return decoded; 
	}
}
