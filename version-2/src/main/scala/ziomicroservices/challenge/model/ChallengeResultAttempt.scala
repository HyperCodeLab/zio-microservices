package ziomicroservices.challenge.model

import ziomicroservices.challenge.model._
import zio.json._


case class ChallengeResultAttempt(
        user: User,
        challenge: Challenge,
        resultAttempt: Int
        )

object ChallengeResultAttempt:
  given JsonEncoder[ChallengeResultAttempt] = DeriveJsonEncoder.gen[ChallengeResultAttempt]
  given JsonDecoder[ChallengeResultAttempt] = DeriveJsonDecoder.gen[ChallengeResultAttempt]

