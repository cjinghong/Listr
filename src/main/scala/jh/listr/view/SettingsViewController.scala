package jh.listr.view

import jh.App

import scalafx.scene.control._
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{AnchorPane, HBox}
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.animation.{FillTransition, TranslateTransition}
import scalafx.beans.property.{BooleanProperty, IntegerProperty}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle}
import scalafx.stage.FileChooser.ExtensionFilter
import scalafx.util.Duration
import scalafx.stage.FileChooser

@sfxml
class SettingsViewController(
                              // General tab variable
                              private val gn_hbox: HBox,
                              private val gn_anchor: AnchorPane,
                              private val gn_tog_circle: Circle,
                              private val gn_tog_rec: Rectangle,
                              private val gn_browse: Button,
                              private val gn_del_period: ComboBox[String],
                              private val gn_save_bar: TextField,
                              private val gn_save: Button,
                              private val gn_cancel: Button,

                              // Appearance tab variable
                              private val ap_hbox: HBox,
                              private val ap_cancel: Button,
                              private val ap_font: ComboBox[String],
                              private val ap_save: Button,
                              private val ap_t_red: Button,
                              private val ap_t_blue: Button,
                              private val ap_t_gray: Button,
                              private val ap_t_black: Button
                            ) {
  var changed = false
  var toggle: Boolean = App.settingsVal
  var saveLocal = ""
  val tempToggle: Boolean = toggle
  val tempDuration: Int = App.settingsDuration
  val tempFont: Int = App.ap_font

  /**Initialize the settings view, populates combobox value to default saved value,
    * set toggle switch to correct position and hides save confirmation box
    *
    * delPeriod is fixed and used to populate gn_del_period combobox
    * fonts retrieves the font families and used to populate ap_font combobox
    * condition(toggle) is used to set the default position when settings view is shown
    *
    * Default position is stored in App.settingsVal
    */
  private def settingsInitialize(): Unit = {
    val delPeriod = List("2 Days", "3 Days", "1 Week")
    val fonts = scalafx.scene.text.Font.families
    //populates the delete period combobox
    for(x <- delPeriod) {
      gn_del_period.+=(x)
    }
    //populates the fonts combobox
    for(x <- fonts) {
      ap_font.+=(x)
    }
    //hides the save confirmation buttons
    ap_hbox.visible_=(false)
    gn_hbox.visible_=(false)

    if(toggle) {
      toggleSwitchAnimate(100, Color.White, Color.LightGreen, gn_tog_circle, gn_tog_rec, 20)
      gn_del_period.disable = false
      gn_del_period.getSelectionModel.select(App.settingsDuration)
    }

    ap_font.getSelectionModel.select(App.ap_font)
  }

  //initialize call
  settingsInitialize()

  /**Used to animate toggle switch
    *
    * @param duration how fast should the toggle animation be
    * @param x color before fillanimation
    * @param y color transitioned to when fillanimation is complete
    * @param circ the toggle switch, will move depending on the position given
    * @param rect the toggleswitch backround, fill animation is acted on this
    * @param pos move the circ to x position
    */
  private def toggleSwitchAnimate(duration: Int, x: Color, y: Color, circ: Circle, rect: Rectangle, pos: Int): Unit = {
    val transition = new TranslateTransition(Duration(duration), circ)
    transition.toX = pos
    transition.play()
    val fill = new FillTransition(Duration(duration), rect, x, y)
    fill.play()
  }

  /**Called everytime user toggles the switch
    * perform the following function
    * 1) toggle switch animation
    * 2) disable or enable associated combobox
    *
    * toggle is the condition to animate the switch
    * if toggle is true, it means the switch is on and thus
    * will display the off animation; gn_del_period combobox is disabled
    *
    * if toggle = false, animate on animation; gn_del_period is enabled
    *
    * the toggle info is then passed and stored in main App to set the
    * toggle switch to correct position when user switch view and switch back
    * settings
    */
  private def toggleSwitch(): Unit = {
    if (toggle) {
      toggleSwitchAnimate(100, Color.LightGreen, Color.White, gn_tog_circle, gn_tog_rec, 0)
      toggle <== false
      gn_del_period.disable = true
    } else {
      toggleSwitchAnimate(100, Color.White, Color.LightGreen, gn_tog_circle, gn_tog_rec, 20)
      toggle <== true
      gn_del_period.disable = false
    }
    App.settingsVal = toggle
  }

  /**Displays or hides the tab's respective save buttons when user made changes to settings
    *
    * @param tab condition to display the correct save buttons for respective tab
    *            0 == General Tab save buttons
    *            1 == Appearance Tab save buttons
    */
  private def showSave(tab: Int): Unit = {
    if (tab == 0) {
      gn_hbox.visible = true
    } else {
      ap_hbox.visible = true
    }
  }

  private def cancelSettings(tab: Int): Unit = {
    if (tab == 0) {
      toggle = !tempToggle
      gn_del_period.getSelectionModel.select(tempDuration)
      gn_hbox.visible = false
      toggleSwitch()
    } else {
      ap_font.getSelectionModel.select(tempFont)
      ap_hbox.visible = false
    }
  }

  /**Used to save settings
    *
    * @param check condition to check which settings user is saving
    *              0 == General settings
    *              1 == Appearance settings
    */
  private def saveSettings(check: Int): Unit = {
    if (check == 0) {
      //saves the toggle position to App
      App.settingsVal = toggle
      //saves user selected del duration to App
      App.settingsDuration = gn_del_period.getSelectionModel.getSelectedIndex
      //hides the save buttons
      gn_hbox.visible = false
    } else {
      //saves user selected font
      App.ap_font = ap_font.getSelectionModel.getSelectedIndex
      //hides the save buttons
      ap_hbox.visible = false
    }
    //Display save confirmed dialog box
    new Alert(AlertType.Information) {
      initOwner(App.stage)
      title = "Settings"
      headerText = "Settings saved"
    }.showAndWait()
  }

  /**
    * Used to export database to chosen folder
    */
  private def saveLocation(): Unit = {
    val save = new FileChooser()
    save.setTitle("Export Database Location")
    save.getExtensionFilters().add(new ExtensionFilter("Database File", "*.db"))

    val saveWindow = save.showSaveDialog(
      ownerWindow = App.stage
    )
    //checks if user selected a valid save path
    //if valid, stores the export location
    if (saveWindow != null) {
      saveLocal = saveWindow.getAbsolutePath
      gn_save_bar.text = saveLocal
    }
  }

  ap_font
  //everytime user selects or changes the delete period, call showSave() to display save buttons
  gn_del_period.valueProperty().onChange(showSave(0))
  //everytime user selects or changes the font, call showSave() to display save buttons
  ap_font.valueProperty().onChange(showSave(1))

  //when user clicks on the theme button, pass the css theme file location to changeStylesheets()
  //in App to change the theme
  ap_t_red.onMouseClicked = (e:MouseEvent) => App.changeStylesheets("./listr/view/red_theme.css")
  ap_t_blue.onMouseClicked = (e:MouseEvent) => App.changeStylesheets("./listr/view/blue_theme.css")
  ap_t_gray.onMouseClicked = (e:MouseEvent) => App.changeStylesheets("./listr/view/gray_theme.css")
  ap_t_black.onMouseClicked = (e:MouseEvent) => App.changeStylesheets("./listr/view/style.css")

  //When user made changes to settings and save buttons show up, user can click on the save button
  //and the saveSettings() will be called to save the settings
  gn_save.onMouseClicked = (e:MouseEvent) => saveSettings(0)
  ap_save.onMouseClicked = (e:MouseEvent) => saveSettings(1)

  gn_cancel.onMouseClicked = (e:MouseEvent) => cancelSettings(0)
  ap_cancel.onMouseClicked = (e:MouseEvent) => cancelSettings(1)
  //used to animate the toggle switch by calling toggleSwitch() everytime user clicks on the toggle switch
  gn_tog_rec.onMouseClicked =(e:MouseEvent) => toggleSwitch()
  gn_tog_circle.onMouseClicked =(e:MouseEvent) => toggleSwitch()

  //a save window will be called when user either clicks on browse button or the address bar
  gn_browse.onMouseClicked = (e:MouseEvent) => saveLocation()
  gn_save_bar.onMouseClicked = (e:MouseEvent) => saveLocation()
}
