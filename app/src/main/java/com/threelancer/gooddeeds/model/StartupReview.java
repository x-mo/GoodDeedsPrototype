package com.threelancer.gooddeeds.model;

/**
 * Created by hussam on 06/05/16.
 */
public class StartupReview {

    Integer img;
    String name, review;

    public StartupReview(Integer img, String name, String review) {
        this.img = img;
        this.name = name;
        this.review = review;
    }

    public Integer getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getReview() {
        return review;
    }
}
