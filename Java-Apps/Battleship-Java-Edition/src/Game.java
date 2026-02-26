import java.util.Scanner;

public class Game
{
    /**Displays the main menu with options to start the game or read the game rules.*/
    public static void menu()
    {   Scanner input = new Scanner(System.in);

        System.out.println("\n** Welcome to Battleship! **\n\nEnter your selection: \nn) new game\nr) rules\n");
        String selection = input.next().substring(0,1);

        // while input is anything other than one of the two choices, prompt again.
        while (!selection.equalsIgnoreCase("r") && !selection.equalsIgnoreCase("n")){
            System.out.println("\nPlease try again.\nEnter your selection: \nn) new game\nr) rules\n");
            selection = input.next().substring(0,1);
        }
        if(selection.equalsIgnoreCase("n")){  // for input "n", start game.
            System.out.println("\n\n\n\n");
            setup();
        }
        if(selection.equalsIgnoreCase("r")){  // for input "r", display game rules.
            Rules.print();

            // prompt player for any input to show main menu.
            System.out.print("Enter any key to return to main menu. ");
            if(input.hasNext()) {
                System.out.println("\n\n\n\n");
                menu();
            }
        }
    }


    /**Creates the players' boards, adds ships to the boards, and calls the method to begin the game.*/
    private static void setup()
    {
        String[] specific = {" A", " B", " C", " S", " D"}; // set array to hold markers for specific ships
        String[] generic = {" ●", " ●", " ●", " ●", " ●"};  // set array to hold marker for generic ships

        // get new, blank board and set to String array variable for player and cpu (both visible and hidden)
        String[][] player = buildBoard();
        String[][] playerHidden = buildBoard();
        String[][] cpu = buildBoard();
        String[][] cpuHidden = buildBoard();

        // generate random number to determine ship layout for player and cpu.
        int playerLayout = (int)(4*Math.random()+1);
        int cpuLayout = (int)(4*Math.random()+1);

        // if cpu's random number matches player's random number, generate new number for cpu until it is different.
        while (playerLayout == cpuLayout)
        {
            cpuLayout = (int)(4*Math.random()+1);
        }

        // add specific ships with to player's hidden board
        addShips(playerHidden, playerLayout, specific);

        // add generic ship markers to player's visible board
        addShips(player, playerLayout, generic);

        // add specific ships to cpu's hidden board
        addShips(cpuHidden, cpuLayout,  specific);

        playGame(player, playerHidden, cpu, cpuHidden);     // begin game
    }


    /**
     * Creates a new blank game board with rows and columns labeled.
     * @return new, blank board */
    public static String[][] buildBoard()
    {
        String[][] board = new String[11][11];    // create new 2d array
        String[] boardCols = {" ","A","B","C","D","E","F","G","H","I","J"}; // define column labels

        // set top row of grid to letters A-J
        for (int col = 1; col < board.length; col++) // loop through each column in the grid starting at index 1
        {
            for (int l = 0; l < board.length; l++)  // loop through each row in the grid
            {
                board[0][l] = " " + boardCols[l];   // set top row of columns to predefined column labels
            }
        }

        // set leftmost column to numbers 1-10 (w/ formatting)
        for (int r = 1; r < board.length; r++)  // loop through each row in the grid starting at index 1
        {
            for (int num = 1; num < board.length; num++)    // loop through each column in the grid
            {
                if (num < 10)
                {
                    board[num][0] = " " + num; // format numbers 1-9 before setting label to assigned row
                }
                else
                    board[num][0] = String.valueOf(num);    // set row 10 label.
            }
        }

        // set playable area to "water"
        for (int row = 1; row < board.length; row++)    // loop through each row of 'playable' board
        {
            for (int col = 1; col < board.length; col++)    // loop through each column in each row of 'playable' board
            {
                board[row][col] = " ~";  // set each element to a 'water' symbol
            }
        }
        return board;    // return defined 2d array (blank board) to calling method
    }


    /**
     * Runs the game taking turns while ships remain on both boards. Once a board has been cleared of its ships, a
     * winner is determined and the game ends.
     * @param player player's visible game board with generic ship icons
     * @param playerHidden player's hidden board with all ship types
     * @param cpu targeting grid against cpu, visible to player
     * @param cpuHidden CPU's hidden game board with all ship types */
    public static void playGame(String[][] player, String[][] playerHidden, String[][] cpu, String[][] cpuHidden)
    {
        // for each player, set initial # of hits left to sink each ship
        int[] cpuShips = {5,4,3,3,2};
        int[] playerShips = {5,4,3,3,2};

        print(cpu,"cpu");    // print blank targeting grid to console

        // while a positive number of hits remain for both players (not all ships have been sunk:
        while(hitsRemaining(cpuShips) != 0 && hitsRemaining(playerShips) != 0)
        {
            // print targeting grid for player, get player's target coordinates, display updated targeting grid
            // to reflect the shot made.
            print(CPU.targeted(cpuHidden, cpu, cpuShips), "cpu");

            // print player's visible game board, get random target coordinates against player, display updated game
            // board for the player to reflect the shot made by the CPU.
            print(Player.targeted(player, playerHidden, playerShips), "player");

            System.out.println("----------------------------------------"); // display delineation between rounds
            printSunkShips("CPU's", cpuShips);  // display CPU's sunken ships, if any
            printSunkShips("Your", playerShips);    // display player's sunken ships, if any
        }
        if (hitsRemaining(cpuShips) == 0)  // if cpu has no ships left, player wins.
        {
            System.out.println("\n\n\n\n*  *  *  YOU WIN!!!  *  *  *");   // display win
            System.exit(0); // exit program
        }
        if (hitsRemaining(playerShips) == 0)  // if player has no ships left, CPU wins.
        {
            System.out.println("\n\n\n\n-- -- -- YOU HAVE BEEN DEFEATED -- -- --");  // display loss
            System.exit(0); // exit program
        }
    }


