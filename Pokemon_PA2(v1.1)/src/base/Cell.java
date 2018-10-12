package base;

/**
 * Class Cell is a type containing the (row, col) coordinate which represents a cell in the maze.
 * 
 * @author AndrewPang
 */
public class Cell {
	private int row;
	private int col;
	
	/**
	 * The constructor which defines both row and column to be 0.
	 */
	public Cell() {
		this.row = 0;
		this.col = 0;
	}
	
	/**
	 * The constructor which defines the row and column according to the specific number.
	 * 
	 * @param row		The row number of the cell
	 * @param col		The column number of the cell
	 */
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	/**
	 * A method that sets the cell to a new coordinate.
	 * 
	 * @param row		The row number of the cell
	 * @param col		The column number of the cell
	 */
	public void setCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	/**
	 * A method that only sets the row number.
	 * 
	 * @param row		The row number of the cell
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * A method that only sets the column number.
	 * 
	 * @param col		The column number of the cell
	 */
	public void setCol(int col) {
		this.col = col;
	}
	
	/**
	 * A method that gets the row number.
	 * 
	 * @return			Return the row number of a cell.
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * A method that gets the column number.
	 * 
	 * @return			Return the column number of a cell.
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * The hashCode to be used with equals method.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	/**
	 * Override the equals method from the Object class in order to compare
	 * two cells is the same or not.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}


}
