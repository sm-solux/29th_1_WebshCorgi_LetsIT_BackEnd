package letsit_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calendarId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private TeamPost teamId;

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate; // 날짜보다 + 1??

//    @Builder
//    public Calendar(TeamPost teamPost, String title, String description, LocalDate start, LocalDate end) {
//        this.teamId = teamPost;
//        this.title = title;
//        this.description = description;
//        this.start = start;
//        this.end = end;
//    }
}
