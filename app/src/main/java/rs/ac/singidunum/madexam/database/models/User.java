package rs.ac.singidunum.madexam.database.models;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {
    private int id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String dateOfBirth;
    private String gender;
}
