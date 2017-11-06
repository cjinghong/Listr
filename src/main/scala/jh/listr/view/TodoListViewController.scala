package jh.listr.view

import java.time.LocalDate
import java.util.Date
import javafx.scene.layout.{AnchorPane, BorderPane}

import jh.App
import jh.listr.model.{Importance, TodoItem}
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

		// For the list of items
//        private val vbox: VBox

	    private val tableView: TableView[TodoItem]
	) {

	private val flatRedColorHex = "D24D57"
	private var todoItemImportance: Importance = Importance.Low

	// Setup
	datePicker.setValue(LocalDate.now())    // Today date
	setLowPriority()                        // Low priority by default
	setupTableView()

	private def setupTableView(): Unit = {
		tableView.columnResizePolicy = TableView.ConstrainedResizePolicy
		tableView.selectionModel = null

		if (App.todoItems != null) {
			tableView.setItems(App.todoItems)

			val column = new TableColumn[TodoItem, TodoItem]()
			tableView.columns.add(column)

			// Todo: - Hide table header
//			val header = tableView.lookup("TableHeaderRow")

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
						cell.setGraphic(createTodoItemCell(item))
					}
				}
				cell
			}
		}

		def createTodoItemCell(item: TodoItem): AnchorPane = {
			val resource = getClass.getResourceAsStream("TodoItem.fxml")
			val loader = new FXMLLoader(null, NoDependencyResolver)
			loader.load(resource)
			val controller = loader.getController[TodoItemController#Controller]()
			controller.setTodoItem(item)
			return loader.getRoot[AnchorPane]
		}

	}

	def setLowPriority(): Unit = {
		println("Low priority item")
		selectPriorityButton(lowPriorityButton)
		todoItemImportance = Importance.Low
	}

	def setMediumPriority(): Unit = {
		println("Medium priority item")
		selectPriorityButton(mediumPriorityButton)
		todoItemImportance = Importance.Medium
	}

	def setHighPriority(): Unit = {
		println("High priority item")
		selectPriorityButton(highPriorityButton)
		todoItemImportance = Importance.High
	}

	def addTodoItem(): Unit = {
		val title = titleTextField.text.value
		val date = datePicker.getValue
		val newItem = new TodoItem(title, date, todoItemImportance)
		App.todoItems.add(newItem)
	}

	private def selectPriorityButton(button: Button): Unit = {
		// Remove styling for all buttons
		lowPriorityButton.setStyle("")
		mediumPriorityButton.setStyle("")
		highPriorityButton.setStyle("")

		val style = "-fx-background-color: " + flatRedColorHex + "; " +
					"-fx-text-fill: white"

		// Style the selected button
		button.setStyle(style)
	}

}
