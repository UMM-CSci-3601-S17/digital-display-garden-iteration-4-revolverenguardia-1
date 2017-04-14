# Digital Display Garden
[![Build Status](https://travis-ci.org/UMM-CSci-3601-S17/digital-display-garden-iteration-3-revolverenguardia.svg?branch=master)](https://travis-ci.org/UMM-CSci-3601-S17/digital-display-garden-iteration-3-revolverenguardia)
Software Design S2017, Iteration 3, Team _Revolver en Guardia_ 

This repository is a fork from [Iteration 2 , Team _Grimaldi_](https://github.com/UMM-CSci-3601-S17/digital-display-garden-iteration-2-grimaldi.git)
which is a fork from [Iteration 1 , Team _Claude Arabo_](https://github.com/UMM-CSci-3601-S17/digital-display-garden-iteration-1-claudearabo).
and substantially incorporates code from [Iteration 2 , Team _Oman Anwar_](https://github.com/UMM-CSci-3601-S17/digital-display-garden-iteration-2-omaranwar.git)


## Setup

Cloning the project inside IntelliJ:

- When prompted to create a new IntelliJ project, select **yes**.
- Select **import project from existing model** and select **Gradle.**
- Make sure **Use default Gradle wrapper** is selected.
- Click **Finish.**

:fire: If IntelliJ ever prompts you to compile typescript files into
javascript **say no!**. Doing this will confuse webpack and break the client
side of your project during build. No permanent damage will be done, but it's
pretty annoying to deal with.

When you load the project on a new machine, tell Gradle to Refresh linked Gradle projects.

## Running your project
> Run the server  
> Run the client  

If you have data in the database from a previous version it would be
best to drop() the test database. In order to populate the database
* Run the Server and Client
* go to localhost:9000/admin (or whatever ip/port the client is running on)
* Import the data set from the Excel spreadsheet (.xlsx)
* the liveUploadId will be set to the latest data set imported
* liveUploadId determines which set of data to refer to within the database.

## Attempted or Completed Stories

#### Improve Admin
* Allow admin users to update the spreadsheet without losing previously obtained visitor data
* Export more data
  * Comments: Basic Plant info, Plant Comments, Comment Date
  * Plant Metadata: Likes, Dislikes, Comment Counts, Page Views
  * Bed Metadata: Bed page Views, QR Scans

#### Visual Aids for Visitor Traffic &mdash; Google Charts
* Provide visual aids for visitor traffic data
* Track all forms of visitor traffic
  * Track the number of times a flower page is visited
  * Track the number of times a bed page is visited

#### Improve Front-end &mdash; visitor's website
* Allow visitors to filter plants by common name &mdash; complete 
* Provide visitors with social media links &mdash; complete
* Improve the overall UIs &mdash; additional improvements can be made

#### Refactoring and Stories to polish
Change all ObjectIds that are generated with the sole purpose of dates into some sort of Date object
  This has become a problem, because eveyr object must have a unique ObjectId.
  When the database is patched, all of the objects are duplicated and therefore have new ObjectIds
  Which means all old data's dates (under the new uploadId) becomes new ObjectIds.



#### Finished by previous iteration groups
**Grimaldi**
* Leave comments about plants
* Allow a visitor to rate a plant
* Display counts of likes and dislikes on visitor website
* Display counts of comments on visitor website
* Generate QR Codes

**Oman Anwar**
* Present plant data in a list
* Filter by bed number
* Display specific plant information
* Gather metadata from plant information pages; obtain likes, dislikes, page-views, and comments from plant pages, and store them on the server

**Claude Arabo**
* Import Excel Spreadsheet to Database

## Documentation
* [Excel File Requirements](Documentation/ExcelFileRequirements.md)  
* [Excel Parser Documentation](Documentation/ExcelParser.md) 



## Libraries used
### Client-Side
* **Angular 2**
* **Jasmine** and **Karma** 

### Server-Side
* **Java** 
* **Spark** is used for the server operations
* **JUnit** is used for testing
* **Apache** is used for importing and exporting data in .xlsx format
* **zxing** is used for generating QR codes (supports reading them if we want) 
* **joda** is used for making an unique LiveUploadID

### Source of branches at end of Iteration 3 

* **database-patching**
* **export-more-data**
* **fix-footer** Fixed footer content and style in the very beginning of the iteration
* **GoogleCharts**
* **google-charts(actual-data)**
* **home-page**
* **measure-qrscans**
* **merge-all**
* **merge-grimanwar** Merged Grimaldi's back-end and OmanAnwar's front-end
* **testing-PlantController**
* **testing-admin**
* **testing-client**
* **visitor-interface-overhaul / vistor-interface-overhaul** Added the ability to filter by common name and improved the UIs of the visitor's website 
* **master**

## Resources

- [Bootstrap Components][bootstrap]
- [Mongo's Java Drivers (Mongo JDBC)][mongo-jdbc]
- [What _is_ Angular 2... why TypeScript?][angular-2]
- [What _is_ webpack...?][whats-webpack]
- [Testing Angular 2 with Karma/Jasmine][angular2-karma-jasmine]

[angular-2]: https://www.infoq.com/articles/Angular2-TypeScript-High-Level-Overview
[angular2-karma-jasmine]: http://twofuckingdevelopers.com/2016/01/testing-angular-2-with-karma-and-jasmine/
[labtasks]: LABTASKS.md
[travis]: https://travis-ci.org/
[whats-webpack]: https://webpack.github.io/docs/what-is-webpack.html
[bootstrap]: https://getbootstrap.com/components/ 
[mongo-jdbc]: https://docs.mongodb.com/ecosystem/drivers/java/ 
