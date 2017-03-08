import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dlo on 17/02/17.
 */
class BuildProperty implements ConstNames {
    private static List<PropertyAbstract> properties;
    private  static Scanner input;
    private static Bank bank;
    static List<PropertyAbstract> getBuildProperties(Bank bank,String fileName) throws IOException {
        BuildProperty.bank = bank;
        properties = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            properties.add(new PropertyDemo(new String[]{"0", "0", "0"}));
        }
        input = new Scanner(new File(fileName));
        initProperty();
        return  properties;

    }

    private  static void initProperty() {
        String line;
        while (input.hasNext()) {
            line = input.nextLine();
            String[] lineInfo = line.split("/");
            switch(lineInfo[1]) {
                case NO_OWNER + "":
                    properties.set(Integer.parseInt(lineInfo[0]), new Property(lineInfo));
                    break;
                case GO + "":
                    properties.set(Integer.parseInt(lineInfo[0]), new PropertyDemo(lineInfo));
                    break;
                case COMM_CHEST + "":
                    properties.set(Integer.parseInt(lineInfo[0]), new PropertyDemo(lineInfo));
                    break;
                case INCOME_TAX + "":
                    properties.set(Integer.parseInt(lineInfo[0]), new PropertyDemo(lineInfo));
                    break;
                case RIALROAD + "":
                    properties.set(Integer.parseInt(lineInfo[0]), new RailRoad(lineInfo));
                    break;
                case CHANCE_CARD + "":
                    properties.set(Integer.parseInt(lineInfo[0]), new PropertyDemo(lineInfo));
                    break;
                case JAIL + "":
                    properties.set(Integer.parseInt(lineInfo[0]), new PropertyDemo(lineInfo));
                    break;
                case UTILITY + "":
                    properties.set(Integer.parseInt(lineInfo[0]), new Utility(BuildProperty.bank, lineInfo));
                    break;
                case FREE_PARK + "":
                    properties.set(Integer.parseInt(lineInfo[0]), new PropertyDemo(lineInfo));
                    break;
                case GO_TO_JAIL + "":
                    properties.set(Integer.parseInt(lineInfo[0]), new PropertyDemo(lineInfo));
                    break;
                case LUXURY_TAX + "":
                    properties.set(Integer.parseInt(lineInfo[0]), new PropertyDemo(lineInfo));
                    break;
            }
        }
    }
}
