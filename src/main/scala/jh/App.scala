package jh

import java.util.Date
import javafx.scene.layout._
import javafx.{scene => jfxs}

import jh.listr.model.{Importance, TodoItem}
import jh.listr.util.Database
import jh.listr.view.TodoItemEditController

import scala.util.{Failure, Success}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.BooleanProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}
import scalafx.scene.image.Image
import scalafx.stage.{Modality, Stage}
import scalafxml.core.{FXMLLoader, NoDependencyResolver}

object App extends JFXApp {

	// The id of the Node that is currently at the center
	private val ID_TODOLIST = "todolist"
	private val ID_TIMELINE = "timeline"
	private val ID_SETTINGS = "settings"

	Database.setupDB()

	/** The data as an observable list of TodoItems */
	val todoItems = new ObservableBuffer[TodoItem]()
	todoItems ++= TodoItem.getAllTodoItems

	/**
	  * settingsVal, settingsDuration, ap_font stores the user settings
	  * passed from SettingsViewController
	  * settingsVal is used to store toggle switch information to initialize
	  * the correct toggle switch position when user returns to settings view
	  *
	  * settingsDuration stores the delete period chosen in int, which is then
	  * used to set the del_period combobox default value
	  *
	  * selectedFontIndex is the index of the selected font from SettingsViewController
	  */
	var settingsVal = false
	var settingsDuration = 0
	var selectedFontIndex = 0

	private var currentlyDisplayingView: String = ""
	//default css location
	private val cssResource = getClass.getResource("./listr/view/style.css").toExternalForm

	private val rootResource = getClass.getResourceAsStream("./listr/view/RootMenu.fxml")
	private val loader = new FXMLLoader(null, NoDependencyResolver)
	loader.load(rootResource)
	private val roots = loader.getRoot[BorderPane]

	stage = new PrimaryStage {
		minWidth = 700
		minHeight = 300
		title = "Listr"
		scene = new Scene {
			stylesheets ++= List(cssResource)
			root = roots
		}
		resizable = true
	}

	// Set appicon
	stage.getIcons.add(new Image(getClass.getResourceAsStream("appicon.png")))

	// TodoListView is the first to be shown by default
	showToDoList()

	/** Saves the given todo item to database, then updates the observable list. */
	def addItem(item: TodoItem): Unit = {
		item.save() match {
			case Success(x) =>
				if (!todoItems.contains(item)) {
					todoItems.add(item)
					todoItems.sort({ (a, b) =>
						a.dueDate.value.getTime < b.dueDate.value.getTime
					})
				}
			case Failure(x) =>
				println("Failed to add todo item")
		}
	}

	def deleteItem(item: TodoItem): Unit = {
		item.delete() match {
			case Success(x) =>
				todoItems.remove(item)
				todoItems.sort({ (a, b) =>
					a.dueDate.value.getTime < b.dueDate.value.getTime
				})
			case Failure(x) =>
				println("Failed to remove todo item")
		}
	}

	/** used to change the theme(css file) of the App
	  *
	  * @param cssLocate the css theme location in string passed from SettingsViewController
	  */
	def changeStylesheets(cssLocate: String): Unit = {
		var cssResource = getClass.getResource(cssLocate).toExternalForm
		stage.getScene.getStylesheets.remove(cssResource)
		stage.getScene.getStylesheets.add(cssResource)
	}

	/** Change main app font
	  *
	  * @param font receives font family in the form of string
	  */
	def changeFont(font: String): Unit = {
		stage.getScene.getRoot.setStyle(s"-fx-font-family: $font")
	}

	// ---------
	// MAIN TABS
	// ---------

	/** Re-loads the current view. */
	def refreshCurrentView(): Unit = {
		val currentlyDisplayedView = this.roots.center.value.id.value

		currentlyDisplayedView match {
			case ID_TODOLIST => showToDoList(true)
			case ID_TIMELINE => showTimeline(true)
			case ID_SETTINGS => showSettings(true)
		}
	}

