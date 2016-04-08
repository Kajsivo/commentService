package commentService.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Comment {

    @Id
    @Column(nullable = false, insertable = true, updatable = false)
    public String id;

    @Column(nullable = false, insertable = true, updatable = false)
    public Date createdDate;

    @Column(nullable = false)
    public String itemId;

    @Column(nullable = false, insertable = true, updatable = false)
    public String author;

    @Column(nullable = false)
    public String content;
}
