package model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phoneNo;

    @Column(name = "department")
    private String department;

    @Column(name = "designation")
    private String designation;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reports_to")
    private Employee reportsTo;

    @Column(name = "salary")
    private double salary;

    @Column(name = "experience")
    private double experience;

}


