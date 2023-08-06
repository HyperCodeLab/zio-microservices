package ziomicroservices.challenge.controller

import zio.json._
import zio.http._
import ziomicroservices.challenge.model.Challenge
import ziomicroservices.challenge.service.ChallengeService

object ChallengeController:
  def apply(): Http[ChallengeService, Throwable, Request, Response] =
    Http.collectZIO[Request] {
      case Method.GET -> Root / "challenges" / "random" => {
        ChallengeService.createRandomMultiplication().map(response => Response.json(response.toJson))
      }
    }