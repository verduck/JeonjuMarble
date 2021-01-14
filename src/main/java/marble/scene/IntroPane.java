package marble.scene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import marble.Place;

public class IntroPane extends SubScene {
	protected BorderPane root;
	
	private Text placeName;
	private WebView webView;
	private WebEngine webEngine;
	private Button btnOk;
	
	public IntroPane() {
		super(new BorderPane(), 500, 500);
		root = (BorderPane) getRoot();
		root.setStyle("-fx-background-color: white; -fx-border-radius: 20;");
		root.setPadding(new Insets(10, 10, 10, 10));

		BorderPane titlePane = new BorderPane();
		titlePane.setPadding(new Insets(10, 10, 10, 10));
		placeName = new Text("이름");
		placeName.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 48));
		titlePane.setCenter(placeName);
		root.setTop(titlePane);

		webView = new WebView();
		webEngine = webView.getEngine();
		webEngine.load("http://193.123.245.244/map/");
		root.setCenter(webView);

		BorderPane btnPane = new BorderPane();
		btnPane.setPadding(new Insets(10, 10, 10, 10));
		btnOk = new Button();
		btnOk.setText("확인");
		btnOk.setPrefSize(100, 30);
		btnOk.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent arg0) {
				setVisible(false);
			}

		});
		btnPane.setCenter(btnOk);
		root.setBottom(btnPane);

		setVisible(false);
		setLayoutX((800 / 2) - (500 / 2));
		setLayoutY((600 / 2) - (500 / 2));
	}
	
	public void show(Place place) {
		placeName.setText(place.getName());
		placeName.setLayoutX(15);
		if (!place.getName().equals("상품")) {
			webView.setVisible(true);
			webEngine.executeScript("moveTo(" + place.getLat() + ", " + place.getLng() + ")");
		} else {
			webView.setVisible(false);
		}
		setVisible(true);

	}
}
