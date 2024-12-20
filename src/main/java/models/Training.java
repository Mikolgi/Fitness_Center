package models;

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

public class Training {
    private int id;
    private String name;
    private String description;
    private String schedule;
    private int clientId;
    private int trainerId;



}
