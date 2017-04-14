# Google Charts
Everything you need to know about using Google Charts in this and project or any other Angular 2 project.
### Helpful Resources:
* [Google Charts Website:](https://developers.google.com/chart/) A good introduction into what google charts can do.
This resource is also super helpful when finding what types of charts you can use, what options they contain, and how to structure your data.
* [ng2-google-charts:](https://github.com/gmazzamuto/ng2-google-charts) The github page for the library we are using to use Google Charts in Angular.
Use this when trying to figure out how to implement google charts in Angular 2.
We **highly** recomend viewing the demo, and the corresponding source code.

## Adding Gooogle Charts to your project
Because we are using gradle to handle all of our building, using outside libraries gets a whole lot more interesting!
Fortunatly, many hours of our hard work have culiminated in a single line of very easily copy pasteable code for you.
We are using a library called [ng2-google-charts](https://github.com/gmazzamuto/ng2-google-charts) to integrate google charts with Angular 2.
To add *ng2-google-charts* to your project, locate `package.json` in the client directory.
When you are there, copy the following line of code into `dependencies` section.

```ng2-google-charts": "^2.1.0" ```

Next, clean, and rebuild gradle, and you should be good to go!

## How we use Google Charts in our project
We use google charts to display garden wide data on the admin page.
![Admin Page](/Graphics/AdminPage.png)

138.197.120.23
