package com.example.model;

import com.example.model.dto.EmployeeDTO;
import com.example.model.entity.Employee;
import com.example.model.exceptions.BadRequestException;
import com.example.model.helper.HelperUtils;
import com.example.model.repository.EmployeesRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.UUID;

public class EmployeeService {

    EmployeesRepository employeesRepository = new EmployeesRepository();

    public static List<EmployeeDTO> mapEmployeesToDTO(List<Employee> employees) {
        return employees.stream().map(EmployeeDTO::fromEntity).toList();
    }

    public static EmployeeDTO mapEmployeeToDTO(Employee employee) {
        return EmployeeDTO.fromEntity(employee);
    }

    public Employee saveEmployee(Employee employee, String id, String managerId, Boolean isInsert) {

        if (HelperUtils.hasText(managerId) && !HelperUtils.isUUID(managerId)) {
            throw new BadRequestException("Invalid manager ID: " + managerId);
        }

        if (HelperUtils.hasText(id) && !HelperUtils.isUUID(id)) {
            throw new BadRequestException("Invalid ID: " + id);
        }

        if (BooleanUtils.isFalse(isInsert)) {   // Update case
            employee.setId(id);
        } else {                                // Insert case
            employee.setId(UUID.randomUUID().toString());
        }

        if (StringUtils.isNotEmpty(managerId)) {
            Employee manager = employeesRepository.fetchById(managerId);

            if (manager == null) {
                throw new BadRequestException("No such employee exists, having id: " + managerId);
            }
            employee.setReportsTo(manager);
        }

        employeesRepository.upsertDelta(employee);
        return employee;
    }


}
