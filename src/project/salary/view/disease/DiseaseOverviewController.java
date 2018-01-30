package project.salary.view.disease;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.salary.Main;
import project.salary.model.Connect;
import project.salary.model.Disease;

import java.sql.Date;

/**
 * Created by Ekaterina Kiseleva on 28.03.2017.
 */
public class DiseaseOverviewController {
    @FXML
    private TableView diseaseTable;
    @FXML
    private TableColumn<Disease, Date> beginDisease;
    @FXML
    private TableColumn<Disease, Date> endDisease;
    @FXML
    private TableColumn<Disease, Integer> month;

    private Stage dialogStage;
    private ObservableList<Disease> diseaseData;
    private int idWorker;
    private int idDisease;
    Main main = new Main();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setDisease(int idWorker) {
        this.diseaseData = Connect.getDiseases(idWorker);
        beginDisease.setCellValueFactory(new PropertyValueFactory<Disease, Date>("dateBegin"));
        endDisease.setCellValueFactory(new PropertyValueFactory<Disease, Date>("dateEnd"));
        month.setCellValueFactory(new PropertyValueFactory<Disease, Integer>("idMonth"));

        // заполняем таблицу данными
        diseaseTable.setItems(diseaseData);
        int idDisease = diseaseData.size()+1; //делаем уникальным для данного работника
        this.idWorker = idWorker;
        this.idDisease = idDisease;
    } //заполняем таблицу в приложении

    public void handleNewDisease () {
        Disease tempDisease = new Disease();
        tempDisease.setIdWorker(idWorker);
        tempDisease.setIdDisease(idDisease);

        boolean OkClicked = main.showNewDiseaseDialog(tempDisease);
        diseaseData.add(tempDisease);
        if (OkClicked) {
            Connect.addDisease(tempDisease);
            main.getDiseaseData().add(tempDisease);
        }
    }

    public void handleDeleteDisease() {
        int selectedIndex = diseaseTable.getSelectionModel().getSelectedIndex();
        try {
            Connect.deleteDisease((Disease)diseaseTable.getSelectionModel().getSelectedItem());
            diseaseTable.getItems().remove(selectedIndex);
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Disease Selected");
            alert.setContentText("Please select a disease in the table.");

            alert.showAndWait();
        }
    }
}
