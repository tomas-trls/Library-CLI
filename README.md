# 🌟 The Quiet Corner CLI 📚

<div align="center">

[![wakatime](https://wakatime.com/badge/github/tomas-trls/Library-CLI.svg?style=flat-square)](https://wakatime.com/badge/github/tomas-trls/Library-CLI)
</div>

This is a Library Management System CLI is a console application that allows users to manage a library system, including loaning and returning books, viewing books available in the library, and running reports. The application is built in Java and uses a CSV file for persistence.

## 💥 Setup / Installation Requirements 💥

To run the application, you need to have Java installed on your machine. 
Clone the repository from GitHub and navigate to the project directory.

```bash
  git clone https://github.com/tomas-trls/Library-CLI
  cd Library-CLI
```

## 🌚 Demo 🌝

<br />
<p align="center">

   ![Application](http://g.recordit.co/XKlhSyxTrk.gif)
   
</p>

## ⚙️ Features

### 📊 Data Management
The data for books and users is provided in CSV format. The application reads the data from the CSV file and stores it in an ArrayList. 
The loan information is also stored in the ArrayList.

### 📚 Book Management
Users can view all the books available in the library, as well as search and sort the books by ID, title, author, genre, publisher, and loan counter. 
The loan counter indicates the number of times a book has been loaned.

<h3>🤵 User Management</h3>
Admins have access to a user management system where they can view all the users and their loan history, as well as search and sort the users by name or ID.

### 📝 Loan System
Customers can loan a book and view their currently loaned books. They cannot loan a book that is already out on loan. Admins can run a report that shows all books currently out on loan and the number of times each book has been loaned out.

### 🧾 Reports
Admins can run a report that shows all books currently out on loan and the number of times each book has been loaned out. The report can be outputted in CSV format.

## ⚡️ Technologies used 

<p align="center">
 <img src="https://www.vectorlogo.zone/logos/java/java-icon.svg" alt="java" width="80" height="80"/> 
 <img src="https://resources.jetbrains.com/storage/products/company/brand/logos/IntelliJ_IDEA_icon.png?_ga=2.49565158.1382529061.1682429692-955666481.1682429683" alt="IntellijIDEA" width="80" height="80"/> 
</p>

## 🚀 Future Features

- Sorting the Loaned books ArrayList
- Searching the Loaned Books ArrayList
- Adding the option to leave reviews to the books
- Sorting and Searching books by reviews
- Adding multiple times the same book, with different id, but common counter. To see if the book is popular.
- Adding option to add and Remove books to Library by admins (already done in background, just need to add method in commandRunner)
- Pass the book Arrays and document Object Model Database to a Relational Database with SQL

## 💎 Contact Me 💎

<strong>[@tomas-trls](https://www.github.com/tomas-trls) / tomast25@hotmail.com </strong>
