package com.threelancer.gooddeeds.model;


public class Review {
    Integer img;
    String name, review;

    public Review(Integer img, String name, String review) {
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
