package mhashim6.game1024.UI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameUI extends Application {

	GridPane grid = new GridPane();

	@Override
	public void start(Stage arg0) throws Exception {

		grid.setGridLinesVisible(true);
		grid.setPadding(new Insets(4, 4, 4, 4));
	}

	public static void main(String[] args) {
		launch(args);
	}

}
