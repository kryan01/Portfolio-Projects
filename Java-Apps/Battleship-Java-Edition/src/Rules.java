public class Rules
{
    /**
     * Prints the game rules in a formatted fashion.
     */
    public static void print(){
        System.out.println(
                "\nGame Components:\n" +
                "\tGame Boards\n" +
                "\t\t• You will have a primary ocean grid (10x10) with your fleet pre-positioned\n " +
                    "\t\t  and you will have a targeting grid to track shots fired against the CPU.\n" +
                "\t Ships \n" +
                "\t\t• Each player has a fleet of five ships:\n" +
                "\t\t   Carrier (5 spaces)\n" +
                "\t\t   Battleship (4 spaces)\n" +
                "\t\t   Cruiser (3 spaces)\n" +
                "\t\t   Submarine (3 spaces)\n" +
                "\t\t   Destroyer (2 spaces)\n" +
                "\t Shot Markers\n" +
                "\t\t• Misses will be indicated with a dashed circle  ◌\n" +
                "\t\t• Hits will be indicated by a large X\n" +
                "\t\t• Your placed ships are represented by filled circles  ●\n\n" +

                "Positioning Ships:\n" +
                "\t• The ships are placed on each board at the beginning of the game. The positioning is based on " +
                "one of four randomly selected layouts.\n" +
                "   All layouts adhere to the following standard rules:\n" +
                "\t• Ships may be placed either horizontally or vertically, but not diagonally.\n" +
                "\t• Ships must be placed within the boundaries of the grid and cannot overlap each other.\n" +

                "\nGameplay Overview:\n" +
                "Players take turns calling out coordinates to attack the opponent's ships, attempting to guess the " +
                "locations of the ships.\n\n" +
                "\t1. Calling a Shot:\n" +
                "\t\t• On your turn, you will enter into the console your desired target coordinates on the CPU's " +
                "grid (e.g., \"B7\").\n" +
                "\t\t\t(If the coordinates entered are invalid or have already been targeted, you will be asked to " +
                "re-enter your desired coordinates)\n" +
                "\t\t• Your entered coordinates are checked against the CPU's board and whether the shot is a " +
                "\"hit\" or a \"miss\" " +
                "\n\t\t    will be displayed above their board.\n" +
                "\t\t• If it’s a hit: an X will be displayed at that coordinate on the targeting grid.\n" +
                "\t\t• If it’s a miss: a dashed circle ◌ will be displayed at that coordinate on the targeting grid.\n" +
                "\t2. Recording Hits and Misses:\n" +
                "\t\t  Each time a ship is hit but not sunk, it will be displayed above the respective board." +
                "\n\t\t When a hit sinks a ship, the specific ship is named instead. (e.g., \"YOU SANK THE BATTLESHIP!\").\n" +
                "\nSinking Ships:\n" +
                "\tA ship is considered sunk when all of its coordinates have been hit.\n" +

                "\nWinning the Game:\n" +
                "\tThe game continues in alternating turns until one player has sunk all five of their opponent’s ships.\n" +
                "\tThe first player to sink all of their opponent's ships is the winner.\n\n");
    }
}

