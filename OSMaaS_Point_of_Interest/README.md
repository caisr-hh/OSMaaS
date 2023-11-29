# Point of Interest


The Point of Interest (POI) Android Automotive app is a powerful tool designed to enhance the navigation experience for vehicle users within the Android Automotive platform. This application leverages OpenStreetMap data to provide a curated selection of Points of Interest (POIs) based on the user's current location. By harnessing data from an open map library (OpenStreetMap), the app offers a carefully curated selection of POIs based on the user's current location. This app covers a wide range of categories, allowing users to effortlessly explore various points of interest, including dining options, electric vehicle charging stations, recreational facilities, scenic spots, as well as cultural and historical sites. Users can also mark their favorite places for quick and convenient access. 

The repository is focused on managing and displaying points of interest within the specific application. This could be useful in various contexts like travel, navigation, and tourism using OpenStreetMap. It allows users to filter and view different categories of points of interest, such as restaurants, landmarks, recreational facilities, etc. The repository could contribute to the broader OpenStreetMap ecosystem by creating tools, applications, or services that utilize OpenStreetMap data in innovative ways.

There are key features both users seeking to maximize their experience and developers looking to extend the application's functionality.

Dynamic POI Categories: The app categorizes POIs into various sections, ensuring users can easily find a diverse range of destinations including restaurants, charging points, recreational facilities, natural attractions, cultural landmarks, and much more.

Seamless Integration with Android Automotive: The app seamlessly integrates with the Android Automotive platform, offering an intuitive and user-friendly interface that enhances the in-vehicle navigation experience.

Real-time Updates: Leveraging OpenStreetMap data, the app offers real-time updates on POIs, ensuring users have access to the latest information on nearby attractions and amenities.

Personalized Recommendations: Users can tailor their experience by saving and curating a list of their favorite places, creating a personalized travel itinerary.

## Repository structure
The Point of Interest Android Automotive application is structured into several key components that collectively form its high-level architecture. The objectbox-models module manages object-relational mapping, facilitating data storage and retrieval. It defines entities like "Category," "SubCategory," and "POIItem," each with specific properties and relationships, outlined in a JSON configuration file crucial for ObjectBox's data management. The src directory houses critical source code, including Kotlin files, XML layouts, images, and assets. Noteworthy components include "DummyHandler.kt" for populating the database with test data and various Kotlin files managing location permissions, core component initialization, and database setup. The res directory contains essential Android resources for the user interface and functionality, with specialized subdirectories catering to specific themes and screen orientations. Additionally, the assets folder stores the "POI.csv" file, containing details of points of interest, including the closest bus stop and bus details. These details are utilized and displayed in the POI detail screen.

The AndroidManifest.xml file configures the app, specifying required hardware features, permissions, and other essential settings. The build.gradle.kts script file handles build process configuration, while the 'keystore.properties.template' file provides a template for securely storing key-related information. This structured approach ensures efficient development, deployment, and maintenance of the application, encompassing data management, source code organization, resource handling, configuration, and security considerations. Additionally, the assets folder stores the "POI.csv" file, containing details of points of interest, including the closest bus stop and bus details. These details are utilized and displayed in the POI detail screen.


## Technology
The programming language used is Kotlin, which is a modern statically-typed programming language that runs on the Java Virtual Machine (JVM). Kotlin is often used for Android app development and is known for its concise syntax, enhanced safety features, and seamless interoperability with Java.

## Point of Interest Data
The dummy data module enriches the user experience by employing a collection of structured data, primarily sourced from the "POI.csv" file, containing points of interest relevant to the Android Automotive platform. The data primarily consists of a set of point of interest relevant to the Android Automotive platform. These include establishments such as restaurants, charging points, recreational facilities, natural landmarks, cultural sites, and favorite places. The data is structured in a hierarchical manner, with categories representing broader classifications, each containing subcategories and associated point of interest items.

The source of this data is exclusively the "POI.csv" file, which includes points of interest data for the 'Varberg' region. The CSV file allows for easy updation and modifications to the dataset. It includes categories like 'Bekvämligheter' (Amenities) and 'Mat & Dryck' (Food & Drink), with subcategories like 'Restauranger' (Restaurants) and 'Pubar och barer' (Pubs and Bars). Each POI item encompasses essential details including title, description, geographic coordinates (latitude and longitude), and a flag indicating user favoritism.

Developers can now update or modify the data by editing the "POI.csv" file directly. To extend the existing data structure, they can add new categories, subcategories, and corresponding points of interest items, ensuring adherence to the defined data models outlined in the code. This modification simplifies the process of managing and customizing the dataset.

