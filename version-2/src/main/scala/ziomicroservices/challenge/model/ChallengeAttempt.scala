package ziomicroservices.challenge.model

import ziomicroservices.challenge.model._
import zio.json._


case class ChallengeAttempt(
        user: User,
        challenge: Challenge,
        resultAttempt: Int
        )

object ChallengeAttempt:
  given JsonEncoder[ChallengeAttempt] = DeriveJsonEncoder.gen[ChallengeAttempt]
  given JsonDecoder[ChallengeAttempt] = DeriveJsonDecoder.gen[ChallengeAttempt]

