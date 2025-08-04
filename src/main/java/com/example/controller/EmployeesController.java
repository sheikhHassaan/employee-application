package com.example.controller;

import com.example.model.EmployeeService;
import com.example.model.dto.EmployeeDTO;
import com.example.model.entity.Employee;
import com.example.model.repository.EmployeesRepository;
import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import static com.example.model.EmployeeService.mapEmployeeToDTO;
import static com.example.model.EmployeeService.mapEmployeesToDTO;

@Path("employee")
public class EmployeesController {

    Gson gson = new Gson();
    EmployeeService employeeService = new EmployeeService();
    EmployeesRepository employeesRepository = new EmployeesRepository();

    @GET
    @Path("/list")
    public Response fetchAllEmployees() {
        List<EmployeeDTO> employees = mapEmployeesToDTO(employeesRepository.fetchAll());
        String employeesJson = gson.toJson(employees);
        return Response.ok(employeesJson, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    public Response fetchEmployeeById(@PathParam("id") String empId) {
        EmployeeDTO employee = mapEmployeeToDTO(employeesRepository.fetchById(empId));
        String employeeJson = gson.toJson(employee);
        return Response.ok(employeeJson).build();
    }

    @GET
    @Path("/constraint")
    public Response fetchEmployeesByDepartmentAndManager(@QueryParam("department") String department, @QueryParam("reports-to") String managerName) {
        List<EmployeeDTO> employees = mapEmployeesToDTO(employeesRepository.fetchEmpByDeptAndMgr(department, managerName));
        String employeesJson = gson.toJson(employees);
        return Response.ok(employeesJson, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/promotion-eligible")
    public Response fetchPromotionEligibleEmployees() {
        List<EmployeeDTO> employees = mapEmployeesToDTO(employeesRepository.fetchPromotionEligibleEmployees());
        String employeesJson = gson.toJson(employees);
        return Response.ok(employeesJson, MediaType.APPLICATION_JSON).build();
    }

    @POST
    public Response addEmployee(String employeeJson, @QueryParam("reports_to") String reportsTo) {

        Employee employee = gson.fromJson(employeeJson, Employee.class);
        employee = employeeService.saveEmployee(employee, null, reportsTo, true);
        return Response.ok(gson.toJson(employee)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateEmployee(@PathParam("id") String id, @QueryParam("reports_to") String reportsTo, String employeeJson) {

        Employee employee = gson.fromJson(employeeJson, Employee.class);
        employee = employeeService.saveEmployee(employee, id, reportsTo, false);
        return Response.ok(gson.toJson(employee)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") String id) {
        employeesRepository.delete(id);
        return Response.ok().build();
    }

}
