package ziomicroservices.challenge.service

import zio._
import ziomicroservices.challenge.model._

case class ChallengeServiceImpl(randomGeneratorService: RandomGeneratorService) extends ChallengeService:

  def checkAttempt(attempt: ChallengeAttempt): Task[Boolean] = {
    ZIO.succeed(attempt.challenge.valueA * attempt.challenge.valueB == attempt.resultAttempt)
  }

  def createRandomMultiplication(): ZIO[Any, Nothing, Challenge] = {
    for {
      id1 <- randomGeneratorService.generateRandomFactor()
      id2 <- randomGeneratorService.generateRandomFactor()
    } yield (Challenge(id1, id2))
  }

object ChallengeServiceImpl {
  def layer: ZLayer[RandomGeneratorService, Nothing, ChallengeServiceImpl] = ZLayer {
    for {
      generator <- ZIO.service[RandomGeneratorService]
    } yield ChallengeServiceImpl(generator)
  }
}