    /**
     * Prints a labeled game board for a given "player".
     * @param board the board to be printed
     * @param name the "player" whose board is being printed */
    public static void print(String[][] board, String name)
    {
        for (String[] strings : board)  // for each single array (rows) of the table...
        {
            for (int c = 0; c < board.length; c++)  // for each element (column) of each row...
            {
                System.out.print(strings[c] + " ");  // print the value at given index, with space for formatting
            }
            System.out.println();   // return new line after each row
        }
        if(name.equals("cpu"))
            System.out.print(" — — — — targeting grid — — — —\n\n"); // print label under cpu's grid
        else
            System.out.print(" — — — — — your board — — — — —\n\n"); // print label under player's board
    }


    /**
     * If any ships have been sunk, this method displays the owner's name and the names of the ships that have been sunk.
     * @param whose the player whose ships have sunk
     * @param ships the number of hits left to sink each type of ship */
    private static void printSunkShips(String whose, int[] ships){
        String[] shipNames = {"Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer"}; // names of each ship
        String sunk = "";   // empty String variable placeholder

        for(int i = 0; i< ships.length; i++)  // for each ship in the list
        {
            if(ships[i] == 0)   // if any ship has 0 hits left (ship is sunk)
            {
                sunk += ((shipNames[i]) + " "); // add the ship name to the String variable "sunk".
            }
        }
        if(!sunk.isEmpty())   // if string variable is no longer empty (due to ship being sunk)
        {
            // display player's name and the name of the ship(s) that is/are sunk.
            System.out.println(whose + " ships sunk: " + sunk);
        }
    }


    /**
     * Determines if any ships remain on the board to be targeted
     * @param shipsLeft remaining number of hits left to sink each of the ships
     * @return number of hits needed for opponent to win */
    private static int hitsRemaining(int[] shipsLeft)
    {
        int total = 0;  // int variable to hold total number of hits left for a given player
            for (int ship : shipsLeft)  // for each ship in the list of initial ships on the board:
            {
                total += ship; // add the number of hits left to sink the ship to the running total
            }
        return total;   // return total number of hits remaining for the given player
    }


    /**
     * Accepts a char and converts it to the equivalent index in the array (board)
     * @param c the letter portion of given coordinates
     * @return  equivalent int to be used on the array */
    public static int convert(char c)
    {
        return switch (c)
        {
            case 'A' -> 1;
            case 'B' -> 2;
            case 'C' -> 3;
            case 'D' -> 4;
            case 'E' -> 5;
            case 'F' -> 6;
            case 'G' -> 7;
            case 'H' -> 8;
            case 'I' -> 9;
            default -> 10;
        };
    }


    /**
     * Based on a randomly generated number, adds specific ships to a board.
     * @param board game board to which ships are added
     * @param random number determines ship layout
     * @param ships the "icons" used for the ships, whether specific types or generic */
    public static void addShips (String[][] board, int random, String... ships)
    {
        String carrier = ships[0];
        String battleship = ships[1];
        String cruiser = ships[2];
        String submarine = ships[3];
        String destroyer = ships[4];

        if(random == 1)
        {
            board[5][1] = carrier;
            board[6][1] = carrier;
            board[7][1] = carrier;
            board[8][1] = carrier;
            board[9][1] = carrier;

            board[3][5] = battleship;
            board[3][6] = battleship;
            board[3][7] = battleship;
            board[3][8] = battleship;

            board[9][7] = submarine;
            board[9][8] = submarine;
            board[9][9] = submarine;

            board[6][2] = cruiser;
            board[6][3] = cruiser;
            board[6][4] = cruiser;

            board[1][2] = destroyer;
            board[2][2] = destroyer;
        }
        else if(random == 2)
        {
            board[2][9] = carrier;
            board[3][9] = carrier;
            board[4][9] = carrier;
            board[5][9] = carrier;
            board[6][9] = carrier;

            board[9][7] = battleship;
            board[9][8] = battleship;
            board[9][9] = battleship;
            board[9][10] = battleship;

            board[6][5] = submarine;
            board[7][5] = submarine;
            board[8][5] = submarine;

            board[3][3] = cruiser;
            board[4][3] = cruiser;
            board[5][3] = cruiser;

            board[9][2] = destroyer;
            board[10][2] = destroyer;
        }
        else if(random == 3)
        {
            board[8][6] = carrier;
            board[8][7] = carrier;
            board[8][8] = carrier;
            board[8][9] = carrier;
            board[8][10] = carrier;

            board[2][6] = battleship;
            board[2][7] = battleship;
            board[2][8] = battleship;
            board[2][9] = battleship;

            board[5][7] = submarine;
            board[5][8] = submarine;
            board[5][9] = submarine;

            board[7][3] = cruiser;
            board[8][3] = cruiser;
            board[9][3] = cruiser;

            board[3][3] = destroyer;
            board[4][3] = destroyer;
        }
        else if(random == 4)
        {
            board[7][5] = carrier;
            board[7][6] = carrier;
            board[7][7] = carrier;
            board[7][8] = carrier;
            board[7][9] = carrier;

            board[1][9] = battleship;
            board[2][9] = battleship;
            board[3][9] = battleship;
            board[4][9] = battleship;

            board[5][1] = submarine;
            board[5][2] = submarine;
            board[5][3] = submarine;

            board[7][2] = cruiser;
            board[8][2] = cruiser;
            board[9][2] = cruiser;

            board[2][4] = destroyer;
            board[3][4] = destroyer;
        }

    }

}
