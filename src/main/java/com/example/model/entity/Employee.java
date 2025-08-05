package com.example.model.entity;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Employee getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(Employee reportsTo) {
        this.reportsTo = reportsTo;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getExperience() {
        return experience;
    }

    public void setExperience(Double experience) {
        this.experience = experience;
    }
}


