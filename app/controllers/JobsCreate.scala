package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms.{mapping, text}

import models._
import org.squeryl.PrimitiveTypeMode._

object JobsCreate extends Controller {
  
  val jobForm = Form(
    mapping(
      "desc" -> text
      )(Job.apply)(Job.unapply)
  )

  def create = Action { implicit request =>
    // bind the request to the form and get a job if one
    jobForm.bindFromRequest.value map{ job => 
      // insert in db
      inTransaction(AppDb.jobTable insert job)
      Redirect(routes.Jobs.list)
    // if no job, return error
    } getOrElse BadRequest 
  }

}