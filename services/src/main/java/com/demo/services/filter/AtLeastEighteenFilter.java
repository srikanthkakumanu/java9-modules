package com.demo.services.filter;

import com.demo.services.api.PersonFilter;

import com.demo.models.Person;
import com.demo.services.api.PersonFilter;
public class AtLeastEighteenFilter implements PersonFilter {
    @Override
    public boolean filter(Person p) {
        return p.age() >= 18;
    }
}
