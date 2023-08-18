package ziomicroservices.challenge.controller

import zio.ZIO
import zio.json._
import zio.http._
import ziomicroservices.challenge.model._
import ziomicroservices.challenge.service.ChallengeService
import zio.http.HttpAppMiddleware.cors
import zio.http.Header.{AccessControlAllowMethods, AccessControlAllowOrigin, Origin}
import zio.http.internal.middlewares.Cors.CorsConfig

object ChallengeController:
  def apply(): Http[ChallengeService, Throwable, Request, Response] =
    Http.collectZIO[Request] {
      
      case Method.GET -> Root / "challenges" / "random" => 
        ChallengeService.createRandomMultiplication().map(response => Response.json(response.toJson))

      case req@(Method.POST -> Root / "challenges" / "attempt") => (
        for {
          u <- req.body.asString.map(_.fromJson[ChallengeAttempt]) 
          r <- u match
            case Left(e) =>
              ZIO
                .debug(s"Failed to parse the input: $e")
                .as(Response.text(e).withStatus(Status.BadRequest))
            case Right(u) =>
              ChallengeService.checkAttempt(u).map(out => Response.json(out.toJson))
        } yield r).orDie

      case Method.GET -> Root / "challenges" /"results" / id =>
        ChallengeService.getAttemptById(id).map(out => Response.json(out.toJson)).orDie
      
      case Method.GET -> Root / "challenges" /"users" / userAlias =>
        ChallengeService.getAttemptsByUser(userAlias).map(out => Response.json(out.toJson)).orDie

    } @@ cors(CorsConfig(
      allowedOrigin = {
        case origin@Origin.Value(_, host, _) => Some(AccessControlAllowOrigin.All)
        case _ => Some(AccessControlAllowOrigin.All)
      },
      allowedMethods = AccessControlAllowMethods(Method.PUT, Method.DELETE, Method.GET, Method.POST),
    ) )
      