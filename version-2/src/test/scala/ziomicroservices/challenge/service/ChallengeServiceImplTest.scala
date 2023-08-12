package ziomicroservices.challenge.service

import zio._
import zio.test._
import zio.test.Assertion.equalTo
import ziomicroservices.challenge.model.Challenge

object ChallengeServiceImplTest extends ZIOSpecDefault {

  def spec = {
    suite("Test RandomGenerator Service")(
      test("RandomGenerator service should provide random number back") {
        for {
          _ <- TestRandom.setSeed(42L)
          randomService <- ZIO.service[RandomGeneratorService]
          challenge <- ChallengeServiceImpl(randomService).createRandomMultiplication()
        } yield assert(challenge)(equalTo(Challenge(9, 4)))
      })
  }.provideLayer(
    RandomGeneratorServiceImpl.layer
  )
}