package ziomicroservices.challenge.repository

import zio._
import ziomicroservices.challenge.model._


case class InMemoryChallengeAttemptRepository(map: Ref[Map[String, ChallengeAttempt]]) extends ChallengeAttemptRepository:

    def save(att: ChallengeAttempt): UIO[String] = 
        for
            id <- Random.nextUUID.map(_.toString)
            _  <- map.update(_ + (id -> att))
        yield id
    
    def find(id: String): UIO[Option[ChallengeAttempt]] = 
      map.get.map(_.get(id))

    def findAttemptsByUser(userAlias: String): Task[List[ChallengeAttempt]] = 
      map.get.map( x => x.values.filter(x => x.user.alias == userAlias).toList )


object InMemoryChallengeAttemptRepository:

  def layer: ZLayer[Any, Nothing, InMemoryChallengeAttemptRepository] =
    ZLayer.fromZIO(
      Ref.make(Map.empty[String, ChallengeAttempt]).map(new InMemoryChallengeAttemptRepository(_))
    )