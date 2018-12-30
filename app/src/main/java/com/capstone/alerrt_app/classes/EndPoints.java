package com.capstone.alerrt_app.classes;

public class EndPoints {
    //public static String BASE_URL = "http://alerrt.x10.mx/ALERRT/"; //for production
    public static String BASE_URL = "http://192.168.1.4/ALERRT/"; //for development

    public static String LOGIN_USER = BASE_URL + "loginUser.php";
    public static String REGISTER_USER = BASE_URL + "registerUser.php";
    public static String LOGGED_IN_USER_INFO = BASE_URL + "getInfoOfLoggedInUser.php?emailAddress=";
    public static String POST_SOMETHING = BASE_URL = "postSomething.php";
    public static String SHOW_ALL_POST = BASE_URL + "showAllPost.php";
}
