package com.Zaffierce.codefellowship.models;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    ApplicationUser owner;

    private String body;
    private String createdAt;

    public String getBody() {
        return body;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public long getId() {
        return id;
    }

    public Post() {}

    public Post(String body, ApplicationUser owner) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        this.body = body;
        this.createdAt = dateFormat.format(date);
        this.owner = owner;
    }

    public String toString() {
        return String.format("%s -- Created on %s", this.body, this.createdAt);
    }
}
