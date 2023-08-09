package co.com.alpha.bcb.model.post.events;


import co.com.alpha.bcb.model.post.generic.DomainEvent;

public class PostCreated extends DomainEvent {
    private String title;
    private String author;

    private String commentId;
    private String comment;

    private String commentAuthor;

    public PostCreated(){
        super("posada.santiago.postcreated");
    }

    public PostCreated( String title,
                        String author,
                        String commentId,
                        String comment,
                        String commentAuthor) {

        super("posada.santiago.postcreated");
        this.title = title;
        this.author = author;
        this.commentId = commentId;
        this.comment = comment;
        this.commentAuthor = commentAuthor;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
