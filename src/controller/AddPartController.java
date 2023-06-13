package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static model.Inventory.getAllParts;


/** This class contains methods to control data flow between model and views for users interaction with the Add Part UI.*/
public class AddPartController implements Initializable {

    /** This method initializes the AddPartController class
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    Stage stage;
    Parent scene;

    @FXML private TextField addPartIdTxt;

    @FXML private RadioButton addPartInHouseRBtn;

    @FXML private TextField addPartInvTxt;

    @FXML private TextField addPartMachineIdOrCompanyNameTxt;

    @FXML private TextField addPartMaxTxt;

    @FXML private TextField addPartMinTxt;

    @FXML private TextField addPartNameTxt;

    @FXML private RadioButton addPartOutsourcedRBtn;

    @FXML private TextField addPartPriceTxt;

    @FXML private Label addPartMachineIdCompanyNameFld;

    @FXML private ToggleGroup tGroup;


    /** This method changes label text from 'Company Name' to 'Machine ID'
     * @param event
     */
    public void onActionAddPartInHouse(ActionEvent event) {

        addPartMachineIdCompanyNameFld.setText("Machine ID");
    }

    /** This method changes label text from 'Machine ID' to 'Company Name'
     * @param event
     */
    public void onActionAddPartOutsourced(ActionEvent event) {

        addPartMachineIdCompanyNameFld.setText("Company Name");
    }

    /** This method saves an added part and loads the 'MainScreen.fxml' view
     * @param event
     */
    @FXML void onActionSaveAddPart(ActionEvent event) throws IOException {

        try{
            int id = autoGeneratePartID();
            String name = addPartNameTxt.getText();
            int inv = Integer.parseInt(addPartInvTxt.getText());
            double price = Double.parseDouble(addPartPriceTxt.getText());
            int max = Integer.parseInt(addPartMaxTxt.getText());
            int min = Integer.parseInt(addPartMinTxt.getText());

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
            if (addPartInHouseRBtn.isSelected()) {

                int machineId = Integer.parseInt(addPartMachineIdOrCompanyNameTxt.getText());
                InHouse newPart = new InHouse(id, name, price, inv, min, max, machineId);
                Inventory.addPart(newPart);

            } else if(addPartOutsourcedRBtn.isSelected()){

                String companyName = addPartMachineIdOrCompanyNameTxt.getText();
                OutSourced newPart = new OutSourced(id, name, price, inv, min, max, companyName);
                Inventory.addPart(newPart);
            }

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NumberFormatException e){
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Warning Dialog");
            warningAlert.setContentText("Please check for incorrect input for all the fields");
            warningAlert.showAndWait();
        }
    }


    /** This method cancels an action and loads the 'MainScreen.fxml' view
     * @param event
     */
    @FXML void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method generates an ID to part
     * @return generatedPartID
     */
    public int autoGeneratePartID(){
        int generatedPartID = 1;
        for (int i = 0; i < getAllParts().size(); i++){
            generatedPartID++;
        }
        return generatedPartID;
    }


}
