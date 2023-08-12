package ziomicroservices.challenge.controller

import zio._
import zio.http._
import zio.json._
import zio.test._
import zio.test.Assertion.equalTo
import ziomicroservices.challenge.model.Challenge
import ziomicroservices.challenge.service.{ChallengeServiceImpl, RandomGeneratorServiceImpl}

object RandomGeneratorControllerTest extends ZIOSpecDefault {

  def spec = {
    suite("Test RandomGenerator Controller")(
      test("Controller should return right entity back when requested new challenge") {
        TestRandom.setSeed(42L)
        val app = ChallengeController()
        val req = Request.get(URL(Root  / "challenges" / "random"))
        assertZIO(app.runZIO(req).map(x => x.body))(equalTo(Response.json(Challenge(3, 8).toJson).body))
      }).provide(
      RandomGeneratorServiceImpl.layer,
      ChallengeServiceImpl.layer
    )
  }

}
