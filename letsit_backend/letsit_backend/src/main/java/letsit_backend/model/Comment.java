package letsit_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post postId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Member userId;

    @Column(nullable = false)
    private String comContent;

    @CreatedDate
    private Timestamp comCreateDate;

    @LastModifiedDate
    private Timestamp comUpdateDate;

    public void update(String comment) {
        this.comContent = comment;
    }
}
