#!/bin/bash

# generate morphir ir
morphir-elm make
# generate scala code from ir with dependencies
morphir-elm gen -c
# show ui for business logic
morphir-elm develop