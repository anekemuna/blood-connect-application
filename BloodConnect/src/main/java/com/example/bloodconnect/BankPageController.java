package com.example.bloodconnect;

import com.example.bloodconnect.dao.BloodBankDAO;
import com.example.bloodconnect.model.BloodBank;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;


public class BankPageController {

    @FXML
    TableColumn<BloodBank, String> bankIdColumn;
    @FXML
    TableColumn<BloodBank, String> nameColumn;
    @FXML
    TableColumn<BloodBank, String> addressColumn;
    @FXML
    TableColumn<BloodBank, String> contactNoColumn;
    @FXML
    private TableView<BloodBank> bankTable;
    private BloodBankDAO bankDAO;
    private Stage stage;
    private Scene scene;

    @FXML
    private void initialize() {
        // Initialize the table columns
        bankIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getBloodBankId())));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        contactNoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContactNo()));
    }

    public void setBankDAO(BloodBankDAO bankDAO) {
        this.bankDAO = bankDAO;
        viewBanks();
    }

    private void viewBanks() {
        if (bankDAO != null) {
            bankTable.getItems().setAll(bankDAO.getAllBank());
        }
    }

    @FXML
    private void backToDashboard(ActionEvent event) throws IOException {
        // Return to the dashboard
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("DashboardView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Blood Connect - Dashboard");
        stage.setScene(scene);
        stage.show();
    }

}
