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

public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String role;
    private String qualification;

}
