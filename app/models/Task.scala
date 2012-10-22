package models




case class Task(id: Long, label: String)

object Task
{
  import anorm._
  import anorm.SqlParser._
  import play.api.db._
  import play.api.Play.current

  val task = {
    get[Long]("id") ~
    get[String]("label") map {
      case id~label => Task(id, label)
    }
  }

  def all(): List[Task] = DB.withConnection { implicit connection =>
    SQL("SELECT * FROM task").as(task *)
  }

  def create(label: String) {
    DB.withConnection { implicit connection =>
      SQL("INSERT INTO task (label) VALUES ({label})").on(
        'label -> label
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit connection =>
      SQL("DELETE FROM task WHERE id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }
}
