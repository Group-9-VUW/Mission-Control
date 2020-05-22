#Software

Dependencies should be managed in the Maven POM

Use Eclipse with the mandated checkstyle settings

Should have a JRE 1.8 runtime available

#Style

By default all variables and fields should be non null, unless explicitly defined as nullable

This is how blocks are formatted

if (condition) { 
    dpadjiowaj; 
} else if (condition) {
    dadwa;
} else {
    //very long comment with lottttttttttttttttttttttttttttts of text
    dawdwa; //short comment
}

Variable names for mutable variables should be in camelCase
static final variables in WHATEVER_THIS_CASE_IS

/***
 * Javadoc
 */
public void myfunction(type param1, type param2) {
    stuff;
}

Interface: InterfaceName
Main Implementation: InterfaceNameImpl
Factory: InterfaceNameFactory

switch (variable) {
    case whatever:
        djawoidjwaoij;
        break;
    case thaefdwad:
        fsdwa; 
        break;
    default:
        oawjdoawij;
        break;
}

There should be no (non-static) public fields.

class A {

    public static final VARIABLE;

    protected final variable2;
    
    private final variable;
    
    protected variable;
    
    protected variable4;

}