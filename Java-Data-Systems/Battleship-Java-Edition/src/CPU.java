import java.util.Scanner;

/**These methods get player's target coordinates and manipulate CPU's board accordingly */
public class CPU
{
    /**
     * Prompt player for target coordinates, validate input.
     * @return Player's target coordinates
     */
    private static String[] getPlayersTarget()
    {
        Scanner input = new Scanner(System.in);

        System.out.print("\nEnter target coordinates: "); // prompt player for target coordinates
        String coordinates = input.next().toUpperCase();    // format input
        String letter = coordinates.substring(0,1); // get letter portion of coordinates for column choice

        // While number portion of coordinates is not an integer, prompt again.
        while (!coordinates.substring(1).matches("\\d+"))
        {
            System.out.print("Try again. Enter target coordinates (e.g., \"A1\"): "); // re-prompt for row choice
            coordinates = input.next().toUpperCase();   // format new input
            letter = coordinates.substring(0,1);    // get letter portion of new input coordinates
        }

        int num = Integer.parseInt(coordinates.substring(1));   // get number portion of coordinates

        // check for input outside acceptable range for both letter and number
        while ( (!letter.matches("[A-J]")) || (num < 1 || num > 10) )
        {
            while (!letter.matches("[A-J]"))  // re-prompt loop for column choice, re-define variable.
            {
                System.out.print("\nTry again. Enter a letter A-J: ");
                letter = input.next().toUpperCase();
            }

            while (num < 1 || num > 10) // [out of bounds] re-prompt loop for row choice, re-define variable.
            {
                System.out.print("\nEnter a number 1-10: ");
                String number = input.next();

                while (!number.matches("\\d+")) // [NaN] re-prompt loop for row choice, re-define variable.
                {
                    System.out.print("\nEnter a number 1-10: ");
                    number = input.next();
                }
                num = Integer.parseInt(number);
            }
        }
        return new String[]{letter, String.valueOf(num)};   // return player's chosen coordinates
    }

    /**
     * Handles getting player's target input, determining hit on CPU board, displaying miss, hit, or sink, and
     * manipulating both hidden and visible CPU boards to reflect targeted coordinates.
     * @param hidden CPU's ship locations
     * @param board previously targeted locations by player
     * @param ships remaining hits til sunk for each of CPU's ships
     * @return targeting grid
     */
    public static String[][] targeted(String[][] hidden, String[][] board, int[] ships)
    {
        String[] coordinates = getPlayersTarget();  // get player's target coordinates and set to string variable

        int x = Integer.parseInt(coordinates[1]);    // set int variable to represent row
        int y = Game.convert(coordinates[0].charAt(0));    // set int variable to represent column

        // if coordinates have already been targeted, prompt again for input and re-define variables.
        while(board[x][y].equals(" X") || board[x][y].equals(" ◌"))
        {
            System.out.print("You have already targeted these coordinates. Choose another.");
            coordinates = getPlayersTarget();
            x = Integer.parseInt(coordinates[1]);
            y = Game.convert(coordinates[0].charAt(0));
        }

        // determine if a ship has been hit
        if (hidden[x][y].equals(" A") || hidden[x][y].equals(" B") || hidden[x][y].equals(" C") ||
                hidden[x][y].equals(" S") || hidden[x][y].equals(" D"))
        {
            // display hit to console
            System.out.println("It's a direct hit!");

            if(hidden[x][y].equals(" A"))   // if carrier has been hit:
            {
                if(ships[0] == 1)   // if "hits left" count is currently 1:
                {
                    System.out.println("YOU SUNK THE CARRIER!");    // display carrier has sunk
                }

                ships[0]--; // decrement "hits left" on carrier
            }

            if(hidden[x][y].equals(" B"))   // if battleship has been hit:
            {
                if(ships[1] == 1)   // if "hits left" count is currently 1:
                {
                    System.out.println("YOU SUNK THE BATTLESHIP!"); // display battleship has sunk
                }

                ships[1]--; // decrement "hits left" on battleship
            }

            if(hidden[x][y].equals(" C"))   // if cruiser has been hit:
            {
                if (ships[2] == 1)  // if "hits left" count is currently 1:
                {
                    System.out.println("YOU SUNK THE CRUISER!");    // display cruiser has sunk
                }

                ships[2]--; // decrement "hits left" on cruiser
            }

            if(hidden[x][y].equals(" S"))   // if submarine has been hit:
            {
                if(ships[3] == 1)    // if "hits left" count is currently 1:
                {
                    System.out.println("YOU SUNK THE SUBMARINE!");  // display submarine has sunk
                }

                ships[3]--; // decrement "hits left" on submarine
            }

            if(hidden[x][y].equals(" D"))   // if destroyer has been hit:
            {
                if(ships[4] == 1)   // if "hits left" count is currently 1:
                {
                    System.out.println("YOU SUNK THE DESTROYER!");  // display destroyer has sunk
                }

                ships[4]--; // decrement "hits left" on destroyer
            }

        // display hit icon on cpu's visible board
            board[x][y] = " X"; // display hit icon on cpu's visible board
        }

    // if shot hits water:
        else if (hidden[x][y].equals(" ~"))
        {
            System.out.println("It's a miss...");   // print miss
            board[x][y] = " ◌"; // display miss icon on cpu's visible board
        }

        return board;   // return updated targeting grid
    }

}
