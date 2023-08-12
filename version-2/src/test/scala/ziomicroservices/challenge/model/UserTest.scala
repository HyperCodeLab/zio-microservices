package ziomicroservices.challenge.model

import zio._
import zio.test._
import zio.json._

object UserTest extends ZIOSpecDefault {

  def spec = {
    suite("Challenge Encoder / Decoding")(
      test("converts from class to json") {
        assertTrue(User("Test1").toJson == """{"alias":"Test1"}""")
      },
      test("converts from json to case class") {
        assertTrue( """{"alias":"Test1"}""".fromJson[User].getOrElse(null) == User("Test1"))
      }
    )
  }
}