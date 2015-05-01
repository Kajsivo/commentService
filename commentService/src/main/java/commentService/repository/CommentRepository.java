package commentService.repository;

import commentService.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT id FROM comment WHERE item_id = :itemId", nativeQuery = true)
    List<Long> findIdsByItemId(@Param("itemId") String itemId);
}
