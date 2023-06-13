import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;


/** This is the main class which creates an app that is the Inventory Management System. */
public class Main extends Application {


    //javadoc files are inside a folder called 'javadoc" that is located in: 'Software I Project.zip\Software I Project\javadoc"
    /** This is the main method. This is the first method that is called when running this java program.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Creating InHouse and OutSourced objects
        InHouse brakes = new InHouse(1, "Brakes", 12.99, 10, 1, 50, 100);
        InHouse tire = new InHouse(2, "Tire", 14.99, 16, 1, 50, 101);
        OutSourced rim = new OutSourced(3, "Rim", 56.99, 10, 1, 25, "Bikeology");

        //populating data into inventory allParts ObservableList
        Inventory.addPart(brakes);
        Inventory.addPart(tire);
        Inventory.addPart(rim);

        //Creating Product objects
        Product giantBicycle = new Product(1, "Giant Bicycle", 299.99, 5, 1, 5);
        Product scottBicycle = new Product(2, "Scott Bicycle", 199.99, 5, 1, 5);
        Product gtBike = new Product(3, "GT Bike", 99.99, 3, 1, 5);

        //Populating data into inventory allProducts ObservableList
        Inventory.addProduct(giantBicycle);
        Inventory.addProduct(scottBicycle);
        Inventory.addProduct(gtBike);


        /** This method is called to run the default init and then start method.
         * @param args
         */
        launch(args);


    }

    /** This is the start method, it initializes the 'MainScreen.fxml' file.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/MainScreen.fxml"));
        primaryStage.setScene(new Scene(root, 1000, 350));
        primaryStage.show();
    }
}
