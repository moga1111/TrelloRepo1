# Trello Rest Api Sample
Sample Api project that access Trello to retrieve and modify cards

You can JUnit-test the project by
* Importing it as Intellj Idea project.
* Command line by running the included jar.
  
## Importing as Intellj project is straigt forward step
* Import into idea.
* Provide the required system parameters.
* Run > Edit Configs > VM options (replace with yours).
* Run module
  
  ![alt text](http://pichoster.net/images/2017/01/16/Screenshotfrom2017-01-1612-46-13.png "Example")
  
## Using command line requires using the jar file

* After downloading TrellRest.jar, run the command line with 5 parameters.
* Copy/paste and replace the whole [..] with your own:
  
  ````shell
  > java -jar -ea -Dkey=[key] -Dtoken=[token] -DboardId=[boardId] -DcardId=[cardId] -DidList=[idList] TrelloRest.jar
  ````
  
