module com.demo.api {
    // Here we can mention open source projects also
    // examples are:
    // requires org.slf4j;
    // requires com.fasterxml.jackson.databind;

    requires com.demo.services;
    // We need to make the api module a service consumer module
    // by explicitly stating that we’re consuming a service of type PersonReader.
    //requires kotlin.stdlib; // Add this if required to fix module resolution issues if there are any.
    uses com.demo.services.api.PersonReader;
    uses com.demo.services.api.PersonFilter;
    uses com.demo.services.api.PersonReaderProvider;
}