# SwingTools

## Description
These are tools that extend the capabilities of the Swing standard library in Java.


## Contents
> ColorTools - tools for better color management. For example, a change in transparency or a controlled increase in brightness.

> Factory - a class that makes it easy to create some Swing classes.

> FontSizeCalculator - class for calculating font sizes. For example, it contains a method for calculating the maximum font size in some container.

> animation package - provides easy creation of animations in Swing.

> interfaces package - a package with all used interfaces.

# Installation
You can use both GitHub releases and build systems.

Maven:
``` xml
<dependency>
    <groupId>com.anywaythanks</groupId>
    <artifactId>SwingTools</artifactId>
    <version>0.1-BETA</version>
</dependency>
```

Ivy:
``` html
<dependency org="com.anywaythanks" name="SwingTools" rev="0.1-BETA">
    <artifact name="SwingTools" ext="jar"/>
</dependency>
```

Gradle:
``` python
compile(group: 'com.anywaythanks', name: 'SwingTools', version: '0.1-BETA')
```

Sbt:
``` java
libraryDependencies += "com.anywaythanks" % "SwingTools" % "0.1-BETA"
```
