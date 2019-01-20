package com.capstone.alerrt_app.classes.beans;

public class NotificationCommentsBean {
    private String commentID,postID,topicTitle,topicStatus,commentBy_fullname,commmentBy_profilePic,commentBy_userID,comment,dateAndTimeCommented;

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getTopicStatus() {
        return topicStatus;
    }

    public void setTopicStatus(String topicStatus) {
        this.topicStatus = topicStatus;
    }

    public String getCommentBy_fullname() {
        return commentBy_fullname;
    }

    public void setCommentBy_fullname(String commentBy_fullname) {
        this.commentBy_fullname = commentBy_fullname;
    }

    public String getCommmentBy_profilePic() {
        return commmentBy_profilePic;
    }

    public void setCommmentBy_profilePic(String commmentBy_profilePic) {
        this.commmentBy_profilePic = commmentBy_profilePic;
    }

    public String getCommentBy_userID() {
        return commentBy_userID;
    }

    public void setCommentBy_userID(String commentBy_userID) {
        this.commentBy_userID = commentBy_userID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateAndTimeCommented() {
        return dateAndTimeCommented;
    }

    public void setDateAndTimeCommented(String dateAndTimeCommented) {
        this.dateAndTimeCommented = dateAndTimeCommented;
    }
}
