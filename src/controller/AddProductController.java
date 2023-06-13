package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.getAllProducts;

/** This class contains methods to control data flow between model and views for users interaction with the Add Product UI.*/
public class AddProductController implements Initializable {

    // Creating this list to hold associated parts here
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    Stage stage;
    Parent scene;

    @FXML private TableColumn<Product, Integer> addProductAssociatedPartsInventoryLevelPartCol;

    @FXML private TableColumn<Product, Integer> addProductAssociatedPartsPartIdCol;

    @FXML private TableColumn<Product, String> addProductAssociatedPartsPartNameCol;

    @FXML private TableColumn<Product, Double> addProductAssociatedPartsPriceCostPerUnitPartCol;

    @FXML private TableView<Part> addProductAssociatedPartsTableView;

    @FXML private TextField addProductIdTxt;

    @FXML private TextField addProductInvTxt;

    @FXML private TableColumn<Part, Integer> addProductInventoryLevelCol;

    @FXML private TextField addProductMaxTxt;

    @FXML private TextField addProductMinTxt;

    @FXML private TextField addProductNameTxt;

    @FXML private TableColumn<Part, Integer> addProductPartIdCol;

    @FXML private TableColumn<Part, String> addProductPartNameCol;

    @FXML private TableView<Part> addProductPartsTableView;

    @FXML private TableColumn<Part, Double> addProductPriceCostPerUnitCol;

    @FXML private TextField addProductPriceTxt;

    @FXML private TextField addProductSearchPartIdOrNameTxt;

    /** This method initializes the AddProductController class
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Populating parts table
        addProductPartsTableView.setItems(Inventory.getAllParts());
        addProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCostPerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        // Populating associated parts table
        addProductAssociatedPartsTableView.setItems(associatedParts);
        addProductAssociatedPartsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductAssociatedPartsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductAssociatedPartsInventoryLevelPartCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductAssociatedPartsPriceCostPerUnitPartCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    /** This method saves an added product and loads the 'MainScreen.fxml' view
     * @param event
     */
    @FXML
    void onActionSaveChangesOnAddProduct(ActionEvent event) throws IOException {

        try {

            int id = autoGenerateProductID();
            String name = addProductNameTxt.getText();
            int inv = Integer.parseInt(addProductInvTxt.getText());
            double price = Double.parseDouble(addProductPriceTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());

            if (min > max){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("Maximum must be greater than Minimum.");
                errorAlert.showAndWait();
                return;
            }
            if(min > inv || inv > max){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("Inventory quantity must be within min and max range.");
                errorAlert.showAndWait();
                return;
            }

            Product newProduct = new Product(id, name, price, inv, min, max);
            for(Part part: associatedParts){
                if(part != associatedParts){
                    newProduct.addAssociatedPart(part);
                }
            }

            Inventory.getAllProducts().add(newProduct);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch(NumberFormatException e){
                Alert errorAlert = new Alert(Alert.AlertType.WARNING);
                errorAlert.setTitle("Warning Dialog");
                errorAlert.setContentText("Please check for incorrect input for all the fields");
                errorAlert.showAndWait();
        }
    }

    /** This method adds an associated part to the product
     * @param event
     * FUTURE ENHANCEMENT: There are no check in case user tries to add same associated part twice to the same product.
     * A good fix for that would be adding another catch statement
     * with an error letting user know that part is already listed as associated part to that product.
     */
    @FXML
    void onActionAddAssociatedPartOnAddProduct(ActionEvent event) {

        try{
            Part part = addProductPartsTableView.getSelectionModel().getSelectedItem();
            if(part.equals(addProductPartsTableView.getSelectionModel().getSelectedItem())) {
                associatedParts.add(part);
                addProductAssociatedPartsTableView.setItems(associatedParts);
                addProductPartsTableView.getSelectionModel().clearSelection();
                addProductAssociatedPartsTableView.getSelectionModel().clearSelection();
            }

        }catch(NullPointerException e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Dialog");
            errorAlert.setContentText("Please select a part to be added");
            errorAlert.showAndWait();
            addProductPartsTableView.getSelectionModel().clearSelection();
        }
    }

    /** This method deletes an associated part from a product
     * @param event
     */
    @FXML
    void onActionRemoveAssociatedPartOnAddProduct(ActionEvent event) {

        try{
            Part part = addProductAssociatedPartsTableView.getSelectionModel().getSelectedItem();
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to proceed?");
            Optional<ButtonType> answer = confirmationAlert.showAndWait();

            if(answer.isPresent() && answer.get() == ButtonType.OK){
                if (part.equals(addProductAssociatedPartsTableView.getSelectionModel().getSelectedItem())) {
                    associatedParts.remove(part);
                    addProductAssociatedPartsTableView.setItems(associatedParts);
                    addProductPartsTableView.getSelectionModel().clearSelection();
                    addProductAssociatedPartsTableView.getSelectionModel().clearSelection();
                }
            }

        }catch(NullPointerException e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Dialog");
            errorAlert.setContentText("Please select a part to be removed");
            errorAlert.showAndWait();
        }
    }

    /** This method cancels an action and loads the 'MainScreen.fxml' view
     * @param event
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method searches for parts in the ObservableList according to what was typed in the search bar
     * @param event
     */
    @FXML
    void onActionAddProductSearchPartIdTxt(ActionEvent event) {

        String searchedText = addProductSearchPartIdOrNameTxt.getText();
        try{
            ObservableList<Part> answer = Inventory.lookupPart(searchedText);
            addProductPartsTableView.setItems(answer);
            addProductPartsTableView.getSelectionModel().clearSelection();
            if(answer.isEmpty()){
                int searchedID = Integer.parseInt(searchedText);
                Part newAnswer = Inventory.lookupPart(searchedID);
                answer.add(newAnswer);
                addProductPartsTableView.getSelectionModel().select(Inventory.lookupPart(searchedID));
                addProductPartsTableView.setItems(Inventory.getAllParts());

                if(newAnswer == null){
                    addProductPartsTableView.setItems(Inventory.getAllParts());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("There are no results with this ID");
                    alert.showAndWait();
                }
            }

        }catch(NumberFormatException e){
            addProductPartsTableView.setItems(Inventory.getAllParts());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("There are no results with this Name");
            alert.showAndWait();

        }
    }

    /** This method generates an ID to product
     * @return generatedProductID
     */
    public int autoGenerateProductID(){
        int generatedProductID = 1;
        for (int i = 0; i < getAllProducts().size(); i++){
            generatedProductID++;
        }
        return generatedProductID;
    }
}
