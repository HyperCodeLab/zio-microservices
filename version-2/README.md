# Zio Microservices - Version 2

Initial version of the Application built for the post: Introduction to Scala ZIO.

Medium post: [placeholder]()

## Content

The application contains:

### Routes
- GET /challenges/random
- POST /challenges/attempt

### Models
- Challenge
- ChallengeAttempt
- User

### Services
- ChallengeService
- RandomGeneratorService

### Controllers
- ChallengeController


## How to run:

From an sbt shell you can run:

- To run the application on port 8080:

```
compile
run
```

- To run the tests:

```
test
```

