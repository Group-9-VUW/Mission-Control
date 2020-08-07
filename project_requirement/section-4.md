### **Code Verification**

**Style Enforcement**

We will utilise the Checkstyle tool that is within Eclipse to enforce a specific code style. Checkstyle performs static code analysis to check if our source code complies with the Google Java Style Guide. It is however, very customisable, so we can set Checkstyle to comply with our own style specifications.

**Compiler Settings**

Through Eclipse, our compiler settings will be set to notify the developer with specific non default warning about their code. Particularly, this will include setting the following situations to the &quot;Warning&quot; level.

**Code Style** :

 Non-static access to static member.

Indirect access to static member.

Unqualified access to instance field.

Access to a non-accessible member of an enclosing type.

Parameter assignment.

Undocumented empty block.

Method with a constructor name.

Method can be static.

Method can potentially be static.

**Potential Programming Problems:**

Comparing identical values (&#39;x == x&#39;)

Assignment has no effect (e.g. &#39;x = x&#39;)

Using a char array in string concatenation.

Inexact type match for vararg arguments.

Unlikely argument type for collection methods using &#39;Object&#39;.

Incomplete &#39;switch&#39; cases on enum.

Hidden catch block.

&#39;finally&#39; does not complete normally.

Dead code (e.g. &#39;if (false)&#39;).

Resource leak.

Serializable class without serialVersionUID.

**Name Shadowing and Conflicts:**

Type parameter hides another type.

Method does not override package visible method.

Interface method conflicts with protected &#39;Object&#39; method.

**Unnecessary Code.**

Value of local variable is not used.

Value of exception parameter is not used.

Unused import.

**Null Analysis**

Null pointer access.

Potential null pointer access.

Violation of null specification.

Conflict between null annotations and null inference.

Unchecked conversion from non-annotated type to @NonNull type.

Problems detected by pessimistic analysis for free type variables.

Unsafe &#39;@Nonnull&#39; interpretation of free type variables.

Redundant null annotation.

&#39;@NonNull&#39; parameter not annotated in overriding method.

### **Integrated Development Environment (IDE)**

Our main IDE for development will be Eclipse. However, group members may prefer to develop in other IDEs. Use of those other IDEs will be acceptable if those group members still compile their code through Eclipse before pushing it to the repository. This will ensure that all those static code analysis notifications listed above will still be ran.

### **Testing Specific Requirements**

**Weather Data**

Tests should verify the HTTP status codes returned by the weather (i.e. 200 for successful POST request, 400 for a malformed URL).

**Map Data**

Verification of the data returned by the map API should be done. This may include checking the names of buildings returned from the map. Standard REST API verification like checking the status codes should also occur.

**Simulation**

Our software should test that it can correctly parse data returned by the OpenRocket software. The correctness of the resulting data structures resulting from the parsing of the data should be verified.

**Launch Control**

The system should check that it does not incorrectly give a GO signal when it should really be a NO GO. Vice versa.