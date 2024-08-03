package letsit_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@DynamicUpdate
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class TeamPost {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @OneToOne
    @JoinColumn(name = "POST_ID")
    private Post postId;

    @Column(nullable = false)
    private String prjTitle;

    @CreationTimestamp
    private Timestamp teamCreateDate;

    private String notionLink;

    private String githubLink;

    @Column(nullable = false)
    private Boolean isComplete;


    // TODO 빌드주입으로 변경
    public TeamPost(Post post, String prjTitle, String notionLink, String githubLink) {
        this.postId = post;
        this.prjTitle = prjTitle;
        this.notionLink = notionLink;
        this.githubLink = githubLink;
        this.isComplete = false;
    }

    public void TeamUpdate(String title, String githubLink, String notionLink) {
        if (title != null) {this.prjTitle = title;}
        if (githubLink != null) {this.githubLink = githubLink;}
        if (notionLink != null) {this.notionLink = notionLink;}
    }

    public void projectEnd() {
        if (!this.isComplete) {
            this.isComplete = true;
        }
    }

}
