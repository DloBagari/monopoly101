import javafx.scene.image.*;

import java.io.IOException;

public class PrefImageView extends ImageView {
    private Image I;
    private Cell cell;
    private final double imageWidth;
    private final double imageHeight;

    PrefImageView(Image image, Cell cell) {
        super(image);
        I = getImage();
        this.cell = cell;
        setPreserveRatio(false);
        imageWidth = I.getWidth();
        imageHeight = I.getHeight();
    }



    public void updatePosition(Cell toCell ,int whoIsTurn, int position, int status) throws InterruptedException, IOException, ClassNotFoundException {

        cell.removeTokenFromTokenQueue(position);

        this.cell = toCell;
        this.cell.addTokenTotokensQueue(this, whoIsTurn, position, status);
    }

    @Override
    public double minWidth(double height) {
        return 20;
    }

    @Override
    public double prefWidth(double width) {
        if (I==null) return minWidth(width);
        return I.getWidth() / cell.getNumberOftokensInCell() ;
    }

    @Override
    public double maxWidth(double width) {
        return I.getWidth() / cell.getNumberOftokensInCell() ;
    }

    @Override
    public double minHeight(double height) {
        return 20;
    }

    @Override
    public double prefHeight(double height) {
        if (I==null) return minHeight(height);
        return I.getHeight() / cell.getNumberOftokensInCell() ;
    }

    @Override
    public double maxHeight(double height) {

        return I.getHeight() / cell.getNumberOftokensInCell() ;
    }

    @Override
    public boolean isResizable()
    {
        return true;
    }

    @Override
    public void resize(double width, double height) {
        setFitWidth(width);
        setFitHeight(height);
    }
    public void updateSize() {
        resize(imageWidth / cell.getNumberOftokensInCell(),
                imageHeight / cell.getNumberOftokensInCell());
    }
}