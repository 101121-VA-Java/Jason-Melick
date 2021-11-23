package com.revature.daos;

import java.util.List;

import com.revature.models.Department;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Employee;
import com.revature.util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EmployeeHibernate implements EmployeeDao{

	/**
	 * Adds an employee to the database
	 * @return the employee with the new id or null if no record is created
	 */
	@Override
	public Employee add(Employee e) {

		try(Session s = HibernateUtil.getSessionFactory().openSession()) {

			Transaction tx = s.beginTransaction();
			int id = (int) s.save(e);
			tx.commit();
			s.close();

			return e;
		}
	}

	/**
	 * Retrieves an employee by its id from the database
	 * @return the employee if found or null otherwise
	 */
	@Override
	public Employee getById(int id) {

		try(Session s = HibernateUtil.getSessionFactory().openSession()) {

			Employee e = s.get(Employee.class, id);

			return e;
		}
	}



	/**
	 * Retrieves an employee by its name from the database
	 * @return the employee if found or null otherwise
	 */
	@Override
	public Employee getByName(String name) {

		try(Session s = HibernateUtil.getSessionFactory().openSession()){

			Employee e = s.get(Employee.class, name);

			return e;
		}
	}

	/**
	 * Retrieves all employees from the database
	 * @return a List of Employee objects
	 */
	@Override
	public List<Employee> getEmployees() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){

			List<Employee> employees = s.createQuery("from Employee", Employee.class).list();

			return employees;
		}
	}

	/**
	 * Retrieves employees by department id from the database
	 * @return a List of Employee objects
	 */
	@Override
	public List<Employee> getEmployeesByDepartmentId(int deptId) {

		List<Employee> employees = null;

		try(Session s = HibernateUtil.getSessionFactory().openSession()){

			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
			Root<Employee> root = cq.from(Employee.class);

			Predicate forDeptId = cb.equal(root.get("deptId"), deptId);

			cq.select(root).where(forDeptId);

			employees = s.createQuery(cq).getResultList();

			return employees;
		}
	}

	/**
	 * Updates an employee
	 */
	@Override
	public void update(Employee e) {

		try(Session s = HibernateUtil.getSessionFactory().openSession()){

			Transaction tx = s.beginTransaction();
			s.update(e);
			tx.commit();
		}
	}
	
	/**
	 * Deletes an employee
	 */
	@Override
	public void delete(Employee e) {

		try(Session s = HibernateUtil.getSessionFactory().openSession()){

			Transaction tx = s.beginTransaction();
			s.delete(e);
			tx.commit();
		}
	}
}
