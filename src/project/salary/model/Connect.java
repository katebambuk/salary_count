package project.salary.model;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by Ekaterina Kiseleva on 25.03.2017.
 */
public class Connect {
    private static final String URL = "jdbc:mysql://localhost:3306/salary";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static String resultTableName = "";

    Connect() {
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException ex) {
            System.out.println("Не удалось создать драйвер");
        }
    }

    public static ObservableList<Worker> getWorkers() { //вытаскиваем все данные из БД
        ObservableList<Worker> workerData = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from workers");
            while (resultSet.next()) {
                Worker worker = new Worker();
                worker.setId(resultSet.getInt("id"));
                worker.setName(resultSet.getString("name"));
                worker.setPosition(resultSet.getString("position"));
                worker.setStableSalary(resultSet.getInt("stableSalary"));
                worker.setFamilyStatus(resultSet.getString("familyStatus"));
                worker.setMountOfChildren(resultSet.getInt("kids"));

                workerData.add(worker);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
        return workerData;
    }

    public static int addWorker (Worker worker) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            String workerName = worker.getName();
            String position = worker.getPosition();
            String familyStatus = worker.getFamilyStatus();
            //int id = worker.getId(); //автоинкремент в БД
            int stableSalary = worker.getStableSalary();
            int kids = worker.getMountOfChildren();

            statement.execute(String.format("insert into workers (name, position, stableSalary, familyStatus, kids) " +
                            "values ('%s', '%s', '%d', '%s', '%d')", workerName, position, stableSalary,
                    familyStatus, kids));
            String query = "select * from workers where name='" + workerName + "'";
            ResultSet res = statement.executeQuery(query);
            int key = 0;
            while (res.next()) {
                key = res.getInt("id");
            }
            return key;
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
            return 0;
        }
    }

    public static void editWorker(int key, Worker worker) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            String name = worker.getName();
            String position = worker.getPosition();
            String familyStatus = worker.getFamilyStatus();
            int stableSalary = worker.getStableSalary();
            int kids = worker.getMountOfChildren();

            statement.execute(String.format("UPDATE workers SET `name`='%s', `position`='%s', " +
                            "`familyStatus`='%s', `stableSalary`='%d', `kids`='%d' " +
                            "WHERE `id`='%d'", name, position, familyStatus, stableSalary, kids, key));
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
    }

    public static void deleteWorker(Worker selectedItem) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            int key = selectedItem.getId();

            statement.execute(String.format("DELETE FROM workers WHERE `id`='%d'", key));
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
    }

    public static ObservableList<Disease>  getDiseases(int idWorker) {
        ObservableList<Disease> diseaseData = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("select * from disease where idWorker=" + idWorker);
            while (resultSet.next()) {
                Disease disease = new Disease();
                disease.setIdWorker(resultSet.getInt("idWorker"));
                disease.setIdMonth(resultSet.getInt("idMonth"));
                disease.setIdDisease(resultSet.getInt("idDisease"));
                disease.setDateBegin(resultSet.getDate("dateBegin"));
                disease.setDateEnd(resultSet.getDate("dateEnd"));
                //disease.setIdDisease(idDisease); //рассчитывается на основе заполненых строк
                diseaseData.add(disease);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
        return diseaseData;
    }

    public static void addDisease(Disease disease) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            int idDisease = disease.getIdDisease();
            int idMonth = disease.getIdMonth();
            int idWorker = disease.getIdWorker();
            Date dateBegin = disease.getDateBegin();
            Date dateEnd = disease.getDateEnd();

            statement.execute(String.format("insert into disease (idWorker, idMonth, idDisease, dateBegin, dateEnd) " +
                            "values ('%d', '%d', '%d', '%s', '%s')", idWorker, idMonth, idDisease,
                    dateBegin, dateEnd));
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
    }

    public static void deleteDisease(Disease selectedItem) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            int key1 = selectedItem.getIdWorker();
            int key2 = selectedItem.getIdMonth();
            int key3 = selectedItem.getIdDisease();

            statement.execute(String.format("DELETE FROM disease WHERE `idWorker`='%d' and `idMonth`='%d' and `idDisease`='%d'",
                    key1, key2, key3));
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
    }

    public static void deleteDisease(int idWorker) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.execute(String.format("DELETE FROM disease WHERE `idWorker`='%d'",
                    idWorker));
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
    } //вызывается в том случае, когда удаляем работника

    public static void createRealSalaryTable(ArrayList<RealSalaryForEveryone> salaryForEveryone) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            int idMonth = salaryForEveryone.get(0).getIdMonth();
            //для введенного месяца создается таблица, название таблицы - id месяца
            resultTableName = String.valueOf(idMonth);
            statement.execute(String.format("CREATE TABLE `salary`.`%d` (`idWorker` INT NOT NULL,"+
            "`realSalary` INT NULL, `name` VARCHAR(150) NULL, PRIMARY KEY (`idWorker`))", idMonth));

            for (int i = 0; i < salaryForEveryone.size(); i++) {
                statement.execute(String.format("INSERT INTO `salary`.`%d` (`idWorker`, `realSalary`, `name`) VALUES ('%d', '%d', '%s')",
                        idMonth, salaryForEveryone.get(i).getIdWorker(),
                        (int)(salaryForEveryone.get(i).getRealSalary()), salaryForEveryone.get(i).getName()));
            }

        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
    }

    public static ObservableList<RealSalaryForEveryone> getRealSalary() {
        ObservableList<RealSalaryForEveryone> salaryData = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String .format("SELECT * FROM salary.`%s`", resultTableName));
            while (resultSet.next()) {
                RealSalaryForEveryone realSalaryForEveryone = new RealSalaryForEveryone();
                realSalaryForEveryone.setIdWorker(resultSet.getInt("idWorker"));
                realSalaryForEveryone.setRealSalary(resultSet.getDouble("realSalary"));
                realSalaryForEveryone.setIdMonth(Integer.parseInt(resultTableName));
                realSalaryForEveryone.setName(resultSet.getString("name"));
                salaryData.add(realSalaryForEveryone);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
        return salaryData;
    }

    public static ObservableList<Bonus> getBonuses(int idWorker) {
        ObservableList<Bonus> bonusData = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("select * from bonus where idWorker=" + idWorker);
            while (resultSet.next()) {
                Bonus bonus = new Bonus();
                StringBuffer buffer = new StringBuffer();
                buffer.append(resultSet.getInt("idMonth"));
                buffer.insert(buffer.length()-4, ".");
                bonus.setIdMonth(buffer.toString());
                bonus.setIdWorker(resultSet.getInt("idWorker"));
                bonus.setBonus(resultSet.getInt("bonus"));
                bonusData.add(bonus);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
        return bonusData;
    }

    public static void addBonus(Bonus tempBonus) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            StringBuffer tempIdMonth = new StringBuffer(tempBonus.getIdMonth());
            tempIdMonth.deleteCharAt(tempIdMonth.length()-5);
            int idMonth = Integer.parseInt(tempIdMonth.toString());
            int idWorker = tempBonus.getIdWorker();
            int bonus = tempBonus.getBonus();

            statement.execute(String.format("insert into bonus (idWorker, idMonth, bonus) " +
                            "values ('%d', '%d', '%d')", idWorker, idMonth, bonus));

        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
    }

    public static void deleteBonus(Bonus selectedItem) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            int key1 = selectedItem.getIdWorker();
            StringBuffer tempIdMonth = new StringBuffer(selectedItem.getIdMonth());
            tempIdMonth.deleteCharAt(tempIdMonth.length()-5);
            int key2 = Integer.parseInt(tempIdMonth.toString());

            statement.execute(String.format("DELETE FROM bonus WHERE `idWorker`='%d' and `idMonth`='%d'",
                    key1, key2));
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
    }

    public static void deleteBonus(int idWorker) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.execute(String.format("DELETE FROM bonus WHERE `idWorker`='%d'",
                    idWorker));
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
    } //вызывается в том случае, когда удаляем работника
}
