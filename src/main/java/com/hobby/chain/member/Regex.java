package com.hobby.chain.member;

public class Regex {
    public static final String REGEX_USER_ID = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9]+[.][A-Za-z]{2,6}$";
    public static final String REGEX_PASSWORD = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{6,15}$";
    public static final String REGEX_NAME = "^[가-힣]{2,5}$";
    public static final String REGEX_NICKNAME = "^[가-힣A-Za-z0-9]{2,5}$";
    public static final String REGEX_BIRTH = "^[0-9]{4}-?(0[1-9]|1[0-2])-?(0[1-9]|[12][0-9]|3[01])$";
    public static final String REGEX_PHONENUMBER = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$";

    private Regex(){ }
}
