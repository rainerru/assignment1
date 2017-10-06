public class Mine {

	private int posX;
	private int posY;

	public Mine ( int startXValue, int startYValue ) {
		this.posX = startXValue;
		this.posY = startYValue;
	}

	public void setXPos ( int newXValue ) { this.posX = newXValue; }

	public void setYPos ( int newYValue ) { this.posY = newYValue; }

	public int getXPos () { return this.posX; }

	public int getYPos () { return this.posY; }

}
