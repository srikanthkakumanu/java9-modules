<div text-align="justify;">

# Modules in Java 9

Module is a collection of Java packages and related artifacts. They are introduced in Java 9, modularization/modularity and small footprint (rather than full JRE) are the key objectives. 

The modules that comes with JDK are called platform modules. java.base is automatically accessible to all modules. Thus, no need to mention _requires_ statement. For other modules, your module declaration must
include _requires_ clause for the desired module.

To create a module, **module-info.java is required**. It should be **created at base package level** ex: src/main/java. This module-file.java should have only one module definition. It cannot contain other types of declarations.

Example for a reference: https://javalin.io/tutorials/javalin-with-jpms-and-gradle


**Modules Access Control**

| **Accessibility (JDK 1-8)** | **Accessibility (JDK 9+)**      |
|-----------------------------|---------------------------------|
| public                      | **public to everyone**          |
| protected                   | **public to friend modules**    |
| private                     | **public only within a module** |
| package (default)           | protected                       |
|                             | package (default)               |
|                             | private                         |


**Keywords in Modules**

| exports  | modules | open       | opens | provides |
|----------|---------|------------|-------|----------|
| requires | to      | transitive | uses  | with     |



**Package exporting is the key for the visibility:**

- **Explicitly exported packages are visible for reuse:**
  - The _public_ and _protected_ types within a package are accessible to other modules only if they are explicitly exported. Furthermore, the _public_, _protected_ members of those types are also accessible.

- **Packages that are NOT exported are not visible for reuse:** 
  - If a package within a module is not exported, then it is private to that module, including all of its public types.
  - Important to understand that the _public_ and _protected_ types of a package, whether exported or not, are always accessible within that package's module. The _exports_ statement simply makes them accessible to outside
    modules. Thus, any non-exported package is only for the internal use of its module.

- Java provides two important module features to support legacy code and to provide backward compatibility.
  - Unnamed modules
  - Automatic use of classpath rather than module path.

- **Unnamed modules:**
  - The code that is not part of any module, it automatically becomes part of unnamed module. 
  - All the packages in unnamed modules are automatically exported.
  - An unnamed module can access any and all other modules.

- **Automatic use of classpath rather than module path:**
  - For a program that does not use modules, the classpath mechanism is employed. Thus, the program is compiled and run in the same way it was prior to the advent of modules.


### **Resolving transitive or implied dependencies**

Consider there are 3 modules A, B, and C. Then, A requires B and B requires C (A->B->C). A has a indirect dependence on C though A may not use C. In case A does want to use C directly then it is called **transitive dependency or implied dependence or implied readability**. 

**Then better solution is:** 
Add a transitive dependency for C in B's module-info.java. Any module that depends on B will also, automatically, depend on C. Thus, A would automatically have access to C.

```java
// B's module-info.java
module B {
    exports somepackage;
    requires transitive C; 
}
```

**Example:-**

Note: Below mentioned examples are located in examples directory.

![](C:\practice\java9-modules\example.PNG)

SimpleMathFuncs.java
```java
  package com.simplefuncs;

  public class SimpleMathFuncs {
  // Determine if a is a factor of b.
  public static boolean isFactor(int a, int b) {
    return b % a == 0;
  }

  // Return the smallest positive factor that a and b have in common.
  public static int lcf(int a, int b) {
    // Factor using positive values.
    a = Math.abs(a);
    b = Math.abs(b);
    int min = Math.min(a, b);
    for (int i = 2; i <= min / 2; i++) {
      if (isFactor(i, a) && isFactor(i, b))
        return i;
    }
    return 1;
  }

  // Return the largest positive factor that a and b have in common.
  public static int gcf(int a, int b) {
    // Factor using positive values.
    a = Math.abs(a);
    b = Math.abs(b);
    int min = Math.min(a, b);
    for (int i = min / 2; i >= 2; i--) {
      if (isFactor(i, a) && isFactor(i, b))
        return i;
    }
    return 1;
  }
}
```

appfuncs/module-info.java

```java
module appfuncs {
    // exports com.simplefuncs;
    // can also export to specific module - or modules in comma separated list
    // exports com.simplefuncs to appstart; 
    // appsupport module created to demonstrate 
    // transitive dependency or implied dependence or implied readability
    exports com.simplefuncs;
    requires transitive appsupport; // Thus no need to include appsupport in appstart module-info.java
}
```

MyModAppDemo.java
```java
package com.mymodappdemo;

import com.simplefuncs.SimpleMathFuncs;

public class MyModAppDemo {
  public static void main(String[] args) {
    if (SimpleMathFuncs.isFactor(2, 10))
      System.out.println("2 is a factor of 10");
    System.out.println("Smallest factor common to both 35 and 105 is " + SimpleMathFuncs.lcf(35, 105));
    System.out.println("Largest factor common to both 35 and 105 is " + SimpleMathFuncs.gcf(35, 105));
  }
}
```

appstart/module-info.java
```java
module appstart {
    // Requires the module appfuncs.
    requires appfuncs;
}
```

**Important command examples**

```
C:\practice\mymodapp> javac -d appmodules/appfuncs .\appsrc\appfuncs\com\simplefuncs\SimpleMathFuncs.java .\appsrc\appfuncs\module-info.java
C:\practice\mymodapp> javac --module-path appmodules -d appmodules\appstart .\appsrc\appstart\module-info.java .\appsrc\appstart\com\mymodappdemo\MyModAppDemo.java
C:\practice\mymodapp> java --module-path appmodules -m appstart/com.mymodappdemo.MyModAppDemo
<!-- Below is multi-module compilation with --module-source-path option -->
C:\practice\mymodapp> javac -d appmodules --module-source-path appsrc appsrc\appstart\com\mymodappdemo\MyModAppDemo.java
C:\practice\mymodapp> java --module-path appmodules -m appstart/appstart.mymodappdemo.MyModAppDemo
<!-- multi-module compilation for transitive dependency example -->
C:\practice\mymodapp> javac -d appmodules --module-source-path appsrc appsrc/appsupport/com/appsupport/SupportMathFuncs.java appsrc/appsupport/module-info.java appsrc/appfuncs/com/simplefuncs/SimpleMathFuncs.java appsrc/appfuncs/module-info.java appsrc/appstart/com/mymodappdemo/MyModAppDemo.java appsrc/appstart/module-info.java

```
</div>
