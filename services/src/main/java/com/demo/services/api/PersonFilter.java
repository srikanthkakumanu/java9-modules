package com.demo.services.api;

import com.demo.models.Person;
@FunctionalInterface
public interface PersonFilter {
    boolean filter(Person p);
}
