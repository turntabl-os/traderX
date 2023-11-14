FROM node:18

WORKDIR /morphir
COPY . .

EXPOSE 3000

RUN apt-get update && apt-get upgrade -y && \
    apt-get install -y nodejs \
    npm

RUN npm install -g morphir-elm
# ENTRYPOINT ./run.sh

RUN morphir-elm make

RUN morphir-elm gen -c

# RUN morphir-elm develop -p 3001