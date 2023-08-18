package ziomicroservices.challenge.service

import zio._
import zio.test._
import zio.test.Assertion.equalTo

object RandomGeneratorServiceImplTest extends ZIOSpecDefault {

  def spec = {
    suite("Test RandomGenerator Service")(
      test("RandomGenerator service should provide random number back") {
        for {
          _ <- TestRandom.setSeed(42L)
          mul <- RandomGeneratorServiceImpl().generateRandomFactor()
        } yield assert(mul)(equalTo(9))
      }
    )
  }

}

