package com.example.controller;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.example.model.entity.Employee;
import com.example.model.helper.HelperUtils;
import com.example.model.repository.EmployeesRepository;

import java.util.UUID;

@Path("employee")
public class EmployeesController {

    EmployeesRepository employeesRepository = new EmployeesRepository();

    @GET
    @Path("/list")
    public Response fetchAllEmployees() {
        return Response.ok(employeesRepository.fetchAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response fetchEmployeeById(@PathParam("id") String empId) {
        return Response.ok(employeesRepository.fetchById(empId)).build();
    }

    @GET
    @Path("/constraint")
    public Response fetchEmployeesByDepartmentAndManager(@QueryParam("department") String department, @QueryParam("reports-to") String managerName) {
        return Response.ok(employeesRepository.fetchEmpByDeptAndMgr(department, managerName)).build();
    }

    @GET
    @Path("/promotion-eligible")
    public Response fetchPromotionEligibleEmployees() {
        return Response.ok(employeesRepository.fetchPromotionEligibleEmployees()).build();
    }

    @POST
    public Response addEmployee(String employeeJson) {

        Employee newEmployee = new Gson().fromJson(employeeJson, Employee.class);
        newEmployee.setId(UUID.randomUUID().toString());

        employeesRepository.upsert(newEmployee);
        return Response.ok(newEmployee).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateEmployee(@PathParam("id") String id, String employeeJson) {

        Employee employee = new Gson().fromJson(employeeJson, Employee.class);
        if (!HelperUtils.isUUID(id)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid ID " + id + ", must be a UUID.").build();
        }

        employee.setId(id);

        employeesRepository.upsert(employee);
        return Response.ok(employee).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") String id) {
        employeesRepository.delete(id);
        return Response.ok().build();
    }

}
