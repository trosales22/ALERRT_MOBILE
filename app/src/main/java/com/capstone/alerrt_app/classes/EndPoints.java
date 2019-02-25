package com.capstone.alerrt_app.classes;

public class EndPoints {
    private static String BASE_URL = "http://alerrt.x10.mx/ALERRT/mobile_app/"; //for production
    //private static String BASE_URL = "http://192.168.1.2/ALERRT/mobile_app/"; //for development

    public static String LOGIN_USER = BASE_URL + "loginUser.php";
    public static String REGISTER_USER = BASE_URL + "registerUser.php";
    public static String UPDATE_ACCOUNT = BASE_URL + "updateAccount.php?loggedInUser=";
    public static String LOGGED_IN_USER_INFO = BASE_URL + "getInfoOfLoggedInUser.php?emailAddress=";
    public static String ADD_POST = BASE_URL + "addPost.php";
    public static String SHOW_MY_REPORTS = BASE_URL + "showAllPost.php?loggedInUser=";
    public static String SHOW_ALL_COMMENTS = BASE_URL + "showAllComments.php?postID=";
    public static String ADD_COMMENT_TO_POST = BASE_URL + "addCommentToPost.php";
    public static String UPDATE_COMMENT = BASE_URL + "updateComment.php?commentID=";
    public static String DELETE_COMMENT = BASE_URL + "deleteComment.php?commentID=";
    public static String SHOW_ALL_AGENCY = BASE_URL + "showAllAgency.php";
    public static String SHOW_ALL_NOTIF_COMMENTS = BASE_URL + "showAllNotifComments.php?loggedInUser=";
    public static String SHOW_ALL_NOTIF_REPORT_STATUS = BASE_URL + "showAllNotifReportStatus.php?loggedInUser=";
}
