package jh.listr.model

import scalafx.animation.{FadeTransition, TranslateTransition}
import scalafx.scene.Node
import scalafx.util.Duration
import javafx.scene.input.MouseEvent
import javafx.{scene => jfxs}

import scalafx.beans.property.BooleanProperty

trait SwipeDownToggle { this: Node =>

	/** Determines if the node is in the toggle state ON or not */
	var toggleOnProperty = BooleanProperty(true)

	private var maxOpacity = 1.0
	private val MIN_OPACITY = 0.5

	/** The maximum translate required to trigger the action */
	private val MAX_TRANSLATE = 100

	private var orgSceneY, orgTranslateY = 0.0
	private var translationComplete, fadeComplete = true

	this.onMousePressed = (e: MouseEvent) => {
		// Only sets value when animation is completed
		if (translationComplete && fadeComplete) {
			orgSceneY = e.getSceneY
			orgTranslateY = e.getSource.asInstanceOf[jfxs.Node].getTranslateY
		}
	}

	this.onMouseDragged = (e: MouseEvent) => {
		val offsetY = e.getSceneY - orgSceneY
		val newTranslateY = orgTranslateY + offsetY

		// Only can swipe down
		if (newTranslateY >= 0 && newTranslateY <= MAX_TRANSLATE) {
			e.getSource.asInstanceOf[jfxs.Node].setTranslateY(newTranslateY)

			val opacity = (1 - (Math.abs(offsetY) / (this.scene.value.getWidth / 2))) * maxOpacity

			this.opacity =
				if (opacity < MIN_OPACITY) MIN_OPACITY
				else opacity
		}
	}

	this.onMouseReleased = (e: MouseEvent) => {
		returnToOriginalPosition()
	}

	this.onMouseDragExited = (e: MouseEvent) => {
		returnToOriginalPosition()
	}

	private def returnToOriginalPosition(): Unit = {

		// Toggles
		toggleOnProperty.value = !toggleOnProperty.value

		// If it should be toggled on, change max opacity to 1,
		// else, change it to 0.5
		if (toggleOnProperty.value) { maxOpacity = 1 }
		else { maxOpacity = 0.5 }


		// Only animate again when previous animation is completed.
		if (translationComplete && fadeComplete) {
			val translateTransition = new TranslateTransition(Duration(400), this)
			translateTransition.toY = orgTranslateY
			translateTransition.onFinished = { _ =>
				translationComplete = true
			}
			translationComplete = false
			translateTransition.play()

			/** Fade transition to animate the bubble back to normal opacity */
			val fadeTransition = new FadeTransition(Duration(400), this)
			fadeTransition.fromValue = this.opacity.value
			fadeTransition.toValue = maxOpacity
			fadeTransition.onFinished = { _ =>
				fadeComplete = true
			}
			fadeComplete = false
			fadeTransition.play()
		}
	}

}
