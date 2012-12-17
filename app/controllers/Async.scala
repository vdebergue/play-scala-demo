package controllers

import play.api._
import play.api.mvc._

import play.api.libs.ws.WS
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._
import java.util.concurrent.TimeoutException

object Async extends Controller {
  
  def async = Action {
    Async {
      val callUrl = "https://api.github.com/repos/playframework/Play20/commits"
      val request = WS.url(callUrl).withTimeout(3000).get()

      request map { response =>
        Ok("Github Response: "+ (response.json \\ "sha").map(_.as[String]))
      } recover {
        case t: TimeoutException => RequestTimeout(t.getMessage)
        case e => ServiceUnavailable(e.getMessage)
      }
    }
  }
}