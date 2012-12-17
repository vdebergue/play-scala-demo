package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._

import models._
import org.squeryl.PrimitiveTypeMode._

object JobsJson extends Controller {
  
  implicit val jobWrites = Json.writes[Job]

  def listToJson = Action{
    inTransaction{
      val jobs = from(AppDb.jobTable)(select(_)).toList
      Ok(Json.toJson(jobs))
    }
  }
}