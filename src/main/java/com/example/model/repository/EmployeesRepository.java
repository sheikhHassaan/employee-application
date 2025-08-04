package com.example.model.repository;

import jakarta.persistence.criteria.*;
import com.example.model.entity.Employee;
import com.example.model.helper.HelperUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeesRepository {

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static final String DESIGNATION = "designation";
    public static final String EXPERIENCE = "experience";
    public static final String ASSOCIATE_SOFTWARE_ENGINEER = "ASSOCIATE_SOFTWARE_ENGINEER";
    public static final String SOFTWARE_ENGINEER = "SOFTWARE_ENGINEER";
    public static final String SENIOR_SOFTWARE_ENGINEER = "SENIOR_SOFTWARE_ENGINEER";
    public static final String PRINCIPAL_SOFTWARE_ENGINEER = "PRINCIPAL_SOFTWARE_ENGINEER";

    /**
     * @return Returns all Employee records in employees table.
     */
    public List<Employee> fetchAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Employee", Employee.class).list();
        }
    }

    /**
     * @param id Primary key
     * @return Employee record with relevant id.
     */
    public Employee fetchById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Employee e WHERE e.id = :id", Employee.class).setParameter("id", id).getSingleResult();
        }
    }


    public List<Employee> fetchEmpByDeptAndMgr(String department, String managerName) {

        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
            Root<Employee> root = query.from(Employee.class);
            Join<Object, Object> reportsToJoin = root.join("reportsTo", JoinType.LEFT);

            Predicate deptPredicate = cb.equal(root.get("department"), department);
            Predicate managerPredicate = cb.equal(reportsToJoin.get("firstName"), managerName);

            query.select(root).where(cb.and(deptPredicate, managerPredicate));
            return session.createQuery(query).getResultList();
        }
    }


    public List<Employee> fetchPromotionEligibleEmployees() {

        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
            Root<Employee> root = query.from(Employee.class);

            Predicate ase = cb.and(cb.equal(root.get(DESIGNATION), ASSOCIATE_SOFTWARE_ENGINEER), cb.gt(root.get(EXPERIENCE), 1));
            Predicate se = cb.and(cb.equal(root.get(DESIGNATION), SOFTWARE_ENGINEER), cb.gt(root.get(EXPERIENCE), 3));
            Predicate sse = cb.and(cb.equal(root.get(DESIGNATION), SENIOR_SOFTWARE_ENGINEER), cb.gt(root.get(EXPERIENCE), 5));
            Predicate pse = cb.and(cb.equal(root.get(DESIGNATION), PRINCIPAL_SOFTWARE_ENGINEER), cb.gt(root.get(EXPERIENCE), 7));

            query.select(root).where(cb.or(ase, se, sse, pse));
            return session.createQuery(query).getResultList();
        }
    }

    /**
     * This method inserts the record if it doesn't exist already, otherwise updates the existing record.
     * @param newRecord to be inserted or updated.
     */
    public void upsert(Employee newRecord) {

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            Employee existingRecord = session.get(Employee.class, newRecord.getId());
            if (existingRecord == null) {
                // insert case
                existingRecord = new Employee();
                existingRecord.setId(newRecord.getId());
                session.persist(existingRecord);
            }

            existingRecord.setFirstName(HelperUtils.choose(newRecord.getFirstName(), existingRecord.getFirstName()));
            existingRecord.setLastName(HelperUtils.choose(newRecord.getLastName(), existingRecord.getLastName()));
            existingRecord.setAddress(HelperUtils.choose(newRecord.getAddress(), existingRecord.getAddress()));
            existingRecord.setEmail(HelperUtils.choose(newRecord.getEmail(), existingRecord.getEmail()));
            existingRecord.setPhoneNumber(HelperUtils.choose(newRecord.getPhoneNumber(), existingRecord.getPhoneNumber()));
            existingRecord.setDepartment(HelperUtils.choose(newRecord.getDepartment(), existingRecord.getDepartment()));
            existingRecord.setDesignation(HelperUtils.choose(newRecord.getDesignation(), existingRecord.getDesignation()));

            existingRecord.setReportsTo((newRecord.getReportsTo() == null) ? existingRecord.getReportsTo() : newRecord.getReportsTo());
            existingRecord.setSalary(HelperUtils.choose(newRecord.getSalary(), existingRecord.getSalary()));
            existingRecord.setExperience(HelperUtils.choose(newRecord.getExperience(), existingRecord.getExperience()));

            tx.commit();
        }
    }

    /**
     * @param id of the employee to be removed.
     */
    public void delete(String id) {

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                session.remove(employee);
            }
            tx.commit();
        }
    }

}
