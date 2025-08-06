package com.example.model.repository;

import com.example.model.entity.Employee;
import com.example.model.helper.HelperUtils;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeesRepository {

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static final String DESIGNATION = "designation";
    public static final String EXPERIENCE = "experience";
    public static final String ASSOCIATE_SOFTWARE_ENGINEER = "associate software engineer";
    public static final String SOFTWARE_ENGINEER = "software engineer";
    public static final String SENIOR_SOFTWARE_ENGINEER = "senior software engineer";
    public static final String PRINCIPAL_SOFTWARE_ENGINEER = "principal software engineer";

    /**
     * @return Returns all Employee records in employees table.
     */
    public List<Employee> fetchAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Employee", Employee.class).list();
        }
    }

    /**
     * This method returns all employees sorted by their first_name in ascending order.
     *
     * @return List<Employee> sorted by firstname
     */
    public List<Employee> fetchAllSorted() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Employee e ORDER BY e.firstName ASC", Employee.class).list();
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

    /**
     * @param department  String -> department_name
     * @param managerName String -> firstname of reports_to entity
     * @return List<Employees> filtered by constraints
     */
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

    /**
     * @return All promotion eligible employees on a certain hardcoded criteria.
     */
    public List<Employee> fetchPromotionEligibleEmployees() {

        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
            Root<Employee> root = query.from(Employee.class);

            Predicate ase = cb.and(cb.equal(cb.lower(root.get(DESIGNATION)), ASSOCIATE_SOFTWARE_ENGINEER), cb.gt(root.get(EXPERIENCE), 1));
            Predicate se = cb.and(cb.equal(cb.lower(root.get(DESIGNATION)), SOFTWARE_ENGINEER), cb.gt(root.get(EXPERIENCE), 3));
            Predicate sse = cb.and(cb.equal(cb.lower(root.get(DESIGNATION)), SENIOR_SOFTWARE_ENGINEER), cb.gt(root.get(EXPERIENCE), 5));
            Predicate pse = cb.and(cb.equal(cb.lower(root.get(DESIGNATION)), PRINCIPAL_SOFTWARE_ENGINEER), cb.gt(root.get(EXPERIENCE), 7));

            query.select(root).where(cb.or(ase, se, sse, pse));
            return session.createQuery(query).getResultList();
        }
    }

    /**
     * This method inserts the record if it doesn't exist already, otherwise updates the existing record.
     *
     * @param newRecord to be inserted or updated.
     */
    public void upsertDelta(Employee newRecord) {

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            Employee existingRecord = session.get(Employee.class, newRecord.getId());
            if (existingRecord == null) {
                // Insert case
                existingRecord = new Employee();
                existingRecord.setId(newRecord.getId());
                existingRecord.setFirstName(newRecord.getFirstName());
                existingRecord.setLastName(newRecord.getLastName());
                existingRecord.setAddress(newRecord.getAddress());
                existingRecord.setEmail(newRecord.getEmail());
                existingRecord.setPhoneNumber(newRecord.getPhoneNumber());
                existingRecord.setDepartment(newRecord.getDepartment());
                existingRecord.setDesignation(newRecord.getDesignation());
                existingRecord.setReportsTo(newRecord.getReportsTo());
                existingRecord.setSalary(newRecord.getSalary());
                existingRecord.setExperience(newRecord.getExperience());

                session.persist(existingRecord);
            } else {
                // Update case
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
            }

            tx.commit();
        }
    }

    /**
     * Merges the new Employee object without any delta.
     *
     * @param newEmployee object to be updated.
     */
    public void upsert(Employee newEmployee) {

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(newEmployee);
            tx.commit();
        }
    }

    /**
     * This method fetches all the resources that report to Manager having employeeId empId
     *
     * @param empId Employee ID of the manager
     * @return Subordinates of the manager.
     */
    public void removeIfManager(String empId) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<Employee> subordinates = session.createQuery("FROM Employee e WHERE e.reportsTo.id = :empId", Employee.class)
                    .setParameter("empId", empId)
                    .list();
            subordinates.forEach(s -> s.setReportsTo(null));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new SessionException("Failed to fetch subordinates", e);
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
                removeIfManager(employee.getId());
                session.remove(employee);
            }
            tx.commit();
        }
    }

}