In addition to the general points of interest, the "POI.csv" file also contains bus details, including information about the closest bus stop associated with each point of interest. This data is crucial for the POI detail screen, where users can access information about the nearby bus stop and relevant bus details. For real-world scenarios, it's recommended to integrate dynamic data sources or APIs to keep the information up-to-date and relevant, ensuring a more accurate representation of the surrounding environment.

## App technical overview
This section gives a high-level understanding of how different components of the project are organized and how they interact with each other.

objectbox-models - The ObjectBox models are used for object-relational mapping in Android applications. This module is responsible for handling data storage and retrieval. The JSON configuration file for ObjectBox file outlines the structure of data models in the ObjectBox database. It defines three entities: "Category" representing categories of points of interest (POIs) with properties like id, title, and iconRes, linked to "SubCategory" entities; "POIItem" representing individual POIs with properties including id, title, description, lat, lng, and isFavorite; and "SubCategory" with properties id and title, linked to "POIItem" entities. Each entity and property has a unique ID for internal database management. ModelVersion denotes the data model version, crucial for data operations. The file ensures backward compatibility with modelVersionParserMinimum. Overall, this JSON configuration file serves as a blueprint for ObjectBox to understand the structure of the data it manages. It defines the entities, their properties, relations, and other metadata necessary for database operations.

src - This is a critical directory in Android projects. It contains the source code for the application, including Java/Kotlin files, resources (like XML layout files and images), and other assets. Some of the components are described below.

assets – This folder contains the "POI.csv" file, serving as the primary data source for points of interest.

Dummydata - The DummyHandler.kt defines a DummyHandler class with a companion object containing a method initDummyData(). This method populates the data from POI.csv. It uses the ObjectBox database management system. The categories include amenities, food & drink, and various others. Each category contains subcategories, and each subcategory contains specific points of interest (POIItems). These POIItems have attributes like title, description, latitude, longitude, and a flag indicating whether they are marked as favorites. The data structure is organized using ObjectBox annotations, with Category, SubCategory, and POIItem being the entity classes. The Category entity has a one-to-many relationship with SubCategory, and SubCategory has a one-to-many relationship with POIItem. The code ensures that if the categoryBox (database) is empty, it will populate it with the predefined dummy data. This code is crucial for initializing the database with sample data for testing or demonstration purposes.

Kotlin files - Some of the other Kotlin files that form integral parts of the repository are the following. 'LocationPermissionScreen.kt' manages the acquisition and handling of location permissions. 'MainSession.kt' is the entry point, responsible for initializing core components. 'MyCarAppService.kt' handles background tasks specific to automotive features. 'ObjectBox.kt' is related to setting up the ObjectBox database. 'POIApplication.kt' is the application class, handling global initialization. 'PlaceDetailsScreen.kt' manages the UI and functionality for displaying detailed place information, 'PlaceListScreen.kt' deals with listing places with filtering capabilities, and 'SubCategoryListScreen.kt' handles subcategory displays within specific categories. These files collectively contribute to the app's functionality, encompassing location permissions, database management, UI screens, and automotive-related services.

res – This directory encompasses essential Android resources vital for the application's user interface and functionality. It includes directories such as 'drawable' for images, 'layout' for defining UI structures, and 'mipmap' for launcher icons tailored to different screen densities. Additionally, specialized directories like 'values-night' and 'values-land' cater to specific themes and screen orientations, ensuring a cohesive user experience. 'navigation' house XML files for managing app navigation, while 'values' holds various XML resources like colors and styles. Altogether, these resource directories are meticulously organized to accommodate diverse device configurations and deliver a seamless experience across a wide array of Android devices.

AndroidManifest.xml – The AndroidManifest.xml file is a crucial configuration file for the Point of Interest Android Automotive application. It contains vital information such as app components, required permissions, and more. The key components and settings within this file include the manifest declaration, features and permissions, application declaration, activity declaration, intent filter, metadata declaration, Car App service, metadata for automotive app description, and the minimum Car API level. These elements collectively define the behavior and characteristics of the application within the Android Automotive platform, ensuring seamless integration and functionality.

build.gradle.kts- This is a Kotlin DSL build script file. It contains configuration information for the build process, including dependencies, plugins, and other project settings. This file is crucial for building and configuring the project.

keystore.properties.template - The file serves as a template for storing sensitive information related to the app's signing key in Android development. It provides placeholders for key properties such as storePassword, keyPassword, keyAlias, storeFile.


## Installation and Setup

This section provides detailed, step-by-step instructions for setting up and installing the Point of Interest (POI) project.

