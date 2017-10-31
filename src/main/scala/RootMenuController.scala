import scalafx.animation.TranslateTransition
import scalafx.event.ActionEvent
import scalafx.scene.image.ImageView
import scalafx.scene.layout.StackPane
import scalafx.util.Duration
import scalafxml.core.macros.sfxml

@sfxml
class RootMenuController(
                        private val todoListStackPane: StackPane,
                        private val timelineStackPane: StackPane,
                        private val settingsStackPane: StackPane,
                        private val trianglePointer: ImageView
                        ) {

	def showTodoView(action: ActionEvent): Unit = {
		println("Show todo view")
		App.showToDoList()

		val transition = new TranslateTransition(Duration(300), trianglePointer)
		transition.toY = 0
		transition.play()
	}

	def showTimelineView(action: ActionEvent): Unit = {
		println("Show timeline view")
		App.showTimeline()

		val transition = new TranslateTransition(Duration(300), trianglePointer)
		transition.toY = 113 - 33
		transition.play()
	}

	def showSettingsView(action: ActionEvent): Unit = {
		println("Show settings view")
		App.showSettings()

		val transition = new TranslateTransition(Duration(300), trianglePointer)
		transition.toY = 193 - 33
		transition.play()
	}

}
