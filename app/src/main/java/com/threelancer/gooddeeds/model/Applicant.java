package com.threelancer.gooddeeds.model;

public class Applicant {

    private String name, email, prposal;

    public Applicant() {
    }

    public Applicant(String name, String email, String prposal) {
        this.name = name;
        this.email = email;
        this.prposal = prposal;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPrposal() {
        return prposal;
    }
}
