package project.salary.view.bonus;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.salary.Main;
import project.salary.model.Bonus;
import project.salary.model.Connect;

/**
 * Created by Ekaterina Kiseleva on 01.04.2017.
 */
public class BonusOverviewController {
    private Stage dialogStage;
    private ObservableList<Bonus> bonusData;
    private int idWorker;
    Main main = new Main();

    @FXML
    private TableView bonusTable;
    @FXML
    private TableColumn<Bonus, String> idMonthColumn;
    @FXML
    private TableColumn<Bonus, Integer> bonusColumn;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setBonus(int idWorker) {
        this.bonusData = Connect.getBonuses(idWorker);
        idMonthColumn.setCellValueFactory(new PropertyValueFactory<>("idMonth"));
        bonusColumn.setCellValueFactory(new PropertyValueFactory<>("bonus"));

        bonusTable.setItems(bonusData);
        this.idWorker = idWorker;
    }

    public void handleNewBonus () {
        Bonus tempBonus = new Bonus();
        tempBonus.setIdWorker(idWorker);

        boolean OkClicked = main.showNewBonusDialog(tempBonus);
        bonusData.add(tempBonus);
        if (OkClicked) {
            Connect.addBonus(tempBonus);
            main.getBonusData().add(tempBonus);
        }
    }

    public void handleDeleteBonus() {
        int selectedIndex = bonusTable.getSelectionModel().getSelectedIndex();
        try {
            Connect.deleteBonus((Bonus)bonusTable.getSelectionModel().getSelectedItem());
            bonusTable.getItems().remove(selectedIndex);
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No bonus Selected");
            alert.setContentText("Please select a bonus in the table.");

            alert.showAndWait();
        }
    }
}
