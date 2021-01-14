package marble.scene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import marble.MarbleApp;
import marble.Place;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class TitleScene extends MarbleScene {
	private ImageView logo;
	private ImageView background;
	private ImageView slogan;
	private Button btnCar;
	private Button btnWalk;
	private Button btnFree;
	private Button btnHelp;
	private HelpPane helpPane;
	private ChoiceRoutePanel panel;
	
	public TitleScene(MarbleApp app, double width, double height) {
		super(app, width, height);
		getStylesheets().add(getClass().getResource("/marble/scene/scene.css").toExternalForm());
		background = new ImageView();
		background.setImage(new Image(new File("res/타이틀배경.png").toURI().toString()));
		root.getChildren().add(background);
		logo = new ImageView();
		logo.setImage(new Image(new File("res/로고.png").toURI().toString()));
		root.getChildren().add(logo);
		slogan = new ImageView();
		slogan.setImage(new Image(new File("res/슬로건.png").toURI().toString()));
		slogan.setLayoutX(650);
		slogan.setLayoutY(0);
		root.getChildren().add(slogan);
		
		btnCar = new Button();
		btnCar.setPrefSize(100, 110);
		btnCar.setLayoutX(220);
		btnCar.setLayoutY(360);
		btnCar.setText("붕붕이");
		btnCar.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent arg0) {
				app.changeScene(1, 0);
			}
			
		});
		root.getChildren().add(btnCar);
		btnWalk = new Button();
		btnWalk.setPrefSize(100, 110);
		btnWalk.setLayoutX(350);
		btnWalk.setLayoutY(360);
		btnWalk.setText("뚜벅이");
		btnWalk.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent arg0) {
				app.changeScene(1, 1);
			}
			
		});
		root.getChildren().add(btnWalk);

		btnFree = new Button();
		btnFree.setPrefSize(100, 110);
		btnFree.setLayoutX(480);
		btnFree.setLayoutY(360);
		btnFree.setText("자유여행");
		btnFree.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent arg0) {
				panel.setVisible(true);
			}
		});
		root.getChildren().add(btnFree);

		btnHelp = new Button();
		btnHelp.setPrefSize(360, 40);
		btnHelp.setLayoutX(220);
		btnHelp.setLayoutY(500);
		btnHelp.setText("도움말");
		btnHelp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				helpPane.setVisible(true);
			}
		});
		root.getChildren().add(btnHelp);

		helpPane = new HelpPane();
		helpPane.setVisible(false);
		root.getChildren().add(helpPane);
		
		panel = new ChoiceRoutePanel();
		panel.setVisible(false);
		root.getChildren().add(panel);
		
	}

	@Override
	public void initialize(int type) {
		
		
	}
	
	private class ChoiceRoutePanel extends SubScene {
		private String place[]  = {""};
		protected BorderPane root;
		private ListView<Place> listView;
		private ArrayList<Place> places;
		
		public ChoiceRoutePanel() {
			super(new BorderPane(), 500, 300);
			root = (BorderPane) getRoot();
			root.setPadding(new Insets(10, 10, 10, 10));
			root.setStyle("-fx-background-color: white; -fx-border-radius: 20;");
			setLayoutX(400 - 250);
			setLayoutY(300 - 150);

			BorderPane titlePane = new BorderPane();
			titlePane.setPadding(new Insets(5, 5, 5, 5));
			Text title = new Text();
			title.setText("이동 경로 정하기");
			titlePane.setCenter(title);
			root.setTop(titlePane);

			listView = new ListView<>();
			places = new ArrayList<>();
			JSONParser parser = new JSONParser();
			try {
				JSONObject object = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream("res/car_route.json"), "UTF-8")));
				JSONArray array = (JSONArray) object.get("places");
				for (int i = 0; i < array.size(); i++) {
					JSONObject result = (JSONObject) array.get(i);
					String name = result.get("name").toString();
					String qr = result.get("qr").toString();
					double lat = (double) result.get("lat");
					double lng = (double) result.get("lng");
					places.add(new Place(name, qr, lat, lng));
				}

			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
			listView.setItems(FXCollections.observableList(places));
			listView.setCellFactory(param -> new PlaceCell());
			root.setCenter(listView);

			FlowPane btnPane = new FlowPane();
			btnPane.setPadding(new Insets(5, 5, 5, 5));
			btnPane.setAlignment(Pos.CENTER);
			btnPane.setHgap(15);

			Button btnStart = new Button();
			btnStart.setText("시작");
			btnStart.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					createJson();
					app.changeScene(1, 2);
				}
			});
			btnPane.getChildren().add(btnStart);

			Button btnClose = new Button();
			btnClose.setText("닫기");
			btnClose.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					setVisible(false);
				}
			});
			btnPane.getChildren().add(btnClose);

			root.setBottom(btnPane);
		}

		private void createJson() {
			JSONObject freeRoute = new JSONObject();
			JSONArray placeList = new JSONArray();
			for (Place p : places) {
				JSONObject placeInfo = new JSONObject();
				placeInfo.put("name", p.getName());
				placeInfo.put("qr", p.getQrCode());
				placeInfo.put("lat", p.getLat());
				placeInfo.put("lng", p.getLng());
				placeList.add(placeInfo);
			}
			freeRoute.put("places", placeList);
			try {
				BufferedWriter file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("res/free_route.json"), "UTF-8"));
				file.write((freeRoute.toJSONString()));
				file.flush();
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		private class PlaceCell extends ListCell<Place> {
			public PlaceCell() {
				setAlignment(Pos.CENTER);

				setOnDragDetected(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						if (getItem() == null) {
							return;
						}

						ObservableList<Place> items = getListView().getItems();
						Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
						ClipboardContent content = new ClipboardContent();
						content.putString(getItem().getName());
						dragboard.setContent(content);
						mouseEvent.consume();
					}
				});

				setOnDragOver(new EventHandler<DragEvent>() {
					@Override
					public void handle(DragEvent dragEvent) {
						if (dragEvent.getGestureSource() != this && dragEvent.getDragboard().hasString()) {
							dragEvent.acceptTransferModes(TransferMode.MOVE);
						}
						dragEvent.consume();
					}
				});

				setOnDragEntered(new EventHandler<DragEvent>() {
					@Override
					public void handle(DragEvent dragEvent) {
						if (dragEvent.getGestureSource() != this && dragEvent.getDragboard().hasString()) {
							setOpacity(0.3);
						}
					}
				});

				setOnDragExited(new EventHandler<DragEvent>() {
					@Override
					public void handle(DragEvent dragEvent) {
						if (dragEvent.getGestureSource() != this && dragEvent.getDragboard().hasString()) {
							setOpacity(1);
						}
					}
				});

				setOnDragDropped(new EventHandler<DragEvent>() {
					@Override
					public void handle(DragEvent dragEvent) {
						if (getItem() == null) {
							return;
						}

						Dragboard dragboard = dragEvent.getDragboard();
						boolean success = false;

						if (dragboard.hasString()) {
							ObservableList<Place> items = getListView().getItems();
							int draggedIndex = items.indexOf(new Place(dragboard.getString(), ""));
							int thisIndex = items.indexOf(getItem());

							Place temp = places.get(draggedIndex);
							places.set(draggedIndex, places.get(thisIndex));
							places.set(thisIndex, temp);

							items.set(draggedIndex, getItem());
							items.set(thisIndex, places.get(thisIndex));

							success = true;
						}
						dragEvent.setDropCompleted(success);
						dragEvent.consume();
					}
				});

				setOnDragDone(DragEvent::consume);
			}

			@Override
			protected void updateItem(Place place, boolean b) {
				super.updateItem(place, b);

				if (b || place == null) {
					setText("");
				} else {
					setText(place.getName());
				}
			}
		}
	}

}
