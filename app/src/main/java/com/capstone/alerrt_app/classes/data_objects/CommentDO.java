package com.capstone.alerrt_app.classes.data_objects;

public class CommentDO {
    private String commentID,postID,commentBy,commentByID,comment,dateAndTimeCommented;

    public CommentDO(String commentID, String postID, String commentBy, String commentByID, String comment, String dateAndTimeCommented) {
        this.commentID = commentID;
        this.postID = postID;
        this.commentBy = commentBy;
        this.commentByID = commentByID;
        this.comment = comment;
        this.dateAndTimeCommented = dateAndTimeCommented;
    }

    public String getCommentID() {
        return commentID;
    }

    public String getPostID() {
        return postID;
    }

    public String getCommentBy() {
        return commentBy;
    }

    public String getCommentByID() {
        return commentByID;
    }

    public String getComment() {
        return comment;
    }

    public String getDateAndTimeCommented() {
        return dateAndTimeCommented;
    }
}
