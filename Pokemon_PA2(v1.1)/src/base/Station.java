package base;

import java.io.File;
import java.lang.Runnable;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is a class that about a Station object with its own attributes.
 * 
 * @author AndrewPang
 */
public class Station implements Runnable {
	private int row;
	private int column;
	private int balls;
	
	private ImageView icon;
	public volatile boolean collision;

	
	/**
	 * A constructor that creates a Station object with specified attributes.
	 * 
	 * @param row			The row number of the Station
	 * @param column		The column number of the Station
	 * @param balls			The number of balls can get from the Station
	 */
	public Station(int row, int column, int balls) {
		this.row = row;
		this.column = column;
		this.balls = balls;
		icon = setImageView();
		collision = false;
	}
	/**
	 * A method gets the row number of the Station
	 * 
	 * @return				Return the row number
	 */
	public int getRow() {
		return row;
	}
	/**
	 * A method gets the column number of the Station
	 * 
	 * @return				Return the column number
	 */
	public int getCol() {
		return column;
	}
	/**
	 * A method gets the number of balls of the Station
	 * 
	 * @return				Return the number of balls
	 */
	public int getBalls() {
		return balls;
	}
	/**
	 * The hashCode for the equals() method
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + balls;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}
	/**
	 * Override the equals() method from Object to compare two stations is the same or not.
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
		Station other = (Station) obj;
		if (balls != other.balls)
			return false;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	public ImageView setImageView() {
	 String imageURL = new File("icons/ball_ani.gif").toURI().toString();
	 ImageView station = new ImageView(new Image(imageURL));
	 station.setFitHeight(40);
	 station.setFitWidth(40);
	 station.setPreserveRatio(true);
	 station.relocate(column*40, row*40);
	 
	 return station;
	}
	
	public ImageView getImageView() {
		return icon;
	}
	
	public void setCollision() {
		collision = true;
	}
	
	public void threadSleep(String stationName) {
		Random r = new Random();
		int low = 5000, high = 10000;
		int delay = r.nextInt(high-low) + low;
		System.out.println(Thread.currentThread().getName());
		if (Thread.currentThread().getName().equals(stationName)) {
			try {
				/**
				 * FOR DEBUG
				 */
				System.out.println("SLEEPING" + stationName);
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		while (true) {
			if (collision) {
				threadSleep("Station(" + row + "," + column + ")");
				collision = false;			// Reset the collision variable
				icon.setVisible(true);
			}
		}
	}
}
