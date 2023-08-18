package ziomicroservices.challenge.service

import zio.*
import ziomicroservices.challenge.model.*
import ziomicroservices.challenge.repository.ChallengeAttemptRepository

case class ChallengeServiceImpl(randomGeneratorService: RandomGeneratorService, caRepo: ChallengeAttemptRepository) extends ChallengeService:

  def checkAttempt(attempt: ChallengeAttempt): Task[Boolean] = {
    attempt.check = Some(attempt.challenge.valueA * attempt.challenge.valueB == attempt.resultAttempt)
    for {
      _ <- ZIO.logInfo(s"Attempt ${attempt}")
      _ <- caRepo.save(attempt)
    } yield (attempt.check.get)
  }

  def createRandomMultiplication(): ZIO[Any, Nothing, Challenge] = {
    for {
      id1 <- randomGeneratorService.generateRandomFactor()
      id2 <- randomGeneratorService.generateRandomFactor()
    } yield (Challenge(id1, id2))
  }

  def getAttemptById(id: String): Task[ChallengeAttempt] = {
    caRepo.find(id)
      .map {
        case Some(attempt) => attempt
        case None => null
      }
  }

    def getAttemptsByUser(userAlias: String): Task[List[ChallengeAttempt]] = {
      for {
        attemps <- caRepo.findAttemptsByUser(userAlias)
      } yield (attemps)
    }



object ChallengeServiceImpl:
  
  def layer: ZLayer[RandomGeneratorService & ChallengeAttemptRepository, Nothing, ChallengeServiceImpl] = ZLayer {
    for {
      generator <- ZIO.service[RandomGeneratorService]
      repo <- ZIO.service[ChallengeAttemptRepository]
    } yield ChallengeServiceImpl(generator, repo)
  }