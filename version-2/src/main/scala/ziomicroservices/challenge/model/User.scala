package ziomicroservices.challenge.model

import zio.json._

case class User(alias: String)

object User:
  given JsonEncoder[User] = DeriveJsonEncoder.gen[User]
  given JsonDecoder[User] = DeriveJsonDecoder.gen[User]


