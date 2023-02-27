package tfip.nus.iss.Day27Workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tfip.nus.iss.Day27Workshop.model.Comment;
import tfip.nus.iss.Day27Workshop.repo.CommentRepo;

@Controller
@RequestMapping
public class CommentController {

    @Autowired
    private CommentRepo commentRepo;

    @GetMapping(path = { "/", "/index.html" })
    public String landingPage() {
        return "index";
    }

    @PostMapping(path = "/submitted")
    @ResponseBody
    public ResponseEntity<String> submitComment(@RequestBody MultiValueMap<String, String> form, Model model) {
        Comment c = Comment.toComment(form);
        Integer i = commentRepo.insertComment(c);
        if (i == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("GID not found");
        }
        String cId = c.getcId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("%s inserted".formatted(cId));
    }

    @PutMapping(path = "/review/{cId}")
    @ResponseBody
    public ResponseEntity<String> updateComment(@RequestBody Comment c, @PathVariable String cId) {
        Comment temp = c;
        if ((temp.getRating() > 10) || (temp.getRating() < 1)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Rating has to be >= 1 and <= 10");
        }
        Integer commentFound = commentRepo.updateComment(cId, c);
        if (commentFound == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("CID not found");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("%s comment updated".formatted(cId));
    }

}
