# shop-infomation-app
An Utility to Display Active Shops Informations for searched date using Spring Boot, React, Bootstrap

## Server Setup
Goto the folder **spring-boot-server** and run **_gradle clean build_**

After installed all dependencies run **_gradle bootRun_**

Change port if required by **adding server.port={your_port}** to the file **_application.properties_**

Run **_gradle test_** to check unit test cases 

## UI Setup
Goto the folder **react-ui** and run **_npm install_** to install node_modules

After installed all node_modules goto **src/axios.js** file change _baseURL_ to server url

Run **_npm start_** to start the application

Goto the ui application running url in the browser and check

## Note
Folder **screen-shots** contains snaps taken from system for different scenarios of Server and UI, please go through it.