IDE: Android Studio
1.	Download Android Studio:
•	Visit the official Android Developer website: https://developer.android.com/studio.
•	Click on the "Download Android Studio" button.
•	Choose the appropriate version for your operating system (Windows, macOS, or Linux).
2.	Run the Installer:
•	Once the download is complete, run the installer.
•	Follow the on-screen prompts. Make sure to read and accept the license agreement.
3.	Choose Components:
•	You'll be presented with a window where you can choose the components to install. The default selections are usually sufficient. Click "Next" to proceed.
4.	Choose Install Location:
•	Select the location where you want Android Studio to be installed. Click "Next".
5.	Select Start Menu Folder:
•	Choose the Start Menu folder where you want the Android Studio shortcuts to be placed. Click "Install".
6.	Install Wizard:
•	The installer will begin downloading and installing the selected components. This may take some time depending on your internet speed.
7.	Complete Installation:
•	Once the installation is complete, click "Next" and then click "Finish".
8.	Launch Android Studio:
•	After installation, Android Studio will launch automatically. If not, you can find it in your Start Menu or Application folder.
9.	Set up Android Studio:
•	Android Studio will prompt you to install the Android SDK components that it needs. Follow the prompts to download and install these components.
10.	Select UI Theme:
•	Android Studio will ask you to choose a UI theme. You can select either "Light" or "Dark" depending on your preference.
11.	Import Previous Settings (Optional):
•	If you have previously used Android Studio and want to import settings from an older installation, you can do so in this step.
12.	Finish Setup:
•	Once the setup is complete, Android Studio will be ready for use.
To run the GitHub repository in Android Studio, follow these steps:

1.	Clone the Repository:
•	Open a terminal or command prompt on your computer.
•	Navigate to the directory where you want to clone the repository.
•	Use the following command to clone the repository:
git clone https://github.com/caisr-hh/OSMaaS.git 
•	This will create a local copy of the repository on your machine.
2.	Open the Project in Android Studio:
•	Launch Android Studio.
•	Click on "Open an existing Android Studio project" or go to File > Open
•	Navigate to the directory where you cloned the repository and select the Point_of_Interest folder. Click "OK" to open the project.
3.	Wait for Gradle Sync:
•	Android Studio will start syncing the project with Gradle. This process may take a few moments depending on your internet speed and system performance.
4.	Set Up Emulator or Connect a Device:
•	Launch the Android emulator (steps to set up emulator are given below).
5.	Run the Application:
•	In Android Studio, select the target device from the device dropdown menu (located next to the "app" module dropdown).
•	Click the "Run" button (the green play button) in the toolbar or go to Run > Run 'app'. This will build and deploy the application to the selected device.
6.	Interact with the App:
•	Once the app is installed on the emulator, you can interact with it directly on the emulator/device screen.


Emulator: Android automotive emulator (Polestar variant)
1.	Open Android Studio:
•	Make sure you have Android Studio installed on your system. Open Android Studio.
2.	Launch AVD Manager:
•	Click on the "AVD Manager" icon in the toolbar or go to View > Tool Windows > AVD Manager.
3.	Create a New Virtual Device:
•	In the AVD Manager, click on the "Create Virtual Device" button.
4.	Select Hardware:
•	In the "Select Hardware" screen, choose a hardware profile that supports Android Automotive. For example, "Automotive Polestar 2" should be available. Select it and click "Next".
5.	Select a System Image:
•	Choose a system image. Make sure to select one with Android Automotive. If you haven't downloaded the necessary image, you can do so by following the steps below.
•	In Android Studio, select Tools > SDK Manager. Click the SDK Update Sites tab. Click Add icon
•	Enter the following Name and URL, then click OK
•	Name: Polestar 2 System Image
•	URL: https://developer.polestar.com/sdk/polestar2-sys-img.xml
•	Click Apply, then click OK and proceed to create a car AVD
6.	Configure the AVD:
•	Give your AVD a name, like "AutomotivePolestarAVD". Then, click "Finish".
7.	Launch the AVD:
•	Back in the AVD Manager, select the AVD you just created and click the green play button (Launch this AVD in the emulator).
8.	Wait for Emulator to Start:
•	The emulator will take some time to start. This can vary depending on your system's performance.
9.	Configure Automotive System:
•	Once the emulator starts, it will boot into the Android Automotive system. Follow the on-screen instructions to set up the system, similar to setting up a new Android device.
10.	Explore the Emulator:
•	You're now in the Android Automotive environment. You can interact with the emulator as you would with a real device.
11.	Testing the application:
•	You can now test your application in the Android Automotive environment.
Once these steps are completed, the interface is set up and ready for further development or testing.


