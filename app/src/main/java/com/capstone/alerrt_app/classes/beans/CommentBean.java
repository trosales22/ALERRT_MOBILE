package com.capstone.alerrt_app.classes.beans;

public class CommentBean {
    private String commentID,postID,commentBy,commentByID,comment,dateAndTimeCommented;

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

    public String getCommentBy() {
        return commentBy;
    }

    public String getCommentByID() {
        return commentByID;
    }

    public void setCommentByID(String commentByID) {
        this.commentByID = commentByID;
    }

    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
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
