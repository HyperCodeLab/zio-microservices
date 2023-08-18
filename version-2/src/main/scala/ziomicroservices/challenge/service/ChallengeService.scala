package ziomicroservices.challenge.service

import zio._
import ziomicroservices.challenge.model._

trait ChallengeService:
  def checkAttempt(attempt: ChallengeAttempt): Task[Boolean]
  def createRandomMultiplication(): UIO[Challenge]
  def getAttemptById(id: String): Task[ChallengeAttempt]
  def getAttemptsByUser(userAlias: String): Task[List[ChallengeAttempt]]


object ChallengeService:
  def checkAttempt(attempt: ChallengeAttempt): ZIO[ChallengeService, Throwable, Boolean] = ZIO.serviceWithZIO[ChallengeService](_.checkAttempt(attempt))
  def createRandomMultiplication(): ZIO[ChallengeService, Nothing, Challenge] = ZIO.serviceWithZIO[ChallengeService](_.createRandomMultiplication())
  def getAttemptById(id: String): ZIO[ChallengeService, Throwable, ChallengeAttempt] = ZIO.serviceWithZIO[ChallengeService](_.getAttemptById(id))
  def getAttemptsByUser(userAlias: String): ZIO[ChallengeService, Throwable, List[ChallengeAttempt]] = ZIO.serviceWithZIO[ChallengeService](_.getAttemptsByUser(userAlias))