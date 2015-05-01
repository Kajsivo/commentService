package commentService.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Date createdDate;

    @Column(nullable = false)
    public long itemId;

    @Column(nullable = false)
    public String author;

    @Column(nullable = false)
    public String content;
}
