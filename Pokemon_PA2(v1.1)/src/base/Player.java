package base;

import java.util.ArrayList;

/**
 * This is the class that defines the player/user in the maze. A player is inside a
 * specific maze, that is why Player class inherited Map class. Those player's
 * attributes and behavior, e.g. moving along the maze, will be defined here.
 * 
 * @author AndrewPang
 */
public class Player extends Map{
	private ArrayList<Pokemon> cPokemon;
	private ArrayList<Station> pStation;
	private int numBalls;
	private boolean arrive;
	private Cell curr;
	private Cell dest;
	private ArrayList<ArrayList<Cell>> paths;		// Two dimensional array
//	private ArrayList<Cell> visited;
	private int steps;
	private int score;
	
	
	/**
	 * This is the constructor of Player class which initialize all Player's
	 * attributes, including the map the player is in.
	 * 
	 * @param totRow	Total number of rows in the maze
	 * @param totCol	Total number of columns in the maze
	 */
	public Player(int totRow, int totCol) {
		super(totRow, totCol);
		cPokemon = new ArrayList<Pokemon>();
		pStation = new ArrayList<Station>();
		curr = new Cell();
		dest = new Cell();
//		visited = new ArrayList<Cell>();
		steps = 0;
		numBalls = 0;
		score = 0;
		arrive = false;
	}
	/**
	 * The method sets the current location of the player.
	 * 
	 * @param cRow		current row of the player in the maze
	 * @param cCol		current column of the player in the maze
	 */
	public void setCurr(int cRow, int cCol) {
		curr.setCell(cRow, cCol);
	}
	/**
	 * The method sets the destination coordinate of the maze.
	 * 
	 * @param dRow		the row of the destination point
	 * @param dCol		the column of the destination point
	 */
	public void setDest(int dRow, int dCol) {
		dest.setCell(dRow, dCol);
	}
	/**
	 * The method saves the Pokemon the player successfully caught.
	 * 
	 * @param obj		A Pokemon object from Pokemon class
	 */
	public void addPokemon(Pokemon obj) {
		cPokemon.add(obj);
	}
	/**
	 * The method saves the Station the player passed.
	 * 
	 * @param obj		A station object from Station class
	 */
	public void addStation(Station obj) {
		pStation.add(obj);
	}
	/**
	 * The method adds the number of balls the player got from the maze.
	 * 
	 * @param num		The number of ball
	 */
	public void addBall(int num) {
		numBalls += num;
	}
	/**
	 * The method calculates the current score of player
	 */
	public void calScore() {
		score = numBalls + 5*cPokemon.size() + 10*getNumType() + getMaxCP() - steps;
	}
	/**
	 * The method moves the player to the next cell with specified coordinate.
	 * 
	 * @param row		The row number the player wants to move to
	 * @param col		The column number the player wants to move to
	 */
	/**
	 * minus 1 to score, decrement
	 */
	public void minusScore() {
		score--;
		steps++;
	}
/*	public void move(int row, int col) {
		curr.setCell(row, col);
		Cell pass = new Cell(row, col);
//		visited.add(pass);
		steps++;
	}*/
	/**
	 * The method get how many Pokemons the player got.
	 * 
	 * @return		Return the number of Pokemons the player caught through out the maze
	 */
	public int getNumPokemons() {
		return cPokemon.size();
	}
	/**
	 * The method counts how many types of Pokemons the player caught.
	 * 
	 * @return		Return the number of types of Pokemons the player caught
	 */
	public int getNumType() {
		ArrayList<String> types = new ArrayList<String>();
		if (cPokemon.size() > 0)
			types.add(cPokemon.get(0).getType());
//		for (Pokemon t1 : cPokemon) {
//			for (String t2 : types) {
//				if (t1.getType().equals(t2))
//					break;
//				else
//					types.add(t1.getType());
//			}
//		}
		for (int i = 0; i < cPokemon.size(); i++) {
			for (int j = 0; j < types.size(); j++) {
				if (cPokemon.get(i).getType().equals(types.get(j)))
					break;
				else
					types.add(cPokemon.get(i).getType());
			}
		}
		return types.size();
	}
	/**
	 * The method get the maximum CP of the Pokemons the player caught.
	 * 
	 * @return		Return the maximum CP of the Pokemons 
	 */
	public int getMaxCP() {
		int max = 0;
		for (Pokemon p : cPokemon) {
			if (p.getCP() > max)
				max = p.getCP();
		}
		return max;
	}
	/**
	 * The method gets the number of PokeBalls the player collected.
	 * 
	 * @return		Return the number of PokeBalls
	 */
	public int getBalls() {
		return numBalls;
	}
	/**
	 * The method gets the calculated score the player has.
	 * 
	 * @return		Return the total score of the player
	 */
	public int getScore() {
		calScore();
		return score;
	}
	/**
	 * The method gets the current coordinate of the player which is a type of Cell.
	 * 
	 * @return		Return the current location of the player
	 */
	public Cell getCurr() {
		return curr;
	}
	/**
	 * The method gets the destination coordinate of the maze.
	 * 
	 * @return		Return the destination location of the maze
	 */
	public Cell getDest() {
		return dest;
	}
	
	public void minusBalls(int num) {
		if (numBalls >= num)
			numBalls -= num;
		else
			System.out.println("In minusBalls(int): num > numBalls player owned");
	}
	
	/**
	 * The methods get the path the player walked from starting point to destination.
	 * 
	 * @return		Return an arraylist of string which is a path
	 */
/*	public ArrayList<String> getPath() {
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < visited.size(); i++) {
			if (i == visited.size()-1)
				temp.add("<" + visited.get(i).getRow() + "," + visited.get(i).getCol() + ">");
			else
				temp.add("<" + visited.get(i).getRow() + "," + visited.get(i).getCol() + ">->");
		}
		return temp;
	}*/
	

}
