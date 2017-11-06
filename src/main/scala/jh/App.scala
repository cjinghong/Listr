package jh

import java.time.LocalDate
import javafx.scene.layout._

import jh.listr.model.{Importance, TodoItem}

import scala.io.Source
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafxml.core.{FXMLLoader, NoDependencyResolver}

object App extends JFXApp {

	/**
	  * The data as an observable list of TodoItems
	  * */
	val todoItems = new ObservableBuffer[TodoItem]()
	todoItems += new TodoItem("Do assignments!", LocalDate.now(), Importance.High)
	todoItems += new TodoItem("Wash the floor", LocalDate.now(), Importance.High)
	todoItems += new TodoItem("Kill myself", LocalDate.now(), Importance.High)
	todoItems += new TodoItem("Something", LocalDate.now(), Importance.High)

	val rootResource = getClass.getResourceAsStream("./listr/view/RootMenu.fxml")
	val loader = new FXMLLoader(null, NoDependencyResolver)
	loader.load(rootResource)
	val roots = loader.getRoot[BorderPane]

	stage = new PrimaryStage {
		minWidth = 700
		minHeight = 300
		title = "Listr"
		scene = new Scene(roots)
	}
	stage.getIcons.add(new Image(getClass.getResourceAsStream("appIcon.png")))

	showToDoList()

	def showToDoList(): Unit = {
		val resource = getClass.getResourceAsStream("./listr/view/TodoListView.fxml")
		val loader = new FXMLLoader(null, NoDependencyResolver)
		loader.load(resource)
		val todoListView = loader.getRoot[AnchorPane]
		this.roots.setCenter(todoListView)
	}

	def showTimeline(): Unit = {
		this.roots.setCenter(null)
	}

	def showSettings(): Unit = {
		this.roots.setCenter(null)
	}
}
