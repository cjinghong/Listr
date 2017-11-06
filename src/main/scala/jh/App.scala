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
import javafx.scene.control.ScrollPane
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

	private val rootResource = getClass.getResourceAsStream("./listr/view/RootMenu.fxml")
	private val loader = new FXMLLoader(null, NoDependencyResolver)
	loader.load(rootResource)
	private val roots = loader.getRoot[BorderPane]

	stage = new PrimaryStage {
		minWidth = 700
		minHeight = 300
		title = "Listr"
		scene = new Scene(roots)
		resizable = false
	}

	// Set appicon
	stage.getIcons.add(new Image(getClass.getResourceAsStream("appIcon.png")))

	// TodoListView is the first to be shown by default
	showToDoList()

	/**
	  * Displays the TodoListView
	  * */
	def showToDoList(): Unit = {
		val resource = getClass.getResourceAsStream("./listr/view/TodoListView.fxml")
		val loader = new FXMLLoader(null, NoDependencyResolver)
		loader.load(resource)
		val todoListView = loader.getRoot[AnchorPane]
		this.roots.setCenter(todoListView)
	}
	/**
	  * Displays the TimelineView
	  * */
	def showTimeline(): Unit = {
		val resource = getClass.getResourceAsStream("./listr/view/TimelineView.fxml")
		val loader = new FXMLLoader(null, NoDependencyResolver)
		loader.load(resource)
		val timelineView = loader.getRoot[AnchorPane]
		this.roots.setCenter(timelineView)
	}
	/**
	  * Displays the SettingsView
	  * */
	def showSettings(): Unit = {
		this.roots.setCenter(null)
	}
}
