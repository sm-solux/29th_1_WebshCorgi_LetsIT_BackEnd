package letsit_backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OngoingProjectDto {
    private Long teamId;
    private String prjTitle;
    private List<String> profileImages;
}
