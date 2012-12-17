package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

import org.squeryl.{Schema, KeyedEntity}

case class Job(desc: String) extends KeyedEntity[Long]{
  val id: Long = 0
}

object AppDb extends Schema {
  val jobTable = table[Job]("job")
}
