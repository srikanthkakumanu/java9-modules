package com.demo.api;

// commented below import to utilize service decoupling
// For more details, please check module-info.java
// And we are using ServiceLoader instead of direct class
//import com.demo.services.inmemory.InMemoryPersonReader;
import com.demo.services.api.PersonFilter;
import com.demo.services.api.PersonReader;
import com.demo.services.api.PersonReaderProvider;

import java.util.ServiceLoader;

public class API {
    public static void main(String[] args) {
        System.out.println("API's kind of alive for real! :-)");

        // var personReader = new InMemoryPersonReader();
        // <-- Getting an implementation for the PersonReader interface from the ServiceLoader
        //var personReader = ServiceLoader.load(PersonReader.class).findFirst().get();
        var personReader = PersonReaderProvider.provider();
        var result = personReader.getAll();
        System.out.printf("API: found the following people - %s\n", result);

    }
}