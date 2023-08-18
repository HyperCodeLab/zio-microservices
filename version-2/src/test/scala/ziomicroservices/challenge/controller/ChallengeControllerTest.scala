package ziomicroservices.challenge.controller

import zio._
import zio.http._
import zio.json._
import zio.test._
import zio.test.Assertion.equalTo
import ziomicroservices.challenge.model._
import ziomicroservices.challenge.repository._
import ziomicroservices.challenge.service.{ChallengeServiceImpl, RandomGeneratorServiceImpl}

object ChallengeControllerTest extends ZIOSpecDefault {

  def spec = {
    suite("Test Challenge Controller")(
      test("Controller should return right entity back when requested new challenge") {
        TestRandom.setSeed(42L)
        val app = ChallengeController()
        val req = Request.get(URL(Root  / "challenges" / "random"))
        assertZIO(app.runZIO(req).map(x => x.body))(equalTo(Response.json(Challenge(3, 8).toJson).body))
      },
      test("Controller should return True when validating challenge attempt") {
        val app = ChallengeController()
        val req = Request.post(Body.fromString("""{"user":{"alias":"TestUser"},"challenge":{"valueA":2,"valueB":2},"resultAttempt":4}"""), 
            URL(Root  / "challenges" / "attempt"))
        assertZIO(app.runZIO(req).map(x => x.body))(equalTo(Response.json("true").body))
      },
      test("Controller should return False when validating challenge attempt") {
        val app = ChallengeController()
        val req = Request.post(Body.fromString("""{"user":{"alias":"TestUser"},"challenge":{"valueA":2,"valueB":2},"resultAttempt":5}"""), 
            URL(Root  / "challenges" / "attempt"))
        assertZIO(app.runZIO(req).map(x => x.body))(equalTo(Response.json("false").body))
      },
      test("Get result of a previous attempt") {
        val app = ChallengeController()
        val entity = ChallengeAttempt(User("TestUser"), Challenge(2, 2), 4)
        for {
          repo <- ZIO.service[ChallengeAttemptRepository]
          id <- repo.save(entity)
          response <- app.runZIO(Request.get(URL(Root  / "challenges" / "results" / id)) ).map(x => x.body)
        } yield assert(response)(equalTo(Response.json(entity.toJson).body)) 
      },
      test("Get attempts of users") {
        val app = ChallengeController()
        val entity = ChallengeAttempt(User("TestUser"), Challenge(2, 2), 4)
        for {
          repo <- ZIO.service[ChallengeAttemptRepository]
          _ <- repo.save(entity)
          response <- app.runZIO(Request.get(URL(Root  / "challenges" / "users" / "TestUser")) ).map(x => x.body)
        } yield assert(response)(equalTo(Response.json(List(entity).toJson).body)) 
      }
      )}.provide(
      RandomGeneratorServiceImpl.layer,
      ChallengeServiceImpl.layer,
      InMemoryChallengeAttemptRepository.layer
    )
}
