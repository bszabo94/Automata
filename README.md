# Automata Simulator

### Description

Simulates the functioning of Mealy and Moore Machines, allows to fully costumize them, supports input and output, as well as some special operations defined on finite-state automata, such as the conversion between each other.

### Requirement

Requires 1.8 or newer version of ´java´ and 3.0 or newer version of ´maven´.

### Build and Run

In the main directory, use the following commands:

```
mvn package
java -jar Automata-Assembly/target/Automata-Assembly-1.0-jar-with-dependencies.jar
```

### Generating Site

```
mvn site
mvn site
mvn site:stage
```

We need to use the ´mvn site´ command twice, for a nasty behavior of the maven reporting plugins, what runs them on the parent project first.
