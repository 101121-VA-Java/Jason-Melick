package dev.melick.services;

import dev.melick.models.Employee;
import dev.melick.repositories.EmpRepo;
import dev.melick.repositories.hibernate.EmployeeHibernateRepo;

public class EmpService {

    private static EmpRepo er = new EmpRepo();
    private static EmployeeHibernateRepo ehr = new EmployeeHibernateRepo();

    public boolean credPass(String password, Employee emp){

        try {
            if(emp.getPassword().equals(password)){
                return true;
            }
        }catch (NullPointerException e){
            System.out.println("Username or password not found.");
        }
        return false;
    }

    public Integer getSupId(Integer empId){

        Employee emp = ehr.getById(empId);
        String dept = emp.getDepartment();

        Employee sup = ehr.getSupByDept(dept);

        return sup.getEmpId();

    }

    public Integer getDeptHeadId(Integer empId){

        Employee emp = ehr.getById(empId);
        String dept = emp.getDepartment();

        Employee dh = ehr.getDeptHeadByDept(dept);

        return dh.getEmpId();

    }
}
