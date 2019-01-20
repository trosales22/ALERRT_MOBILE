package com.capstone.alerrt_app.classes.data_objects;

public class NotificationCommentsDO {
    private String commentID,postID,topicTitle,topicStatus,commentBy_fullname,commmentBy_profilePic,commentBy_userID,comment,dateAndTimeCommented;

    public NotificationCommentsDO(String commentID, String postID, String topicTitle, String topicStatus, String commentBy_fullname, String commmentBy_profilePic, String commentBy_userID, String comment, String dateAndTimeCommented) {
        this.commentID = commentID;
        this.postID = postID;
        this.topicTitle = topicTitle;
        this.topicStatus = topicStatus;
        this.commentBy_fullname = commentBy_fullname;
        this.commmentBy_profilePic = commmentBy_profilePic;
        this.commentBy_userID = commentBy_userID;
        this.comment = comment;
        this.dateAndTimeCommented = dateAndTimeCommented;
    }

    public String getCommentID() {
        return commentID;
    }

    public String getPostID() {
        return postID;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public String getTopicStatus() {
        return topicStatus;
    }

    public String getCommentBy_fullname() {
        return commentBy_fullname;
    }

    public String getCommmentBy_profilePic() {
        return commmentBy_profilePic;
    }

    public String getCommentBy_userID() {
        return commentBy_userID;
    }

    public String getComment() {
        return comment;
    }

    public String getDateAndTimeCommented() {
        return dateAndTimeCommented;
    }
}
