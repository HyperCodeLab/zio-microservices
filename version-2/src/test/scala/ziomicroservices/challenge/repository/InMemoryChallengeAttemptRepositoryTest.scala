package ziomicroservices.challenge.repository

import zio._
import zio.test._
import zio.test.Assertion.equalTo
import ziomicroservices.challenge.model._

object InMemoryChallengeAttemptRepositoryTest extends ZIOSpecDefault {

  def spec = {
    suite("InMemory Challenge Attempt Repository")(
      test("Repository should save the entity") {
        val entity = ChallengeAttempt(User("TestUser"), Challenge(2, 2), 4)
        TestRandom.setSeed(42L)
        for {
          repository <- ZIO.service[InMemoryChallengeAttemptRepository]
          id <- repository.save(entity)
        } yield assert(id)(equalTo("b2c8ccb8-191a-4233-9b34-3e3111a4adaf"))
      },
    test("Repository should find an existing the entity") {
        val entity = ChallengeAttempt(User("TestUser"), Challenge(2, 2), 4)
        for {
          repository <- ZIO.service[InMemoryChallengeAttemptRepository]
          id <- repository.save(entity)
          expectedEntity <- repository.find(id)
        } yield assert(expectedEntity)(equalTo(Some(entity)))
      },
      test("Repository should find all attempts for a user") {
        val entity1 = ChallengeAttempt(User("TestUser"), Challenge(2, 2), 4)
        val entity2 = ChallengeAttempt(User("TestUser"), Challenge(2, 3), 6)
        val entity3 = ChallengeAttempt(User("TestUser2"), Challenge(2, 3), 6)

        for {
          repository <- ZIO.service[InMemoryChallengeAttemptRepository]
          id1 <- repository.save(entity1)
          id2 <- repository.save(entity2)
          id3 <- repository.save(entity3)
          expectedEntity <- repository.findAttemptsByUser("TestUser")
        } yield assert(expectedEntity)(equalTo((List(entity1, entity2))))
      },
      
    )
  }.provideLayer(
    InMemoryChallengeAttemptRepository.layer
  )
}