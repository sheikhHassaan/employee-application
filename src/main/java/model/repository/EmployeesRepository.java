package model.repository;

import model.entity.Employee;
import model.helper.HelperUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeesRepository {

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

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
