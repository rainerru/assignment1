import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MineSweeperGame implements Assignment1  {

	private LinkedList<Minefield> minefields;

	/**
	 * Standard constructor, initializes the LinkedList of minefields. Nothing else
	 * is done.
	 */
	public MineSweeperGame () {
		this.minefields = new LinkedList<Minefield>();
	}

	/**
	 * This method uses java patterns and matchers to read data from a file. It reads
	 * several minefields, until the line "0 0" is reached. Everything after that line
	 * is ignored. Lines that don't match the expected behaviour lead to the termination of
	 * the program. The return value, which is the sum of all hints of the minefields, is 
	 * calculated by using the printAllHints() method from this class.
   * @param	inputfile		the file from which the minefields shall be generated
   * @return						the sum of all hints of all the minefields
	 */
	@Override
	public long minesweep ( File inputfile ) {

		// step 0: initialization
		Pattern pattern = Pattern.compile("\\s*");
		Matcher matcher;
		
		String line;
		int lineNumber = 0;
		int mineFieldCounter = 0;

    try {
			Reader reader = new FileReader(inputfile);
			BufferedReader br = new BufferedReader(reader);

			// abandon program if the document is empty
			if ( !br.ready() ) {
				br.close();
				FormatException e = new FormatException("empty Document");
				throw e;
			}
			
			while ( br.ready() ) {
				line = br.readLine();
				lineNumber++;

				// expected behaviour: "x y" tells that a minefield with dimension x times y follows
				pattern = Pattern.compile("\\s*([0-9]+)\\s*([0-9]+)\\s*");
				matcher = pattern.matcher(line);
				if ( !matcher.matches() ) {
					br.close();
					FormatException e = new FormatException("wrong format in line " + lineNumber);
					throw e;
				}
				int firstInt = Integer.parseInt(matcher.group(1));
				int secondInt = Integer.parseInt(matcher.group(2));

				// "0 x" or "x 0" with x!=0 is invalid input
				if ( firstInt == 0 ^ secondInt == 0 ) {
					br.close();
					FormatException e = new FormatException("wrong format in line " + lineNumber);
					throw e;
				}
				if ( firstInt == 0 && secondInt == 0 ) {
					br.close();
					return this.printAllHints();
				}

				// initialize a new minefield, now that the dimesion has been ascertained.
				Minefield currentMinefield = new Minefield(++mineFieldCounter,
																										Integer.parseInt(matcher.group(1)),
																										Integer.parseInt(matcher.group(2)));
				this.minefields.add(currentMinefield);
				int minefield_linecounter = 0;

				// try to read the minefield itself
				while ( br.ready() && minefield_linecounter < firstInt ) {
					line = br.readLine();
					lineNumber++;

					// it must contain the right amount of chars AND only points and asterisks.
					pattern = Pattern.compile("\\s*([\\*\\.]+)\\s*");
					matcher = pattern.matcher(line);
					if ( !matcher.matches() || matcher.group(1).length() != secondInt ) {
						br.close();
						FormatException e = new FormatException("wrong format in line " + lineNumber);
						throw e;
					}

					String currentInput = matcher.group(1);
					int charCounter = 0; // also counts the Y-position

					for ( char currentChar: currentInput.toCharArray() ) {

						// adding mines using the position in the file
						if ( currentChar == '*' ) {
							currentMinefield.addMine(charCounter,minefield_linecounter);
						} else if ( currentChar != '.' ) {
							br.close();
							FormatException e = new FormatException("wrong format in line " + lineNumber);
							throw e;
						}
						charCounter++;
					}

					minefield_linecounter++;
				}
			}
		}  catch ( FormatException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		}

		return 0;

	}

	/**
	 * Prints the hints of every single minefield contained in the list of minefields.
	 * Returns the sum of all the hints by taking the return value of the printHints()
	 * method of the minefields and adding them.
   * @return		the sum of all hints of all the minefields
	 */
	public long printAllHints () {

		long sumOfAllHints = 0;
		Iterator<Minefield> Iterator = this.minefields.iterator();
    while (Iterator.hasNext()) {
			// get next minefield and print the hints.
			Minefield currentMinefield = Iterator.next();
			sumOfAllHints += currentMinefield.printHints();
    }
		System.out.println(sumOfAllHints);
		return sumOfAllHints;

	}


}
