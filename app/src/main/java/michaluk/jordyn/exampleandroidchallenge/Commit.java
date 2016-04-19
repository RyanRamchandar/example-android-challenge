package michaluk.jordyn.exampleandroidchallenge;

/**
 * Created by Jordyn on 2016-04-18.
 * Model for a commit object, which includes the author name, message, and sha.
 */
public class Commit {
    private String author;
    private String message;
    private String commit;

    public Commit(String author, String message, String date) {
        this.author = author;
        this.message = message;
        this.commit = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
}
