package com.example.action;

import com.example.model.entity.Employee;
import com.example.model.repository.EmployeesRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class EmployeeAction extends ActionSupport {

    private List<Employee> employees;
    private List<Employee> managers;
    private Employee employee;
    private Employee manager;
    private String managerId;
    private String id;

    public static final String SUCCESS = "SUCCESS";

    EmployeesRepository employeesRepository = new EmployeesRepository();

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

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @StrutsParameter(depth = 1)
    public List<Employee> getManagers() {
        return managers;
    }

    public void setManagers(List<Employee> managers) {
        this.managers = managers;
    }

    @StrutsParameter(depth = 1)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getManagerId() {
        return managerId;
    }

    @StrutsParameter(depth = 1)
    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    @StrutsParameter(depth = 0)
    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getId() {
        return id;
    }

    @StrutsParameter(depth = 0)
    public void setId(String id) {
        this.id = id;
    }
}
