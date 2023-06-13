package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/** This class contains methods to control data flow between model and views for users interaction with the Modify Part UI.*/
public class ModifyPartController implements Initializable {

    /** This method initializes the ModifyPartController class
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private int currentIndex = 0;
    Stage stage;
    Parent scene;


    @FXML private TextField modifyPartIdTxt;

    @FXML private RadioButton modifyPartInHouseRBtn;

    @FXML private TextField modifyPartInvTxt;

    @FXML private TextField modifyPartMachineIdOrCompanyNameTxt;

    @FXML private TextField modifyPartMaxTxt;

    @FXML private TextField modifyPartMinTxt;

    @FXML private TextField modifyPartNameTxt;

    @FXML private RadioButton modifyPartOutsourcedRBtn;

    @FXML private TextField modifyPartPriceTxt;

    @FXML private Label modifyPartMachineIdCompanyNameFld;

    @FXML private ToggleGroup tGroup;


    /** This method changes label text from 'Company Name' to 'Machine ID'
     * @param event
     */
    public void onActionModifyPartInHouse(ActionEvent event) {

        modifyPartMachineIdCompanyNameFld.setText("Machine ID");
    }

    /** This method changes label text from 'Machine ID' to 'Company Name'
     * @param event
     */
    public void onActionModifyPartOutsourced(ActionEvent event) {

        modifyPartMachineIdCompanyNameFld.setText("Company Name");
    }

    /** This method receives selected data from the "MainScreenController" and set them into the fields in this controller to be modified.
     * @param selectedIndex
     * @param part
     */
    public void sendDetails(int selectedIndex, Part part){

        currentIndex = selectedIndex;

        modifyPartIdTxt.setText(String.valueOf(part.getId()));
        modifyPartNameTxt.setText(part.getName());
        modifyPartInvTxt.setText(String.valueOf(part.getStock()));
        modifyPartPriceTxt.setText(String.valueOf(part.getPrice()));
        modifyPartMaxTxt.setText(String.valueOf(part.getMax()));
        modifyPartMinTxt.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse){
            modifyPartInHouseRBtn.setSelected(true);
            modifyPartMachineIdOrCompanyNameTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
            modifyPartMachineIdCompanyNameFld.setText("Machine ID");
        }
        else{
            modifyPartOutsourcedRBtn.setSelected(true);
            modifyPartMachineIdOrCompanyNameTxt.setText(((OutSourced) part).getCompanyName());
            modifyPartMachineIdCompanyNameFld.setText("Company Name");
        }

    }
    /** This method saves a change and loads the 'MainScreen.fxml' view
     * @param event
     */
    @FXML
    void onActionSaveModifyPart(ActionEvent event) throws IOException {

        try {

            int id = Integer.parseInt(modifyPartIdTxt.getText());
            String name = modifyPartNameTxt.getText();
            int inv = Integer.parseInt(modifyPartInvTxt.getText());
            double price = Double.parseDouble(modifyPartPriceTxt.getText());
            int max = Integer.parseInt(modifyPartMaxTxt.getText());
            int min = Integer.parseInt(modifyPartMinTxt.getText());

            if (min > max){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("Maximum must be greater than Minimum.");
                errorAlert.showAndWait();
                return;
            }
            else if(min > inv || inv > max){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("Inventory quantity must be within min and max range.");
                errorAlert.showAndWait();
                return;
            }

            if(modifyPartInHouseRBtn.isSelected()) {

                int machineId = Integer.parseInt(modifyPartMachineIdOrCompanyNameTxt.getText());
                InHouse updatedInPart = new InHouse(id, name, price, inv, min, max, machineId);
                Inventory.updatePart(currentIndex, updatedInPart);
            }

            if(modifyPartOutsourcedRBtn.isSelected()){

                String companyName = modifyPartMachineIdOrCompanyNameTxt.getText();
                OutSourced updateOutPart = new OutSourced(id, name, price, inv, min, max, companyName);
                Inventory.updatePart(currentIndex, updateOutPart);
            }

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

}
