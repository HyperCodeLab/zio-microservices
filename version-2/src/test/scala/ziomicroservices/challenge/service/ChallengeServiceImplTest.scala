package ziomicroservices.challenge.service

import zio._
import zio.test._
import zio.test.Assertion.equalTo
import ziomicroservices.challenge.model._

object ChallengeServiceImplTest extends ZIOSpecDefault {

  def spec = {
    suite("Test RandomGenerator Service")(
      test("RandomGenerator service should provide random number back") {
        for {
          _ <- TestRandom.setSeed(42L)
          randomService <- ZIO.service[RandomGeneratorService]
          challenge <- ChallengeServiceImpl(randomService).createRandomMultiplication()
        } yield assert(challenge)(equalTo(Challenge(9, 4)))
      },
      test("ChallengeService should check a ChallengeAttempt and yield true") {
        val challenge = ChallengeAttempt(User("TestUser"), Challenge(2, 3), 6)
        for {
          check <- ChallengeServiceImpl(null).checkAttempt(challenge)
        } yield assert(check)(equalTo(true))
      },
      test("ChallengeService should check a ChallengeAttempt and yield false") {
        val challenge = ChallengeAttempt(User("TestUser"), Challenge(2, 3), 5)
        for {
          check <- ChallengeServiceImpl(null).checkAttempt(challenge)
        } yield assert(check)(equalTo(false))
      }
      )
  }.provideLayer(
    RandomGeneratorServiceImpl.layer
  )
}