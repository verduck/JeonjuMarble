package marble;

import javafx.animation.TranslateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Player extends Canvas {
	private GraphicsContext gc;
	private Image image;

	public Player() {
		super();
		gc = getGraphicsContext2D();
		String path = "res/car.png";
		try {
			FileInputStream fis = new FileInputStream(path);
			BufferedInputStream bis = new BufferedInputStream(fis);
			image = new Image(bis);
			
			try {
				bis.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		setWidth(image.getWidth());
		setHeight(image.getHeight());
		gc.drawImage(image, 0, 0);
	}
	
	public void move(Marble marble) {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(1));
		transition.setNode(this);
		transition.setToX(marble.getLayoutX());
		transition.setToY(marble.getLayoutY());
		//transition.setToX(marble.getLayoutX() + (marble.getWidth() / 2));
		//transition.setToY(marble.getLayoutY() + (marble.getHeight() / 2));
		transition.play();
	}
}
