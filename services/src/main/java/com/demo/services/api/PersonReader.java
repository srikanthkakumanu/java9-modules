package com.demo.services.api;

import java.util.List;
import com.demo.models.Person;

@FunctionalInterface
public interface PersonReader {
    List<Person> getAll();
}
