package ui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.GameLogic;

public class Storage extends GridPane {
	
	private ObservableList<StorageCell> cell = FXCollections.observableArrayList();
	
	
	public Storage() {
			
		this.setPrefWidth(150);
		this.setAlignment(Pos.BOTTOM_CENTER);
		this.setVgap(12);
		this.setHgap(20);
		this.setPadding(new Insets(12));
		this.setBackground(new Background(new BackgroundFill(Color.SADDLEBROWN, null, null)));
		
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 10; y++) {
				StorageCell cells = new StorageCell();
				cell.add(cells);
				this.add(cells, x, y);
			}
		}
		
		for (StorageCell storageCell : cell) {
	 		storageCell.setOnAction(new EventHandler<ActionEvent>() {
	 			public void handle(ActionEvent event) {
	 				if (storageCell != null) { 
	 					sellPopUp();
	 					//System.out.println("ok");
	 					
	 				}
	 				
	
	 			}
	 		});
	 	}
		
	}
	
	private void sellPopUp() {
		Stage sellShow = new Stage();
        sellShow.initModality(Modality.APPLICATION_MODAL);
        //sellShow.initOwner(primaryStage);
        VBox sellVbox = new VBox(100);
        sellVbox.setAlignment(Pos.CENTER);
        sellVbox.setSpacing(3.5);
        Text sellText = new Text("Sell this item?");
        sellText.setFont(Font.font("Comic Sans Ms", FontWeight.BOLD, FontPosture.REGULAR, 20 ));
        Button yesBut = new Button("YES");
        yesBut.setFont(new Font(10));
        yesBut.setPrefWidth(40);
        yesBut.setPrefHeight(30);
        Button noBut = new Button("NO");
        noBut.setFont(new Font(10));
        noBut.setPrefWidth(40);
        noBut.setPrefHeight(30);
        VBox fishInfo = new VBox(20);
        fishInfo.setAlignment(Pos.CENTER);		
        Image fishImage = GameLogic.getInstance().bass_Right;
        BackgroundImage bgImg = new BackgroundImage(fishImage, 
        	    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        	    BackgroundPosition.DEFAULT, 
        	    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        fishInfo.getChildren().add(bgImg);
        sellVbox.getChildren().addAll(sellText, fishInfo, yesBut, noBut);
        Scene sellScene = new Scene(sellVbox, 200, 200);
        sellShow.setScene(sellScene);
        sellShow.show();
	}
	
	
	
	
}




