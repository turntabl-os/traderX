gbuild:
	gradlew build

publish: gbuild
	gradlew publishToMavenLocal


compose: publish
	cd .. && docker compose up

