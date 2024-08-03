package letsit_backend.dto.team;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamEvaluationRequestDto {
    private double frequency;
    private double participate;
    private double kindness;
    private double promise;

    @Builder
    public TeamEvaluationRequestDto(double frequency, double participate, double kindness, double promise) {
        this.frequency = frequency;
        this.participate = participate;
        this.kindness = kindness;
        this.promise = promise;
    }
}
