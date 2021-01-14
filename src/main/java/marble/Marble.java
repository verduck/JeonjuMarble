package marble;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Marble extends AnchorPane {
	private Place place;
	private Text placeName;
	
	public Marble() {
		placeName = new Text();
		placeName.setFont(Font.font("돋음", FontWeight.BOLD, FontPosture.REGULAR, 18));
		placeName.setFill(Color.BLACK);
		placeName.setStrokeWidth(1);
		placeName.setStroke(Color.BLACK);
		placeName.setLayoutX(0);
		placeName.setLayoutY(25);
		getChildren().add(placeName);
	}
	
	public void setPlace(Place place) {
		this.place = place;
		if (!place.getName().equals("상품")) {
			placeName.setText(place.getName());
		}
	}

	public Place getPlace() {
		return place;
	}
}
