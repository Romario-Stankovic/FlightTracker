package rs.ac.singidunum.madexam.api.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    private int id;
    private String flightKey;
    private String flightNumber;
    private String destination;
    private Date scheduledAt;
    private Date estimatedAt;
    private String connectedType;
    private String connectedFlight;
    private String plane;
    private String gate;
    private String terminal;
}
