package dev.melick.services;

import dev.melick.models.Attachment;
import dev.melick.repositories.AttRepo;
import dev.melick.repositories.hibernate.AttachmentHibernateRepo;

public class AttService {

    private static AttachmentHibernateRepo ahr = new AttachmentHibernateRepo();
    private static AttRepo ar = new AttRepo();

    public Integer createAttachment(Integer attBy, String fileName, String attText){

        Attachment att = new Attachment();

        att.setAttBy(attBy);
        att.setFileName(fileName);
        att.setFileExt(".txt");
        att.setFile(attText);

        Attachment a = ahr.add(att);

        return a.getAttId();

    }

/*    public static void main(String[] args){

        AttService as = new AttService();

        Integer attBy = 1;
        String fileName = "Attachment File";
        String attText = "Hello World!";

        Integer newAttId = as.createAttachment(attBy, fileName, attText);
        System.out.println(newAttId);

    }*/
}
