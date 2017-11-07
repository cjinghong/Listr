package jh.listr.view

import java.time.LocalDate
import java.util.Date
import javafx.beans.property.ReadOnlyObjectPropertyBase
import javafx.scene.layout.{AnchorPane, BorderPane}
import javax.swing.event.{ChangeEvent, ChangeListener}

import jh.App
import jh.listr.model.{Importance, ListrTheme, TodoItem}
import jh.listr.model.Importance.Importance

import scalafx.beans.property.ObjectProperty
import scalafx.beans.value.ObservableValue
import scalafx.scene.Node
import scalafx.scene.control._
import scalafx.scene.layout.{Pane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
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

	    private val tableView: TableView[TodoItem]
	) {

	private val flatRedColorHex = "D24D57"
	private var todoItemImportance: Importance = Importance.Low

	// Setup
	datePicker.setValue(LocalDate.now())    // Today date
	setLowPriority()                        // Low priority by default
	setupTableView()

	// Setup the table view, along with its custom cell factory
	private def setupTableView(): Unit = {
		tableView.columnResizePolicy = TableView.ConstrainedResizePolicy
		tableView.style =
			"-fx-table-cell-border-color: transparent;" +
			"-fx-focus-color: transparent;" +
			"-fx-selection-bar: transparent; " +
			"-fx-selection-bar-non-focused: transparent;"

		// Detect when an item is selected
		// TODO: - Shows popup dialog to edit the TodoItem
		tableView.getSelectionModel.selectedItemProperty().addListener( { (item) =>
			val todoItem = item.asInstanceOf[ReadOnlyObjectPropertyBase[TodoItem]].getValue
			println(todoItem.title.value + " selected")
		})

		if (App.todoItems != null) {
			tableView.setItems(App.todoItems)

			val column = new TableColumn[TodoItem, TodoItem]()
			tableView.columns.add(column)

			column.sortable = false
			column.text = ""
			column.cellValueFactory = { _.value.asProperty }
			column.cellFactory = { _ =>
				val cell = new TableCell[TodoItem, TodoItem]()
				val btn = new Button()

				cell.contentDisplay = ContentDisplay.GraphicOnly
				cell.item.onChange { (_, _, item) =>
					if (item == null) {
						cell.setGraphic(null)
					} else {
						val resource = getClass.getResourceAsStream("TodoItemView.fxml")
						val loader = new FXMLLoader(null, NoDependencyResolver)
						loader.load(resource)
						val controller = loader.getController[TodoItemController#Controller]()
						controller.setTodoItem(item)
						cell.setGraphic(loader.getRoot[AnchorPane])
					}
				}
				cell
			}
		}
	}

	// Setting item priority. Each function is connected to the different buttons

	/** Set the TodoItem's `importance` to low priority */
	def setLowPriority(): Unit = {
		println("Low priority item")
		selectPriorityButton(lowPriorityButton)
		todoItemImportance = Importance.Low
	}
	/** Set the TodoItem's `importance` to medium priority */
	def setMediumPriority(): Unit = {
		println("Medium priority item")
		selectPriorityButton(mediumPriorityButton)
		todoItemImportance = Importance.Medium
	}
	/** Set the TodoItem's `importance` to high priority */
	def setHighPriority(): Unit = {
		println("High priority item")
		selectPriorityButton(highPriorityButton)
		todoItemImportance = Importance.High
	}

	/** Adds and save the new TodoItem, only if there is a title */
	def addTodoItem(): Unit = {
		val title = titleTextField.text.value
		val date = datePicker.getValue

		if (title.isEmpty) {
			// TODO: - Highlight the title text field (RED)
		} else {
			val newItem = new TodoItem(title, date, todoItemImportance)
			App.todoItems.add(newItem)
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
