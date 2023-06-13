package controller;

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


/** This class contains methods to control data flow between model and views for users interaction with the Main Screen UI.*/
public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML private TableColumn<Part, Integer> inventoryLevelCol;

    @FXML private TableColumn<Product, Integer> inventoryLevelProductCol;

    @FXML private TextField mainSearchPartIdOrNameTxt;

    @FXML private TextField mainSearchProductIdOrNameTxt;

    @FXML private TableColumn<Part, Integer> partIdCol;

    @FXML private TableColumn<Part, String> partNameCol;

    @FXML private TableView<Part> partsTableView;

    @FXML private TableColumn<Part, Double> priceCostPerUnitPartCol;

    @FXML private TableColumn<Product, Double> priceCostPerUnitProductCol;

    @FXML private TableColumn<Product, Integer> productIdCol;

    @FXML private TableColumn<Product, String> productNameCol;

    @FXML private TableView<Product> productsTableView;

    /** This method initializes the MainScreenController class and populates the Parts and Products tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Populating parts table
        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCostPerUnitPartCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        // Populating products table
        productsTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelProductCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCostPerUnitProductCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    /** This method loads the 'AddPart.fxml' view
     * @param event
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method loads the 'AddProduct.fxml' view
     * @param event
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method deletes a part selected by the user
     * @param event
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {

        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (partsTableView.getSelectionModel().isEmpty()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Dialog");
            errorAlert.setContentText("Please select a part to be deleted");
            errorAlert.showAndWait();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to proceed?");
        Optional<ButtonType> answer = confirmationAlert.showAndWait();

        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
            partsTableView.getItems().remove(selectedPart);
            //partsTableView.setItems(Inventory.getAllParts());
        }
    }

    /** This method deletes a product selected by the user
     * @param event
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) throws IOException {

        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if (productsTableView.getSelectionModel().isEmpty()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Dialog");
            errorAlert.setContentText("Please select a product to be deleted");
            errorAlert.showAndWait();
            productsTableView.getSelectionModel().clearSelection();
            return;
        } else{
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to proceed?");
            Optional<ButtonType> answer = confirmationAlert.showAndWait();
            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                if (!selectedProduct.getAllAssociatedParts().isEmpty()){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error Dialog");
                    errorAlert.setContentText("There is/are part(s) related to this product.");
                    errorAlert.showAndWait();
                    productsTableView.getSelectionModel().clearSelection();
                    return;

                } else {
                    Inventory.deleteProduct(selectedProduct);
                    //productsTableView.setItems(Inventory.getAllProducts());
                    productsTableView.getItems().remove(selectedProduct);
                }
            }
        }
    }

    /** This method loads the 'ModifyPart.fxml' view
     * @param event
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();

            ModifyPartController ADMController = loader.getController();
            ADMController.sendDetails(partsTableView.getSelectionModel().getSelectedIndex(), partsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }catch(NullPointerException  e){

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Dialog");
            errorAlert.setContentText("Please select a part to be edited");
            errorAlert.showAndWait();
        }
    }

    /** This method loads the 'ModifyProduct.fxml' view
     * @param event
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController ADMController = loader.getController();
            ADMController.sendDetailsProducts(productsTableView.getSelectionModel().getSelectedIndex(), productsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }catch(NullPointerException  e){

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Dialog");
            errorAlert.setContentText("Please select a product to be edited");
            errorAlert.showAndWait();
        }

        /*stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();*/

    }

    /** This method searches for parts in the ObservableList according to what was typed in the search bar
     * @param event
     * RUNTIME ERROR: Caused by: java.lang.NullPointerException: Cannot invoke "javafx.collections.ObservableList.isEmpty()" because "answer" is null at controller.MainScreenController.onActionMainSearchPartIdTxt(MainScreenController.java:128)
     * This was happening because I was returning null in the method lookupPart(partName:String)in the Inventory class
     * To fix that I changed 'return null' to 'return answer'
     */
    @FXML
    void onActionMainSearchPartIdTxt(ActionEvent event) {

        String searchedText = mainSearchPartIdOrNameTxt.getText();
        try{
            ObservableList<Part> answer = Inventory.lookupPart(searchedText);
            partsTableView.setItems(answer);
            partsTableView.getSelectionModel().clearSelection();
            if(answer.isEmpty()){
                int searchedID = Integer.parseInt(searchedText);
                Part newAnswer = Inventory.lookupPart(searchedID);
                answer.add(newAnswer);
                partsTableView.getSelectionModel().select(Inventory.lookupPart(searchedID));
                partsTableView.setItems(Inventory.getAllParts());

                if(newAnswer == null){
                    partsTableView.setItems(Inventory.getAllParts());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("There are no results with this ID");
                    alert.showAndWait();
                }
            }

        }catch(NumberFormatException e){
            partsTableView.setItems(Inventory.getAllParts());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("There are no results with this Name");
            alert.showAndWait();

        }

    }

    /** This method searches for products in the ObservableList according to what was typed in the search bar
     * @param event
     */
    @FXML
    void onActionMainSearchProductIdTxt(ActionEvent event) {

        String searchedText = mainSearchProductIdOrNameTxt.getText();
        try{
            ObservableList<Product> answer = Inventory.lookupProduct(searchedText);
            productsTableView.setItems(answer);
            productsTableView.getSelectionModel().clearSelection();
            if(answer.isEmpty()){
                int searchedID = Integer.parseInt(searchedText);
                Product newAnswer = Inventory.lookupProduct(searchedID);
                answer.add(newAnswer);
                productsTableView.getSelectionModel().select(Inventory.lookupProduct(searchedID));
                productsTableView.setItems(Inventory.getAllProducts());

                if(newAnswer == null){
                    productsTableView.setItems(Inventory.getAllProducts());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("There are no results with this ID");
                    alert.showAndWait();
                }
            }

        }catch(NumberFormatException e){
            productsTableView.setItems(Inventory.getAllProducts());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("There are no results with this Name");
            alert.showAndWait();

        }

    }

    /** This method exits the application
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        Optional<ButtonType> answer = confirmationAlert.showAndWait();

        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
