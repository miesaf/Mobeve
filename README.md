<p align="center"><img src="https://dimensikini.xyz/img/Mobeve-Logo.png" width="100"></p>

# Mobile Event Planner (Mobeve)

## About Mobeve

This is a thesis project in fulfilment of the requirements for `CS251 - Bachelor of Computer Science (Hons.) Netcentric Computing` program under the `Faculty of Computer and Mathematical Science` at `MARA University of Technology Malacca Branch Jasin Campus`.

## Project Title

Event Planner Mobile Application Using Rule-based Expert System (Mobeve)

## Abstract

Event planning is crucial in organizing an event. Different types of event have different approaches to achieve its purpose of organizing. Planning an event can sometimes be a hustle especially if the event manager has no experience or lack of it. Event manager especially among students may have zero knowledge in organizing an event for university clubs or class projects. Sometimes, a person may also be the event leader for two or more events simultaneously. As a result, the event leader may unable to plan wisely due to tension and pressure. Currently there is no application that can plan an event especially with automation. Thus, an application is to be developed to counter such problems using the Event Planner Mobile Application Using Rule Based (Mobeve). The mobile application is an Android application that can suggest what bureaus shall be formed and what tasks shall exist to execute the event. The application uses the rule-based approach to suggest bureaus and task. In this paper, the application front-end was developed in Android Studio using Java programming language and XML for interface designing while the back end and database was developed using PHP and SQL. By using the Mobeve application, planning an event would consume less time, paperless and accessible from anywhere if internet connection was present. Functionality and user acceptance tests was conducted to ensure all functions are working and the application meet the user satisfactory experience.

## Objectives

- To design a standardized event planning and execution mobile application for universities environment by using rule-based expert system approach

- To develop an event planner mobile application for Android operating system using Android Studio

- To evaluate the event planner mobile application using functionality and user acceptance test

## Scope

- Target users
	- Event leaders
	- Event committee members
- Target institution
	- Universities citizens in Malaysia
- Types of event covered
	- workshops
	- talks
	- sports
	- volunteerism

## Repository Deployment

### Deployment Requirements

1. Android Studio
2. XAMPP (with PHP 7 and above)
3. Android Mobile Phone OR Android Emulator with minimum of Android 8 Operating System
4. Internet connection

### Deployment Steps

1. Install XAMPP and Android Studio.
2. Deploy the content of `db_api_files` folder into `htdocs` folder of XAMPP.
3. Run the file `db_api_files/db/mobeve_database.sql` in `phpMyAdmin` to deploy database structure.
4. Alter database login credentials accordingly in the file `db_api_files/db/db_connect.php`.
5. Extract and import repository into Android Studio.
6. Alter all URL in all Java files that maps to `https://dimensikini.xyz/api/mobeve/*` so that it maps to XAMPP's IP address (example: http://localhost/*).

**NOTE**:	Android may not allow communication without valid SSL Certificate. Instead of using XAMPP, it is recommended to use web hosting domain that has a SSL Certificate.*

## Personal Particulars

| Attribute | Value |
| ------------- | ------------- |
| Class | M3CS2516A |
| Semester | Sept 2019 - Jan 2020 |
| Supervisor | Mr. Anwar Farhan bin Zolkeplay |
| Supervisee | Muhamad Amirul Ikhmal bin Azman |
| Examiner | Mrs. Nor Azylia binti Ahmad Azam |
| Observer | Mrs. Nor Aimuni Md Rashid |
| CSP650 Lecturer | Mr. Mohamad Asrol bin Arshad |

###### Contact Details for Enquiry
E-Mail: miesaf@dimensikini.xyz<br/>
Contact Form: https://dimensikini.xyz/hubungi.php
