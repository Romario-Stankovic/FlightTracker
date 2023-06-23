package rs.ac.singidunum.madexam.database.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFlight {
    private int id;
    private int userId;
    private int flightId;
}
