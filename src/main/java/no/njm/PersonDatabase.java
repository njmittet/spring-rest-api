package no.njm;

import java.util.List;
import java.util.Optional;

public interface PersonDatabase {

    List<Person> listPersons(int limit);

    Optional<Person> getPerson(long id);

    Optional<Person> addPerson(String firstName, String lastName);
}
