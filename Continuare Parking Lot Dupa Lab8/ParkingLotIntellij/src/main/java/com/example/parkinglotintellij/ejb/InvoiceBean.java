package com.example.parkinglotintellij.ejb;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import java.util.HashSet;
import java.util.Set;

@Stateful
@SessionScoped
public class InvoiceBean {
    private Set<Integer> userIds = new HashSet<>();

    public Set<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Integer> userIds) {
        this.userIds = userIds;
    }
}
