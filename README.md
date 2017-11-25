# Listr

_The modern todo list application with a simple design._\
![Listr Screenshot](./ListrScreenshot.png)

## Main Features

There are 3 main screens for this application:

1. List view
   * Quickly add a todo item to the list
   * Shows a list of todo item (sorted by date ascending)
   * Check and uncheck a todo item
2. Timeline
   * Enables the user to visualize what todo next
   * Shows a horizontal scrolling screen with circles, each circle indicates a todo item
   * The first circle always shows the first item on today's list
   * The second circle shows the next day, etc.
3. Settings
   * Able to choose from a list of selected themes.

## Group Report

### Application of Concepts in Application

* A **_Has-A_** relationship is implemented in our application by containing a reference to an object inside another object. An example of this is when a **TodoCircle** is contained inside a **TimelineCardController**.
* An **_Is-A_** relationship is implemented in our application by implementing the use of inheritance. An example of this is when an **TodoCircle** object extends **StackPane**.
* **_Inheritance_** is implemented in the class **TodoCircle**. **TodoCircle** extends the class **StackPane**.
* **_Composition_** is implemented in object **App**. An example of this is that the **App** object contains a **RootMenuController**, and they cannot outlive the **App** object, which means that the **RootMenuController** class cannot outlive the **App** object.
* **_Method Overriding_** is implemented in the class **TodoItem**. An example of this is that the implementation of the _equals_ method of the **Object** class is overridden by that inside the **TodoItem** class, to compare between 2 TodoItems.
* **_Polymorphism_** is implemented in the class **TodoItem**. _Method overloading_ is used in the constructor to enable the **TodoItem** to be initialized with different parameters. _Method overriding_ is used when the _equals_ method of the **Object** class is overridden by the **TodoItem** class. 
* **_Encapsulation_** is implemented in the **TodoListViewController**. **TodoListViewController** contains the private variables such as **titleTextField** and **datePicker**, which ensures that nothing else outside of the class can make changes to it.
* **_Abstract Classes_** are not implemented inside our application.
* **_Object Casting_** is implemented in the **TodoItem** class. For example, the _obj_ variable inside the _equals()_ method is cast to an instance of _TodoItem_.

### Problems Encountered, and Solutions

* The first problem we encountered was lack of understanding of the Scala libraries. To overcome this, we read up on the documentation that was provided by the Scala developers, and also consulted **StackOverflow**.
* The second problem we encountered was code conflict. To overcome this, we used GitHub to solve code merge conflicts, and also used GitHub's pull request feature to properly merge code.

### Evaluation of Strengths and Weaknesses

The first strength of our application is the modularization. Components such as controllers are handled by their very own files, and data models are also handled by a different files. The second strength of our application is the clean GUI. The GUI only contains the essentials, and stuff such as the settings icon are located at the left part of the screen.

The weakness of our application is the presence of code redundancy. For example, the setting of priority inside **TodoListViewController** is handled by three methods, _setLowPriority_, _setMediumPriority_, and _setHighPriority_, one for each button. It is best to only use 1 method to set the priority of a **TodoItem**, by connecting all 3 buttons to the same method. Another weakness is the inconsistency when it comes to binding properties to UI components. Some properties are binded both ways, and have unexpected behaviour. Our temporary fix is to just use the _onChange()_ callback on the property. But this becomes a consistency problem, because we ended up having a mixture of binding (<==>) and _onChange()_ all over the place.

### Members Contribution

* CHAN JING HONG (20%)
* MARK ANTHONY MIDIN (17%)
* TEOH ZHAN TAO (17%)
* SHUM KA POH (17%)
* DARIUS LAW HONG SHENG (17%)
* WONG KENG SAM (12%)
