import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

public class Minefield {

	private LinkedList<Mine> mines;
	private int numberOfMinefield;
	private int numberOfLines;
	private int numberOfRows;

	/**
	 * Constructor, initializes the LinkedList and inserting the initial values
	 * for the numbers of this minefield.
	 */
	public Minefield ( int numberMinefield, int numberLines, int numberRows ) {
		this.numberOfMinefield = numberMinefield;
		this.numberOfLines = numberLines;
		this.numberOfRows = numberRows;
		this.mines = new LinkedList<Mine>();
	}

	public int getNumberOfMinefield () { return this.numberOfMinefield; }

	/**
	 * This method inserts a mine into the LinkedList of this minefield. If the
	 * given position by the input is "out of bounds" regarding the dimension of
	 * the minefield, an excpetion is thrown.
   * @param	posX	the x-position of the mine that is to be inserted
   * @param	posY	the y-position of the mine that is to be inserted
	 */
	public void addMine ( int posX, int posY ) throws FormatException {
		if ( posX >= numberOfRows || posY >= numberOfLines ) {
			FormatException e = new FormatException("Mine position out of bounds");
					throw e;
		}
		Mine newMine = new Mine(posX,posY);
		this.mines.add(newMine);
	}

	/**
	 * First, a two-dimensional array of integers matching the dimension of the
	 * minefield is created. Then, by iterating all mines in the LinkedList, a "9"
	 * is inserted into the grid signifying a mine on that position. Further, all
	 * existing surrounding positions, are incremented by 1. This way, at the end one
	 * can see that numbers 9 and above mean "here is a mine" and numbers 0 through
	 * 8 is the hint: the number of mines surrounding this position.
   * @return						the sum of all hints of this minefield
	 */
	public long printHints () {

		// create grid
		int[][] grid = new int[this.numberOfRows][this.numberOfLines];

		//	insert mines and calculate hints
		//	use the convention: 0-8 count adjacent mines, a field with >=9 means theres a mine 
		Iterator<Mine> Iterator = this.mines.iterator();
		int currentX, currentY;
    while (Iterator.hasNext()) {

			// get next mine and mark it in the grid
			Mine currentMine = Iterator.next();
			currentX = currentMine.getXPos();
			currentY = currentMine.getYPos();
			grid[currentX][currentY] = 9;

			// now add 1 to all adjacent fields of the grid
			if ( currentX > 0 && currentY > 0 )
				grid[currentX-1][currentY-1]++; // up left
			if ( currentY > 0 ) 
				grid[currentX][currentY-1]++; // up
			if ( currentX < numberOfRows - 1 && currentY > 0 ) 
				grid[currentX+1][currentY-1]++; // up right
			if ( currentX > 0 ) 
				grid[currentX-1][currentY]++; // left
			if ( currentX < numberOfRows - 1 ) 
				grid[currentX+1][currentY]++; // right
			if ( currentY < numberOfLines - 1 && currentX > 0 ) 
				grid[currentX -1][currentY+1]++; // down left
			if ( currentY < numberOfLines - 1 ) 
				grid[currentX][currentY+1]++; // down
			if ( currentY < numberOfLines - 1 && currentX < numberOfRows - 1) 
				grid[currentX+1][currentY+1]++; // down right
    }

		// print grid and information; start counting the hints
		long sumOfHints = 0;

		System.out.println("Field: " + this.getNumberOfMinefield());
		for ( currentY = 0; currentY < numberOfLines; currentY++ )	{
			for ( currentX = 0; currentX < numberOfRows; currentX++ )	{
				if ( grid[currentX][currentY] > 8 ) System.out.print("*");
				else {
					System.out.print(grid[currentX][currentY]);
					sumOfHints += grid[currentX][currentY];
				}
			}
			System.out.println();
		}

		// return the calculated sum
		return sumOfHints;

	}

}
