package controllers

import play.api._
import play.api.mvc._

import models._
import org.squeryl.PrimitiveTypeMode._

object Jobs extends Controller {
  
  def list = Action {
    inTransaction{
      val jobs = from(AppDb.jobTable)(select(_)).toList
      Ok(views.html.jobList.render(jobs))
    }
  }
  
}