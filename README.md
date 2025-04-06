Gym Management System – Complete Project
🔧 Technologies Used:
Backend: Java, Spring Boot, MySQL

Frontend: HTML, CSS, Bootstrap, JavaScript

Build Tool: Maven

Server: Embedded Tomcat

Database: MySQL

Email: JavaMailSender (Spring Boot)

Version Control: Git & GitHub

📌 Project Overview
The Gym Management System is a Java Spring Boot + HTML/CSS/JS-based admin-only web application designed to automate and manage gym operations. It allows the gym admin to manage members, track memberships, send alerts, generate financial reports, and manage workout/diet plans efficiently.

It is designed as a desktop web application, where opening the project runs the embedded Tomcat server and launches the app. There is no member login — only admin access is available, which keeps the control centralized.

👨‍💼 Admin Features & Functionalities
🔐 Admin Login
Secure login page.

Only one admin manages the system.

Admin dashboard shows all key options in one place.

👤 Member Management
➕ Add New Member
Add member details like:

Name

Contact Number

Membership Type

Membership Start Date

Paid/Due Amount

Renewal Date

Membership End Date

Profile Photo

Membership duration is automatically calculated based on type (e.g., 1 month, 3 months).

Confirmation email is sent to the member after registration.

🔁 Renew Membership
Allows renewal of expired memberships.

Automatically extends the end date.

Stores full renewal history.


🧾 Member Details View
Full list of members with search functionality.

Show all details including photo and past renewal history.

"Show All" button displays full membership log.

❌ Block Membership
Temporarily blocks a member without deleting their data.

Blocked members are hidden from active lists and excluded from reports.

📅 Membership Tracking
✅ Active Members List
Shows all currently active members.

⌛ Expired Membership List
Displays members whose memberships have ended.

Includes “Send Custom Alert” button to send manual renewal reminders.

⏳ Ending Soon List
Shows members whose membership will expire in 2–3 days.

Automatically highlighted for admin attention.


Sent on:

Registration

Membership Renewal

2 Days before Membership Expiry

1 Day before Membership Expiry

🥗 Diet Plan Module
🍽️ Add Diet Plan
Admin can select a member and input a diet plan.

Diet plan includes breakfast, lunch, dinner, and supplements.

Saved plans can be updated or re-downloaded.

PDF download option available.

🏋️ Workout Plan Module
💪 Add Workout Plan (New Feature)
Similar to the Diet Plan module.

6-Day Workout Split:

Day 1 to Day 6 with specific workouts.

Drop-down list of workouts per day (e.g., Cardio, Chest, Legs, Back, etc.).

Save and generate workout plans in PDF format.

🔍 Member Search
🔎 Search by Name
Admin can search for a member by name.

Displays:

Current membership status

Contact details

All renewal history

Option to send alert or update membership

🗂️ Database Tables
members – Stores member info

renewal_history – Logs each renewal

diet_plan – Stores diet data per member

workout_plan – Stores workout per day per member

collections – Tracks payment data

blocked_members – Keeps record of blocked members

📦 Deployment & Run
Just run the project (via main() method in Spring Boot).

Automatically launches the embedded Tomcat server.

Opens the application on the default port (e.g., http://localhost:8080).

Admin interface is ready for use — no complex setup required.

✅ Future Enhancements (Optional Ideas)
Admin panel with charts & dashboard metrics.

Role-based login (Trainer/Admin/Receptionist).

SMS gateway integration.

Attendance tracking via QR code or ID.

Payment gateway integration for online renewals.

## 🏠 Homepage Screenshot

![Homepage](Screenshot/Screenshot%20(39).png)
(Screenshot/Screenshot%20(40).png)

