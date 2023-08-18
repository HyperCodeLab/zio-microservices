package ziomicroservices.challenge.repository

import zio._
import ziomicroservices.challenge.model._

trait ChallengeAttemptRepository:
  def save(at: ChallengeAttempt): Task[String]
  def find(id: String): Task[Option[ChallengeAttempt]]
  def findAttemptsByUser(userAlias: String): Task[List[ChallengeAttempt]]
  


object ChallengeAttemptRepository:
  def save(at: ChallengeAttempt): ZIO[ChallengeAttemptRepository, Throwable, String] = ZIO.serviceWithZIO[ChallengeAttemptRepository](_.save(at))
  def find(id: String): ZIO[ChallengeAttemptRepository, Throwable, Option[ChallengeAttempt]] = ZIO.serviceWithZIO[ChallengeAttemptRepository](_.find(id))
  def findAttemptsByUser(userAlias: String): ZIO[ChallengeAttemptRepository, Throwable, List[ChallengeAttempt]] = ZIO.serviceWithZIO[ChallengeAttemptRepository](_.findAttemptsByUser(userAlias))