	/** Displays the TodoListView
	  *
	  * @param forced If the view is already presented, calling the function does nothing.
	  *               This is a flag that determines if the view needs to be reloaded not matter what.
	  *               Default value is `false`.
	  */
	def showToDoList(forced: Boolean = false): Unit = {

		var currentDisplayedView = ""
		Option(this.roots.center.value) match {
			case None => println("There are NO view displayed yet")
			case Some(node) => currentDisplayedView = node.id.value
		}

		// Only loads if it is not already showing, or when forced is true
		if (currentDisplayedView != ID_TODOLIST || forced) {
			val resource = getClass.getResourceAsStream("./listr/view/TodoListView.fxml")
			val loader = new FXMLLoader(null, NoDependencyResolver)
			loader.load(resource)

			val todoListView = loader.getRoot[jfxs.Parent]
			todoListView.id = ID_TODOLIST

			this.roots.setCenter(todoListView)
		}

	}

	/** Displays the TimelineView
	  *
	  * @param forced If the view is already presented, calling the function does nothing.
	  *               This is a flag that determines if the view needs to be reloaded not matter what.
	  *               Default value is `false`.
	  */
	def showTimeline(forced: Boolean = false): Unit = {
		var currentDisplayedView = ""
		Option(this.roots.center.value) match {
			case None => println("There are NO view displayed yet")
			case Some(node) => currentDisplayedView = node.id.value
		}

		// Only loads if it is not already showing
		if (currentDisplayedView != ID_TIMELINE || forced) {
			val resource = getClass.getResourceAsStream("./listr/view/TimelineView.fxml")
			val loader = new FXMLLoader(null, NoDependencyResolver)
			loader.load(resource)

			val timelineView = loader.getRoot[jfxs.Parent]
			timelineView.id = ID_TIMELINE

			this.roots.setCenter(timelineView)
		}
	}

	/** Displays the SettingsView
	  *
	  * @param forced If the view is already presented, calling the function does nothing.
	  *               This is a flag that determines if the view needs to be reloaded not matter what.
	  *               Default value is `false`.
	  */
	def showSettings(forced: Boolean = false): Unit = {
		var currentDisplayedView = ""
		Option(this.roots.center.value) match {
			case None => println("There are NO view displayed yet")
			case Some(node) => currentDisplayedView = node.id.value
		}

		// Only loads if it is not already showing
		if (currentDisplayedView != ID_SETTINGS || forced) {
			val resource = getClass.getResourceAsStream("./listr/view/SettingsView.fxml")
			val loader = new FXMLLoader(null, NoDependencyResolver)
			loader.load(resource)
			val settingsView = loader.getRoot[jfxs.Parent]
			settingsView.id = ID_SETTINGS

			this.roots.setCenter(settingsView)
		}
	}

	// -------------
	// MODAL DIALOGS
	// -------------

	/** Show a popup dialog for when the title is empty */
	def showNoTitleError(): Unit = {
		new Alert(AlertType.Warning, "Please enter a title.") {
			headerText = "Empty title"
			initOwner(stage)
			initModality(Modality.ApplicationModal)
		}.showAndWait()
	}

	/** Show a confirmation popup dialog for when there a duplicating TodoItem already existed. */
	def showDuplicatingItemError(item: TodoItem): Unit = {
		val result = new Alert(AlertType.Warning, "A duplicating TodoItem already exist.") {
			headerText = "Item exists"
			initOwner(stage)
			initModality(Modality.ApplicationModal)
		}.showAndWait()
	}

	def showEditDialog(todoItem: TodoItem): Unit = {
		val resource = getClass.getResourceAsStream("./listr/view/TodoItemEditDialog.fxml")
		val loader = new FXMLLoader(null, NoDependencyResolver)
		loader.load(resource);
		val roots2 = loader.getRoot[jfxs.Parent]
		val control = loader.getController[TodoItemEditController#Controller]
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
}






