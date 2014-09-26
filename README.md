kboyle_assignment1
==================
Readme CMPUT 301 Assignment 1

Kieran Boyle


The intention of my Android application is to allow users to enter a series of tasks that they want to accomplish. This application was developed using Java in an eclipse environment. The test will be ran on a Samsung Galaxy Nexus 7. Upon boot of my application the user is presented with a screen that asks the user to enter a todo item. I use the action bar to branch into my other activities which include email, usage summary, and and archive tab. Unfortuantely a lot of the heavy lifting is done by the main activity. The responsibilities of main include keeping track of the content in the todo list, the archive list and assembling the email list. I send a lot of the data back between activities using intents and the sharedPreferences. As you might imagine the lack of object oriented programming leaves something to be desired.


In order to say that an item is completed the user simply clicks the item in the todo list. This functionality is brought about by the use of listview that implements simple_list_item_multiple_choice. To archive and delete items the user simply  holds the selected item until the context menu appears and they are presented with the option to delete or archive an item. To archive the item is sent to an archive list that waits until the archive activity is called. When that happens I simply send the list via intent and loafd the list in the archive activity. Once in the archive activity I simply load the list and present it to the user. When I want to delete or dearchive an item I use shared preferences to  save the value and go back to the main activity so that the changes will take place. In the main activity I do the deletes and updates to the todo/archive list. 


In my email I first combine both the archive and todo lists in the main. I then send this master list via intent to the email activity where I have a checklist of all the current items in the application. I then allow the user to check off all the values that they want to email. Once that is done a run a loop concatenating these values into a string then I send said string to the email application via intent. The user is then prompted to fill out the res of the email

Finally for my statistics I calculate them in my main and send them via intent into the statistics activity. Due to a bunch of hitches in development there is no savfeature for my data other than saved preferencses. 


 Resources I used:

http://www.youtube.com/watch?v=tLKkLusLWOE sept 19 2014 
http://wptrafficanalyzer.in/blog/deleting-selected-items-from-listview-in-android/ sept 19 2014 
http://stackoverflow.com/questions/2558591/remove-listview-items-in-android  sept 19 2014 
http://stackoverflow.com/questions/3772751/get-row-position-in-oncreatecontextmenu  sept 19 2014 
http://stackoverflow.com/questions/4487911/the-constructor-intentnew-view-onclicklistener-classdrinkstwitter-is-un sept 21 2014 
http://stackoverflow.com/questions/22182888/actionbar-up-button-destroys-parent-activity-back-does-not sept 22 2014 
http://stackoverflow.com/questions/20491764/returning-data-result-to-parent-activity-using-intents sept 23 2014
http://www.youtube.com/watch?v=1xQSK772rPs sept 23 2014

http://wptrafficanalyzer.in/blog/deleting-selected-items-from-listview-in-android/ sept 26 2014 (last used) 
http://theopentutorials.com/tutorials/android/listview/android-multiple-selection-listview/ sept 26 2014 (last used)
http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/ sept 26 2014(last_used)
http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application  sept 26 2014

Abran Hindles various class notes and videos:
https://eclass.srv.ualberta.ca/course/view.php?id=19130 various times
Student Picker for Android: 1 Storyboarding an Android Application http://youtu.be/5PPD0ncJU1g 
Student Picker for Android: 2 Laying out the UI screens http://youtu.be/VKVYUXNuDDg
Student Picker for Android: 3 Developing and Testing the Model http://youtu.be/k9ZNbsc0Qgo
Student Picker for Android: 4 Navigation between Activities http://youtu.be/fxjIA4HIruU
Student Picker for Android: 5 Controllers and adding students http://youtu.be/uLnoI7mbuEo



Licences:
//   kboyle_todo a simple todo list application
//   Copyright 2014 Kieran Boyle
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.