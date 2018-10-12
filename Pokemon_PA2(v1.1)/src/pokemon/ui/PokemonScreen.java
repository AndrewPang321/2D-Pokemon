package pokemon.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//import base.Cell;
//import base.Empty;
//import base.Map;
import base.Pokemon;
import base.Station;
import base.Game;
import base.Map;
import base.Player;
//import base.Wall;

public class PokemonScreen extends Application {
		
	/**
	 * input File which contains the map information
	 */
	File inputFile = new File("./sampleIn.txt");
	
	/**
	 * the user in the map from "inputFile"
	 */
	Player user = Game.initialize(inputFile);

	/**
	 * width of the window
	 */
	private static int W;

	/**
	 * height of the window
	 */
	private static int H;


	// this define the size of one CELL
	private static int STEP_SIZE = 40;
	
	// this are the urls of the images
	private static final String front = new File("icons/front.png").toURI().toString();
	private static final String back = new File("icons/back.png").toURI().toString();
	private static final String left = new File("icons/left.png").toURI().toString();
	private static final String right = new File("icons/right.png").toURI().toString();

	private ImageView avatar;
	private Image avatarImage;


	// these booleans correspond to the key pressed by the user
	boolean goUp, goDown, goRight, goLeft;

	// current position of the avatar
	double currentPosx = 0;
	double currentPosy = 0;

	protected boolean stop = false;
	
	private ArrayList<ImageView> p = new ArrayList<>();
	private ArrayList<ImageView> s = new ArrayList<>();
	private ArrayList<Thread> tPokemons = new ArrayList<>();
	private ArrayList<Thread> tStations = new ArrayList<>();
	private Label currScore;
	private Label cPokemons;
	private Label cPokeballs;

