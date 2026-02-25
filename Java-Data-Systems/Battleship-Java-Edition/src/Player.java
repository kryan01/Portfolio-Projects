import java.util.Random;

/**These methods generate CPU's target and manipulate the player's board accordingly.*/
public class Player
{
    /**
     * Handles generating random target to player, determining hit on player's board, displaying miss, hit, or sink, and
     * manipulating both hidden and visible player boards to reflect targeted coordinates.
     * @param hidden player's specific ship type and their locations
     * @param board generic ship locations and previously targeted locations by CPU
     * @param ships remaining hits til sunk for each of player's ships
     * @return  player's visible board
     */
    public static String[][] targeted(String[][] board, String[][] hidden, int[] ships)
    {
      // generate coordinates:

        int x = (int)(10*Math.random()+1);  // generate random row number
        // generate random char A-J to represent columns
        char rand = (char) Character.toUpperCase('a' + new Random().nextInt(10));
        int y = Game.convert(rand);     // convert letter to column number

      // if coordinates have already been targeted, generate and set new coordinates.
        while(board[x][y].equals(" X") || board[x][y].equals(" ◌"))
        {
            x = (int)(10*Math.random()+1);
            rand = (char) Character.toUpperCase('a' + new Random().nextInt(10));
            y = Game.convert(rand);
        }

        // display cpu's random target coordinates
        System.out.println("The CPU has targeted " + rand + x);

      // determine if a ship has been hit
        if (hidden[x][y].equals(" A") || hidden[x][y].equals(" B") || hidden[x][y].equals(" C") ||
                hidden[x][y].equals(" S") || hidden[x][y].equals(" D"))
        {
            // display hit to console
            System.out.println("It's a direct hit!");

            if (hidden[x][y].equals(" A"))  // if carrier has been hit:
            {
                if (ships[0] == 1) // and if "hits left" count is currently 1:
                {
                    System.out.println("YOUR CARRIER HAS BEEN SUNK!");  // display carrier has sunk
                }

                ships[0]--; // decrement "hits left" on carrier
            }

            if (hidden[x][y].equals(" B"))  // if battleship has been hit:
            {
                if (ships[1] == 1)  // and if "hits left" count is currently 1:
                {
                    System.out.println("YOUR BATTLESHIP HAS BEEN SUNK!");   // display battleship has sunk
                }

                ships[1]--; // decrement "hits left" on battleship
            }

            if (hidden[x][y].equals(" C"))  // if cruiser has been hit:
            {
                if (ships[2] == 1)  // and if "hits left" count is currently 1:
                {
                    System.out.println("YOUR CRUISER HAS BEEN SUNK!");  // display cruiser has sunk
                }

                ships[2]--; // decrement "hits left" on cruiser
            }

            if (hidden[x][y].equals(" S"))  // if submarine has been hit:
            {
                if (ships[3] == 1)  // and if "hits left" count is currently 1:
                {
                    System.out.println("YOUR SUBMARINE HAS BEEN SUNK!");    // display submarine has sunk
                }

                ships[3]--; // decrement "hits left" on submarine
            }

            if (hidden[x][y].equals(" D"))  // if destroyer has been hit:
            {
                if (ships[4] == 1)  // and if "hits left" count is currently 1:
                {
                    System.out.println("YOUR DESTROYER HAS BEEN SUNK!");    // display destroyer has sunk
                }

                ships[4]--; // decrement "hits left" on destroyer
            }

        // display hit icon on player's board
            board[x][y] = " X";
        } 

      // if shot hits water:
        else if (hidden[x][y].equals(" ~"))
        {
            System.out.println("It's a miss...");   // print miss
            board[x][y] = " ◌"; // display miss icon on player's board
        }

        return board;   // return updated player board
    }
}