package tfip.nus.iss.Day27Workshop.repo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import tfip.nus.iss.Day27Workshop.model.Comment;

@Repository
public class CommentRepo {

    @Autowired
    private MongoTemplate template;

    public Integer insertComment(Comment c) {
        // find if GID exist
        Integer gid = c.getGid();
        Criteria criterial = Criteria.where("gid").is(gid);
        Query q = Query.query(criterial);
        List<Document> game = template.find(q, Document.class, "game");
        if (game.size() == 0) {
            return 0; // 0 to show error page gid not exist
        }
        Document d = c.toDocument();
        template.insert(d, "comment");
        System.out.println(c.getcId());
        return 1;
    }

    public Integer updateComment(String cid, Comment updateComment) {
        Criteria criterial = Criteria.where("c_id").is(cid);
        Query q = Query.query(criterial);
        List<Document> comment = template.find(q, Document.class, "comment");
        if (comment.size() == 0) {
            return 0;
        }

        List<Document> editedArray = comment.get(0).getList("edited", Document.class);
        if (editedArray == null) {
            editedArray = new LinkedList<Document>();
        }
        editedArray.add(0, updateComment.toEdited()); // Add the edited version of the updated comment to the beginning
                                                      // of the editedArray
        editedArray = editedArray.stream().sorted(Comparator.comparing(d -> -d.getDate("posted").getTime()))
                .collect(Collectors.toList()); // Sort the editedArray in descending order based on the posted field of
                                               // each edited version of the comment
        Update updateOps = new Update().set("rating", updateComment.getRating()).set("c_text",
                updateComment.getcText()).set("posted", updateComment.toEdited().getDate("posted"))
                .set("edited", editedArray);
        UpdateResult updateResult = template.updateFirst(q, updateOps, Document.class, "comment");
        System.out.println(updateResult.getModifiedCount());
        return 1;
    }

}
