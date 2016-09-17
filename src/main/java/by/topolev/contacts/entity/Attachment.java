package by.topolev.contacts.entity;

import by.topolev.contacts.orm.annotation.Column;
import by.topolev.contacts.orm.annotation.Id;
import by.topolev.contacts.orm.annotation.Table;

import java.util.Date;

/**
 * Created by Vladimir on 17.09.2016.
 */
@Table(name = "attachment")
public class Attachment {
    @Id
    private Integer id;

    @Column(name = "name_file")
    private String nameFile;

    @Column(name = "comment_file")
    private String commentFile;

    /*
    @Column(name = "date_file")
    private Date dateFile;*/

    @Column(name = "name_file_in_system")
    private String nameFileInSystem;

    @Column(name="contact_id")
    private Integer contact_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getCommentFile() {
        return commentFile;
    }

    public void setCommentFile(String commentFile) {
        this.commentFile = commentFile;
    }
/*
    public Date getDateFile() {
        return dateFile;
    }

    public void setDateFile(Date dateFile) {
        this.dateFile = dateFile;
    }*/


    public String getNameFileInSystem() {
        return nameFileInSystem;
    }

    public void setNameFileInSystem(String nameFileInSystem) {
        this.nameFileInSystem = nameFileInSystem;
    }

    public Integer getContact_id() {
        return contact_id;
    }

    public void setContact_id(Integer contact_id) {
        this.contact_id = contact_id;
    }
}
