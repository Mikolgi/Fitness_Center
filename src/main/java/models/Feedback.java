package models;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Feedback {
    private int id;
    private int userId;
    private int trainerId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}