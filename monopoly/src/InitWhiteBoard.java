import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;

/**
 * Created by dlo on 10/02/17.
 */
class InitWhiteBoard {

    static void initWhiteBoardsBottom(ArrayList<Cell> cells, Pane boardCenter,HBox bottomRow) {
        final int y = 360;
        final int x = 78;
        int i = 1;
        BorderPane p1;
        ImageView im1 = new ImageView("/images/whilteBoard.png");
        ImageView im2 = new ImageView("/images/arrowJail.png");
        StackPane a;
        HBox buttonsH;
        BorderPane center;

        for(int j = 0; j < bottomRow.getChildren().size(); j++) {
            Cell cell = (Cell)bottomRow.getChildren().get(j);
            switch (cell.getCellNumber()) {
                case 1:
                    //cell 11 jail
                    //set images here
                    p1 = new BorderPane();
                    p1.setVisible(false);
                    p1.setLayoutX(2);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.setAlignment(Pos.CENTER_LEFT);
                    a.getChildren().add(im2);
                    p1.setBottom(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 2:
                    //cell 10  connecticut avenue
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/connecticu_ ave.jpg");
                    im2 = new ImageView("/images/arrowBottom.png");
                    p1.setVisible(false);
                    p1.setLayoutX(2);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.setAlignment(Pos.CENTER_LEFT);
                    a.getChildren().add(im2);
                    p1.setBottom(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttonsH = new HBox(20);
                    buttonsH.setAlignment(Pos.CENTER);
                    buttonsH.getChildren().addAll(
                            cell.getbuyButton(), cell.getAuctionButton());
                    center.setTop(buttonsH);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 3:
                    //cell 9 vermont ave
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/vermont_ave.jpg");
                    im2 = new ImageView("/images/arrowBottom.png");
                    p1.setVisible(false);
                    p1.setLayoutX(2);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setBottom(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttonsH = new HBox(20);
                    buttonsH.setAlignment(Pos.CENTER);
                    buttonsH.getChildren().addAll(
                            cell.getbuyButton(), cell.getAuctionButton());
                    center.setTop(buttonsH);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;

                case 4:
                    //cell 8 chance
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/whilteBoard.png");
                    im2 = new ImageView("/images/arrowBottom.png");
                    p1.setVisible(false);
                    p1.setLayoutX(20 + x * i++);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setBottom(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 5:
                    //cell 7 Oriental ave
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/oriental_ave.jpg");
                    im2 = new ImageView("/images/arrowBottom.png");
                    p1.setVisible(false);
                    p1.setLayoutX(20 + x * i++);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setBottom(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttonsH = new HBox(20);
                    buttonsH.setAlignment(Pos.CENTER);
                    buttonsH.getChildren().addAll(
                            cell.getbuyButton(), cell.getAuctionButton());
                    center.setTop(buttonsH);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 6:
                    //cell 6 Reading Rail road
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/reading_railroad.jpg");
                    im2 = new ImageView("/images/arrowBottom.png");
                    p1.setVisible(false);
                    p1.setLayoutX(20 + x * i++);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setBottom(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttonsH = new HBox(20);
                    buttonsH.setAlignment(Pos.CENTER);
                    buttonsH.getChildren().addAll(
                            cell.getbuyButton(), cell.getAuctionButton());
                    center.setTop(buttonsH);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 7:
                    //cell 5 income tax
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/whilteBoard.png");
                    im2 = new ImageView("/images/arrowBottom.png");
                    p1.setVisible(false);
                    p1.setLayoutX(20 + x * i++);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setBottom(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 8:
                    //cell 4 baltic ave
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/baltic_ave.jpg");
                    im2 = new ImageView("/images/arrowBottom.png");
                    p1.setVisible(false);
                    p1.setLayoutX(20 + x * i++);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setBottom(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttonsH = new HBox(20);
                    buttonsH.setAlignment(Pos.CENTER);
                    buttonsH.getChildren().addAll(
                            cell.getbuyButton(), cell.getAuctionButton());
                    center.setTop(buttonsH);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 9:
                    //cell 3 community cest
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/whilteBoard.png");
                    im2 = new ImageView("/images/arrowBottom.png");
                    p1.setVisible(false);
                    p1.setLayoutX(20 + x * i);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setBottom(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 10:
                    //cell 2 mediterranean ave mediterranean_ave.jpg
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/mediterranean_ave.jpg");
                    im2 = new ImageView("/images/arrowBottom.png");
                    p1.setVisible(false);
                    p1.setLayoutX(44 + x * 6);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    a.setAlignment(Pos.CENTER_RIGHT);
                    p1.setBottom(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttonsH = new HBox(20);
                    buttonsH.setAlignment(Pos.CENTER);
                    buttonsH.getChildren().addAll(
                            cell.getbuyButton(), cell.getAuctionButton());
                    center.setTop(buttonsH);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 11:
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/whilteBoard.png");
                    im2 = new ImageView("/images/arrowGo.png");
                    p1.setVisible(false);
                    p1.setLayoutX( 44 + x * 6);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.setAlignment(Pos.CENTER_RIGHT);
                    a.getChildren().add(im2);
                    p1.setBottom(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
            }
        }
    }

    static void initWhiteBoardTop(ArrayList<Cell> cells, Pane boardCenter, HBox topRow) {
        final int  y = 1;
        final int x = 78;
        int i = 1;
        HBox buttons;
        BorderPane p1;
        ImageView im1;
        ImageView im2;
         BorderPane center;
        StackPane a;
        for (int j = 0; j < topRow.getChildren().size(); j++) {
            Cell cell = (Cell)topRow.getChildren().get(j);

            switch (cell.getCellNumber()) {
                case 1:
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/whilteBoard.png");
                    im2 = new ImageView("/images/arrowParking.png");
                    p1.setVisible(false);
                    p1.setLayoutX(2);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.setAlignment(Pos.CENTER_LEFT);
                    a.getChildren().add(im2);
                    p1.setTop(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 2:
                    //cell 21 kentucky ave
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/kentucky_ave.jpg");
                    im2 = new ImageView("/images/arrowTop.png");
                    p1.setVisible(false);
                    p1.setLayoutX(2);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.setAlignment(Pos.CENTER_LEFT);
                    a.getChildren().add(im2);
                    p1.setTop(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttons = new HBox(20);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(cell.getbuyButton(), cell.getAuctionButton());
                    center.setBottom(buttons);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 3:
                    //cell 22 chance
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/whilteBoard.png");
                    im2 = new ImageView("/images/arrowTop.png");
                    p1.setVisible(false);
                    p1.setLayoutX(20);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setTop(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 4:
                    //cell 23 indiana ave
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/indiana_ave.jpg");
                    im2 = new ImageView("/images/arrowTop.png");
                    p1.setVisible(false);
                    p1.setLayoutX(20 + x * i++);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setTop(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttons = new HBox(20);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(cell.getbuyButton(), cell.getAuctionButton());
                    center.setBottom(buttons);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 5:
                    //cell 24 illinois ave
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/illinois_ave.jpg");
                    im2 = new ImageView("/images/arrowTop.png");
                    p1.setVisible(false);
                    p1.setLayoutX(20 + x * i++);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setTop(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttons = new HBox(20);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(cell.getbuyButton(), cell.getAuctionButton());
                    center.setBottom(buttons);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 6:
                    //cell 25 B&O railroad
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/b&O_railRoad.jpg");
                    im2 = new ImageView("/images/arrowTop.png");
                    p1.setVisible(false);
                    p1.setLayoutX(20 + x * i++);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setTop(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttons = new HBox(20);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(cell.getbuyButton(), cell.getAuctionButton());
                    center.setBottom(buttons);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 7:
                    //cell 26 atlantic ave
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/atlantic_ave.jpg");
                    im2 = new ImageView("/images/arrowTop.png");
                    p1.setVisible(false);
                    p1.setLayoutX(20 + x * i++);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setTop(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttons = new HBox(20);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(cell.getbuyButton(), cell.getAuctionButton());
                    center.setBottom(buttons);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 8:
                    //cell 27 ventinor ave
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/ventnor_ave.jpg");
                    im2 = new ImageView("/images/arrowTop.png");
                    p1.setVisible(false);
                    p1.setLayoutX(20 + x * i);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setTop(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttons = new HBox(20);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(cell.getbuyButton(), cell.getAuctionButton());
                    center.setBottom(buttons);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 9:
                    //cell 28 water work
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/util_water.jpg");
                    im2 = new ImageView("/images/arrowTop.png");
                    p1.setVisible(false);
                    p1.setLayoutX( 44 + x * 6);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setTop(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttons = new HBox(20);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(cell.getbuyButton(), cell.getAuctionButton());
                    center.setBottom(buttons);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 10:
                    //cell 29 marvin gardens
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/marvin_gardens.jpg");
                    im2 = new ImageView("/images/arrowTop.png");
                    p1.setVisible(false);
                    p1.setLayoutX( 44 + x * 6);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.setAlignment(Pos.CENTER_RIGHT);
                    a.getChildren().add(im2);
                    p1.setTop(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttons = new HBox(20);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(cell.getbuyButton(), cell.getAuctionButton());
                    center.setBottom(buttons);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 11:
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/whilteBoard.png");
                    im2 = new ImageView("/images/arrowGoToJail.png");
                    p1.setVisible(false);
                    p1.setLayoutX( 44 + x * 6);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.setAlignment(Pos.CENTER_RIGHT);
                    a.getChildren().add(im2);
                    p1.setTop(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
            }
        }
    }
    static void initWhiteBoardLeft(ArrayList<Cell> cells, Pane boardCenter, VBox leftCol) {
        int y = 1;
        int x = 1;
        int yIncrement = 73;
        int cellNumber = 19;
        int j = 1;
        BorderPane p1;
        ImageView im1;
        ImageView im2;
        StackPane a;
        BorderPane center;
        HBox buttonsH;
        for (int i = 0; i < leftCol.getChildren().size(); i++) {
            Cell cell = (Cell) leftCol.getChildren().get(i);
            switch (cell.getCellNumber()) {
                case 1:
                    // cell 20 new york ave
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/new_york.jpg");
                    im2 = new ImageView("/images/arrowLeft.png");
                    p1.setVisible(false);
                    p1.setLayoutX(x);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.setAlignment(Pos.TOP_LEFT);
                    a.getChildren().add(im2);
                    center = new BorderPane();
                    center.setCenter(im1);
                    buttonsH = new HBox(20);
                    buttonsH.setAlignment(Pos.CENTER);
                    buttonsH.getChildren().addAll(
                            cells.get(cellNumber).getbuyButton(), cells.get(cellNumber).getAuctionButton());
                    center.setBottom(buttonsH);
                    p1.setCenter(center);
                    p1.setLeft(a);
                    boardCenter.getChildren().add(p1);
                    cells.get(cellNumber--).setWhiteBoard(p1);
                    break;
                case 2:
                    //cell 19 tennessee ave
                    p1 = new BorderPane();
                    p1.setVisible(false);
                    im1 = new ImageView("/images/tennessee_ave.jpg");
                    im2 = new ImageView("/images/arrowLeft.png");
                    p1.setLayoutX(x);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setLeft(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    buttonsH = new HBox(20);
                    buttonsH.setAlignment(Pos.CENTER);
                    buttonsH.getChildren().addAll(
                            cells.get(cellNumber).getbuyButton(), cells.get(cellNumber).getAuctionButton());
                    center.setBottom(buttonsH);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cells.get(cellNumber--).setWhiteBoard(p1);
                    break;
                case 3:
                    //cell 18 community chest
                    p1 = new BorderPane();
                    p1.setVisible(false);
                    im1 = new ImageView("/images/whilteBoard.png");
                    im2 = new ImageView("/images/arrowLeft.png");
                    p1.setLayoutX(x);
                    p1.setLayoutY(y + yIncrement * j++);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setLeft(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cells.get(cellNumber--).setWhiteBoard(p1);
                    break;
                case 4:
                    // cell 17 st_james_place.jpg
                    p1 = new BorderPane();
                    p1.setVisible(false);
                    im1 = new ImageView("/images/st_james_place.jpg");
                    im2 = new ImageView("/images/arrowLeft.png");
                    p1.setLayoutX(x);
                    p1.setLayoutY(y + yIncrement * j++);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setLeft(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    buttonsH = new HBox(20);
                    buttonsH.setAlignment(Pos.CENTER);
                    buttonsH.getChildren().addAll(
                            cells.get(cellNumber).getbuyButton(), cells.get(cellNumber).getAuctionButton());
                    center.setBottom(buttonsH);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cells.get(cellNumber--).setWhiteBoard(p1);
                    break;
                case 5:
                    //cell 16 pennsylvania_railroad.jpg
                    p1 = new BorderPane();
                    p1.setVisible(false);
                    im1 = new ImageView("/images/pennsylvania_railroad.jpg");
                    im2 = new ImageView("/images/arrowLeft.png");
                    p1.setLayoutX(x);
                    p1.setLayoutY(y + yIncrement * j++);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setLeft(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    buttonsH = new HBox(20);
                    buttonsH.setAlignment(Pos.CENTER);
                    buttonsH.getChildren().addAll(
                            cells.get(cellNumber).getbuyButton(), cells.get(cellNumber).getAuctionButton());
                    center.setBottom(buttonsH);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cells.get(cellNumber--).setWhiteBoard(p1);
                    break;
                case 6:
                    //cell 15 virginia_ave.jpg
                    p1 = new BorderPane();
                    p1.setVisible(false);
                    im1 = new ImageView("/images/virginia_ave.jpg");
                    im2 = new ImageView("/images/arrowLeft.png");
                    p1.setLayoutX(x);
                    p1.setLayoutY(y + yIncrement * j++);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setLeft(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    buttonsH = new HBox(20);
                    buttonsH.setAlignment(Pos.CENTER);
                    buttonsH.getChildren().addAll(
                            cells.get(cellNumber).getbuyButton(), cells.get(cellNumber).getAuctionButton());
                    center.setBottom(buttonsH);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cells.get(cellNumber--).setWhiteBoard(p1);
                    break;
                case 7:
                    //cell 14 status ave
                    p1 = new BorderPane();
                    p1.setVisible(false);
                    im1 = new ImageView("/images/states_ave.jpg");
                    im2 = new ImageView("/images/arrowLeft.png");
                    p1.setLayoutX(x);
                    p1.setLayoutY(y + yIncrement * j++);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setLeft(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    buttonsH = new HBox(20);
                    buttonsH.setAlignment(Pos.CENTER);
                    buttonsH.getChildren().addAll(
                            cells.get(cellNumber).getbuyButton(), cells.get(cellNumber).getAuctionButton());
                    center.setBottom(buttonsH);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cells.get(cellNumber--).setWhiteBoard(p1);
                    break;
                case 8:
                    //cell 13 electric
                    p1 = new BorderPane();
                    p1.setVisible(false);
                    im1 = new ImageView("/images/util_electic.jpg");
                    im2 = new ImageView("/images/arrowLeft.png");
                    p1.setLayoutX(x);
                    p1.setLayoutY(y + yIncrement * j++);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setLeft(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    buttonsH = new HBox(20);
                    buttonsH.setAlignment(Pos.CENTER);
                    buttonsH.getChildren().addAll(
                            cells.get(cellNumber).getbuyButton(), cells.get(cellNumber).getAuctionButton());
                    center.setTop(buttonsH);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cells.get(cellNumber--).setWhiteBoard(p1);
                    break;
                case 9:
                    //cell 11 st. charles ave
                    p1 = new BorderPane();
                    p1.setVisible(false);
                    im1 = new ImageView("/images/st_charles_place.jpg");
                    im2 = new ImageView("/images/arrowLeft2.png");
                    p1.setLayoutX(x);
                    p1.setLayoutY(y + yIncrement * 6 );
                    a = new StackPane();
                    a.setAlignment(Pos.BOTTOM_LEFT);
                    a.getChildren().add(im2);
                    center = new BorderPane();
                    p1.setLeft(a);
                    center.setCenter(im1);
                    buttonsH = new HBox(20);
                    buttonsH.setAlignment(Pos.CENTER);
                    buttonsH.getChildren().addAll(
                            cells.get(cellNumber).getbuyButton(), cells.get(cellNumber).getAuctionButton());
                    center.setTop(buttonsH);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cells.get(cellNumber).setWhiteBoard(p1);
            }
        }
    }

    static void initWhiteBoardsRight(ArrayList<Cell> cells , Pane boardCenter, VBox rightCol) {
        final int y = 1;
        final int x = 440;
        int yIncrement = 75;
        int i = 1;
        BorderPane p1;
        ImageView im1;
        ImageView im2;
        StackPane a;
        BorderPane center;
        HBox buttons;
        for (int j = 0; j < rightCol.getChildren().size(); j++) {
            Cell cell = (Cell)rightCol.getChildren().get(j);
            switch (cell.getCellNumber()) {
                case 1:
                    //cell 31 pacific ave
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/pacific_avenue.jpg");
                    im2 = new ImageView("/images/arrowRight.png");
                    p1.setVisible(false);
                    p1.setLayoutX(x);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.setAlignment(Pos.TOP_LEFT);
                    a.getChildren().add(im2);
                    p1.setRight(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttons = new HBox(20);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(cell.getbuyButton(), cell.getAuctionButton());
                    center.setBottom(buttons);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 2:
                    //cell 32 north carolina ave
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/pacific_avenue.jpg");
                    im2 = new ImageView("/images/arrowRight.png");
                    p1.setVisible(false);
                    p1.setLayoutX(x);
                    p1.setLayoutY(y);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setRight(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttons = new HBox(20);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(cell.getbuyButton(), cell.getAuctionButton());
                    center.setBottom(buttons);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 3:
                    //cell 33 community chest
                    p1 = new BorderPane();
                    p1.setVisible(false);
                    im1 = new ImageView("/images/whilteBoard.png");
                    im2 = new ImageView("/images/arrowRight.png");
                    p1.setLayoutX(x);
                    p1.setLayoutY(y + yIncrement * i++ - 5);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setRight(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 4:
                    //cell 34 pennsylvania ave
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/pennysylvania_ave.jpg");
                    im2 = new ImageView("/images/arrowRight.png");
                    p1.setVisible(false);
                    p1.setLayoutX(x);
                    p1.setLayoutY(y + yIncrement * i++);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setRight(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttons = new HBox(20);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(cell.getbuyButton(), cell.getAuctionButton());
                    center.setBottom(buttons);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 5:
                    //cell 35 short line
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/short_line.jpg");
                    im2 = new ImageView("/images/arrowRight.png");
                    p1.setVisible(false);
                    p1.setLayoutX(x);
                    p1.setLayoutY(y + yIncrement * i++);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setRight(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttons = new HBox(20);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(cell.getbuyButton(), cell.getAuctionButton());
                    center.setBottom(buttons);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 6:
                    //cell 36 short line
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/whilteBoard.png");
                    im2 = new ImageView("/images/arrowRight.png");
                    p1.setVisible(false);
                    p1.setLayoutX(x);
                    p1.setLayoutY(y + yIncrement * i++);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setRight(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 7:
                    //cell 37 park place
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/park_place.jpg");
                    im2 = new ImageView("/images/arrowRight.png");
                    p1.setVisible(false);
                    p1.setLayoutX(x);
                    p1.setLayoutY(y + yIncrement * i++);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setRight(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttons = new HBox(20);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(cell.getbuyButton(), cell.getAuctionButton());
                    center.setBottom(buttons);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 8:
                    //cell 38 luxury tax
                    p1 = new BorderPane();
                    im1 = new ImageView("/images/whilteBoard.png");
                    im2 = new ImageView("/images/arrowRight.png");
                    p1.setVisible(false);
                    p1.setLayoutX(x);
                    p1.setLayoutY(y + yIncrement * i);
                    a = new StackPane();
                    a.getChildren().add(im2);
                    p1.setRight(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
                    break;
                case 9:
                    p1 = new BorderPane();
                    p1.setVisible(false);
                    im1 = new ImageView("/images/boardworlk.jpg");
                    im2 = new ImageView("/images/arrowRight.png");
                    p1.setLayoutX(x);
                    p1.setLayoutY( y + yIncrement * 6 - 20);
                    a = new StackPane();
                    a.setAlignment(Pos.BOTTOM_LEFT);
                    a.getChildren().add(im2);
                    p1.setRight(a);
                    center = new BorderPane();
                    center.setCenter(im1);
                    p1.setCenter(center);
                    buttons = new HBox(20);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(cell.getbuyButton(), cell.getAuctionButton());
                    center.setTop(buttons);
                    boardCenter.getChildren().add(p1);
                    cell.setWhiteBoard(p1);
            }
        }
    }
}
