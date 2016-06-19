package com.threelancer.gooddeeds.util;


public final class Constants {

    public static final String FIREBASE_URL = "http://gooddeedsapp.firebaseIO.com";
    public static final String FIREBASE_LOCATION_USERS = "users";
    public static final String FIREBASE_LOCATION_REQUESTS = "requests";
    public static final String FIREBASE_LOCATION_APPLICATIONS = "requestApplications";
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;
    public static final String FIREBASE_URL_REQUESTS = FIREBASE_URL + "/" + FIREBASE_LOCATION_REQUESTS;
    public static final String FIREBASE_URL_APPLICATIONS = FIREBASE_URL + "/" + FIREBASE_LOCATION_APPLICATIONS;

    /**
     * Constants for Firebase object properties
     */
    public static final String FIREBASE_PROPERTY_TIMESTAMP_CREATED = "timestampCreated";
    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";
    public static final String FIREBASE_PROPERTY_EMAIL = "email";
    public static final String FIREBASE_PROPERTY_REQUESTOR_NAME = "requestorName";
    public static final String FIREBASE_PROPERTY_REQUESTOR_EMAIL = "requestorEmail";
    public static final String FIREBASE_PROPERTY_DESCRIPTION = "description";
    public static final String FIREBASE_PROPERTY_REQUIREMENTS = "requirements";
    public static final String FIREBASE_PROPERTY_OFFER = "offer";
    public static final String FIREBASE_PROPERTY_TIME = "date";

    public static final String FIREBASE_PROPERTY_USER_REG_ID = "reg_id";


    public static final String KEY_LIST_ID = "requestId";
    public static final String KEY_EMAIL = "encodedEmail";
    public static final String KEY_USER_NAME = "username";


    public static final String PASSWORD_PROVIDER = "password";
}
