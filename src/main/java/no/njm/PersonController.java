package no.njm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/persons")
class PersonController {

    private static Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonDatabase persons;

    @RequestMapping(method = RequestMethod.GET)
    public List<Person> listPersons(@RequestParam(value = "limit", required = false) String limit) {
        return persons.listPersons(parseString(limit));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> getPerson(@PathVariable long id) {
        Optional<Person> person = persons.getPerson(id);
        if (person.isPresent()) {
            return new ResponseEntity<>(person.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Person> createPerson(@RequestParam String firstName, @RequestParam String lastName) {
        if (isEmpty(firstName) || isEmpty(lastName)) {
            log.warn("All values is mandatory: firstName={}, lastname={}", firstName, lastName);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Person> person = persons.addPerson(firstName, lastName);
        if (!person.isPresent()) {
            log.error("Database returned empty object: firstName={}, lastname={}", firstName, lastName);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(person.get(), HttpStatus.CREATED);
    }

    private boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    private int parseString(String string) {
        int parsedInt;
        try {
            parsedInt = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return -1;
        }
        return parsedInt;
    }
}
