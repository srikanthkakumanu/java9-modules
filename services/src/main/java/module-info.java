// The purpose of below import statements is to
// decouple API from services by introducing ServiceLoader

import com.demo.services.api.PersonReader;
import com.demo.services.api.PersonFilter;
import com.demo.services.filter.AtLeastEighteenFilter;
import com.demo.services.api.PersonReaderProvider;

module com.demo.services {
//    exports com.demo.services.api;
//    exports com.demo.services.inmemory;
//
//    requires com.demo.models;

    exports com.demo.services.api;
    requires com.demo.models;
    // To decouple API from services by
    // introducing a ServiceLoader:
    // We're telling the ServiceLoader that the
    // InMemoryPersonReader provides the implementation
    // for the PersonReader interface
    //provides PersonReader with InMemoryPersonReader;
    // Comment above line since we have our dedicated provider now
    // And tell the module system to use our dedicated provider to get an instance of our service provider
    provides PersonReader with PersonReaderProvider;

    // register our filter implementation
    provides PersonFilter with AtLeastEighteenFilter;
    // And we also have to state that our services module uses a service of type PersonFilter.
    // You still have to do this even though the service itself is defined in our own module.
    uses PersonFilter;
}