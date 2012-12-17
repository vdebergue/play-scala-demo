import play.api._
import play.api.mvc._
import models.{AppDb,Job}
import org.squeryl.adapters.{H2Adapter}
import org.squeryl.internals.DatabaseAdapter
import org.squeryl.{Session, SessionFactory}
import org.squeryl.PrimitiveTypeMode._
import play.api.db.DB

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Logger.info("Application has started")

    SessionFactory.concreteFactory = app.configuration.getString("db.default.driver") match {
      case Some("org.h2.Driver") => Some(() => getSession(new H2Adapter, app))
      case _ => sys.error("Database driver must be org.h2.Driver")
    }

    inTransaction{
      AppDb.create
      // Inserting some jobs
      AppDb.jobTable insert Job("IT developer")
      AppDb.jobTable insert Job("Sys ops")
      AppDb.jobTable insert Job("Web dev @ work4labs")
    }

  }  

  def getSession(adapter:DatabaseAdapter, app: Application) = Session.create(DB.getConnection()(app), adapter)
  
  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }  
    
}