package jh.listr.util

import jh.listr.model.TodoItem
import scalikejdbc._

trait Database {
	val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

	val dbURL = "jdbc:derby:myDB;create=true;"
	// initialize JDBC driver & connection pool
	Class.forName(derbyDriverClassname)

	ConnectionPool.singleton(dbURL, "me", "mine")

	// ad-hoc session provider on the REPL
	implicit val session: AutoSession = AutoSession
}

object Database extends Database {

	/** Initializes all the available databases */
	def setupDB(): Unit = {
		if (!hasDBInitialize)
			TodoItem.initializeTable()
	}

	private def hasDBInitialize: Boolean = {
		DB getTable "todo" match {
			case Some(x) => true
			case None => false
		}
	}
}
