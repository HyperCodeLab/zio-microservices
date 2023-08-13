package ziomicroservices.challenge.controller

import zio._
import zio.http._
import zio.json._
import zio.test._
import zio.test.Assertion.equalTo
import ziomicroservices.challenge.model.Challenge
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
      ).provide(
      RandomGeneratorServiceImpl.layer,
      ChallengeServiceImpl.layer
    )
  }

}
