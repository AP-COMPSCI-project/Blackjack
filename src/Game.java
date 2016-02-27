import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static Scanner input;
    private static Deck deck;
    private static List<Card> playerHand;
    private static List<Card> compHand;
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
                String response = input.next();

                if (response.equalsIgnoreCase("yes")) {
                    playAgain = true;
                    break;
                }
                else if (response.equalsIgnoreCase("no")) {
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
            // check to see if the game has ended
            if(deck.isEmpty())
                return;
            if(deck.deck21())
                return;

            // deal two cards to each player
            playerHand.add(deck.deal());
            playerHand.add(deck.deal());

            compHand.add(deck.deal());
            compHand.add(deck.deal());

            // does either player already have 21?
            if (playerAutoWins() && compAutoWins()) {
                System.out.println("It's a tie! Gold stars for everyone!");
                System.out.println("The cards in both players hands go into the discard pile.");
                printCompHand(true);
                printPlayerHand();
                discard();
                continue gameLoop;
            } else if (playerAutoWins()) {
                System.out.println("You win!");
                printPlayerHand();
                toPlayerPile();
                continue gameLoop;
            } else if(compAutoWins()) {
                System.out.println("The house wins!");
                printCompHand(true);
                toCompPile();
                continue gameLoop;
            }

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
                printPlayerHand();

                status = input.next();

                if(status.equalsIgnoreCase("hit")) {
                    playerHand.add(deck.deal());
                    printPlayerHand();
                }
                else if(!status.equalsIgnoreCase("stand"))
                    System.out.println("Please say \"hit\" or \"stand\".");
            } while(!status.equalsIgnoreCase("stand"));

            // once the player passes the turn, begin the house's turn
            while(!compAutoWins() && !compBusts() && (compHand.get(0).getValue() + compHand.get(1).getValue() < 17)) {
                System.out.println("It's now the house's turn...");
                printCompHand(true);
                compHand.add(deck.deal());
                System.out.println("The house will now hit.");
                printCompHand(true);
            }

            resolveWin();
        }
    }

    private static void printPlayerHand() {
        for(Card card : playerHand)
            System.out.print(card + " ");
        System.out.println();
    }

    /**
    If the boolean arg is true, it will print both cards in the computer's hand. If false, it will only print the first.
    */
    private static void printCompHand(boolean secondCardRevealed) {
        for(int i = 0; i < compHand.size(); i++)
            System.out.print( (i > 0 || secondCardRevealed ? compHand.get(i) : "???") + " ");
        System.out.println();
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
        playerPile.add(playerHand.get(1));
        playerHand.clear();

        playerPile.add(compHand.get(0));
        playerPile.add(compHand.get(1));
        compHand.clear();
    }

    /**
     Puts the cards currently in both players hands into the player's pile.
     */
    private static void toCompPile() {
        compPile.add(playerHand.get(0));
        compPile.add(playerHand.get(1));
        playerHand.clear();

        compPile.add(compHand.get(0));
        compPile.add(compHand.get(1));
        compHand.clear();
    }

    /**
    Puts the cards currently in both players hands into the discard pile.
    */
    private static void discard() {
        discard.add(playerHand.get(0));
        discard.add(playerHand.get(1));

        discard.add(compHand.get(0));
        discard.add(compHand.get(1));

        playerHand.clear();
        compHand.clear();
    }

    /**
    Figures out who won the round and displays the appropriate message.
    */
    private static void resolveWin() {
        int playerScore = 0;
        int compScore = 0;

        for(Card card : playerHand)
            playerScore += card.getValue();

        for(Card card : compHand)
            compScore += card.getValue();

        if(playerScore > compScore) {
            System.out.println("You win this hand!");
            toPlayerPile();
        } else if(compScore > playerScore) {
            System.out.println("The house wins this hand!");
            toCompPile();
        } else {
            System.out.println("It's a tie!");
            discard();
        }
    }

    /**
     Figures out who won the game and displays the appropriate message.
    */
    private static void gameOver() {
        if(playerPile.size() > compPile.size()) {
            System.out.println("Congrats on winning!");
        } else if(playerPile.size() < compPile.size()) {
            System.out.println("Unfortunately you've lost!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    /**
    For multiple blank lines. Possibly unnecessary.
    */
    private void newlines(int numberOfLines) {
        for(int i = 0; i < numberOfLines; i++)
            System.out.println();
    }
}
