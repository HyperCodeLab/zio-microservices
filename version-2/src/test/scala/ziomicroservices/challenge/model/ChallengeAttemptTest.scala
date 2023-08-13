package ziomicroservices.challenge.model

import zio._
import zio.test._
import zio.json._

object ChallengeAttemptTest extends ZIOSpecDefault {

  def spec = {
    suite("Challenge Attempt Encoder / Decoding")(
      test("converts from class to json") {
        assertTrue(ChallengeAttempt(User("TestUser"), Challenge(2, 2), 4).toJson 
        == """{"user":{"alias":"TestUser"},"challenge":{"valueA":2,"valueB":2},"resultAttempt":4}""")
      },
       test("converts from json to case class") {
        assertTrue( """{"user":{"alias":"TestUser"},"challenge":{"valueA":3,"valueB":3},"resultAttempt":9}""".fromJson[ChallengeAttempt].getOrElse(null) 
        == ChallengeAttempt(User("TestUser"), Challenge(3, 3), 9))
      }
    )
  }
}