package rs.ac.singidunum.madexam.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pageable<T> implements Serializable {

    private List<T> content = new ArrayList<>();

    private int totalPages;
    private int totalElements;
    private int size;
    private int number;

}
