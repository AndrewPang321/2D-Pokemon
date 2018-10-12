package base;

import java.util.ArrayList;

/**
 * This is a class contains all the attributes and behavior of the map, which is also a maze.
 * 
 * @author AndrewPang
 */
public class Map extends Cell {
	
	private static String[][] map;
	private static int totRow;
	private static int totCol;
	private ArrayList<Station> stations;
	private ArrayList<Pokemon> pokemons;
	

	/**
	 * The constructor of Map class which initializes the attributes.
	 * 
	 * @param totRow		Total number of rows of the maze
	 * @param totCol		Total number of columns of the maze
	 */
	public Map(int totRow, int totCol) {
		map = new String[totRow][totCol];
		stations = new ArrayList<Station>();
		pokemons = new ArrayList<Pokemon>();
		Map.totRow = totRow;
		Map.totCol = totCol;
	}
	/**
	 * A method that sets the cell to indicate the type of the cell (Wall, Pokemon, Station, EmptyRoad).
	 * 
	 * @param row			The row number of the cell
	 * @param column		The column number of the cell
	 * @param type			The type of the cell (Wall, Pokemon, Station, EmptyRoad)
	 */
	public void setPoint(int row, int column, String type) {
		map[row][column] = type;
	}
	/**
	 * A methods that save the station's information.
	 * 
	 * @param row			The row number of the cell
	 * @param column		The column number of the cell
	 * @param balls			The number of PokeBalls that station has
	 */
	public void setStation(int row, int column, int balls) {
		stations.add(new Station(row, column, balls));
	}
	/**
	 * A method that save the Pokemon's information
	 * 
	 * @param row			The row number of the cell
	 * @param column		The column number of the cell
	 * @param name			The name of the Pokemon
	 * @param type			The type of the Pokemon
	 * @param cp			The combat power of the Pokemon
	 * @param ball			The number of balls that needs to catch the Pokemon
	 */
	public void setPokemon(int row, int column, String name, String type, int cp, int ball) {
		pokemons.add(new Pokemon(row, column, name, type, cp, ball));
	}
	/**
	 * A method that removes the station from the record.
	 * 
	 * @param row			The row number of the cell
	 * @param column		The column number of the cell
	 */
	public void removeStation(int row, int column) {
		for (Station s : stations)
			if (s.getRow() == row && s.getCol() == column) {
				stations.remove(s);
				break;
			}
	}
	/**
	 * A method that removes the Pokemon from the record.
	 * 
	 * @param row			The row number of the cell
	 * @param column		The column number of the cell
	 */
	public void removePokemon(int row, int column) {
		for (Pokemon s : pokemons)
			if (s.getRow() == row && s.getCol() == column) {
				pokemons.remove(s);
				break;
			}
	}
	/**
	 * A method that gets the type of the cell (Wall, Pokemon, Station, EmptyRoad)
	 * 
	 * @param row			The row number of the cell
	 * @param column		The column number of the cell
	 * @return				Return the type
	 */
	public String getType(int row, int column) {
		return map[row][column];
	}
	/**
	 * Return the 2-dimensional map array
	 * 
	 * @return the map
	 */
	public static String[][] getMap() {
		return map;
	}
	/**
	 * A method that gets the total number of rows of the maze.
	 * 
	 * @return				Return the total number of rows
	 */
	public static int getTotRow() {
		return totRow;
	}
	/**
	 * A method that gets the total number of columns of the maze.
	 * 
	 * @return				Return the total number of columns
	 */
	public static int getTotCol() {
		return totCol;
	}
	/**
	 * Return the ArrayList which contains all the pokemons
	 * @return pokemons
	 */
	public ArrayList<Pokemon> getPokemons() {
		return pokemons;
	}
	/**
	 * Return the ArrayList which conatins all the stations
	 * @return stations
	 */
	public ArrayList<Station> getStations() {
		return stations;
	}
	/**
	 * A method that finds a particular station from the record.
	 * 
	 * @param row			The row number of the station
	 * @param column		The column number of the station
	 * @return				Return the Station object if it is found, otherwise return null
	 */
	public Station findStation(int row, int column) {
		Station temp = null;
		for (Station s : stations) {
			if (s.getRow() == row && s.getCol() == column) {
				temp = s;
				return temp;
			}
		}
		return null;
	}
	/**
	 * A method that finds a particular Pokemon from the record.
	 * 
	 * @param row			The row number of the Pokemon
	 * @param column		The column number of the Pokemon
	 * @return				Return the Pokemon object if it is found, otherwise return null
	 */
	public Pokemon findPokemon(int row, int column) {
		Pokemon temp = null;
		for (Pokemon p : pokemons) {
			if (p.getRow() == row && p.getCol() == column) {
				temp = p;
				return temp;
			}
		}
		return null;
	}
}
