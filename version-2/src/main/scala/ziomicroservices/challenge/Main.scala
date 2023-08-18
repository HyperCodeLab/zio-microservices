package ziomicroservices.challenge

import zio.http.Server
import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}
import ziomicroservices.challenge.controller.ChallengeController
import ziomicroservices.challenge.service.{ChallengeServiceImpl, RandomGeneratorServiceImpl}
import ziomicroservices.challenge.repository.{InMemoryChallengeAttemptRepository}

object Main extends ZIOAppDefault {
  def run: ZIO[Environment with ZIOAppArgs with Scope, Throwable, Any] =
    val httpApps =  ChallengeController()
    Server
      .serve(
        httpApps.withDefaultErrorResponse
      )
      .provide(
        Server.defaultWithPort(8080),
        RandomGeneratorServiceImpl.layer,
        ChallengeServiceImpl.layer,
        InMemoryChallengeAttemptRepository.layer
      )
}
