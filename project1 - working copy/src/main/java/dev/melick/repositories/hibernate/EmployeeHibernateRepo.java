package dev.melick.repositories.hibernate;

import dev.melick.models.Employee;
import dev.melick.models.Attachment;
import dev.melick.utils.hibernate.connection.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class EmployeeHibernateRepo implements CrudRepository<Employee>{

    @Override
    public Employee add(Employee employee) {
        return null;
    }

    @Override
    public Employee getById(Integer id) {

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employee e = session.get(Employee.class, id);
            session.close();
            return e;
        }
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            employees = session.createQuery("from Employee", Employee.class).list();
            return employees;
        }
    }

    public Employee getByUserName(String username){


        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> root = cq.from(Employee.class);

            Predicate pred = cb.equal(root.get("username"), username);

            //Predicate predicateForBoth = cb.and(predicateForDept, predicateForSup);
            cq.select(root).where(pred);
            Employee e = (Employee) session.createQuery(cq).getSingleResult();
            return e;
        }
    }

    public Employee getSupByDept(String dept){

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> root = cq.from(Employee.class);
            Predicate[] preds = new Predicate[2];
            preds[0] = cb.equal(root.get("department"), dept);
            preds[1] = cb.isTrue(root.get("supervisor"));
            //Predicate predicateForBoth = cb.and(predicateForDept, predicateForSup);
            cq.select(root).where(preds);
            Employee sup = (Employee) session.createQuery(cq).getSingleResult();

            return sup;
        }
    }

    public Employee getDeptHeadByDept(String dept){

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> root = cq.from(Employee.class);
            Predicate[] preds = new Predicate[2];
            preds[0] = cb.equal(root.get("department"), dept);
            preds[1] = cb.isTrue(root.get("deptHead"));
            cq.select(root).where(preds);
            Employee dHead = (Employee) session.createQuery(cq).getSingleResult();

            return dHead;
        }
    }

    public Integer getBencoId(){

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> root = cq.from(Employee.class);
            Predicate[] preds = new Predicate[2];
            preds[0] = cb.equal(root.get("department"), "benco");
            preds[1] = cb.isFalse(root.get("supervisor"));
            cq.select(root).where(preds);
            Employee benco = (Employee) session.createQuery(cq).getSingleResult();

            return benco.getEmpId();
        }
    }

    @Override
    public void update(Employee employee) {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            Transaction tx = session.beginTransaction();
            session.update(employee);
            tx.commit();
        }
    }

    @Override
    public void delete(Integer id) {

    }

/*    public static void main(String[] args){
        EmployeeHibernateRepo ehr = new EmployeeHibernateRepo();
        Employee keanu = ehr.getSupByDept("development");
        System.out.println(keanu);
    }*/
}

