import java.io.File;

public class MineSweeperTest {

	public static void main ( String[] args ) {

		MineSweeperGame game1 = new MineSweeperGame();
		File file = new File("./input.txt");
		long sumOfHints = game1.minesweep(file);

	}

}
