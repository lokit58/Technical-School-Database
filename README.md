# Technical School Database Project


A Java-based student database management system that allows you to:
- Add and remove students
- Assign and store grades
- Find student by ID
- Sort students by specialization
- List average grades and student count of specializations
- Use special student abilities based by specialization (HashName or Name in morse code)
- Store data using SQLite

## Authors

Vít Němeček
Ema Ondrušková

## Project Structure

```
src/
├── CyberStudent.java
├── Database.java
├── InputManager.java
├── SpecialSkills.java
├── SQLDatabase.java
├── Student.java
├── TechnicalDatabaseMain.java
└── TeleStudent.java   
```


##  How to Run

1. **Compile the project**

2. **Run the main class: TechnicalDatabaseMain.java**

##  Database

The application uses an SQLite database called `StudentDatabase.db` and automatically creates tables for:
- Students
- Grades
- IDs

## Input Format (for loading from a file)

Each line should follow this format:

```
Name; Surname; Date of birth (YYYY-MM-DD); Specialisation (CYBERSECURITY or TELECOMMUNICATIONS); Grades (any number of 1, 2, 3, 4, 5 divided by ',' or none)
```

Example:
```
Tomas; Kral; 1997-01-05; CYBERSECURITY; 5, 4, 3, 2, 2, 1
```


## 📌 Notes

- Grades must be integers between 1 and 5.
- IDs are assigned automatically, starting from 0.
- Date format: `YYYY-MM-DD`
- RecoveryInput.txt included for test loading from DB
