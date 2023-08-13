package ziomicroservices.challenge.model

import zio.json._

case class Challenge(valueA: Int, valueB: Int)

object Challenge:
  given JsonEncoder[Challenge] = DeriveJsonEncoder.gen[Challenge]
  given JsonDecoder[Challenge] = DeriveJsonDecoder.gen[Challenge]

