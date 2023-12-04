# FINOS | TraderX | Morphir | Domain Model Trading App

This is a gradle module containing the business logic of traderX modeled in [morphir-elm](https://github.com/finos/morphir-elm)

The generated scala files are sourced into a jar file, stored as a library and then used in other projects


## Building domain-model project:
### Change directory into the domain-model directory
 ```./gradlew build``` 
### To publish generated library to local maven repository:
```./gradlew publishToMavenLocal```

## Building traderx project:
Requirements:
- Docker engine
- Make
### In the domain-model directory, run:
``` make compose ```



