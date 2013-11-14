package de.nilspreusker.devoxx13.todo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Nils Preusker - n.preusker@gmail.com - http://www.nilspreusker.de
 */
@Entity(name = "todo_item")
public class TodoItem {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "created_date")
    private Date createdDate = new Date();
    @Column(name = "text")
    private String text;
    @Column(name = "done")
    private boolean done;

    public TodoItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
