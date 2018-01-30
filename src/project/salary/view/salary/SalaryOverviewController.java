package project.salary.view.salary;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.salary.Main;
import project.salary.model.*;

import java.util.ArrayList;


/**
 * Created by Ekaterina Kiseleva on 30.03.2017.
 */
public class SalaryOverviewController {
    public double realSalary;
    private Main main = new Main();

    @FXML
    private TextField idMonthField;

    private Stage dialogStage;
    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void onCountAllClick() {
        ArrayList<RealSalaryForEveryone> salaryForEveryone = new ArrayList<RealSalaryForEveryone>();
        long beginTime;
        long endTime;
        long daysDisease = 0;

        try {
            int idMonth = Integer.parseInt(idMonthField.getText());
            if (idMonth > 122000 || idMonth < 1200) throw new Exception();

            ObservableList<Worker> workers = Connect.getWorkers();
            for (int i = 0; i < workers.size(); i++) {
                RealSalaryForEveryone realSalaryForEveryone = new RealSalaryForEveryone();
                ObservableList<Disease> diseases = Connect.getDiseases(workers.get(i).getId());
                ObservableList<Bonus> bonuses = Connect.getBonuses(workers.get(i).getId());
                int bonus = 0;
                for (int j = 0; j < diseases.size(); j++) {
                    if (diseases.get(j).getIdMonth() == idMonth) {
                        beginTime = diseases.get(j).getDateBegin().getTime();
                        endTime = diseases.get(j).getDateEnd().getTime(); //если конец больничного в другом месяце, все похерится
                        if (endTime == beginTime) daysDisease++;
                        else daysDisease += (endTime - beginTime) / (24 * 60 * 60 * 1000);
                    }
                }
                for (int j = 0; j < bonuses.size(); j++) {
                    StringBuffer tempIdMonth = new StringBuffer(bonuses.get(j).getIdMonth());
                    tempIdMonth.deleteCharAt(tempIdMonth.length()-5);
                    int idMonthInBonus = Integer.parseInt(tempIdMonth.toString());

                    if (idMonthInBonus == idMonth) {
                        bonus = bonuses.get(j).getBonus();
                    }
                }
                realSalary = (int) (workers.get(i).getStableSalary() - workers.get(i).getStableSalary() * daysDisease / 60 +
                bonus) * 0.87;
                daysDisease = 0;
                realSalaryForEveryone.setIdMonth(idMonth);
                realSalaryForEveryone.setIdWorker(workers.get(i).getId());
                realSalaryForEveryone.setName(workers.get(i).getName());
                realSalaryForEveryone.setRealSalary(realSalary);
                salaryForEveryone.add(realSalaryForEveryone);
            }
            Connect.createRealSalaryTable(salaryForEveryone);
            main.showSalaryForAllDialog();

        } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Неверный ввод");
                alert.setHeaderText("Пожалуйста, заполните поля корректно");

                alert.showAndWait();
        }
    }

}
