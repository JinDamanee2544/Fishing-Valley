package main;
import input.InputUtility;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.GameLogic;
import logic.ObjectManager;
import ui.GameScreen;

public class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Fish Game");
		stage.setResizable(false);
		
		ObjectManager objectManeger = new ObjectManager();
		GameScreen gameScreen = new GameScreen(800, 600);
		
		StackPane popUp = new StackPane();
		Text t = new Text("Hello World");
		t.setFont(new Font(50));
		popUp.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
		popUp.setMaxSize(300, 300);
		popUp.setAlignment(Pos.CENTER);
		popUp.getChildren().add(t);
		popUp.setVisible(false);
		
		
		root.getChildren().addAll(gameScreen,popUp);
		gameScreen.requestFocus();
		
		stage.show();
		
		AnimationTimer animation = new AnimationTimer() {
			public void handle(long now) {
				gameScreen.paintComponent();
				objectManeger.update();
				GameLogic.getInstance().update();
				InputUtility.updateInputState();
			}
		};
		//animation.start();
	}
}
