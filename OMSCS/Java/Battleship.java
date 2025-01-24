import java.util.*;

public class Battleship {
	/* TODO:
	 * [x] check coordinates invalid integers
	 * [x] check occupancy
	 * [x] constuct personal boarding
	 * [x] 100 newlines
	 * 
	 * 3 Boards: player1Only, player2Only, public
	 * 4 states: - @ X O
	 * 
	 * Player 1’s Location Board should be printed first.
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		char[][] player1 = newEmptyBorad(5, 5);
		char[][] player2 = newEmptyBorad(5, 5);
		char[][] feedback1 = newEmptyBorad(5, 5);
		char[][] feedback2 = newEmptyBorad(5, 5);
		char[][] final1 = newEmptyBorad(5, 5);
		char[][] final2 = newEmptyBorad(5, 5);

		// Initialize boardingPlayer1
		System.out.println("Welcome to Battleship!\n");
		System.out.println("PLAYER 1, ENTER YOUR SHIPS' COORDINATES.");
		final1 = initPlayerBoard(input, player1);
		// Initialize boardingPlayer2
		System.out.println("PLAYER 2, ENTER YOUR SHIPS' COORDINATES.");
		final2 = initPlayerBoard(input, player2);

		// Take shot in turn
		while (true) {
			// player1 shot player2
			feedback1 = shotBoard(input, final2, feedback1, 1);
			if (checkWin(final2)) {
				System.out.println("PLAYER 1 WINS! YOU SUNK ALL OF YOUR OPPONENT's SHIPS!\n");
				break;
			}
			// player2 shot player1
			feedback2 = shotBoard(input, final1, feedback2, 2);
			if (checkWin(final1)) {
				System.out.println("PLAYER 2 WINS! YOU SUNK ALL OF YOUR OPPONENT's SHIPS!\n");
				break;
			}
		}
		System.out.println("Final boards:\n");
		printBattleShip(final1);
		System.out.println("");
		printBattleShip(final2);
    }
    
	// Deploy battleships for players
	private static char[][] initPlayerBoard(Scanner input, char[][] board) {
		int numRow = board.length;
		int numCol = board[0].length;

		// 1. Stuff the boarding
		for (int i=1, row, col; i<=5; i++) {
			do {
				System.out.printf("Enter ship %d location:\n", i);
				// read row input
				if (!input.hasNextInt()) {
					System.out.println("Invalid coordinates. Choose different coordinates.");
					input.nextLine(); // clear args and \n
					continue; // invalid int
				}
				row = input.nextInt();
				if (row < 0 || numRow-1 < row) {
					System.out.println("Invalid coordinates. Choose different coordinates.");
					input.nextLine(); // clear args and \n
					continue; // invalid int
				}

				// read col input
				if (!input.hasNextInt()) {
					System.out.println("Invalid coordinates. Choose different coordinates.");
					input.nextLine(); // clear args and \n
					continue; // invalid int
				}
				col = input.nextInt();
				if (col < 0 || numCol-1 < col) {
					System.out.println("Invalid coordinates. Choose different coordinates.");
					input.nextLine(); // clear args and \n
					continue; // invalid int
				}

				// Repeation validation
				if (board[row][col] == '@') {
					System.out.println("You already have a ship there. Choose different coordinates.");
					input.nextLine(); // clear args and \n
					continue; // invalid
				}
				board[row][col] = '@';
				input.nextLine(); // clear args and \n
				break; // valid input
			} while (true);
		}

		// 2. Print the boarding
		printBattleShip(board);
		int count = 100;
		while (count > 0) {
			System.out.println("");
			count--;
		}

		return board;
	}

	// Initialize Empty Board with '-'
	private static char[][] newEmptyBorad(int numRow, int numCol) {
		char[][] board = new char[numRow][numCol];
		for (int row=0 ; row < board.length; row++) {
			for (int col=0 ; col < board[row].length; col++) {
				board[row][col] = '-';
			}
		}
		return board;
	}

	// 
	private static char[][] shotBoard(Scanner input, char[][] board, char[][] feedback, int id) {
		int numRow = board.length;
		int numCol = board[0].length;
		int row, col;

		do {
			System.out.printf("Player %d, enter hit row/column:\n", id);
			// read row input
			if (!input.hasNextInt()) {
				System.out.println("Invalid coordinates. Choose different coordinates.");
				input.nextLine(); // clear args and \n
				continue; // invalid int
			}
			row = input.nextInt();
			if (row < 0 || numRow-1 < row) {
				System.out.println("Invalid coordinates. Choose different coordinates.");
				input.nextLine(); // clear args and \n
				continue; // invalid int
			}

			// read col input
			if (!input.hasNextInt()) {
				System.out.println("Invalid coordinates. Choose different coordinates.");
				input.nextLine(); // clear args and \n
				continue; // invalid int
			}
			col = input.nextInt();
			if (col < 0 || numCol-1 < col) {
				System.out.println("Invalid coordinates. Choose different coordinates.");
				input.nextLine(); // clear args and \n
				continue; // invalid int
			}

			// Repeation validation
			if (board[row][col] == 'O' || board[row][col] == 'X') {
				System.out.println("You already fired on this spot. Choose different coordinates.");
				input.nextLine(); // clear args and \n
				continue; // invalid
			}

			// Valid shot
			if (board[row][col] == '@') {
				System.out.printf("PLAYER %d HIT PLAYER %d's SHIP!\n", id, id==1? 2:1);
				board[row][col] = 'X'; // nice shot
				feedback[row][col] = 'X';
			}
			else {
				System.out.printf("PLAYER %d MISSED!\n", id);
				board[row][col] = 'O'; // miss
				feedback[row][col] = 'O';
			}
			input.nextLine(); // clear args and \n
			break; // valid input
		} while (true);

		// Print
		printBattleShip(feedback);
		System.out.println("");
		return feedback;
	}

	// Check board for winning in real-time
	private static boolean checkWin(char[][] board) {
		for (char[] row : board) {
			for (char element : row) {
				if (element == '@') {
					return false;
				}
			}
		}
		return true;
	}

    // Print game boards to the console.
	private static void printBattleShip(char[][] player) {
		System.out.print("  ");
		for (int row = -1; row < 5; row++) {
			if (row > -1) {
				System.out.print(row + " ");
			}
			for (int column = 0; column < 5; column++) {
				if (row == -1) {
					System.out.print(column + " ");
				} else {
					System.out.print(player[row][column] + " ");
				}
			}
			System.out.println("");
		}
	}
}