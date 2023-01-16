package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainClass {
	
	static ArrayList<Integer> playerPositions = new ArrayList<Integer>();
	static ArrayList<Integer> aiPositions = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		//to enable play again option
		TicTacToe();
				
	}
	
	public static void TicTacToe() {
		
		char[][] gameBoard = {{' ','|',' ','|',' '}, 
				{'-','+','-','+','-'}, 
				{' ','|',' ','|',' '}, 
				{'-','+','-','+','-'}, 
				{' ','|',' ','|',' '}};	
		//5x5 if we account of symbols that make the board look like a board, positions => [0/2/4][0/2/4]
		
		printGameBoard(gameBoard);
		
		while(true) {
			
			//'Scanner' = to convert bytes from input stream into characters
			Scanner scan = new Scanner(System.in);	//here, scanner created
			System.out.println("Enter your position (1-9): ");
			int playerPos = scan.nextInt();		//here, actually input is read, and we want to read the next/new input as Integer
			while(playerPositions.contains(playerPos) || aiPositions.contains(playerPos)) {
				System.out.println("Position taken! Enter valid position: ");
				playerPos = scan.nextInt();
			}
			if(playerPos>9 || playerPos<1) {
				System.out.println("Invalid position! Enter valid position: ");
				playerPos = scan.nextInt();
			}
			placePiece(gameBoard, playerPos, "player");
			playerPositions.add(playerPos);
			printGameBoard(gameBoard);
			checkWinner();
			
			System.out.println("\n------------\n");
			
			Random rand = new Random();
			int aiPos = rand.nextInt(9) + 1;
			while(playerPositions.contains(aiPos) || aiPositions.contains(aiPos))
				aiPos = rand.nextInt(9) + 1;	//produces 0-8 numbers
			placePiece(gameBoard, aiPos, "ai");		//need a minimax method for cpu position
			aiPositions.add(aiPos);
			printGameBoard(gameBoard);
			checkWinner();
		}
	}
	
	//this is in seperate method to make main method a bit cleaner + needs to be used multiple times
	public static void printGameBoard(char[][] gameBoard) {	
		for(char[] row : gameBoard) {		//reads - for each 'char array' called 'row' inside gameBoard array
			for(char col : row) {		//for each 'char' inside that row
				System.out.print(col);
			}
			System.out.println();
		}
	}
	
	public static void placePiece(char[][] gameBoard, int pos, String user) {
		
		char turn = ' ';
		
		if(user.equals("player"))
			turn = 'X';
		else if(user.equals("ai"))
			turn = 'O';
		
		switch(pos) {
			case 1: 
					gameBoard[0][0] = turn;
				break;
			case 2: 
					gameBoard[0][2] = turn;
				break;
			case 3:  
					gameBoard[0][4] = turn;
				break;
			case 4:  
					gameBoard[2][0] = turn;
				break;
			case 5:  
					gameBoard[2][2] = turn;
				break;
			case 6:  
					gameBoard[2][4] = turn;
				break;
			case 7:   
					gameBoard[4][0] = turn;
				break;
			case 8:   
					gameBoard[4][2] = turn;
				break;
			case 9:  
					gameBoard[4][4] = turn;
				break;
		}
	}
	
//	public static void 	Redo(char[][] gameBoard, String user) {
//		//takes care of invalid moves
//		if(user.equals("player")) {
//			Scanner scan = new Scanner(System.in);
//			System.out.println("Please enter valid move: "); 
//			playerPos = scan.nextInt();
//			placePiece(gameBoard, playerPos, "player");
//		}
//		else if(user.equals("ai")) {
//			Random rand = new Random();
//			aiPos = rand.nextInt(9);
//			placePiece(gameBoard, aiPos, "ai");
//		}
//	}
//my sed attempt on my own
	
	public static void checkWinner() {
		
		//we'll save player and ai positions in an array & then check if those arrays have all winning positions in them
		List<Integer> topRow = Arrays.asList(1,2,3);
		List<Integer> midRow = Arrays.asList(4,5,6);
		List<Integer> botRow = Arrays.asList(7,8,9);
		List<Integer> leftCol = Arrays.asList(1,4,7);
		List<Integer> midCol = Arrays.asList(2,5,8);
		List<Integer> rightCol = Arrays.asList(3,6,9);
		List<Integer> cross1 = Arrays.asList(1,5,9);
		List<Integer> cross2 = Arrays.asList(7,5,3);
		
		//add these in another list so we can use for loop & reuse code
		List<List<Integer>> winConditions = Arrays.asList(topRow, midRow, botRow, leftCol, midCol, rightCol, cross1, cross2);
		
		for(List<Integer> list : winConditions) {
			if(playerPositions.containsAll(list)) {
				System.out.println("Congratulations, you win!!");
				playAgain();				
			}
			else if(aiPositions.containsAll(list)) {
				System.out.println("CPU wins, better luck next time :(");
				playAgain();
			}
		}
		
		if(playerPositions.size() + aiPositions.size() == 9) {
			System.out.println("It's a TIE!");
			playAgain();
		}
		//tie condition out of for loop solved the infinite error
	}

	public static void playAgain() {
		
		System.out.println("Play again? (y/n)");
		Scanner ans = new Scanner(System.in);
		String answer = ans.next();
		if(answer.contains("y") || answer.contains("Y")) {
			aiPositions.clear();
			playerPositions.clear();
			TicTacToe();
		}
		else {
			System.exit(0);
		}
	}
	
	
}
