import scalafx.event.ActionEvent
import scalafx.scene.image.ImageView
import scalafxml.core.macros.sfxml

@sfxml
class RootMenuController() {

	def showTodoView(action: ActionEvent): Unit = {
		println("Show todo view")
	}

	def showTimelineView(action: ActionEvent): Unit = {
		println("Show timeline view")
	}

	def showSettingsView(action: ActionEvent): Unit = {
		println("Show settings view")
	}

}
