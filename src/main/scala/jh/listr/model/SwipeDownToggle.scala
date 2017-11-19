package jh.listr.model

import scalafx.animation.{FadeTransition, TranslateTransition}
import scalafx.scene.Node
import scalafx.util.Duration
import javafx.scene.input.MouseEvent
import javafx.{scene => jfxs}

import scalafx.beans.property.BooleanProperty

/** A trait that allows the Node to be swiped down to toggle a state of ON and OFF
  *
  * Any Node that extends this trait will automatically get the ability to
  * toggle on and off by swiping down on the node.
  *
  */
trait SwipeDownToggle { this: Node =>

	/** Determines if the node is in the toggle state ON or OFF.
	  *
	  * If the node is in the ON state, the opacity will be 100% (Fully visible)
	  * If the node is in the OFF state, the opacity will be 50% (Semi-transparent)
	  *
	  * @example
	  * You can bind to this property to observe when the toggle is on or off
	  * {{{val state <== toggleOnProperty}}}
	  *
	  * Or you can set the value of toggleOnProperty to trigger an update in the UI
	  * {{{toggleOnProperty.value = false}}}
	  */
	var toggleOnProperty = BooleanProperty(true)
	toggleOnProperty.onChange({ (_, _, _) =>
		returnToOriginalPosition()
	})

	private val MAX_OPACITY = 1.0
	private val MIN_OPACITY = 0.5

	/** The maximum Y translation that is allowed */
	private val MAX_Y_TRANSLATE = 100

	/** The original position that is recorded when the mouse clicks. */
	private var orgSceneY, orgTranslateY = 0.0

	/** Determines if the animation for translation and fading is completed. Refer to [[returnToOriginalPosition()]] */
	private var translationComplete, fadeComplete = true

	// ------------
	// MOUSE EVENTS
	// ------------
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
		if (newTranslateY >= 0 && newTranslateY <= MAX_Y_TRANSLATE) {
			e.getSource.asInstanceOf[jfxs.Node].setTranslateY(newTranslateY)

			// Only animates opacity when its in the "ON" state.
			if (toggleOnProperty.value) {
				val opacity = 1 - (Math.abs(offsetY) / (this.scene.value.getWidth / 2))
				this.opacity =
					if (opacity < MIN_OPACITY) MIN_OPACITY
					else opacity
			}
		}
	}

	this.onMouseReleased = (e: MouseEvent) => {
		val offsetY = e.getSceneY - orgSceneY
		val newTranslateY = orgTranslateY + offsetY
		if (newTranslateY >= MAX_Y_TRANSLATE) {
			toggleOnProperty.value = !toggleOnProperty.value
		} else {
			returnToOriginalPosition()
		}
	}

	this.onMouseDragExited = (e: MouseEvent) => {
		val offsetY = e.getSceneY - orgSceneY
		val newTranslateY = orgTranslateY + offsetY
		if (newTranslateY >= MAX_Y_TRANSLATE) {
			toggleOnProperty.value = !toggleOnProperty.value
		} else {
			returnToOriginalPosition()
		}
	}

	/** Returns the node back to its original position (the initial position when the mouse clicked) */
	private def returnToOriginalPosition(): Unit = {
		// If it should be toggled on, change max opacity to 1,
		// else, change it to 0.5
		var opacity = 0.0
		if (toggleOnProperty.value) { opacity = MAX_OPACITY }
		else { opacity = MIN_OPACITY }

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
			 fadeTransition.toValue = opacity
			 fadeTransition.onFinished = { _ =>
				 fadeComplete = true
			 }
			 fadeComplete = false
			 fadeTransition.play()
		}
	}

}
