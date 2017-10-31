import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Parent, Scene}
import scalafxml.core.{FXMLLoader, FXMLView, NoDependencyResolver}
import javafx.scene.layout._

import scalafx.scene.image.Image

object App extends JFXApp {

	val rootResource = getClass.getResourceAsStream("RootMenu.fxml")
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
		val resource = getClass.getResourceAsStream("TodoList/TodoListView.fxml")
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
