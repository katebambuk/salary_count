package project.salary;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.salary.model.Bonus;
import project.salary.model.Disease;
import project.salary.view.disease.DiseaseNewOverviewController;
import project.salary.view.disease.DiseaseOverviewController;
import project.salary.view.salary.SalaryForAllOverviewController;
import project.salary.view.salary.SalaryOverviewController;
import project.salary.model.Connect;
import project.salary.model.Worker;
import project.salary.view.bonus.BonusNewOverviewController;
import project.salary.view.bonus.BonusOverviewController;
import project.salary.view.worker.WorkerEditOverviewController;
import project.salary.view.worker.WorkerOverviewController;

import java.io.IOException;

public class Main extends Application {
    private ObservableList<Worker> workerData = FXCollections.observableArrayList();
    private ObservableList<Disease> diseaseData = FXCollections.observableArrayList();
    private ObservableList<Bonus> bonusData = FXCollections.observableArrayList();

    private Stage primaryStage;
    private BorderPane rootLayout;

    public Main() {
        workerData = Connect.getWorkers();
        for (int i = 0; i < workerData.size(); i++) {
            diseaseData = Connect.getDiseases(workerData.get(i).getId());
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public ObservableList<Worker> getWorkerData() {
        return workerData;
    }
    public ObservableList<Disease> getDiseaseData() {
        return diseaseData;
    }
    public ObservableList<Bonus> getBonusData() {
        return bonusData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Зарплата");
        initRootLayout();
        showWorkersView();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //верхнее меню, без функционала (?)

    public void showWorkersView () {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/worker/WorkerOverview.fxml"));
            AnchorPane workerOverview = loader.load();

            rootLayout.setCenter(workerOverview);
            WorkerOverviewController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showWorkerEditDialog(Worker worker) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/worker/WorkerEditOverview.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование данных");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            WorkerEditOverviewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setWorker(worker);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showDiseaseDialog(int idWorker) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/disease/DiseaseOverview.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Больничный лист");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            DiseaseOverviewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDisease(idWorker);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showNewDiseaseDialog(Disease selectedItem) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/disease/DiseaseNewOverview.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Новый больничный лист");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            DiseaseNewOverviewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDisease(selectedItem);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showSalaryCountDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/salary/SalaryOverview.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("ID месяца");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            SalaryOverviewController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSalaryForAllDialog() {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/salary/SalaryForAllOverview.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Данные о ЗП по всему предприятию");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            SalaryForAllOverviewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRealSalary();

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showNewBonusDialog(Bonus tempBonus) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/bonus/BonusNewOverview.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Премирование");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            BonusNewOverviewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setBonus(tempBonus);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showBonusDialog(int idWorker) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/bonus/BonusOverview.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Премии");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            BonusOverviewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setBonus(idWorker);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
