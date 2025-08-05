package com.example.action;

import com.example.model.entity.Employee;
import com.example.model.repository.EmployeesRepository;
import org.apache.struts2.ActionSupport;

import java.util.List;

public class EmployeeAction extends ActionSupport {

    private List<Employee> employees;
    private transient Employee employee;
    private String id;
    public static final String SUCCESS = "SUCCESS";

    EmployeesRepository employeesRepository = new EmployeesRepository();

    public String list() {
        employees = employeesRepository.fetchAll();
        return SUCCESS;
    }

    public String add() {
        if (employee != null) {
            employeesRepository.upsertDelta(employee);
            return SUCCESS;
        }
        return "INPUT";
    }

    public String edit() {
        employee = employeesRepository.fetchById(id);
        return SUCCESS;
    }

    public String update() {
        employeesRepository.upsertDelta(employee);
        return SUCCESS;
    }

    public String delete() {
        employeesRepository.delete(id);
        return SUCCESS;
    }

}
