package ziomicroservices.challenge.controller

import zio.ZIO
import zio.json._
import zio.http._
import ziomicroservices.challenge.model._
import ziomicroservices.challenge.service.ChallengeService

object ChallengeController:
  def apply(): Http[ChallengeService, Throwable, Request, Response] =
    Http.collectZIO[Request] {
      case Method.GET -> Root / "challenges" / "random" => {
        ChallengeService.createRandomMultiplication().map(response => Response.json(response.toJson))
      }
      case req@(Method.POST -> Root / "challenges" / "attempt") => (
        for {
          u <- req.body.asString.map(_.fromJson[ChallengeAttempt]) // Try to parse the request
          r <- u match
            case Left(e) =>
              ZIO
                .debug(s"Failed to parse the input: $e")
                .as(Response.text(e).withStatus(Status.BadRequest))
            case Right(u) =>
              ChallengeService.checkAttempt(u).map(out => Response.json(out.toJson))
        } yield r).orDie
    }
      