	@Override
	public void start(Stage stage) throws Exception {
		
		// create SubScene with W and H for mapGroup
		SubScene scene = new SubScene(addGroup(), W, H);
		HBox hbox = new HBox();
		hbox.getChildren().addAll(scene, addVBox());
		Scene primaryScene = new Scene(hbox);
		
		// add listener on key pressing
		primaryScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					goUp = true;
					avatar.setImage(new Image(back));
					break;
				case DOWN:
					goDown = true;
					avatar.setImage(new Image(front));
					break;
				case LEFT:
					goLeft = true;
					avatar.setImage(new Image(left));
					break;
				case RIGHT:
					goRight = true;
					avatar.setImage(new Image(right));
					break;
				default:
					break;
				}
			}
		});

		// add listener key released
		primaryScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					goUp = false;
					break;
				case DOWN:
					goDown = false;
					break;
				case LEFT:
					goLeft = false;
					break;
				case RIGHT:
					goRight = false;
					break;
				default:
					break;
				}
				stop = false;
			}
		});

		stage.setScene(primaryScene);
		stage.setTitle("Pokemon COMP 3021");
		stage.show();
		
		
		ArrayList<Pokemon> pokemons = user.getPokemons();
		for (Pokemon p : pokemons) {
			Thread thread = new Thread(p);
			thread.setName(p.getName());
			thread.setDaemon(true);
			thread.start();
			tPokemons.add(thread);
		}
		
		ArrayList<Station> stations = user.getStations();
		for (Station s : stations) {
			Thread thread = new Thread(s);
			thread.setName("Station(" + s.getRow() + "," + s.getCol() + ")");
			thread.setDaemon(true);
			thread.start();
			tStations.add(thread);
		}
		
		AnimationTimer anotherTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				for (int i = 0; i < pokemons.size(); i++) {
					// If there are no pokemon object in the array, skip it. (i.e. the pokemon is caught and the corresponding thread is interrupted)
					if (pokemons.get(i) == null)
						continue;
					// If no collision, move the pokemons
					else if (!checkCollision(pokemons.get(i).getRow(), pokemons.get(i).getCol())) {
						p.get(i).relocate(pokemons.get(i).getCol()*STEP_SIZE, pokemons.get(i).getRow()*STEP_SIZE);
					}
				}			
			}
		};
		anotherTimer.start();
		
		// it will execute this periodically
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (stop)
					return;

				int dx = 0, dy = 0;

				if (goUp) {
					dy -= (STEP_SIZE);
				} else if (goDown) {
					dy += (STEP_SIZE);
				} else if (goRight) {
					dx += (STEP_SIZE);
				} else if (goLeft) {
					dx -= (STEP_SIZE);
				} else {
					// no key was pressed return
					return;
				}
				moveAvatarBy(dx, dy);
			}
		};
		// start the timer
		timer.start();
	}
	
	/**
	 * Adding the map to the Group and return it
	 * @return mapGroup
	 */
	private Group addGroup() {
		// at the beginning lets set the image of the avatar front
		avatarImage = new Image(front);
		avatar = new ImageView(avatarImage);
		avatar.setFitHeight(STEP_SIZE);
		avatar.setFitWidth(STEP_SIZE);
		avatar.setPreserveRatio(true);
		
		// Get the size(row/column) of the map from input file and set W & H
		int[] temp = new int[2];
		temp = Game.getSize(inputFile);
		if (temp != null) {
			W = temp[1]*40;		// col number
			H = temp[0]*40;		// row number
		}
		
		/**
		 * DEBUG PRINTOUT
		 */
		//System.out.println("currX " + currentPosx + ", currY " + currentPosy);
		
		Group mapGroup = new Group();
		
		// Add the corresponding image on the mapGroup using setImage(String, int, int)
		String map[][] = Map.getMap();
		// i = row, j = col, temp[0] = total number of rows, temp[1] = total number of cols
		for (int i = 0; i < temp[0]; i++)
			for (int j = 0; j < temp[1]; j++) {
				ImageView icon = setImage(map[i][j], i, j);
				if (icon != null)
					mapGroup.getChildren().add(icon);					
			}
		
		// Add the pokemon image to the mapGroup
		ArrayList<Pokemon> pokemons = user.getPokemons();
		for (Pokemon pokemon : pokemons) {
			ImageView icon = pokemon.getImageView();
			p.add(icon);
			mapGroup.getChildren().add(icon);
		}
		
		// Add the station image to the mapGroup
		ArrayList<Station> stations = user.getStations();
		for (Station station : stations) {
			ImageView icon = station.getImageView();
			s.add(icon);
			mapGroup.getChildren().add(icon);
		}

		avatar.relocate(currentPosx, currentPosy);
		mapGroup.getChildren().add(avatar);
		
		return mapGroup;
	}
	
	/**
	 * Adding a VBox which contains current status and two buttons
	 * @return	
	 */
	private VBox addVBox() {
		currScore = new Label("Current Score: " + user.getScore());			// Add the calculated current score Here
		cPokemons = new Label("# of Pokemons caught: " + user.getNumPokemons());
		cPokeballs = new Label("# of Pokeballs owned: " + user.getBalls());
		Label endGame = new Label("End game!");
		endGame.setTextFill(Color.GREENYELLOW);
		endGame.setVisible(false);
		
		Button buttonResume = new Button("Resume");
		Button buttonPause = new Button("Pause");
		buttonPause.setOnAction(e-> {
			// Handling pause button event using lambda expression
/*			try {			
				Thread.currentThread().wait();
				
				for (Thread tp : tPokemons) 
					tp.wait();
				
				for (Thread ts : tStations) 
					ts.wait();
				
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}*/
		});
		
		HBox hbox = new HBox();
		hbox.setSpacing(8);
		hbox.getChildren().addAll(buttonResume, buttonPause);
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10, 10, 10, 50));
		vbox.setSpacing(10);
		vbox.getChildren().addAll(currScore, cPokemons, cPokeballs, endGame, hbox);
		
		return vbox;
	}
	
	/**
	 * Get the image from a pokemon name. Set the attributes and return it
	 * @param pokemonName
	 * @return an ImageView pokemon
	 */
	private ImageView setPokemonImage(String pokemonName, int row, int col) {
		int id = PokemonList.getIdOfFromName(pokemonName);
		String imageURL = new File("icons/" + id + ".png").toURI().toString();

		Image pokemonImage = new Image(imageURL);
		ImageView pokemon = new ImageView(pokemonImage);
		pokemon.setFitHeight(STEP_SIZE);
		pokemon.setFitWidth(STEP_SIZE);
		pokemon.setPreserveRatio(true);
		pokemon.relocate(col*40, row*40);
		
		return pokemon;
	}
	
	/**
	 * Create an ImageView according to different type
	 * @param type of the map at specific location
	 * @param row
	 * @param col
	 * @return ImageView of icon or null
	 */
	private ImageView setImage(String type, int row, int col) {
		String imageURL;
		ImageView icon = null;
		row *= 40; col *= 40;
		
		switch (type) {
			case"#": imageURL = new File("icons/tree.png").toURI().toString();
					 icon = new ImageView(new Image(imageURL));
					 icon.setFitHeight(STEP_SIZE);
					 icon.setFitWidth(STEP_SIZE);
					 icon.setPreserveRatio(true);
					 icon.relocate(col, row);
					 break;
			case"B": currentPosx = col;
					 currentPosy = row;
					 break;
			case"D": imageURL = new File("icons/exit.png").toURI().toString();
					 icon = new ImageView(new Image(imageURL));
					 icon = new ImageView(new Image(imageURL));
					 icon.setFitHeight(STEP_SIZE);
					 icon.setFitWidth(STEP_SIZE);
					 icon.setPreserveRatio(true);
					 icon.relocate(col, row);
					 break;
			case"S": break;
			case" ": break;
			case"P": break;
			default: System.out.println("In setImage(String, int, int): No such string type");
					 break;
		}
		
		return (icon == null) ? null : icon;
	}

	private void moveAvatarBy(int dx, int dy) {
		final double cx = avatar.getBoundsInLocal().getWidth() / 2;
		final double cy = avatar.getBoundsInLocal().getHeight() / 2;
		double x = cx + avatar.getLayoutX() + dx;
		double y = cy + avatar.getLayoutY() + dy;
		moveAvatar(x, y);
	}

	private void moveAvatar(double x, double y) {
		final double cx = avatar.getBoundsInLocal().getWidth() / 2;
		final double cy = avatar.getBoundsInLocal().getHeight() / 2;
		
		// Get the coordinate(row, col) of the user that are going to move
		double tempRow = (y-cy)/40, tempCol = (x-cx)/40;
		int row = (int)tempRow, col = (int)tempCol;
		
		if (x - cx >= 0 && x + cx <= W && y - cy >= 0 && y + cy <= H) {
			// Move only when it is not a wall
			if (!user.getType(row, col).equals("#")) {
	            // relocate ImageView avatar
				avatar.relocate(x - cx, y - cy);	// x-cx and y-cy are the updated currentPosx and currentPosy
				// update position
				currentPosx = x - cx;
				currentPosy = y - cy;				
				
				/**
				 * Check if there is any collision of the user.
				 */
				ArrayList<Pokemon> pokemons = user.getPokemons();
				ArrayList<Station> stations = user.getStations();
				// A. Collision with pokemon
				for (int i = 0; i < pokemons.size(); i++) {
					if (pokemons.get(i) == null)
						continue;
					// If true, collision happened
					else if (checkCollision(pokemons.get(i).getRow(), pokemons.get(i).getCol())) {
						if (user.getBalls() >= pokemons.get(i).getNumBalls()) {
							user.addPokemon(pokemons.get(i));
							user.minusBalls(pokemons.get(i).getNumBalls());
							user.setPoint(pokemons.get(i).getRow(), pokemons.get(i).getCol(), " ");
							// Stop the thread from running
							tPokemons.get(i).interrupt();
							// Replace the pokemon object from the array to null
							pokemons.set(i, null);						
							p.get(i).setVisible(false);
							
							// Update the label PokemonsCaught
							cPokemons.setText("# of Pokemons caught: " + user.getNumPokemons());
							// Update the label Pokeballs 
							cPokeballs.setText("# of Pokeballs owned: " + user.getBalls());
						}
						else {
							p.get(i).setVisible(false);
							pokemons.get(i).setCollision();
						}
					}
				}
				
				// B. Collision with station
				for (int j = 0; j < stations.size(); j++) {
					// Cannot acquire any balls when the station is sleeping
					if (!s.get(j).isVisible())
						continue;
					// If true, collision happened
					else if (checkCollision(stations.get(j).getRow(), stations.get(j).getCol())) {
						user.addBall(stations.get(j).getBalls());
						user.setPoint(stations.get(j).getRow(), stations.get(j).getCol(), " ");
						s.get(j).setVisible(false);
						stations.get(j).setCollision();
						
						// Update the label Pokeballs 
						cPokeballs.setText("# of Pokeballs owned: " + user.getBalls());
					}
				}
				
				// update the current score of the user
				user.minusScore();
				currScore.setText("Current Score: " + user.getScore());
	
				// I moved the avatar lets set stop at true and wait user release the key :)
				stop = true;
			}
		}
	}
	
	private boolean checkCollision(int row, int col) {
		if (currentPosx/STEP_SIZE == col && currentPosy/STEP_SIZE == row)
			return true;
		return false;
	}

	public static void main(String[] args) {
		// TODO: initialization. Read the input. Create Player obj.	
		launch(args);
	}


}

