package com.nphcda.application.data.entity;

import com.nphcda.application.data.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class PaySlipRequest extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "user")
    private User userdetails;

    private String requestType;
    private String startMonth;
    private String endMonth;


    private String startYear;
    private String endYear;

    private boolean fuifilled;


    public User getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(User userdetails) {
        this.userdetails = userdetails;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public boolean isFuifilled() {
        return fuifilled;
    }

    public void setFuifilled(boolean fuifilled) {
        this.fuifilled = fuifilled;
    }
}
