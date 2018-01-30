package project.salary.view.bonus;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.salary.Main;
import project.salary.model.Bonus;

/**
 * Created by Ekaterina Kiseleva on 01.04.2017.
 */
public class BonusNewOverviewController {
    private Stage dialogStage;
    private Bonus bonus;
    private boolean okClicked = false;
    Main main = new Main();

    @FXML
    private TextField idMonthField;
    @FXML
    private TextField bonusField;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        try {
            bonus.setIdMonth(idMonthField.getText());
            bonus.setBonus(Integer.parseInt(bonusField.getText()));

            if (bonus.getIdMonth().equals(""))
                throw new Exception(); //не отлавливал пустой месяц

            okClicked = true;
            dialogStage.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No data received");
            alert.setHeaderText("No bonus received");
            alert.setContentText("Please enter values");

            alert.showAndWait();
        }
    }
}
