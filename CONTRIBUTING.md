# Software

Dependencies should be managed in the Maven POM.

The program should be written using Eclipse with Google Checks activated.<br>
This will enforce a consistent checkstyle across all program code for all team members.<br>
All warnings should be enabled, and the program should be warning-free, or as near to warning free as possible while remaining functional.

Should have a JRE 1.8 runtime available.

# Style

By default all variables and fields should be non null, unless explicitly defined as nullable.
```
//This ArrayList is @NonNull, meaning nonNullArrayList != null will always be true.
//Its elements are also @NonNull meaning null cannot be added to the ArrayList.
ArrayList<Integer> nonNullArrayList = new ArrayList<>();

//This ArrayList is @NonNull, meaning otherNonNullArrayList != null will always be true.
//Its elements are @Nullable however, meaning otherNonNullArrayList.add(null) is valid.
ArrayList<@Nullable Integer> otherNonNullArrayList = new ArrayList<>();

//This ArrayList is @Nullable, meaning nullableArrayList == null may be true.
//Its elements are @NonNull however, meaning null cannot be added to the ArrayList.
@Nullable ArrayList<Integer> nullableArrayList = new ArrayList<>();

//This ArrayList is @Nullable, meaning otherNullableArrayList == null may be true.
//Its elements are also @Nullable, meaning otherNullableArrayList.add(null) is valid.
@Nullable ArrayList<@Nullable Integer> otherNullableArrayList = new ArrayList<>();
```

Assuming that an interface is named: InterfaceName, the main implementation should be called InterfaceNameImpl and the factory should be called InterfaceNameFactory.
This pattern should hold true for all interfaces, main implementations and factories that we write.

```
class InterfaceNameImpl implements InterfaceName {
    ...
}
```

Abstract classes should begin with "Abstract" and should be implemented with a concrete class.

```
abstract class AbstractClassName {
    
}
```
```
class ConcreteClassName extends AbstractClassName {
    ...
}
```

If-else blocks should be written as:
```
if (condition) { 
    statement;
} else if (condition) {
    statement; 
} else {
    //a very long comment that is too long to fit on the same line, or describes what several lines do
    statement; //short comment
}
```

A method should be written as:
```
/***
 * Javadoc describing what the function does.
 * @param param1 parameter description
 * @param param2 parameter description
 * @return what is returned
 */
public int myfunction(type param1, type param2) {
    int var;
    method;
    return var;
}
```

Switch-case blocks should be written as:
```
switch (variable) {
    case a:
        statement;
        break;
    case b:
        statement; 
        break;
    case c:
    case d:
        statement;
        break;
    default:
        statement;
        break;
}
```

Variable names for mutable variables should be in camelCase.<br>
Static final variables should be in ALL_CAPS_WITH_UNDERSCORES.

There should be no (non-static) public fields. Fields should instead use getters and setters and should be protected or private.<br>
All classes and class level variables should have Javadocs explaining what its purpose.

```
/**
 * Javadoc explaining what class A's purpose is.
 */
class A {
    /**
     * Javadoc explaining what VARIABLE's purpose is.
     */
    public static final VARIABLE;
    /**
     * Javadoc explaining what variable2's purpose is.
     */
    protected final variable2;
    /**
     * Javadoc explaining what variable3's purpose is.
     */
    private final variable3;
    /**
     * Javadoc explaining what variable4's purpose is.
     */
    protected variable4;
    /**
     * Javadoc explaining what variable5's purpose is.
     */
    protected variable5;
}
```