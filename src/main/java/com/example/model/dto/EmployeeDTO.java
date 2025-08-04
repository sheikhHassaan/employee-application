package com.example.model.dto;

import com.example.model.entity.Employee;
import com.google.gson.annotations.SerializedName;

public record EmployeeDTO(
        @SerializedName("id")
        String id,
        @SerializedName("first_name")
        String firstName,
        @SerializedName("last_name")
        String lastName,
        @SerializedName("address")
        String address,
        @SerializedName("email")
        String email,
        @SerializedName("phone_number")
        String phoneNumber,
        @SerializedName("department")
        String department,
        @SerializedName("designation")
        String designation,
        @SerializedName("reports_to")
        String reportsToId,
        @SerializedName("salary")
        Double salary,
        @SerializedName("experience")
        Double experience
) {
    public static EmployeeDTO fromEntity(Employee e) {
        return new EmployeeDTO(
                e.getId(),
                e.getFirstName(),
                e.getLastName(),
                e.getAddress(),
                e.getEmail(),
                e.getPhoneNumber(),
                e.getDepartment(),
                e.getDesignation(),
                e.getReportsTo() != null ? e.getReportsTo().getId() : null,
                e.getSalary(),
                e.getExperience()
        );
    }
}
