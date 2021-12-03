package dev.melick.repositories.hibernate;

import dev.melick.models.Attachment;
import dev.melick.models.Request;
import dev.melick.repositories.hibernate.CrudRepository;
import dev.melick.utils.hibernate.connection.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RequestHibernateRepo implements CrudRepository<Request>{

    @Override
    public Request add(Request r) {
        if (!r.isEventAtt()) {
           r.setEventAttId(1);
        }
        if (!r.isSupPreApp()) {
            r.setSpaAtt(1);
        }
        if (!r.isDhPreApp()) {
            r.setDhpaAtt(1);
        }
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            System.out.println("flag 1 "+r);
            Transaction tx = session.beginTransaction();
            session.save(r);
            tx.commit();
            System.out.println("flag 2 "+r);
            return r;
        }
    }

    @Override
    public Request getById(Integer id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            Request r = session.get(Request.class, id);

            return r;
        }
    }

    public ArrayList<Request> getByReqBy(Integer empId){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Request> cq = cb.createQuery(Request.class);
            Root<Request> root = cq.from(Request.class);
            Predicate pred = cb.equal(root.get("empId"), empId);
            cq.select(root).where(pred);
            ArrayList<Request> requests = (ArrayList<Request>) session.createQuery(cq).getResultList();

            return requests;
        }
    }

    public ArrayList<Request> getSupRequests(Integer supId){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Request> cq = cb.createQuery(Request.class);
            Root<Request> root = cq.from(Request.class);
            Predicate[] preds = new Predicate[3];
            preds[0] = cb.equal(root.get("empId"), supId);
            preds[1] = cb.isFalse(root.get("supApp"));
            preds[2] = cb.isFalse(root.get("denied"));
            cq.select(root).where(preds);
            ArrayList<Request> requests = (ArrayList<Request>) session.createQuery(cq).getResultList();

            return requests;

        }
    }

    public ArrayList<Request> getDhRequests(Integer dhId){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Request> cq = cb.createQuery(Request.class);
            Root<Request> root = cq.from(Request.class);
            Predicate[] preds = new Predicate[3];
            preds[0] = cb.equal(root.get("empId"), dhId);
            preds[1] = cb.isFalse(root.get("dhApp"));
            preds[2] = cb.isFalse(root.get("denied"));
            cq.select(root).where(preds);
            ArrayList<Request> requests = (ArrayList<Request>) session.createQuery(cq).getResultList();

            return requests;

        }
    }



    @Override
    public List<Request> getAll() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            List<Request> requests = null;

            requests = session.createQuery("from Request", Request.class).list();

            return requests;
        }
    }

    public ArrayList<Request> getAllPendingRequests(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Request> cq = cb.createQuery(Request.class);
            Root<Request> root = cq.from(Request.class);
            Predicate[] preds = new Predicate[2];
            preds[0] = cb.isFalse(root.get("bencoApp"));
            preds[1] = cb.isFalse(root.get("denied"));
            cq.select(root).where(preds);
            ArrayList<Request> requests = (ArrayList<Request>) session.createQuery(cq).getResultList();

            return requests;

        }
    }

    public ArrayList<Request> getAllReimbursements(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Request> cq = cb.createQuery(Request.class);
            Root<Request> root = cq.from(Request.class);
            Predicate pred = cb.isTrue(root.get("reimburse"));
            cq.select(root).where(pred);
            ArrayList<Request> requests = (ArrayList<Request>) session.createQuery(cq).getResultList();

            return requests;

        }
    }

    @Override
    public void update(Request r) {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            Transaction tx = session.beginTransaction();
            session.update(r);
            tx.commit();
        }
    }

    @Override
    public void delete(Integer id) {

    }
}
