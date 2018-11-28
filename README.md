# CST2335_FinalProject

CST2335 Graphical Interface Programming
Project Assignment
Due: See Brightspace for due dates

Overview
This is a group project for groups of 4 members (all in the same lab section). If you would like to be assigned a group, send an email to your lab instructor.  If you have chosen partners yourself, then please email the names of the group to your lab instructor.  You must work in the group to which you are assigned.  Use your assigned group number WITH your names in labeling submissions.  Be sure to work through the Group Activity Worksheet together to exchange contact information amongst your group (Algonquin student email addresses at a minimum). You should also determine who would be working on what part of the project. Your group may choose a name for your group if you like.  All work must be the work of the group members and ONLY the group members:
•	If a member submits plagiarized work, the whole group will be charged.
o	Double-check each other’s work to ensure sources are cited within program comments.
•	Each person will be responsible for their own part of the project and graded on this separately
•	A portion of the grade will come from an evaluation of your participation as a group member
Purpose:
The Project is assigned to give you experience in: 
•	Developing software in a group environment. 
•	Dividing workload to meet deadlines. 
•	Designing modular software that allows for that division. 
•	Learning from the work of others 

Requirements:
Here is a list of the requirements for the final project:
1.	Each Activity must have a ListView to present items. Selecting an item from the ListView must show detailed information about the item selected.
2.	Each activity must have at least 1 progress bar and at least 1 button.
3.	Each activity must have at least 1 edit text with appropriate text input method and at least 1 Toast, Snackbar, and custom dialog notification.
4.	The software must have 1 different activity written by each person in your group. The activity must be accessible by selecting a graphical icon from a Toolbar.
5.	Each Activity must use a fragment somewhere in its graphical interface.
6.	Each activity must have a help menu item that displays a dialog with the author’s name, Activity version number, and instructions for how to use the interface.
7.	There must be at least 1 other language supported by your Activity. If you are not bilingual, then you must support both British and American English (words like colour, color, neighbour, neighbor, etc). If you know a language other than English, then you can support that language in your application and don’t need to support American English.
8.	The items listed in the ListView must be stored by the application so that appear the next time the application is launched. The user must be able to add and delete items, which would then also be stored in a database.
9.	Each activity must use an AsyncTask to retrieve data from an http server.
10.	Each activity must provide a summary of the data stored.
11.	All activities must be integrated into a single working application, on a single device or emulator.
12.	The interfaces must look professional, with GUI elements properly laid out and aligned. 
13.	The functions and variables you write must be properly documented using JavaDoc comments.

Milestones:
Bonus marks will be awarded for displaying correct functionality by the following dates:
Milestone # and date	Requirements implemented #	Bonus Marks available
#1 – Thursday Nov 15, 2018	1, 2, 3, 11, 13	1
#2 – Thursday Nov 22, 2018	4, 5, 6, 7, 9, 11, 13
1
#3 – Thursday Nov 29, 2018	8, 4, 7, 10, 11, 12, 13
1



Beginning Steps
•	Create a new GitHub repository from one of the group members’ accounts. That group member must then invite the other group members to contribute. This is done by clicking on the “Settings” tab in Github, then click “Collaborators” on the left side menu, and search the group member names to add them to the project. Other team members should then clone that project to their computer and start making branches for their work. You will not be able to integrate your work if you do not start by first cloning the project!
•	As early as possible:
o	Decide who will work on which application.
o	Determine the additional tasks and decide who will take on each, for example: Technical Lead, Action Bar, Project Management and Communication Lead, Code Custodian, Documentation, Test Plan Integrator, Integration Tester, and any others you can determine
o	Discuss and document a code-freeze date for the group project, I recommend 72 hours before the actual due date so final code files can be merged into the project in preparation for upload to Blackboard.
•	Attempt to write your own code on your own branch and then merge that branch often (after each requirement is finished). Don’t try to merge the code only on the last week.
Grading Guide
•	Grading in 3 parts
•	Group activity worksheet (5%)
o	By Oct 26 – Please fill in the Excel file “CST2335_FinalProject_Team_Activity_Workbook.xlxs” and submit on Brightspace, under Final Project. Everyone in the group should submit the file. 
•	Each student is graded on his or her application separately (85%) 
o	Week of Nov. 15th – demonstrate the Milestone 1 requirements for bonus
o	Week of Nov. 22nd – demonstrate the Milestone 2 requirements for bonus
o	Week of Nov. 29th – demonstrate the Milestone 3 requirements for bonus
o	Week of Dec 6th – Project Demonstration: during the weekly lab sessions. Arrange a single submission of the group deliverable by one of the group members on behalf of the entire group. This should be submitted on Blackboard under the link for Final Project. 
•	Each student is graded on his or her team participation (10%)
o	based on the average of your team members peer review
o	Note: If you do not submit an individual Self and Peer form your self-evaluation becomes zero. If a team member does not submit their self and peer form, the other team members will not be penalized.


