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
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/** This class contains methods to control data flow between model and views for users interaction with the Modify Product UI.*/
public class ModifyProductController implements Initializable {

    // Creating this list to hold associated parts here
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int currentIndex = 0;
    Stage stage;
    Parent scene;

    /** This method initializes the ModifyProductController class
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Populating parts table
        modifyProductPartsTableView.setItems(Inventory.getAllParts());
        modifyProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInventoryLevelPartCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceCostPerUnitPartCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        // Populating associated parts table
        modifyProductAssociatedPartsTableView.setItems(associatedParts);
        modifyProductAssociatedPartsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductAssociatedPartsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductAssociatedPartsInventoryLevelPartCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductAssociatedPartsPriceCostPerUnitPartCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }


    @FXML private TableColumn<Product, Integer> modifyProductAssociatedPartsInventoryLevelPartCol;

    @FXML private TableColumn<Product, Integer> modifyProductAssociatedPartsPartIdCol;

    @FXML private TableColumn<Product, String> modifyProductAssociatedPartsPartNameCol;

    @FXML private TableColumn<Product, Double> modifyProductAssociatedPartsPriceCostPerUnitPartCol;

    @FXML private TableView<Part> modifyProductAssociatedPartsTableView;

    @FXML private TextField modifyProductIdTxt;

    @FXML private TextField modifyProductInvTxt;

    @FXML private TableColumn<Part, Integer> modifyProductInventoryLevelPartCol;

    @FXML private TextField modifyProductMaxTxt;

    @FXML private TextField modifyProductMinTxt;

    @FXML private TextField modifyProductNameTxt;

    @FXML private TableColumn<Part, Integer> modifyProductPartIdCol;

    @FXML private TableColumn<Part, String> modifyProductPartNameCol;

    @FXML private TableView<Part> modifyProductPartsTableView;

    @FXML private TableColumn<Part, Double> modifyProductPriceCostPerUnitPartCol;

    @FXML private TextField modifyProductPriceTxt;

    @FXML private TextField modifyProductSearchPartIdOrNameTxt;



    /** This method receives selected data from the "MainScreenController" and set them into the fields in this controller to be modified.
     * @param selectedIndex
     * @param product
     */
    public void sendDetailsProducts(int selectedIndex, Product product){

        currentIndex = selectedIndex;

        modifyProductIdTxt.setText(String.valueOf(product.getId()));
        modifyProductNameTxt.setText(product.getName());
        modifyProductInvTxt.setText(String.valueOf(product.getStock()));
        modifyProductPriceTxt.setText(String.valueOf(product.getPrice()));
        modifyProductMaxTxt.setText(String.valueOf(product.getMax()));
        modifyProductMinTxt.setText(String.valueOf(product.getMin()));

        for(Part part: product.getAllAssociatedParts()){
            associatedParts.add(part);
        }

    }


    /** This method adds an associated part to the product
     * @param event
     */
    @FXML
    void onActionAddAssociatedPartOnModifyProduct(ActionEvent event) {

        try{
            Part part = modifyProductPartsTableView.getSelectionModel().getSelectedItem();
            if(part.equals(modifyProductPartsTableView.getSelectionModel().getSelectedItem())){
                associatedParts.add(part);
                modifyProductAssociatedPartsTableView.setItems(associatedParts);
                modifyProductPartsTableView.getSelectionModel().clearSelection();
                modifyProductAssociatedPartsTableView.getSelectionModel().clearSelection();
            }
        }catch(NullPointerException e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Dialog");
            errorAlert.setContentText("Please select a part to be added");
            errorAlert.showAndWait();
            modifyProductPartsTableView.getSelectionModel().clearSelection();
        }
    }

    /** This method deletes an associated part from a product
     * @param event
     */
    @FXML
    void onActionRemoveAssociatedPartOnModifyProduct(ActionEvent event) {

        try{
            Part part = modifyProductAssociatedPartsTableView.getSelectionModel().getSelectedItem();
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to proceed?");
            Optional<ButtonType> answer = confirmationAlert.showAndWait();
            if(answer.isPresent() && answer.get() == ButtonType.OK){
                if(part.equals(modifyProductAssociatedPartsTableView.getSelectionModel().getSelectedItem())){
                    associatedParts.remove(part);
                    modifyProductAssociatedPartsTableView.setItems(associatedParts);
                    modifyProductPartsTableView.getSelectionModel().clearSelection();
                    modifyProductAssociatedPartsTableView.getSelectionModel().clearSelection();
                }
            }
        }catch(NullPointerException e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Dialog");
            errorAlert.setContentText("Please select a part to be removed");
            errorAlert.showAndWait();
        }
    }

    /** This method saves a change and loads the 'MainScreen.fxml' view
     * @param event
     */
    @FXML
    void onActionSaveChangesOnModifyProduct(ActionEvent event) throws IOException {

        try {

            int id = Integer.parseInt(modifyProductIdTxt.getText());
            String name = modifyProductNameTxt.getText();
            int inv = Integer.parseInt(modifyProductInvTxt.getText());
            double price = Double.parseDouble(modifyProductPriceTxt.getText());
            int max = Integer.parseInt(modifyProductMaxTxt.getText());
            int min = Integer.parseInt(modifyProductMinTxt.getText());

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
            if (newProduct != associatedParts){
                Inventory.updateProduct(currentIndex, newProduct);
            }

            for (Part part: associatedParts){
                newProduct.deleteAssociatedPart(part);
            }

            for (Part part: associatedParts){
                if(part != associatedParts){
                    newProduct.addAssociatedPart(part);
                }
            }

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
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

    /** This method cancels a change and loads the 'MainScreen.fxml' view
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
    void onActionModifyProductSearchPartIdTxt(ActionEvent event) {

        String searchedText = modifyProductSearchPartIdOrNameTxt.getText();
        try{
            ObservableList<Part> answer = Inventory.lookupPart(searchedText);
            modifyProductPartsTableView.setItems(answer);
            modifyProductPartsTableView.getSelectionModel().clearSelection();
            if(answer.isEmpty()){
                int searchedID = Integer.parseInt(searchedText);
                Part newAnswer = Inventory.lookupPart(searchedID);
                answer.add(newAnswer);
                modifyProductPartsTableView.getSelectionModel().select(Inventory.lookupPart(searchedID));
                modifyProductPartsTableView.setItems(Inventory.getAllParts());

                if(newAnswer == null){
                    modifyProductPartsTableView.setItems(Inventory.getAllParts());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("There are no results with this ID");
                    alert.showAndWait();
                }
            }

        }catch(NumberFormatException e){
            modifyProductPartsTableView.setItems(Inventory.getAllParts());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("There are no results with this Name");
            alert.showAndWait();

        }
    }
}
