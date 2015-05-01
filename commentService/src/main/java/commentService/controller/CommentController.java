package commentService.controller;

import commentService.entity.Comment;
import commentService.repository.CommentRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity save(@RequestBody Comment requestEntity) throws UnsupportedOperationException {
        try {
            requestEntity.createdDate = new Date();
            Comment comment = commentRepository.save(requestEntity);
            return ResponseEntity.ok().body(comment.id);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Something went wrong");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Comment find(@PathVariable @NotNull long id) throws NotFoundException {
        Comment comment = commentRepository.findOne(id);
        if (comment != null) {
            return comment;
        } else {
            throw new NotFoundException("Comment not found");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public HttpStatus delete(@PathVariable @NotNull long id) throws NotFoundException {
        try {
            commentRepository.delete(id);
            return HttpStatus.OK;
        } catch (Exception e) {
            throw new NotFoundException("Comment not found");
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpStatus update(@PathVariable @NotNull long id, @RequestBody Comment requestEntity) throws NotFoundException {
        try {
            requestEntity.id = id;
            commentRepository.save(requestEntity);
            return HttpStatus.OK;
        } catch (Exception e) {
            throw new NotFoundException("Comment not found");
        }
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity handleUnsupportedOperationException(UnsupportedOperationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @RequestMapping(value = "item/{itemId}", method = RequestMethod.GET, produces = "application/json")
    public List findListForItem(@PathVariable @NotNull String itemId) throws NotFoundException
    {
        List<Long> tasksList = commentRepository.findIdsByItemId(itemId);
        if (tasksList != null) {
            return tasksList;
        } else {
            throw new NotFoundException("Comment not found");
        }
    }
}
