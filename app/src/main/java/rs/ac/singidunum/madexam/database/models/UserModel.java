package rs.ac.singidunum.madexam.database.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserModel implements Serializable {
    private int id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String dateOfBirth;
    private String gender;
}
