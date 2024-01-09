<div text-align="justify;">

# Modules in Java 9

Module is a collection of Java packages and related artifacts. They are introduced in Java 9.

**Package exporting is the key for the visibility:**
- Explicitly exported packages are visible for reuse.
- Packages that are NOT exported are not visible for reuse.

**Accessibility (JDK 1-8)**
- public
- protected
- private
- package (default)

**Accessibility (JDK 9+)**
- **public to everyone**
- **public to friend modules**
- **public only within a module**
- protected
- package (default)
- private

To create a Module,
- module-info.java is needed, and it should be created at base package level. ex: src/main/java.

Reference: https://javalin.io/tutorials/javalin-with-jpms-and-gradle

</div>