📚 Namma-Pustaka
Smart Library Assistant for Rural Schools
Android App Development using GenAI
📝 Project Overview
Namma-Pustaka is an Android-based Smart Library Assistant designed for village and rural schools where libraries are often unmanaged and limited to cupboards of books.
The app transforms a traditional book shelf into a digital, trackable, and interactive library system, encouraging reading habits while simplifying book management for teachers.
The app leverages modern Android development tools and GenAI-assisted features to make library usage simple, visual, and engaging for students.
🎯 Problem Statement
In many rural schools:
There is no proper library tracking system
Students do not know which books are available
Teachers maintain manual registers that are error-prone
Books often get lost or returned late
Reading culture is slowly declining
💡 Solution – Namma-Pustaka
Namma-Pustaka acts as a digital library register + student reading companion.
Students can browse books visually
Read Kannada summaries
Reserve and review books
Teachers can issue, track, and monitor overdue books
🚀 Key Features
📖 Book Catalog
Browse books by categories:
Story
Science
History
Grid-style layout showing book covers like a digital shelf
Search books by Book Name or Author
📷 QR Code Borrow System
Each book has a QR code
Teacher scans the QR to issue the book to a student
Automatically records:
Student name
Issue date
Return status
⭐ Review Corner
Students can:
Give Star Ratings (1–5)
Write one-sentence reviews
Helps other students choose books
🏆 Reading Leaderboard
Tracks:
Pages read per student
Monthly reading activity
Displays top readers
Encourages healthy competition
⏰ Overdue Alerts
Automatically detects overdue books
Overdue status text turns RED
Helps teachers follow up easily
🔄 App Usage & User Flow
Teacher adds books using camera-based entry
Student browses catalog & reserves a book
Teacher scans QR code to issue book
Student reads and submits review
🛠 Technical Implementation
📌 Technologies Used
Component
Technology
Programming Language
Kotlin
Database
Room Database
QR Scanning
Google ML Kit
UI
RecyclerView (Grid Layout)
Architecture
MVVM
IDE
Android Studio
📂 Database Structure (Room DB)
Book Table
Book ID
Title
Author
Category
Pages
QR Code ID
Transaction Table
Student Name
Book ID
Issue Date
Return Date
Status (Issued / Returned / Overdue)
📱 User Interface Highlights
Grid-based digital bookshelf
Simple icons and large text (child-friendly)
Color indicators:
🔴 Red → Overdue
🟢 Green → Available
Clean and minimal UI for rural usability
🎯 Impact Goals
📚 Literacy Promotion
Encourages students to read regularly
Makes books attractive using visuals and reviews
🏫 Resource Management
Prevents loss of books
Maintains proper borrowing history
💻 Digital Habits
Introduces students to:
Digital check-in / check-out
Ratings and reviews
Responsible usage of shared resources
✅ Success Criteria
✔ Add new books using camera-based entry
✔ QR-based book issuing system
✔ Overdue books highlighted in RED
✔ Search library by Book Name or Author
✔ Leaderboard updates monthly
📌 Future Enhancements
Cloud sync using Firebase
Parent notification system
AI-based book recommendations
Offline-first support for low internet areas
👨‍🎓 Target Users
Rural School Students
Teachers & Librarians
Government School Administrators
📄 License
This project is developed for educational and academic purposes.
