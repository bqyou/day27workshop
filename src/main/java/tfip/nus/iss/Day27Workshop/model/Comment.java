package tfip.nus.iss.Day27Workshop.model;

import java.util.Date;
import java.util.UUID;

import org.bson.Document;
import org.springframework.util.MultiValueMap;

public class Comment {

    private String cId;
    private Integer gid;
    private Integer rating;
    private String cText;
    private String user;
    private Date posted;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getcText() {
        return cText;
    }

    public void setcText(String cText) {
        this.cText = cText;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static Comment toComment(MultiValueMap<String, String> form) {
        Comment c = new Comment();
        c.setcId(UUID.randomUUID().toString().substring(0, 8));
        c.setGid(Integer.parseInt(form.getFirst("gid")));
        c.setRating(Integer.parseInt(form.getFirst("rating")));
        c.setUser(form.getFirst("user"));
        c.setcText(form.getFirst("cText"));
        Date date = new Date();
        c.setPosted(date);
        return c;
    }

    public Document toDocument() {
        Document d = new Document();
        d.put("c_id", getcId());
        d.put("user", getUser());
        d.put("rating", getRating());
        d.put("c_text", getcText());
        d.put("gid", getGid());
        d.put("posted", getPosted());

        return d;
    }

    public Document toEdited() {
        Document d = new Document();

        d.put("comment", getcText());
        d.put("rating", getRating());
        d.put("posted", new Date());
        return d;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }

}
