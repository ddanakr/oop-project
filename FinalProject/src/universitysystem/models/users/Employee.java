package universitysystem.models.users;

import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class Employee extends User {

	/**
     * Default constructor
     */
    public Employee() {
    }

    /**
     * 
     */
    private double salary;

    /**
     * 
     */
    private Date hireDate;

    protected Employee(
            String name,
            String lastName,
            int id,
            String login,
            String password,
            int age,
            String email,
            String phoneNumber,
            String gender,
            double salary,
            Date hireDate
    ) {
        super(name, lastName, id, login, password, age, email, phoneNumber, gender);
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "Employee{login='" + getLogin() + "', id=" + getId() + ", salary=" + salary + ", hireDate=" + hireDate
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(getLogin(), employee.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }

}