## Key Dependencies
The project relies on several external libraries and frameworks to enhance functionality and streamline development. The dependencies are included in ‘build.gradle.kts’ file 
* implementation(libs.core.ktx)
* implementation(libs.appcompat)
* implementation(libs.material)
* implementation(libs.constraintlayout)
* implementation(libs.navigation.fragment.ktx)
* implementation(libs.navigation.ui.ktx)
* testImplementation(libs.junit)
* androidTestImplementation(libs.androidx.test.ext.junit)
* androidTestImplementation(libs.espresso.core)
* implementation(libs.androidx.app.automotive)
* implementation(libs.timber)
* libs.core.ktx: The Core KTX module provides extensions for common libraries that are part of the Android framework.
* libs.appcompat: The AppCompat library provides backward-compatible versions of Android UI components to ensure consistent behavior across different Android versions.
* libs.material: The Material Design Components library provides ready-to-use UI components following the Material Design guidelines.
* libs.constraintlayout: The ConstraintLayout library is a flexible layout manager for Android that allows you to create complex UIs with a flat view hierarchy.
* libs.navigation.fragment.ktx: This is part of the Android Navigation component, specifically the Kotlin extensions (ktx) for working with fragments in the navigation graph.
* libs.navigation.ui.ktx: Similar to the previous library, this Kotlin extension is for working with UI components in the Android Navigation component.
* libs.junit: This references to the JUnit library, which is a widely-used testing framework for Java and Kotlin.
* libs.androidx.test.ext.junit: This is an extension or utility related to JUnit for AndroidX testing.
* libs.espresso.core: This refers to the Espresso testing framework, which is used for UI testing in Android applications.
* libs.androidx.app.automotive: This module is related to specifically tailored for in-car infotainment systems  for Android Automotive. 
* libs.timber: The Timber logging library simplifies logging in Android applications.
These dependencies are crucial in enabling various features and functionalities within the application.


## Use Case
* The User opens the POI app in the emulator.
* The app displays a menu with various categories of Points of Interest (POIs).
 <img width="200" alt="image" src="https://github.com/caisr-hh/OSMaaS/assets/2185586/6b46824d-6b84-42f7-91e8-f6547e52a356">

* The User selects the category "Mat & Dryck" (Food & Drink) from the menu.
 <img width="200" alt="image" src="https://github.com/caisr-hh/OSMaaS/assets/2185586/66e78b19-b4f5-4229-b8d2-2abce62080bc">

* The app displays subcategories related to "Mat & Dryck," such as "Restauranger" (Restaurants). 
 <img width="196" alt="image" src="https://github.com/caisr-hh/OSMaaS/assets/2185586/c0fae832-6469-4920-a4d4-237573844b40">

* The User selects "Restauranger" (Restaurants) from the subcategories.
* The app fetches and displays a list of nearby restaurants within the selected category.
 <img width="218" alt="image" src="https://github.com/caisr-hh/OSMaaS/assets/2185586/40bc8122-259e-4d69-b705-7fbd82752bef">

* The User is presented with a list of nearby restaurants, each accompanied by relevant details.
* If no restaurants are found in the nearby area, the app displays a message indicating that no results were found.
* If there is a connectivity issue, the app displays an error message and prompts the User to check their internet connection.

## Troubleshooting
1.	Java JDK Not Installed:
•	Error: "Java JDK not found" or "Java Development Kit not found."
•	Solution: Install the Java Development Kit (JDK) and set up the JAVA_HOME environment variable.
2.	SDK Components Missing:
•	Error: "SDK component missing" or "Android SDK components not found."
•	Solution: Launch the SDK Manager from Android Studio and install the necessary SDK components.
3.	Installation Hangs or Freezes:
•	Error: Android Studio installation process hangs or freezes.
•	Solution: Check for antivirus interference, temporarily disable it during installation, and ensure sufficient disk space.
4.	AVD Manager Issues:
•	Error: "AVD Manager is unable to create virtual devices."
•	Solution: Ensure that the necessary components and system images are installed via the SDK Manager.
5.	Unable to Create Virtual Device:
•	Error: Users encounter issues when trying to create a new virtual device.
•	Solution: Ensure that the necessary system images and components are installed via the SDK Manager. Also, check if there is sufficient disk space available.
6.	Google Play Services Not Installed:
•	Error: "Google Play services is not installed on this device."
•	Solution: Create a new AVD with Google Play services enabled, or install Google Play services manually on the existing AVD.


## Building
1. Add your upload keystore to the automotive folder
2. Rename keystore.properties.template to keystore.properties
3. Fill in the details regarding your keystore

https://developer.android.com/training/cars 

Template from: https://github.com/android/car-samples/tree/03440d5e974a060d0216b5e02a7fe3c46b7b9469/car_app_library


## Use Case
The User opens the POI app in the emulator.
The app displays a menu with various categories of Points of Interest (POIs).

The User selects the category "Kultur & Historia" from the menu. 

