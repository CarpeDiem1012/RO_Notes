import javafx.application.Application; //1
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert; //2
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox; //3
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.stage.Stage;

  public class HelloWorld extends Application { //4

      final static double EXCHANGE_RATE = 0.81;

      public void start(Stage stage) { //56

      Label valueLbl = new Label("Input value: $"); //7

      Label poundsLbl = new Label();

      TextField dollarsTxt = new TextField(); //89

      Button exchangeBtn = new Button(); //10

      exchangeBtn.setText("Exchange"); //11 12
      exchangeBtn.setOnAction(event-> { //13 14
         String dollarsStr = dollarsTxt.getCharacters().toString(); // 15
         try {
              double dollars = Double.parseDouble(dollarsStr);
              double pounds = exchange(dollars); //16
              poundsLbl.setText(String.format("%.2f", pounds));
             } catch (NumberFormatException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR); //19
                    a.setTitle("Error"); //20
                    a.setHeaderText("Invalid Dollar Amount");
                    a.setContentText("Please only use digits.");
                    a.showAndWait();
            }
        });

        HBox input = new HBox(); //
        input.setAlignment(Pos.CENTER);
        input.getChildren().addAll(valueLbl, dollarsTxt);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(8);
        root.getChildren().addAll(input, exchangeBtn, poundsLbl);

        Scene scene = new Scene(root, 400, 400); //
        stage.setTitle("Dollars to Pounds");
        stage.setScene(scene);
        stage.show();

    }

    private double exchange(double dollars) {
       return dollars*0.81D; //
    }
}