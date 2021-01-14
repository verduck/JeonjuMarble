package marble.scene;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import marble.Marble;
import marble.MarbleApp;
import marble.Place;
import marble.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class MainScene extends MarbleScene {
	private ArrayList<Marble> marbles;
	private ArrayList<Place> places;
	private Player player;
	
	private ImageView board;
	private Button btnQR;
	
	private IntroPane pane;
	private int step = 0;
	private int next = 1;

	public MainScene(MarbleApp app, double width, double height) {
		super(app, width, height);
		getStylesheets().add(getClass().getResource("/marble/scene/scene.css").toExternalForm());
		marbles = new ArrayList<>();
		places = new ArrayList<>();
		player = new Player();
		pane = new IntroPane();
		root.getChildren().add(pane);
		
		root.setStyle("-fx-background-color: #000000;");
		board = new ImageView(new Image(new File("res/board.png").toURI().toString()));
		root.getChildren().add(board);
		
		btnQR = new Button();
		btnQR.setLayoutX(320);
		btnQR.setLayoutY(540);
		btnQR.setPrefSize(160, 40);
		btnQR.setText("장소인증");
		btnQR.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setInitialDirectory(new File("qrcode"));
				fileChooser.setTitle("QR코드 선택");
				ExtensionFilter imgType = new ExtensionFilter("image file", "*.jpg", "*.gif", "*.png");
				fileChooser.getExtensionFilters().add(imgType);
				
				File selectedFile = fileChooser.showOpenDialog(app.getStage());
				QRCodeReader qrCodeReader = new QRCodeReader();
				try {
					BinaryBitmap bitMap = new BinaryBitmap(new HybridBinarizer(
					        new BufferedImageLuminanceSource(
					                ImageIO.read(new FileInputStream(selectedFile)))));
					Result r = qrCodeReader.decode(bitMap);
					String name = r.getText();
					if (name.equals(marbles.get(next).getPlace().getQrCode())) {
						step = next++;
						if (next > marbles.size() - 1) {
							next = 0;
						}
						player.move(marbles.get(step));
						if (marbles.get(next).getPlace().getName().equals("상품")) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("전주마블");
							alert.setHeaderText(null);
							alert.setContentText("장소 두 곳을 들리셨군요! 온누리상품권이 지급됩니다");
							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == ButtonType.OK) {
								alert.close();
							}
							step = next++;
							if (next > marbles.size() - 1) {
								next = 0;
							}
							player.move(marbles.get(step));
						}
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("전주마블");
						alert.setHeaderText(null);
						alert.setContentText(marbles.get(next).getPlace().getName() + "부터 방문하세요");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							alert.close();
						}
					}
				} catch (NotFoundException | ChecksumException | FormatException | IOException | NullPointerException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("전주마블");
					alert.setHeaderText(null);
					alert.setContentText("잘못된 QR코드 형식입니다");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						alert.close();
					}
				}
				
			}
			
		});
		root.getChildren().add(btnQR);
		
	}

	@Override
	public void initialize(int type) {
		createBoard();
		String route = "";
		if (type == 0) {
			route = "car_route.json";
		} else if (type == 1) {
			route = "walk_route.json";
		} else if (type == 2) {
			route = "free_route.json";
		}
		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream("res/" + route), "UTF-8")));
			JSONArray array = (JSONArray) object.get("places");
			for (int i = 0; i < array.size(); i++) {
				JSONObject result = (JSONObject) array.get(i);
				String name = result.get("name").toString();
				String qr = result.get("qr").toString();
				double lat = (double) result.get("lat");
				double lng = (double) result.get("lng");
				places.add(new Place(name, qr, lat, lng));
			}
			places.add(0, new Place("상품", ""));
			places.add(3, new Place("상품", ""));
			places.add(6, new Place("상품", ""));
			places.add(9, new Place("상품", ""));
			places.add(12, new Place("상품", ""));

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < marbles.size(); i++) {
			marbles.get(i).setPlace(places.get(i));
		}
	}
	
	private void createBoard() {
		Marble m = new Marble();
		m.setLayoutX(670);
		m.setLayoutY(120);
		m.setPrefSize(80, 60);
		root.getChildren().add(m);
		marbles.add(m);
		
		m = new Marble();
		m.setLayoutX(600);
		m.setLayoutY(180);
		m.setPrefSize(80, 60);
		root.getChildren().add(m);
		marbles.add(m);
		
		m = new Marble();
		m.setLayoutX(480);
		m.setLayoutY(190);
		m.setPrefSize(110, 90);
		root.getChildren().add(m);
		marbles.add(m);

		m = new Marble();
		m.setLayoutX(360);
		m.setLayoutY(190);
		m.setPrefSize(110, 90);
		root.getChildren().add(m);
		marbles.add(m);
		
		m = new Marble();
		m.setLayoutX(240);
		m.setLayoutY(190);
		m.setPrefSize(110, 90);
		root.getChildren().add(m);
		marbles.add(m);

		m = new Marble();
		m.setLayoutX(155);
		m.setLayoutY(190);
		m.setPrefSize(80, 90);
		root.getChildren().add(m);
		marbles.add(m);

		m = new Marble();
		m.setLayoutX(50);
		m.setLayoutY(260);
		m.setPrefSize(80, 90);
		root.getChildren().add(m);
		marbles.add(m);
		
		m = new Marble();
		m.setLayoutX(50);
		m.setLayoutY(350);
		m.setPrefSize(80, 90);
		root.getChildren().add(m);
		marbles.add(m);

		m = new Marble();
		m.setLayoutX(150);
		m.setLayoutY(420);
		m.setPrefSize(80, 90);
		root.getChildren().add(m);
		marbles.add(m);

		m = new Marble();
		m.setLayoutX(240);
		m.setLayoutY(420);
		m.setPrefSize(110, 90);
		root.getChildren().add(m);
		marbles.add(m);
		
		m = new Marble();
		m.setLayoutX(360);
		m.setLayoutY(420);
		m.setPrefSize(110, 90);
		root.getChildren().add(m);
		marbles.add(m);

		m = new Marble();
		m.setLayoutX(480);
		m.setLayoutY(420);
		m.setPrefSize(110, 90);
		root.getChildren().add(m);
		marbles.add(m);

		m = new Marble();
		m.setLayoutX(600);
		m.setLayoutY(420);
		m.setPrefSize(110, 90);
		root.getChildren().add(m);
		marbles.add(m);

		player.move(marbles.get(step));
		root.getChildren().add(player);
		pane.toFront();
		
		for (Marble mm : marbles) {
			mm.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
				@Override
				public void handle(MouseEvent arg0) {
					pane.show(mm.getPlace());
				}
			});
		}
	}
}
