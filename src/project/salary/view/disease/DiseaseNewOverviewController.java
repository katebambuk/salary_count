package project.salary.view.disease;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import project.salary.Main;
import project.salary.model.Disease;

import java.sql.Date;

/**
 * Created by Ekaterina Kiseleva on 29.03.2017.
 */
public class DiseaseNewOverviewController {

    private Stage dialogStage;
    private Disease disease;
    private boolean okClicked = false;
    Main main = new Main();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private DatePicker dateBegin;
    @FXML
    private DatePicker dateEnd;

    @FXML
    private void handleOk() {
        try {
            if (dateBegin.getValue().getMonthValue() == dateEnd.getValue().getMonthValue()
                    && dateBegin.getValue().getDayOfMonth() <= dateEnd.getValue().getDayOfMonth()) {

                    disease.setDateBegin(Date.valueOf(dateBegin.getValue()));
                    disease.setDateEnd(Date.valueOf(dateEnd.getValue()));
                    String tempMonthId = dateBegin.getValue().getMonthValue() + "" + dateBegin.getValue().getYear();
                    disease.setIdMonth(Integer.parseInt(tempMonthId));
                    okClicked = true;
                    dialogStage.close();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(main.getPrimaryStage());
                alert.setTitle("Введенные данные некорректны");
                alert.setHeaderText("Измените введенные данные");
                alert.setContentText("Больничный лист рассчитывается не более," +
                        " чем для одного месяца. Выберите дату выздоровления, соотвествующую " +
                        "последнему дню месяца");
                alert.showAndWait();
                disease.setDateEnd(null);
                disease.setDateBegin(null);
                dateBegin.setValue(null);
                dateEnd.setValue(null);
            }
        } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(main.getPrimaryStage());
                alert.setTitle("No dates received");
                alert.setHeaderText("No dates received");
                alert.setContentText("Please enter dates.");

                alert.showAndWait();
        }
    }
}
