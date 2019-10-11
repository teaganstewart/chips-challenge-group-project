**BIG CHONK TODO:**<br>
<br>
Finish level 2, maybe make a level 3? Definitely need to update all the hints since they cant be the same<br>
Recnplay change speed playback<br>
Recnplay keypress updates, with the controls shown in the info panel<br>
Josh's gitlab bugs<br>
David's file loader bug<br>
joptiondialog pop up for level completion with a prompt to show replay or not<br>
Title screen (with maybe a level select if we're feeling fancy otherwise just Start/Resume)<br>
Recnplay tests (Josh is currently doing this)<br>
<br>
**Running in IntelliJ** <br>
<br>
To import the Project through IntelliJ (just in case we forget or have problems): <br>
<br>
1.) Create a New Project from source control (git). Use the HTTPS URL <br>
2.) Select Java JDK and version 8<br>
3.) Add JUnit 5.4 to class path<br>
4.) Mark src folder blue in the modules window<br>
<br>
To Open the IntelliJ Project in Eclipse: <br>
<br>
1.) Open Projects form file system. Select "Directory" and choose the gitlab folder <br>
2.) Right click on the project folder, go to build path, add libraries. Add JUnit <br>
3.) Start the game by running nz.ac.vuw.ecs.swen225.a3.application.Main from Eclipse <br>
<br>
**Turning on Javadoc Errors in Eclipse**<br>
->right click on the project folder<br>
->build path->configure build path<br>
->Java Compiler<br>
->Javadoc<br>
->set everything drop down box to Error<br>