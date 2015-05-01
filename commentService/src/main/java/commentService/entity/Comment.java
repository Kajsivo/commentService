package commentService.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(nullable = false, insertable = true, updatable = false)
    public Date createdDate;

    @Column(nullable = false)
    public long itemId;

    @Column(nullable = false, insertable = true, updatable = false)
    public String author;

    @Column(nullable = false)
    public String content;
}
