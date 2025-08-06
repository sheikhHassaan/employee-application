package com.example.action;

import com.example.model.entity.Employee;
import com.example.model.repository.EmployeesRepository;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class EmployeeAction extends ActionSupport {

    @Setter
    private transient List<Employee> employees;
    @Setter
    private transient List<Employee> managers;
    @Setter
    private transient Employee employee;
    @Setter
    private transient Employee manager;
    @Getter
    private String managerId;
    @Getter
    private String id;

    public static final String SUCCESS = "SUCCESS";

    transient EmployeesRepository employeesRepository = new EmployeesRepository();

    public String list() {
        this.employees = employeesRepository.fetchAll();
        employees = employees.stream().sorted(Comparator.comparing(Employee::getFirstName)).toList();
        return SUCCESS;
    }

    public String add() {
        if (employee != null) {
            employee.setId(UUID.randomUUID().toString());
            if (StringUtils.isNotEmpty(managerId)) {
                manager = employeesRepository.fetchById(managerId);
                employee.setReportsTo(manager);
            }
            employeesRepository.upsertDelta(employee);
            return SUCCESS;
        }
        loadManagers();
        return "INPUT";
    }

    public String edit() {
        employee = employeesRepository.fetchById(id);
        loadManagers();
        managerId = (employee.getReportsTo() != null && StringUtils.isNotEmpty(employee.getReportsTo().getId())) ? employee.getReportsTo().getId() : managers.stream().findFirst().get().getId();
        return SUCCESS;
    }

    public String update() {
        if (StringUtils.isNotEmpty(managerId)) {
            manager = employeesRepository.fetchById(managerId);
            employee.setReportsTo(manager);
        }
        employeesRepository.upsertDelta(employee);
        return SUCCESS;
    }

    public String delete() {
        employeesRepository.delete(id);
        return SUCCESS;
    }

    public void loadManagers() {
        this.managers = employeesRepository.fetchAll();
        managers.add(new Employee());
    }

    @StrutsParameter(depth = 1)
    public List<Employee> getEmployees() {
        return employees;
    }

    @StrutsParameter(depth = 1)
    public List<Employee> getManagers() {
        return managers;
    }

    @StrutsParameter(depth = 1)
    public Employee getEmployee() {
        return employee;
    }

    @StrutsParameter(depth = 1)
    public Employee getManager() {
        return manager;
    }

    @StrutsParameter(depth = 0)
    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    @StrutsParameter(depth = 0)
    public void setId(String id) {
        this.id = id;
    }
}
