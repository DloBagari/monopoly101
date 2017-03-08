import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Cathal on 14/02/2017.
 */
public class CreateCardObjects implements ConstNames {

    static LinkedList<Card> makeCommunityCards(Bank bank, String fileName) throws IOException {
        LinkedList<Card>  treasureCards = new LinkedList<>();
        File cardsFile = new File(fileName);
        Scanner input = new Scanner(cardsFile);
        while(input.hasNext()) {
            String[] lineInfo = input.nextLine().split("/");
            switch(lineInfo[0]) {
                case PAY_TO_BANK + "":
                    treasureCards.add(new CardPayToBank( bank, lineInfo));
                    break;
                case RECEIVE_FROM_BANK + "":
                    treasureCards.add(new CardReceiveFromBank(bank, lineInfo));
                    break;
                case STREET_REPAIRS + "":
                    treasureCards.add(new StreetRepairs(bank,lineInfo));
                    break;
                case BIRTHDAY_COLLECT + "":
                    treasureCards.add(new CardReceiveFromEveryPlayer(bank, lineInfo));
                    break;
                case ADVANCE + "":
                    treasureCards.add(new AdvanceCard(bank, lineInfo));
            }
        }
        return treasureCards;
    }

    static LinkedList<Card> makeChanceCards(Bank bank, String fileName) throws IOException {
        LinkedList<Card>  chanceCards = new LinkedList<>();
        File cardsFile = new File(fileName);
        Scanner input = new Scanner(cardsFile);
        while(input.hasNext()) {
            String[] lineInfo = input.nextLine().split("/");
            switch(lineInfo[0]) {
                case PAY_TO_BANK + "":
                    chanceCards.add(new CardPayToBank( bank, lineInfo));
                    break;
                case RECEIVE_FROM_BANK + "":
                    chanceCards.add(new CardReceiveFromBank(bank, lineInfo));
                    break;
                case STREET_REPAIRS + "":
                    chanceCards.add(new StreetRepairs(bank,lineInfo));
                    break;
                case PAY_TO_EACH_PLAYER + "":
                    chanceCards.add(new PayToEachPlayer(bank, lineInfo));
                    break;
                case MOVE_TO + "":
                    chanceCards.add(new MoveTo(bank, lineInfo));
            }
        }
        return chanceCards;
    }

    private static class PayToEachPlayer extends CardPayToBank {

        public PayToEachPlayer(Bank bank, String[] lineInfo) {
            super(bank, lineInfo);
        }

        @Override
        public void process(int playerID) {
            for (int p = 0; p < bank.customersNumber(); p++) {
                if ( playerID != p) {
                    bank.playerToPlayer(playerID, p, amount,
                            "<= €" + amount + " to player" + p + ": " + cardName + "\n",
                            "=> €" + amount + " from player"+playerID + ": " + cardName + "\n");
                }
            }
        }
    }//end PayToEachPlayer

    private static class MoveTo extends  Card {
        private int jumpTo;
        public MoveTo(Bank bank,String[] lineInfo) {
            super(bank, Integer.parseInt(lineInfo[0].trim())
                    , Integer.parseInt(lineInfo[1].trim()), lineInfo[2]);
            jumpTo = Integer.parseInt(lineInfo[3].trim());

        }

        @Override
        public int getAmount() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void process(int property) throws IOException, InterruptedException {
            bank.movePlayerTo(jumpTo);
        }
    }

}
