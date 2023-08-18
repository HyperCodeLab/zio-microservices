package ziomicroservices.challenge.service

import zio._
import zio.test._
import zio.test.Assertion.equalTo
import ziomicroservices.challenge.model._
import ziomicroservices.challenge.repository._

object ChallengeServiceImplTest extends ZIOSpecDefault {

  def spec = {
    suite("Test RandomGenerator Service")(
      test("RandomGenerator service should provide random number back") {
        for {
          _ <- TestRandom.setSeed(42L)
          randomService <- ZIO.service[RandomGeneratorService]
          challenge <- ChallengeServiceImpl(randomService, null).createRandomMultiplication()
        } yield assert(challenge)(equalTo(Challenge(9, 4)))
      },
      test("ChallengeService should check a ChallengeAttempt and yield true") {
        val challenge = ChallengeAttempt(User("TestUser"), Challenge(2, 3), 6)
        for {
          repo <- ZIO.service[ChallengeAttemptRepository]
          check <- ChallengeServiceImpl(null, repo).checkAttempt(challenge)
        } yield assert(check)(equalTo(true))
      },
      test("ChallengeService should check a ChallengeAttempt and yield false") {
        val challenge = ChallengeAttempt(User("TestUser"), Challenge(2, 3), 5)
        for {
          repo <- ZIO.service[ChallengeAttemptRepository]
          check <- ChallengeServiceImpl(null, repo).checkAttempt(challenge)
        } yield assert(check)(equalTo(false))
      },
      test("ChallengeService should return an existing Attempt back") {
        val entity = ChallengeAttempt(User("TestUser"), Challenge(2, 2), 4)
        for {
          repo <- ZIO.service[ChallengeAttemptRepository]
          id <- repo.save(entity) 
          expectedEntity <- ChallengeServiceImpl(null, repo).getAttemptById(id)
        } yield assert(entity)(equalTo(expectedEntity))
      }
    )
  }.provide(
    RandomGeneratorServiceImpl.layer,
    InMemoryChallengeAttemptRepository.layer
  )
  
}