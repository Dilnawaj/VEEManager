package com.employee.Employee.Managment.model;

import java.util.ArrayList;
import java.util.List;

public class ShareEmail {
    private String id;
    private List<Email> emails = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }
}
