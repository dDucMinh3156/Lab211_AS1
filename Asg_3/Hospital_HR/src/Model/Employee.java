/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.FormatDate;
import java.util.Comparator;
import java.util.Date;

/**
 * Hospital HR
 *
 * @author Ta Minh Duc
 */
public class Employee implements Comparator<Employee> {

    private String id;
    private String name;
    private String phone;
    private Date bDate;
    private String role;
    private Date hiredDate;
    private int salary;
    private int contractTime;
    private Date resignDate;

    public Employee() {
    }

    public Employee(String id, String name, String phone, Date bDate, String role,
            Date hiredDate, int salary, int contractTime, Date resignDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.bDate = bDate;
        this.role = role;
        this.hiredDate = hiredDate;
        this.salary = salary;
        this.contractTime = contractTime;
        this.resignDate = resignDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getbDate() {
        return bDate;
    }

    public void setbDate(Date bDate) {
        this.bDate = bDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getContractTime() {
        return contractTime;
    }

    public void setContractTime(int contractTime) {
        this.contractTime = contractTime;
    }

    public Date getResignDate() {
        return resignDate;
    }

    public void setResignDate(Date resignDate) {
        this.resignDate = resignDate;
    }

    public String saveFormat() {
        return String.format(getId() + ", " + getName() + ", " + getPhone() + ", "
                + FormatDate.formatDateTool(getbDate()) + ", " + getRole() + ", "
                + FormatDate.formatDateTool(getHiredDate(), "dd-MM-yyyy") + ", " + getSalary() + ", "
                + getContractTime() + ", "
                + FormatDate.formatDateTool(getResignDate(), "dd-MM-yyyy"));
    }

    public String displayFormat() {
        return String.format("| %-6s | %-14s | %11s | %12s | %-12s | %12s | %6d | %13d | %12s |\n",
                getId(), getName(), getPhone(),
                FormatDate.formatDateTool(getbDate()), getRole(),
                FormatDate.formatDateTool(getHiredDate(), "dd-MM-yyyy"), getSalary(),
                getContractTime(),
                FormatDate.formatDateTool(getResignDate(), "dd-MM-yyyy"));
    }

    @Override
    public int compare(Employee pr1, Employee pr2) {
        return pr1.getId().compareTo(pr2.getId());
    }

    @Override
    public String toString() {
        return this.id + ", " + this.name + ", " + this.phone + ", " + this.bDate
                + ", " + this.role + ", " + this.hiredDate + ", " + this.salary
                + ", " + this.contractTime + ", " + this.resignDate;

    }
}
