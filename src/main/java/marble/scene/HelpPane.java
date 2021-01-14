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

public class HelpPane extends SubScene {
    protected BorderPane root;

    public HelpPane() {
        super(new BorderPane(), 500, 500);
        root = (BorderPane) getRoot();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setStyle("-fx-background-color: white; -fx-border-radius: 20;");

        BorderPane titlePane = new BorderPane();
        titlePane.setPadding(new Insets(10, 10, 10, 10));
        Text title = new Text("도움말");
        title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 48));
        titlePane.setCenter(title);
        root.setTop(titlePane);

        BorderPane contentPane = new BorderPane();
        Text message = new Text();
        message.setText("1. 자차가 있을 경우 붕붕이를 자차가 없을 경우 뚜벅이를\n 자유롭게 여행하고 싶은 경우 자유여행을 선택합니다.\n\n"
                        + "2. 자유여행을 선택한 경우 장소를 드래그하여 순서를 변경시킬 수 있습니다.\n\n"
                        + "3. 보드판에 적혀있는 장소 순서대로 방문하여 QR코드로 장소인증을 합니다.\n\n");
        contentPane.setCenter(message);
        root.setCenter(contentPane);

        BorderPane btnPane = new BorderPane();
        Button btnOk = new Button();
        btnOk.setPrefSize(100, 30);
        btnOk.setText("확인");
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
}
