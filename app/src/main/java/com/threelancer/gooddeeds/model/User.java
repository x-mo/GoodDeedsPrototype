package com.threelancer.gooddeeds.model;

import java.util.HashMap;

/**
 * Defines the data structure for User objects.
 */
public class User {
    private String name;
    private String email;
    private String reg_id;
    private String location;
    private String info;
    private HashMap<String, Object> timestampJoined;
    private HashMap<String, String> skills;
    private HashMap<String, String> reviews;

    /**
     * Required public constructor
     */
    public User() {
    }

    /**
     * Use this constructor to create new User.
     * Takes user name, email and timestampJoined as params
     *
     * @param name
     * @param email
     * @param timestampJoined
     */
    public User(String name, String email, HashMap<String, Object> timestampJoined, String reg_id) {
        this.name = name;
        this.email = email;
        this.timestampJoined = timestampJoined;
        this.reg_id = reg_id;
//        this.reg_id = reg_id;
        this.info = null;
        this.location = null;
        this.skills = null;
        this.reviews = null;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getReg_id() {
        return reg_id;
    }

    public String getLocation() {
        return location;
    }

    public String getInfo() {
        return info;
    }

    public HashMap<String, String> getSkills() {
        return skills;
    }

    public HashMap<String, String> getReviews() {
        return reviews;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setSkills(HashMap<String, String> skills) {
        this.skills = skills;
    }

    public void setReviews(HashMap<String, String> reviews) {
        this.reviews = reviews;
    }

    /*
    public String getReg_id() {
        return reg_id;
    }
*/

    public HashMap<String, Object> getTimestampJoined() {
        return timestampJoined;
    }

}
