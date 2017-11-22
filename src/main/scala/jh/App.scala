package jh

import java.util.Date
import javafx.scene.layout._
import javafx.{scene => jfxs}

import jh.listr.model.{Importance, TodoItem}
import jh.listr.view.TodoItemEditController

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}
import scalafx.scene.image.Image
import scalafx.stage.{Modality, Stage}
import scalafxml.core.{FXMLLoader, NoDependencyResolver}

object App extends JFXApp {

	/** The data as an observable list of TodoItems */
	val todoItems = new ObservableBuffer[TodoItem]()
	todoItems += new TodoItem("Do assignments!", new Date(1000), Importance.Low)
	todoItems += new TodoItem("Wash the floor", new Date(2000), Importance.Medium)
	todoItems += new TodoItem("Kill myself", new Date(3000), Importance.High)
	todoItems += new TodoItem("Something", new Date(4000), Importance.Low)
//	todoItems += new TodoItem("Do assignments! A duplicating TodoItem already exist. Are you sure you want to add it? A duplicating TodoItem already exist. Are you sure you want to add it?", new Date(1000), Importance.Low)

	private var currentlyDisplayingView: String = ""

	private val rootResource = getClass.getResourceAsStream("./listr/view/RootMenu.fxml")
	private val loader = new FXMLLoader(null, NoDependencyResolver)
	loader.load(rootResource)
	private val roots = loader.getRoot[BorderPane]

	stage = new PrimaryStage {
		minWidth = 700
		minHeight = 300
		title = "Listr"
		scene = new Scene(roots)
		resizable = true
	}

	// Set appicon
	stage.getIcons.add(new Image(getClass.getResourceAsStream("appicon.png")))

	// TodoListView is the first to be shown by default
	showToDoList()

	// todo - Save the todo item here!
	/** Adds a new TodoItem to the list. This also saves the TodoItem to the database. */
	def addItem(newItem: TodoItem): Unit = {
		todoItems.add(newItem)
		todoItems.sort({ (a,b) =>
			a.dueDate.value.getTime < b.dueDate.value.getTime
		})
	}

	// ---------
	// MAIN TABS
	// ---------

	/** Displays the TodoListView */
	def showToDoList(): Unit = {
		val identifier = "todolist"

		// Only loads if it is not already showing
		if (currentlyDisplayingView != identifier) {
			val resource = getClass.getResourceAsStream("./listr/view/TodoListView.fxml")
			val loader = new FXMLLoader(null, NoDependencyResolver)
			loader.load(resource)
			val todoListView = loader.getRoot[jfxs.Parent]

			this.roots.setCenter(todoListView)
			currentlyDisplayingView = identifier
		}
	}
	/** Displays the TimelineView */
	def showTimeline(): Unit = {
		val identifier = "timeline"

		// Only loads if it is not already showing
		if (currentlyDisplayingView != identifier) {
			val resource = getClass.getResourceAsStream("./listr/view/TimelineView.fxml")
			val loader = new FXMLLoader(null, NoDependencyResolver)
			loader.load(resource)
			val timelineView = loader.getRoot[jfxs.Parent]

			this.roots.setCenter(timelineView)
			currentlyDisplayingView = identifier
		}
	}
	/** Displays the SettingsView */
	def showSettings(): Unit = {
		val identifier = "settings"

		// Only loads if it is not already showing
		if (currentlyDisplayingView != identifier) {
			val settingsView = null

			this.roots.setCenter(settingsView)
			currentlyDisplayingView = identifier
		}
	}

	// -------------
	// MODAL DIALOGS
	// -------------

	/** Show a popup dialog for when the title is empty */
	def showNoTitleError(): Unit = {
		new Alert(AlertType.Warning, "Please enter a title."){
			headerText = "Empty title"
			initOwner(stage)
			initModality(Modality.ApplicationModal)
		}.showAndWait()
	}

	/** Show a confirmation popup dialog for when there a duplicating TodoItem already existed. */
	def showDuplicatingItemError(item: TodoItem): Unit = {
		val result = new Alert(AlertType.Confirmation, "A duplicating TodoItem already exist. Are you sure you want to add it?"){
			headerText = "Item exists"
			initOwner(stage)
			initModality(Modality.ApplicationModal)
		}.showAndWait()
		result match {
			case Some(ButtonType.OK) => addItem(item)
			case _ => println("Not adding item")
		}
	}

  def showEditDialog(todoItem: TodoItem): Unit ={
    val resource = getClass.getResourceAsStream("./listr/view/TodoItemEditDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[TodoItemEditController#Controller]
    System.out.print("open")
    val dialog = new Stage() {
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene {
        root = roots2
      }
    }
    control.setTodoItem(todoItem)
    control.dialogStage = dialog
    dialog.showAndWait()
  }

//	/** Sorts the list of todoItems ascending by date, and group them by completed first, then incomplete  */
//	def sortTodoItems(): Unit = {
//		var incompleteTodoItems = new ObservableBuffer[TodoItem]()
//		var completedTodoItems = new ObservableBuffer[TodoItem]()
//
//		for (item <- todoItems) {
//			if (item.completed.value) completedTodoItems.add(item)
//			else incompleteTodoItems.add(item)
//		}
//
//		incompleteTodoItems.sort({ (a, b) =>
//			a.dueDate.value.getTime < b.dueDate.value.getTime
//		})
//		completedTodoItems.sort({ (a, b) =>
//			a.dueDate.value.getTime < b.dueDate.value.getTime
//		})
//
//		todoItems.clear()
//		todoItems ++= incompleteTodoItems
//		todoItems ++= completedTodoItems
//	}
}






