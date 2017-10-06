//import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Iterator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;

public class MineSweeperGame implements Assignment1  {

	private LinkedList<Minefield> minefields;

	public MineSweeperGame () {
		this.minefields = new LinkedList<Minefield>();
	}

	@Override
	public long minesweep ( File inputfile ) {

		Pattern pattern = Pattern.compile("\\s*");
		Matcher matcher;
		
		String line;
		int lineNumber = 0;
		int mineFieldCounter = 0;

    try {
			Reader reader = new FileReader(inputfile);
			BufferedReader br = new BufferedReader(reader);

			if ( !br.ready() ) {
				br.close();
				FormatException e = new FormatException("empty Document");
				throw e;
			}
			
			while ( br.ready() ) {
				line = br.readLine();
				lineNumber++;

				pattern = Pattern.compile("\\s*([0-9]+)\\s*([0-9]+)\\s*");
				matcher = pattern.matcher(line);
				if ( !matcher.matches() ) {
					br.close();
					FormatException e = new FormatException("wrong format in line " + lineNumber);
					throw e;
				}
				int firstInt = Integer.parseInt(matcher.group(1));
				int secondInt = Integer.parseInt(matcher.group(2));

				if ( firstInt == 0 ^ secondInt == 0 ) {
					br.close();
					FormatException e = new FormatException("wrong format in line " + lineNumber);
					throw e;
				}
				if ( firstInt == 0 && secondInt == 0 ) {
					br.close();
					return this.printAllHints();
				}

				Minefield currentMinefield = new Minefield(++mineFieldCounter,
																										Integer.parseInt(matcher.group(1)),
																										Integer.parseInt(matcher.group(2)));
				this.minefields.add(currentMinefield);
				int minefield_linecounter = 0;

				while ( br.ready() && minefield_linecounter < firstInt ) {
					line = br.readLine();
					lineNumber++;

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
