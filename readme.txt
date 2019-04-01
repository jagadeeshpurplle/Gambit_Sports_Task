Prerequisites:
1. selenium 3.14.0
2. java-client 6.0.0
3. testNG 6.14.3


all you need to know is below : 

1. import this project to eclipse
2. inside of `src/test/java` you can find 3 packages names `common`,`pages` and `test_scenarios`
	`common` : inside of this you can find `Common_methods.java` file which containing some methods which uses across the project level
	`pages`  : it is a page object model, inside this i have created a java which a POM file
	`test_scenarios` : inside this you can find `GambitTestScenarios.java` file which is main file for this project, you have to run this via testNG
3. `envProperties`	 : inside of this you can find `env.properties` files which containing some external inputs that will be used across the project
4. `HTMLOutput` : containing the STMExtentReport.html file
5. `input` : containing the searchKeywors.txt file which having predefined search keywords which will passed to search input field
6. `screenshot` : takes and store screenshot whenever a test failure comes


How to run:
1. you can just open `GambitTestScenarios.java` class and run as testNG
2. Or simply you can right click on project and run as testNG