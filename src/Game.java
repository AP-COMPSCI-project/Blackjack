import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static Scanner input;
    private static Deck deck;
    private static List<Card> playerHand;
    private static List<Card> compHand;
    // These could probably be ArrayDeques but meh
    private static List<Card> playerPile;
    private static List<Card> compPile;
    private static List<Card>discard;
    private static boolean gameOver;

    public static void main(String[] args) {
        input = new Scanner(System.in);
        boolean playAgain;

        do {
            // plays the game
            play();

            // restarts the game if the user wants to play again
            while(true) {
                System.out.print("Do you want to play again? ");
                String status = input.next();

                if (status.equalsIgnoreCase("yes")) {
                    playAgain = true;
                    break;
                }
                else if (status.equalsIgnoreCase("no")) {
                    playAgain = false;
                    break;
                }
                else
                    System.out.println("Please say \"yes\" or \"no\".");
            }
        } while(playAgain);

        System.out.println("Thank you for playing!");
    }

    /**
    Entry point for each new game.
    */
    public static void play() {
        init();
        run();
        gameOver();
        return;
    }

    /**
    Sets up components needed to play the game.
    */
    private static void init() {
        deck = new Deck();
        playerHand = new ArrayList<>();
        compHand = new ArrayList<>();
        playerPile = new ArrayList<>();
        compPile = new ArrayList<>();
        discard = new ArrayList<>();
        gameOver = false;
    }

    /**
    Main gameplay loop for this class.
    */
    private static void run() {
        gameLoop : while(true) {
            // TODO check if game has been won

            // deal two cards to each player
            playerHand.add(deck.deal());
            playerHand.add(deck.deal());

            compHand.add(deck.deal());
            compHand.add(deck.deal());

            // does either player already have 21?
            if (playerAutoWins() && compAutoWins()) {
                System.out.println("It's a tie! Gold stars for everyone!");
                System.out.println("The cards in both players hands go into the discard pile.");
                printCompHand();
                printPlayerHand();
                discard();
                continue gameLoop;
            } else if (playerAutoWins()) {
                System.out.println("You win!");
                printPlayerHand();
                toPlayerPile();
                continue gameLoop;
            } else {
                System.out.println("The house wins!");
                printCompHand();
                toCompPile();
                continue gameLoop;
            }

            // TODO complete this crap
            // if not, begin player's turn
            String status;
            do {
                if(playerAutoWins()) {
                    System.out.println("You win this hand!");
                    toPlayerPile();
                    continue gameLoop;
                } else if(playerBusts()) {
                    System.out.println("Bust!");
                    toCompPile();
                    continue gameLoop;
                }

                printCompHand(false);
                System.out.println("Do you want to hit or stand?");

                status = input.next();

                if(status.equalsIgnoreCase("hit")) {
                    playerHand.add(deck.deal());
                    printPlayerHand();
                }
                else if(!status.equalsIgnoreCase("stand"))
                    System.out.println("Please say \"hit\" or \"stand\".");
            } while(!status.equalsIgnoreCase("stand"));
        }
    }

    private static void printPlayerHand() { System.out.println("" + playerHand.get(0) + playerHand.get(1)); }

    /**
    If the boolean arg is true, it will print both cards in the computer's hand. If false, it will only print the first.
    */
    private static void printCompHand(boolean secondCardRevealed) {
        if(secondCardRevealed)
            System.out.println(compHand.get(0) + " " + compHand.get(1));
        else
            System.out.println(compHand.get(0) + " ?");
    }

    /**
    Checks to see if the player is dealt a 21.
    */
    private static boolean playerAutoWins() { return playerHand.get(0).getValue() + playerHand.get(1).getValue() == 21; }

    /**
     Checks to see if the computer is dealt a 21.
     */
    private static boolean compAutoWins() { return compHand.get(0).getValue() + compHand.get(1).getValue() == 21; }

    /**
     Checks to see if the player is dealt over 21.
     */
    private static boolean playerBusts() { return playerHand.get(0).getValue() + playerHand.get(1).getValue() > 21; }

    /**
     Checks to see if the computer is dealt over 21.
     */
    private static boolean compBusts() { return compHand.get(0).getValue() + compHand.get(1).getValue() > 21; }

    /**
     Puts the cards currently in both players hands into the player's pile.
    */
    private static void toPlayerPile() {
        playerPile.add(playerHand.get(0));
        playerHand.remove(0);
        playerPile.add(playerHand.get(1));
        playerHand.remove(1);
        playerPile.add(compHand.get(0));
        compHand.remove(0);
        playerPile.add(compHand.get(1));
        compHand.remove(1);
    }

    /**
     Puts the cards currently in both players hands into the player's pile.
     */
    private static void toCompPile() {
        compPile.add(playerHand.get(0));
        playerHand.remove(0);
        compPile.add(playerHand.get(1));
        playerHand.remove(1);
        compPile.add(compHand.get(0));
        compHand.remove(0);
        compPile.add(compHand.get(1));
        compHand.remove(1);
    }

    /**
    Puts the cards currently in both players hands into the discard pile.
    */
    private static void discard() {
        discard.add(playerHand.get(0));
        playerHand.remove(0);
        discard.add(playerHand.get(1));
        playerHand.remove(1);
        discard.add(compHand.get(0));
        compHand.remove(0);
        discard.add(compHand.get(1));
        compHand.remove(1);
    }

    /**
    Figures out who won the round and displays the appropriate message.
    */
    private static void resolveWin() {

    }

    /**
     Figures out who won the game and displays the appropriate message.
    */
    private static void gameOver() {

    }

    /**
    For multiple blank lines.
    */
    private void newlines(int numberOfLines) {
        for(int i = 0; i < numberOfLines; i++)
            System.out.println();
    }
}
