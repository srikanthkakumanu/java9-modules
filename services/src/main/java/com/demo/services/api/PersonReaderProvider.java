package com.demo.services.api;

import com.demo.services.inmemory.InMemoryPersonReader;

import java.util.ServiceLoader;

// Dedicated class for only container the provider method
public class PersonReaderProvider {
    public static PersonReader provider() {
        return new InMemoryPersonReader(
                ServiceLoader.load(PersonFilter.class).findFirst().get()
        );
    }
}
