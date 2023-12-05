# FINOS | TraderX | Morphir | Domain Model Trading App
![Local Dev Machine Supported](http://badgen.net/badge/local-dev/supported/green)


This is a gradle module containing the business logic of traderX modeled in [morphir-elm](https://github.com/finos/morphir-elm)

More detailed information about traderx can be found [here](https://github.com/finos/traderX)

This project contains elm files which capture the business domain of traderx.
Upon building this project, scala files are generated which are used as the library.

The generated scala files are sourced into a jar file, stored as a library and then used in other projects(ie. traderx)

## Project Structure
```
├── src
│   ├── main
│   │   └── scala
│   │       └──traderx  --scala code is generated here after 
│   │
│   │
│   └──TraderX
│       ├── Trades
│       |   └──Trades.elm -- Trade related types and logic
│       .
│       . {Other business concepts}
│       .
│       └──Types.elm        -- common data types defined here
│       
├── makefile   --commands to start traderx with generated library
├── build.gradle --configurations for gradle project
├── morphir.json --configuration for morphir-elm files
├── package-lock.json 
└── morphir-ir.json --generated after build and used in generation of scala library
```

## Development setup
At the moment, data types and functions are modelled using the existing objects and methods in the trade-processor and trade-service java projects of traderx.

Modelling is done based on business concepts so example:
- All trade objects and logic within traderx are captured and modelled in one module (ie ./src/Traderx/Trades/Trades.elm)
- Common types that cut across multiple concepts like Date are defined in Types.elm



## Building domain-model project:
### Change directory into the domain-model directory
 ```
 ./gradlew build
 ``` 
### To publish generated library to local maven repository:
```
./gradlew publishToMavenLocal
```

## Building traderx project:
Requirements:
- Docker engine
- Make
### In the domain-model directory, run:
``` 
make compose 
```



