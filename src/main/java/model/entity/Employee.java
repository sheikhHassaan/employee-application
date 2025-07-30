package model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "employee")
@Table(name = "employees")
public class Employee {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String address;

    private String email;

    private String phoneNo;

    private String department;

    private String designation;

    private String reportsTo;

    private double salary;

    private double experience;

}