The Applications
Each of the applications (as they are intended) requires similar programming techniques.  Each application takes information from the user, and stores it in a database.  Each application also provides functionality to summarize or analyze the whole body of data entered into the application.  Beyond that you are free to get creative.

Food Nutrition Database
•	The user can search food for the calories and fat content of certain food. Use https://developer.edamam.com/ and sign up for a free API key for the Food Database Lookup. For example use: https://api.edamam.com/api/food-database/parser?app_id=e5bc806d&app_key=5f7521ffeefe491b936cea6271e13d3d&ingr=” followed by the food you are looking for, like “ingr=apple” to search apples.
•	The user can get the results of a search and save it to a list of “favourites”.
•	From a list of favourites, the user can select an item to quickly retrieve the information. From the details page, the user can delete the item from the favourites page.
•	From the favourites page, the user can add a Tag string to a food.
•	The user can get statistics on each tag counting the total calories, average calories, max and min calories.
 

CBC News reader
•	Create an application that reads news stories from the CBC website: https://www.cbc.ca/cmlink/rss-world.
•	There should be a list of Titles that are found in the document. Clicking on a title should load the rest of the news article, and a link to the article. Clicking on the link should go to a web page Intent with the link text sent in the Intent.
•	There should be a button on the article to save this article to your device for later viewing.
•	Your application should generate statistics on the number of articles saved, average, max and min word count.
 
Movie Information
•	This application tracks information about movies. The user can search for movie titles and get information back. Use: http://www.omdbapi.com/?apikey=6c9862c2&r=xml to query information. You should add “&t=” followed by the movie title to search for. You will need to use the function URLEncoder.encode( aString, "UTF-8") to change the title to URL encoded strings. URLs can’t have spaces in them so the encode() function will change “The Matrix” to “The+Matrix”.
•	The information stored includes movie title, year, rating, runtime, main actors, plot, and URL of the movie poster.
•	The Movie poster should be saved locally to the device for later viewing.
•	The user can save a movie description to the device for later viewing, or can delete a saved movie.
•	The saved movies should appear in a list where the user can choose to view details on a details page.
•	The user can view statistics on the shortest, longest, and average movie run time and year of the movies.

OCTranspo Bus Route App
•	The user can query the OCTranspo web servers for bus route information. The previously queried bus routes should be displayed in a listview where the user can select. The can also add, or remove bus stop numbers which they want to get information about, which gets stored in a database. The bus stop number for Algonquin College (Baseline) is #3017. You can see other station numbers here: http://www.octranspo.com/images/files/maps/system_map/systemmap.pdf
•	Connect to the web server to retrieve the bus routes through that station number, and display it in a list.
•	The user can then select a bus route from the list, and view:
o	The trip destination
o	The bus’s latitude and longitude
o	The bus’s gps speed
o	The trip start time
o	The adjustedScheduleTime (how late it is)
•	The api documentation is hosted here: http://www.octranspo.com/developers/documentation and you will need to use the functions: GetRouteSummaryForStop, and GetNextTripsForStop. Use the appId: 223eb5c3 and applicationID: ab27db5b435b8c8819ffb8095328e775. For example, getting the route summary for stop #3017 would be:
https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=3050 . The next trips for the bus #95 from stop #3017 would be:
https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=3017&routeNo=95
•	The user can see statistics on the average adjustedScheduleTime
