package base;

import java.io.File;
import java.lang.Runnable;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pokemon.ui.PokemonList;

/**
 * This is a class that about a Pokemon object with its own attributes.
 * 
 * @author AndrewPang
 */
public class Pokemon implements Runnable {
	private int row;
	private int column;
	private String name;	// Name of the pokemon
	private String type;	// Type of the pokemon
	private int cp;			// The combat power of the pokemon
	private int ballsRequired;		// No. of balls required to catch the pokemon
	
	private ImageView icon;
//	private boolean destroy;
	private volatile boolean collision;
	
	
	/**
	 * A constructor that create a Pokemon object with specified information.
	 * 
	 * @param row			The row number of the Pokemon
	 * @param column		The column number of the Pokemon
	 * @param name			The name of the Pokemon
	 * @param type			The type of the Pokemon
	 * @param cp			The combat power of the Pokemon
	 * @param ball			The number of balls required to catch the Pokemon
	 */
	public Pokemon(int row, int column, String name, String type, int cp, int ball) {
		this.row = row;
		this.column = column;
		this.name = name;
		this.type = type;
		this.cp = cp;
		ballsRequired = ball;
		icon = setImageView();
		collision = false;
//		destroy = false;
	}
	/**
	 * A method gets the row number of the Pokemon.
	 * 
	 * @return			Return the row number of the Pokemon
	 */
	public int getRow() {
		return row;
	}
	/**
	 * A method gets the column number of the Pokemon.
	 * 
	 * @return			Return the column number of the Pokemon
	 */
	public int getCol() {
		return column;
	}
	/**
	 * A method gets the name of the Pokemon.
	 * 
	 * @return			Return the name of the Pokemon
	 */
	public String getName() {
		return name;
	}
	/**
	 * A method gets the type of the Pokemon.
	 * 
	 * @return			Return the type of the Pokemon
	 */
	public String getType() {
		return type;
	}
	/**
	 * A method gets the combat power of the Pokemon.
	 * 
	 * @return			Return the combat power of the Pokemon
	 */
	public int getCP() {
		return cp;
	}
	/**
	 * A method gets the number of balls required to catch the Pokemon.
	 * 
	 * @return			Return the number of balls required
	 */
	public int getNumBalls() {
		return ballsRequired;
	}
	/**
	 * A method sets the name of the Pokemon.
	 * 
	 * @param name		The name of the Pokemon
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * A methods sets the type of the Pokemon.
	 * 
	 * @param type		The type of the Pokemon
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * A method sets the combat power of the Pokemon.
	 * 
	 * @param cp		The combat power of the Pokemon
	 */
	public void setCP(int cp) {
		this.cp = cp;
	}
	/**
	 * A method sets the number of balls required to catch the Pokemon.
	 * 
	 * @param num		The number of balls required
	 */
	public void setNumBalls(int num) {
		ballsRequired = num;
	}
	/**
	 * The hashCode for the equals() method.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ballsRequired;
		result = prime * result + column;
		result = prime * result + cp;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + row;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	/**
	 * Override the equals from Object class in order to compare two Pokemons are the same or not.
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
		Pokemon other = (Pokemon) obj;
		if (ballsRequired != other.ballsRequired)
			return false;
		if (column != other.column)
			return false;
		if (cp != other.cp)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (row != other.row)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	/**
	 * Check if the location in the map is an empty road or not
	 * @param row
	 * @param col
	 * @return true/false
	 */
	public boolean emptyRoad(int row, int col) {
		if (Map.getTotRow() != 0 && Map.getTotCol() != 0)
			if (row < Map.getTotRow() && col < Map.getTotCol()) {
				String map[][] = Map.getMap();
				return (map[row][col] == " ") ? true : false;
			}
		return false;
	}
	
	public ImageView setImageView() {
		String imageURL = new File("icons/" + PokemonList.getIdOfFromName(name) + ".png").toURI().toString();
		ImageView pokemon = new ImageView(new Image(imageURL));
		pokemon.setFitHeight(40);
		pokemon.setFitWidth(40);
		pokemon.setPreserveRatio(true);
		pokemon.relocate(column*40, row*40);
		
		return pokemon;
	}
	
	public ImageView getImageView() {
		return icon;
	}
	
	public void threadSleep(String pokemonName) {
		Random r = new Random();
		int low = 3000, high = 5000;
		int delay = r.nextInt(high-low) + low;
		System.out.println(Thread.currentThread().getName());
		if (Thread.currentThread().getName().equals(pokemonName)) {
			try {
				/**
				 * FOR DEBUG
				 */
				System.out.println("SLEEP");
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setCollision() {
		collision = true;
	}
	
//	public void setDestroy() {
//		destroy = true;
//	}

	
	@Override
	public void run() {
		try {
			while (true) {
				// A boolean variable to indicate if it is an empty cell or not
				boolean empty = false;
				Random r = new Random();
				// 0: up, 1: right, 2: down, 3: left
				int direction = r.nextInt(4);
//				int direction = (int)(Math.random()*10000) % 4;
				
//				if (destroy) {
//					Thread.currentThread().interrupt();
//				}
				
				// Check if the generated direction is an empty road
				switch (direction) {
				case 0: if (emptyRoad(row-1, column)) {
							row -= 1;
							empty = true;
						}
						break;
				case 1: if (emptyRoad(row, column+1)) {
							column += 1;
							empty = true;
						}
						break;
				case 2: if (emptyRoad(row+1, column)) {
							row += 1;
							empty = true;
						}
						break;
				case 3: if (emptyRoad(row, column-1)) {
							column -= 1;
							empty = true;
						}
						break;
				}
				if (empty) {
					// Sleep for 3-5 seconds and resume and reappear
					if (collision) {
						threadSleep(name);
						collision = false;			// Reset the collision variable
						icon.setVisible(true);
					}
					System.out.println(row +" "+ column);
					Thread.sleep(1800);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
