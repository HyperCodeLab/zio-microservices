package ziomicroservices.challenge.service

import zio._
import ziomicroservices.challenge.model._

trait ChallengeService:
  def checkAttempt(attempt: ChallengeAttempt): Task[Boolean]
  def createRandomMultiplication(): UIO[Challenge]

object ChallengeService:
  def checkAttempt(attempt: ChallengeAttempt): ZIO[ChallengeService, Throwable, Boolean] = ZIO.serviceWithZIO[ChallengeService](_.checkAttempt(attempt))
  def createRandomMultiplication(): ZIO[ChallengeService, Nothing, Challenge] = ZIO.serviceWithZIO[ChallengeService](_.createRandomMultiplication())