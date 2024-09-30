# Lab 3: Maven Project, Checkstyle, and JSON file reading

In today's lab, your group will be cleaning up and adding to a small program
which translates country names into different languages.

**To get started, have one member of your group make a fork of this
repo on GitHub and add each other member as a collaborator. This
will allow you to make and review pull requests from each other
during the lab.**

## Part 1: Maven Project
As discussed earlier in the term, build systems are used to help programmers
compile, run, and test their programs. In this lab, we'll be using Maven to
organize and build our program.

### Project Structure
Take a second to get familiar with how this project is structured. Maven projects
are structured in a specific way, which isn't too different from what we have seen so far:
- pom.xml: the Project Object Model (POM) xml file which contains the project configuration (IntelliJ automatically detects this file and runs maven commands to build the project for us)
- src/main/java: directory containing the source files for our project (note that it is automatically marked as the Sources Root)
- src/main/resources: directory containing any resource files our project needs
- src/test/java: directory containing the test files for our project (note that it is automatically marked as the Test Sources Root)

Note: sometimes you may need to right-click the `pom.xml` file and select `Maven -> Reload project' if you
don't see the sources root folders marked automatically.

Note: For those interested, you can read the Maven documentation to learn about the `mvn` commands (like Git, Maven has a command line interface)
but for our purposes you can use the IntelliJ interface to run and test your code as you have been doing.
See the Quercus page for this lab for links to additional resources.

#### Managing Dependencies
The main reason we want to introduce Maven today is that we'll be using an
external library to help us read JSON data. The `pom.xml` file allows us to specify
what external dependencies our project requires. If you open
`pom.xml`, you will see a section like below which indicates that our project depends
on the `junit` and `json` libraries. so maven is important when you have to read the json file yourself 

```xml
<dependencies>
    <!-- https://mvnrepository.com/artifact/org.json/json -->
    <dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20240303</version>
    </dependency>

    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```
Maven will automatically download these libraries for us. In the Project Tool Window,
you can see the `External Libraries` listed.

When adding dependencies, you can use websites like https://mvnrepository.com to find
what entry you need to add to your `pom.xml` in order to add the dependency to your project.

For example, the `json` dependency was obtained from https://mvnrepository.com/artifact/org.json/json/20240303.
You'll also see that you can select other formats for the dependency information if you are
using a different build system, such as Gradle.

### Running your code and tests
Take a second to ensure that you are able to run everything that you need to in the project.

- Confirm that you can run `Main.java`.

- Confirm that you can run the tests by right-clicking the `src/test/java` folder and selecting "Run 'All Tests'".
  Only one test should initially be passing.

Note: these won't do much yet, but we want to know things are working.

If either of these aren't able to run, ask for help from those around you or your TA.

Note: sometimes you may need to right-click the `pom.xml` file and select `Maven -> Reload project' 

## Part 2: CheckStyle
Before we get to writing any new code, we're going to take the time to tidy up
the existing code. Just like we used PyTA to help us write Python code according to established
coding standards, we'll be using CheckStyle in the same way today.

CheckStyle requires a configuration file to be specified which indicates which rules to use.
By default, the plugin is bundled with configurations used by Sun and Google.
For this lab, we'll be using our own configuration in `mystyle.xml`.

Note: If you didn't install the CheckStyle plugin when installing IntelliJ, do so now in the Settings menu.

- Configure CheckStyle to use `mystyle.xml`.

You can do this by going to Settings and then finding the Tools submenu.
Find CheckStyle and click on it. Press the + button under Configuration File to add
a new configuration file.
For Description, put something like "CSC207 Checks".
Select use local file and browse to find `mystyle.xml`. Press Next and then Finish.
Then check the box beside your newly added configuration to make it active, click Apply, and then OK.

Now, errors reported by CheckStyle should show up just like any other code inspections
in IntelliJ. The CheckStyle checks will start with "CheckStyle:", so they are
easy to distinguish from other checks like those by SonarLint or IntelliJ.

Note: we have also added TODOs for the CheckStyle errors you should be fixing.

