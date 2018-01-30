package project.salary.model;

/**
 * Created by Ekaterina Kiseleva on 30.03.2017.
 */
public class RealSalaryForEveryone {
    //вспомогательный класс для удобства расчета ЗП
    private int idMonth;
    private int idWorker;
    private double realSalary;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdMonth() {
        return idMonth;
    }

    public void setIdMonth(int idMonth) {
        this.idMonth = idMonth;
    }

    public int getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(int idWorker) {
        this.idWorker = idWorker;
    }

    public double getRealSalary() {
        return realSalary;
    }

    public void setRealSalary(double realSalary) {
        this.realSalary = realSalary;
    }
}