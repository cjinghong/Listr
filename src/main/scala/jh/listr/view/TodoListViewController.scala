package jh.listr.view

import java.time.LocalDate
import java.util.Date

import jh.App
import jh.listr.model.{Importance, TodoItem}
import jh.listr.model.Importance.Importance
import jh.listr.util.DateUtil._

import javafx.{scene => jfxs}

import scalafx.beans.value.ObservableValue
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.stage.Modality
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import scalafxml.core.macros.sfxml

@sfxml
class TodoListViewController(
	    private val titleTextField: TextField,
	    private val datePicker: DatePicker,
	    private val lowPriorityButton: Button,
	    private val mediumPriorityButton: Button,
	    private val highPriorityButton: Button,
	    private val addButton: Button,

	    private val listView: ListView[TodoItem]
	) {

	private val flatRedColorHex = "#D24D57"
	private var todoItemImportance: Importance = Importance.Low

	// Setup
	datePicker.setValue(LocalDate.now())    // Today date
	setLowPriority()                        // Low priority by default
	setupTableView()

	// Setup the table view, along with its custom cell factory
	private def setupTableView(): Unit = {
		listView.style =
			"-fx-table-cell-border-color: transparent;" +
			"-fx-focus-color: transparent;" +
			"-fx-selection-bar: transparent; " +
			"-fx-selection-bar-non-focused: transparent;"

		// Detect when an item is selected
		// TODO: - Shows popup dialog to edit the TodoItem

		if (App.todoItems != null) {
			listView.setItems(App.todoItems)

			listView.cellFactory = { (_) =>
				val cell = new ListCell[TodoItem]() {
					contentDisplay = ContentDisplay.GraphicOnly
				}

				cell.item.onChange({ (_: ObservableValue[TodoItem, TodoItem], _: TodoItem, newItem: TodoItem) =>
					if (newItem == null) {
						cell.setGraphic(null)
					} else {
						val resource = getClass.getResourceAsStream("TodoItemView.fxml")
						val loader = new FXMLLoader(null, NoDependencyResolver)
						loader.load(resource)
						val controller = loader.getController[TodoItemController#Controller]()
						controller.setTodoItem(newItem)
						val node = loader.getRoot[jfxs.Parent]
						cell.setGraphic(node)
					}
				})

				cell.onMouseClicked = { _ =>
					val todoItem = cell.item.value
					App.showEditDialog(todoItem)
				}

				cell
			}
		}
	}

	// Setting item priority. Each function is connected to the different buttons

	/** Set the TodoItem's `importance` to low priority */
	def setLowPriority(): Unit = {
		selectPriorityButton(lowPriorityButton)
		todoItemImportance = Importance.Low
	}
	/** Set the TodoItem's `importance` to medium priority */
	def setMediumPriority(): Unit = {
		selectPriorityButton(mediumPriorityButton)
		todoItemImportance = Importance.Medium
	}
	/** Set the TodoItem's `importance` to high priority */
	def setHighPriority(): Unit = {
		selectPriorityButton(highPriorityButton)
		todoItemImportance = Importance.High
	}

	/** Adds and save the new TodoItem, only if there is a title */
	def addTodoItem(): Unit = {
		val title = titleTextField.text.value
		val date = datePicker.getValue.toDate

		val newItem = new TodoItem(title, date, todoItemImportance)

		if (title.isEmpty) {
			App.showNoTitleError()
		} else if (App.todoItems.contains(newItem)) {
			App.showDuplicatingItemError(newItem)
		} else {
			App.addItem(newItem)
		}
	}

	private def selectPriorityButton(button: Button): Unit = {
		// Remove styling for all buttons
		lowPriorityButton.setStyle("")
		mediumPriorityButton.setStyle("")
		highPriorityButton.setStyle("")

		// Style the selected button
		button.style = "-fx-background-color: " + flatRedColorHex + "; " +
			"-fx-text-fill: white"
	}

}