Note: you can open the CheckStyle Tool Window (its icon looks like a pencil;
if you don't see it, you can go to 'View -> Tool Windows -> CheckStyle') to toggle
which configuration is being used and manually rerun CheckStyle. In this view, you can
also run CheckStyle on all project files to quickly get a summary of all errors.

- **As a group, work through each source file and fix all CheckStyle warnings.**

You can divide up work by having each member of your group pick a file to work on;
feel free to work in pairs or all together, but make sure you are each getting the
chance to go through the process of making a branch, cleaning up a file, pushing the branch
to GitHub, and making a pull request for the team to review and merge.

**Important: make sure to only commit and push changes to the source files, as
pushing other files may lead to git conflicts when others try to pull your changes.**

After your group is done, make sure all members have pulled the latest version of the code and have no CheckStyle errors.

## Part 3: Translating Country Names
We are now ready to start writing some new code for the program!

- **The overall goal is to complete all "TODO Task" comments in the project files. You can use the TODO Tool Window to see all remaining
  TODOs at a glance.**

The final version of the program should see the `Main.main` method behave
as indicated in its Javadoc: prompting the user to select a country and then a language to have that country's name be displayed in.

To achieve this, you will need to complete code in:
- `Main.java`
- `CountryCodeConverter.java`
- `LanguageCodeConverter.java`
- `JSONTranslator.java`

In lab today, you will do some smaller coding tasks which will get you ready
to complete this code. If you have time, you can get started on writing some of that code too.

### JSON

JSON provides a way to represent objects: key-value pairs, in a simple format. Since the format is standard,
languages like Java have libraries which allow us to conveniently read JSON data. // there will be libraries used to for json data and 
it will in type of dictionary data 

Note: There is some nice discussion of various options for parsing JSON at
https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java. Today, we'll use a specific library in Java,
but you are welcome to explore others in future which offer richer functionality and performance.

#### A first example

Consider the following JSON data:

```json
[{"key1" : "string1a", "key2":21}, {"key1" : "string1b", "key2":22}] 
```

The `[]` denote an array and `{}` denote an object. These can be nested, but
for this example we have a single array with two objects in it. Each object
contains two key-value pairs.

There is an example of how to extract the various data provided in `JSONDemo.java`.

- **Read `src/main/java/org/translation/JSONDemo.java` and complete the TODO.**
  - this task is short, so everyone in your group should complete it. 

#### Understanding our data
Open the `src/main/resources/sample.json` file. It contains the JSON data we'll be
using for this program. It is like the small example we just saw, but contains more entries.
Each object will have exactly the same keys as listed below:
- "id": an integer uniquely identifying each object
- "alpha2": two character string country code (note: we won't use this key at all) no use of alpha too
- "alpha3": three character string country code
- 35 other keys corresponding to 35 different languages; each key is a language code

Note that this data is all in terms of country and language codes, which aren't likely
that intuitive for a user of our program. To help our users, we'll also use data from
`src/main/resources/country-codes.txt` and `src/main/resources/language-codes.txt`.

These files are tab (`"\t"`) delimited and contain mappings between English
names of countries/languages and their corresponding codes.
Using these files, we can create methods to convert from the codes
to the full country or language names. These will be what
we actually display to the user of our program.

- Open each of these files to check what kind of information they
contain. Notice that each of the `.txt` files contains a header row at the top, which should be ignored when reading the data.
- **Each member of your team should pick a random country and language code
from `sample.json` and determine the corresponding country and langauge.**

- **For the language code you picked, complete the TODO in `JSONTranslationExample.java`.**
  - each group member should make a pull request with their completed code. 

Extra: you can also add a test case to `JSONTranslationExampleTest.java` to verify the correctness of your code;
see the existing test for the syntax of Junit.

### Generalizing our code
- **Based on the code you just wrote, implement the TODO for the `JSONTranslationExample.getCountryNameTranslation` method.
You will need to use the JSON data to obtain the country name given the country code and the language code.**
  - feel free to work as a group on this part or work independently then compare your solutions by making pull requests. 

### The rest of the TODOs!
You now have the basics working, so it is just a matter of writing the real code in:
- `Main.java`
- `CountryCodeConverter.java`
- `LanguageCodeConverter.java`
- `JSONTranslator.java`

If you have time left in lab, your group can discuss the remaining TODOs to decide who
wants to work on each part. You don't have to complete it all, but we encourage you
to aim to make one more pull request during lab for some part of the code (for example,
pick one "TODO Task" and complete it).

Note that:
- the code for the two "code converter" files is very similar
- the code for `JSONTranslator.java` is similar to the `JSONTranslationExample.java` code, but
  you are creating instance variables to store the JSON data in a convenient way to then implement the
  `Translator` methods.
- `Main.java` makes use of the other classes
- there are some very minimal tests corresponding to most tasks which you can run to double check your work.

### Extra
If your group does finish the tasks, we recommend:
- discussing the design of the program, such as what you would do differently if building the program from scratch,
  what seemed good about the design, or did the design make it easy for your group to divide up work?
- going back to last week's lab if your group did not have a chance to make any pull requests,
  as it would be good to get more practice collaboratively developing code.
- working through some of the Practice Java Quizzes on Quercus
- getting a start on the homework for this week

## Data Sources
The data used here comes from:
- https://stefangabos.github.io/world_countries/
- https://en.wikipedia.org/wiki/List_of_ISO_639_language_codes
  - Chinese (Taiwan): zh-tw added from https://www.andiamo.co.uk/resources/iso-language-codes/
- https://www.iban.com/country-codes
