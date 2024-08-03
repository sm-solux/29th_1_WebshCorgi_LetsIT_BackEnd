package letsit_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ProjectPortfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prtId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private TeamPost teamId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Member userId;

    @Column(nullable = false)
    private String prtTitle;

    @Column(nullable = false)
    private String workDescription;

    @Column(nullable = false)
    private String issues;

    @Column(nullable = false)
    private String solutions;

    @Column(nullable = false)
    private String feedback;

    @CreationTimestamp
    private Timestamp prtCreateDate;

    @UpdateTimestamp
    private Timestamp prtUpdateDate;
}
