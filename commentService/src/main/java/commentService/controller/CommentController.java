package commentService.controller;

import commentService.entity.Comment;
import commentService.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity save(@RequestBody Comment requestEntity) throws UnsupportedOperationException
    {
        try {
            Comment comment = commentRepository.save(requestEntity);
            return ResponseEntity.ok().body(comment.id);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Something went wrong");
        }
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity handleUnsupportedOperationException(UnsupportedOperationException ex)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
