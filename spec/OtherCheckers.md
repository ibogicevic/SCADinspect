# Checkers research #23
This document shows what other Static Code Analyzers do
# FindBugs
[Full List of Checks](http://findbugs.sourceforge.net/bugDescriptions.html)

[Example Output](http://maven.apache.org/plugins/maven-checkstyle-plugin/findbugs.html)
#### 3 Categories
* __Correctness bug__
    * Probable bug-an apparent coding mistake resulting in code that was probably not what the developer intended. We strive for a low false positive rate.
* __Bad Practice__
    * Violations of recommended and essential coding practice. Examples include hash code and equals problems, cloneable idiom, dropped exceptions, serializable problems, and misuse of finalize. We strive to make this analysis accurate, although some groups may not care about some of the bad practices.
* __Dodgy__
    * Code that is confusing, anomalous, or written in a way that leads itself to errors. Examples include dead local stores, switch fall through, unconfirmed casts, and redundant null check of value known to be null. More false positives accepted. In previous versions of FindBugs, this category was known as Style.

# PDM

[Full Lists of Checks](http://pmd.sourceforge.net/pmd-5.0.4/rules/index.html)

[Example Output](http://maven.apache.org/plugins/maven-checkstyle-plugin/pmd.html)
### Finds
* __Possible bugs__ empty try/catch/finally/switch statements
* __Dead code__ unused local variables, parameters and private methods
* __Suboptimal code__ wasteful String/StringBuffer usage
* __Overcomplicated expressions__ unnecessary if statements, for loops that could be while loops
* __Duplicate code__ copied/pasted code means copied/pasted bugs

# Checkstyle
__3 Severities:__ Info, Warning, Error

__Multiple categories__ e.g. JavaDoc, coding, modifiers, sizes

[Full List of Checks](http://checkstyle.sourceforge.net/checks.html)

[Example Output](http://maven.apache.org/plugins/maven-checkstyle-plugin/checkstyle.html)
### Finds
* Missing JavaDoc, Tags,
* redundant modifiers, missing returns
* hidden objects
