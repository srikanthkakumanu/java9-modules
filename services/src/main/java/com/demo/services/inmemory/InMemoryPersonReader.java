package com.demo.services.inmemory;

import com.demo.services.api.PersonReader;
import java.util.List;
import com.demo.services.api.PersonReader;
import com.demo.models.Person;
import com.demo.services.api.PersonFilter;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public class InMemoryPersonReader implements PersonReader {

    private PersonFilter filter;

    public InMemoryPersonReader(PersonFilter filter) { this.filter = filter; }

    @Override
    public List<Person> getAll() {
        return Stream.of(
                        new Person("Vincent Vega", 45),
                        new Person("Jules Winfield", 34),
                        new Person("Donald Bradman", 12),
                        new Person("Michio Kaku", 17))
                .filter(this.filter::filter).toList();
    }

    // See the provider method in action: static method named "provider" not having any formal parameters
    public static PersonReader provider() {
        return new InMemoryPersonReader(            // We need to instantiate the implementation ourselves
                ServiceLoader.load(PersonFilter.class)  // and we also have to resolve the dependencies ourselves via the ServiceLoader
                        .findFirst()
                        .get()                              // For the sake of completeness: in some real code cater for resolving errors :-)
        );
    }
}