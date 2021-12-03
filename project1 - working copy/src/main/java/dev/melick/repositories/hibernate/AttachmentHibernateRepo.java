package dev.melick.repositories.hibernate;

import dev.melick.models.Attachment;
import dev.melick.utils.hibernate.connection.HibernateUtil;
import dev.melick.repositories.hibernate.CrudRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

public class AttachmentHibernateRepo implements CrudRepository<Attachment>{


    @Override
    public Attachment add(Attachment a) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction tx = session.beginTransaction();
            session.save(a);
            tx.commit();

            return a;
        }
    }


    @Override
    public Attachment getById(Integer id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            Attachment a = session.get(Attachment.class, id);

            return a;
        }
    }

    @Override
    public List<Attachment> getAll() {

        List<Attachment> attachments = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            attachments = session.createQuery("from Attachment", Attachment.class).list();

            return attachments;
        }
    }

    @Override
    public void update(Attachment a) {

    }

    @Override
    public void delete(Integer id) {

    }

/*    public static void main(String [] args){

        AttachmentHibernateRepo ahr = new AttachmentHibernateRepo();
        Attachment att = new Attachment();

        att.setAttBy(2);
        att.setFileName("Hibernate Test Attachment");
        att.setFileExt(".txt");
        att.setFile("Hello from hibernate att repo!");


        System.out.println(ahr.add(att));
    }*/
}
