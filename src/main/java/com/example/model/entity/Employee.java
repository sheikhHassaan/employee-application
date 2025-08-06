package com.example.model.entity;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)")
    @SerializedName("id")
    private String id;

    @Column(name = "first_name")
    @SerializedName("first_name")
    private String firstName;

    @Column(name = "last_name")
    @SerializedName("last_name")
    private String lastName;

    @Column(name = "address")
    @SerializedName("address")
    private String address;

    @Column(name = "email")
    @SerializedName("email")
    private String email;

    @Column(name = "phone_number")
    @SerializedName("phone_number")
    private String phoneNumber;

    @Column(name = "department")
    @SerializedName("department")
    private String department;

    @Column(name = "designation")
    @SerializedName("designation")
    private String designation;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reports_to")
    @SerializedName("reports_to")
    private Employee reportsTo;

    @Column(name = "salary")
    @SerializedName("salary")
    private Double salary;

    @Column(name = "experience")
    @SerializedName("experience")
    private Double experience;

}


