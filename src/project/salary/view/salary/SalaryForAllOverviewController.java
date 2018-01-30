package project.salary.view.salary;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.salary.model.Connect;
import project.salary.model.RealSalaryForEveryone;

/**
 * Created by Ekaterina Kiseleva on 30.03.2017.
 */
public class SalaryForAllOverviewController {
    private ObservableList<RealSalaryForEveryone> realSalaryData;
    private Stage dialogStage;

    @FXML
    private TableView<RealSalaryForEveryone> salaryTable;
    @FXML
    private TableColumn<RealSalaryForEveryone, Integer> idColumn;
    @FXML
    private TableColumn<RealSalaryForEveryone, Double> salaryColumn;
    @FXML
    private TableColumn<RealSalaryForEveryone, String> nameColumn;
    @FXML
    private Label setMonth;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setRealSalary() {
        this.realSalaryData = Connect.getRealSalary();
        idColumn.setCellValueFactory(new PropertyValueFactory<RealSalaryForEveryone, Integer>("idWorker"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<RealSalaryForEveryone, Double>("realSalary"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<RealSalaryForEveryone, String>("name"));

        String data = String.valueOf(realSalaryData.get(0).getIdMonth());
        if (data.length() == 5) {
            setMonth.setText("0" + data.substring(0,1) + "." + data.substring(1));
        }
        if (data.length() == 6) {
            setMonth.setText(data.substring(0,2) + "." + data.substring(2));
        }
        // заполняем таблицу данными
        salaryTable.setItems(realSalaryData);
    }
}
