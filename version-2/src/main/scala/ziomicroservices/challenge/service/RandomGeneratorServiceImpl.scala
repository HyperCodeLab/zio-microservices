package ziomicroservices.challenge.service

import zio._

case class RandomGeneratorServiceImpl() extends RandomGeneratorService:
  val MINIMUM_FACTOR = 1
  val MAXIMUM_FACTOR = 10
  def generateRandomFactor(): UIO[Int] = Random.nextIntBetween(MINIMUM_FACTOR, MAXIMUM_FACTOR)

object RandomGeneratorServiceImpl:
  def layer: ZLayer[Any, Nothing, RandomGeneratorServiceImpl] = ZLayer.succeed( RandomGeneratorServiceImpl() )