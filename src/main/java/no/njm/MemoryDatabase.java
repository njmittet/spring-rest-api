package no.njm;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
class MemoryDatabase implements PersonDatabase {

    private static final int INITIAL_ID = 1;
    private final AtomicLong counter = new AtomicLong(INITIAL_ID);
    private final Map<Long, Person> persons = new HashMap<>();

    @PostConstruct
    public void init() {
        addPerson("Ola", "Dunk");
        addPerson("Kari", "Nordmann");
    }

    @Override
    public List<Person> listPersons(int limit) {
        if (limit <= 0) {
            limit = Integer.MAX_VALUE;
        }
        List<Person> list = new ArrayList<>(persons.size());
        persons.entrySet()
               .stream()
               .limit(limit)
               .forEach(entry -> list.add(entry.getValue()));
        return list;
    }

    @Override
    public Optional<Person> getPerson(long id) {
        if (persons.containsKey(id)) {
            return Optional.of(persons.get(id));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Person> addPerson(String firstName, String lastName) {
        long id = counter.getAndIncrement();
        Person person = new Person(id, firstName, lastName);
        persons.put(id, person);
        return Optional.of(person);
    }
}
