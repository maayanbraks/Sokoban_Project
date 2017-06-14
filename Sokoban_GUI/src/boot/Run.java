package boot;

import java.util.List;

import controller.SokobanController;
import controller.server.MyServer;

import javafx.application.Application;
import javafx.stage.Stage;
import model.MyModel;
import view.GUIController;
import view.GameOverController;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Run extends Application {

	@Override
	public void start(Stage primaryStage) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));

			BorderPane root = (BorderPane) loader.load();

			GUIController view = loader.getController();
			MyModel model = new MyModel();
			MyServer server = null;
			SokobanController controller = new SokobanController(model, view);

			model.addObserver(controller);
			view.addObserver(controller);

			List<String> args = getParameters().getRaw();

			String arg="";
			String str="";

			if(args.size()==2){
				arg = args.get(0);
				str = args.get(1);
			}

			int port = 0;
			for (int i = 0; i < str.length(); i++) {
				port = (port * 10) + (str.charAt(i) - '0');
			}

			if (arg.compareTo("-server") == 0) {
				server = new MyServer(port);
				server.getClient().addObserver(controller);
				server.start();
			} else {
				Scene scene = new Scene(root, 700, 600);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);

				// GameOver SCREEN
				FXMLLoader finishLoader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
				BorderPane rootFinish = (BorderPane) finishLoader.load();
				Stage secondStage = new Stage();
				secondStage.setScene(new Scene(rootFinish));
				GameOverController goc = finishLoader.getController();
				////

				view.start();
				primaryStage.show();
				view.setStage(primaryStage);
				view.setSecondStage(secondStage);
				view.setGameOverController(goc);

			}
			controller.setServer(server);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
