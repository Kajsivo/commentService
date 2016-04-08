package commentService.api;

import commentService.api.dto.CommentDto;
import commentService.entity.Comment;
import commentService.repository.CommentRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentController(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity save(@RequestBody Comment requestEntity) {
        requestEntity.createdDate = new Date();
        requestEntity.id = UUID.randomUUID().toString();
        Comment comment = commentRepository.save(requestEntity);
        return ResponseEntity.ok().body(comment.id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public CommentDto find(@PathVariable @NotNull String id) {
        Comment comment = commentRepository.findOne(id);
        return modelMapper.map(comment, CommentDto.class);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public HttpStatus delete(@PathVariable @NotNull String id) {
        commentRepository.delete(id);
        return HttpStatus.OK;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpStatus update(@PathVariable @NotNull String id, @RequestBody Comment requestEntity) {
        requestEntity.id = id;
        commentRepository.save(requestEntity);
        return HttpStatus.OK;
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
    public List findListForItem(@PathVariable @NotNull String itemId) throws NotFoundException {
        return commentRepository.findIdsByItemId(itemId);
    }
}
