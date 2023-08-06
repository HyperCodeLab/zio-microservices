package ziomicroservices.challenge.service

import zio._
import ziomicroservices.challenge.model.Challenge

case class ChallengeServiceImpl(randomGeneratorService: RandomGeneratorService) extends ChallengeService:

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