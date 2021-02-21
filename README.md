HComTest
===============

.. contents::

Problem Statement
------------

This assessment consists in writing an automation code that would give us a csv file as an output with the top 3 costliest hotels in Bangalore that offer free Wifi from the hotels.com website.

System Requirements:
------------

Please make that below softwares are installed in the machine:
1. Java v1.8
2. Firefox - Latest
3. GeckoDriver - Configured in the path

Test Cases:
------------

Here is the quick look of the test case:
1. Initiate FireFox browser (please setup geckoDriver before this step)
2. Navigate to HCom HomePage
3. Load the test data from TestData.xlsx file
4. Pass test data to the test case method
5. Test case will enter destination, dates, number of rooms and click Search
6. Search page will be loaded with search results
7. Filter for 'Free Wifi' and check if it is filtered
8. Sort Price by descending order and check if it is sorted 
9. Fetch top-3 or top-5 hotels from the results
10.Write the results to a csv file in the current folder.


Steps to Run:
------------

Please Follow below steps to run the project locally:

1. By using IDE like Eclipse or IntelliJ
	- Import it as Maven project
	- Let the libraries download 
	- Run the Project as 'TestNG Test'
	
