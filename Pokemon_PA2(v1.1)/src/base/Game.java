package base;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;

/**
 * The Game class is contains one static variable and two methods which are the
 * main part of the whole program. Also, the program starts here as well.
 * 
 * @author AndrewPang
 * @author Comp3021
 */
public class Game {
	
	static ArrayList<String> result = new ArrayList<String>();
	
	/**
	 * Get the row and column number from the input file and return it
	 * in an array.
	 * 
	 * @param inputFile
	 * @return array of two integer
	 * @throws Exception
	 */
	public static int[] getSize(File inputFile) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			
			int[] rowcol = new int[2];
			// Read the first of the input file
			String line = br.readLine();
			rowcol[0] = Integer.parseInt(line.split(" ")[0]);
			rowcol[1] = Integer.parseInt(line.split(" ")[1]);
			
			br.close();
			
			return rowcol;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in getSize(): File not found.");
			return null;
		}
	}
	
	/**
	 * To initialize the map from inputFile and get the information needed in order to
	 * output the information to the outputFile.
	 * @param inputFile		The file that contains the maze and relevant information
	 * @throws Exception
	 */
	public static Player initialize(File inputFile) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			
			// Read the first of the input file
			String line = br.readLine();
			int M = Integer.parseInt(line.split(" ")[0]);
			int N = Integer.parseInt(line.split(" ")[1]);	
			
			// To do: define a map		
			Player user = new Player(M, N);
			int startRow = 0; int startCol = 0;
			int destRow = 0; int destCol = 0;
			
			// Read the following M lines of the Map
			for (int i = 0; i < M; i++) {
				line = br.readLine();
				
				// to do
				// Read the map line by line
				for (int j = 0; j < N; j++) {
					String type = null;
					if (Character.toString(line.charAt(j)).equals("#"))
						type = "#";
					else if (Character.toString(line.charAt(j)).equals(" "))
						type = " ";
					else if (Character.toString(line.charAt(j)).equals("B")) {
						type = "B";
						startRow = i; startCol = j;
					}
					else if (Character.toString(line.charAt(j)).equals("D")) {
						type = "D";
						destRow = i; destCol = j;
					}
					else if (Character.toString(line.charAt(j)).equals("S"))					
						type = "S";
					else if (Character.toString(line.charAt(j)).equals("P"))
						type = "P";
					user.setPoint(i, j, type);
				}
			}
					
			// to do
			// Find the number of stations and pokemons in the map 
			// Continue read the information of all the stations and pokemons by using br.readLine();
			int stations = 0, pokemons = 0;
			ArrayList<String> info = new ArrayList<String>();
			line = null;
			while ((line = br.readLine()) != null) {
				info.add(line);
				// Using split to distinguish between pokemons or supply stations
				String[] parts = line.split(", ");
				if (parts.length == 5) {
					String[] temp = parts[0].split("<|>|,");
					user.setPokemon(Integer.valueOf(temp[1]), Integer.valueOf(temp[2]), parts[1], parts[2], Integer.valueOf(parts[3]), Integer.valueOf(parts[4]));
					pokemons++;
				}
				else if (parts.length == 2) {
					String[] temp = parts[0].split("<|>|,");
					user.setStation(Integer.valueOf(temp[1]), Integer.valueOf(temp[2]), Integer.valueOf(parts[1]));
					stations++;
				}
			}
			
			// Close the buffer reader
			br.close();
			
			user.setCurr(startRow, startCol);
			user.setDest(destRow, destCol);
			
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in initialize(File): File not found.");
			return null;
		}
	}
	
	/**
	 * This is the main part of the program, which is also where the program executes first.
	 * The information output part will also be done here.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		File inputFile = new File("./sampleIn.txt");
		File outputFile = new File("./sampleOut.txt");
		
		if (args.length > 0) {
			inputFile = new File(args[0]);
		} 

		if (args.length > 1) {
			outputFile = new File(args[1]);
		}
		
		Game game = new Game();
		game.initialize(inputFile);
		// TO DO 
		// Read the configures of the map and pokemons from the file inputFile
		// and output the results to the file outputFile		
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		for (String s : result)
			bw.write(s);
		bw.close();
	}
}
