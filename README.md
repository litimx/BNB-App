# BNB App

Objective is to build an "AirBNB-type" application to allow user to make reservations for a guest with a host. Guest chooses a place to stay for a specified date range. If host location is available, it can be reserved. Reserved locations are NOT available for other guests while reserved. 

Class Details

App

models.Reservation

* Fields:
- Int ID
- Guest guest
- Host host
- LocalDate startDate
- LocalDate endDate
- BigDecimal total

* Full getters and setters

models.Host

* Fields:
- String ID
- String LastName
- String Email
- String Phone
- String Address
- String City
- String State
- String PostalCode
- BigDecimal standard Rate
- BigDecimal weekend Rate

* Methods:
- Full getters and setters

models.Guest
* Fields:
- Int ID
- String FirstName
- String LastName
- String Email
- String Phone
- String State

* Methods:
- Full getters and setters

data.DataException
* Fields:
        -Message (for error messages)   

data.ReservationFileRepository

* Fields:
- file path

* Methods:
- ReadFromFile
- WriteToFile
- UpdateFile
- DeleteFromfile
- serialize
- deserialize
- findAll
- findByHost

data.ReservationRepository (interface)

* Methods:
- addReservation
- editReservation
- deleteReservation
- viewReservationsByHost

data.HostFileRepository

* Fields:
- file path

* Methods:
- ReadFromFile
- WriteToFile

data.HostRepository (interface)

* Methods:
- findHostByID()
- List all Hosts

data.GuestFileRepository

* Fields:
- GuestRepository guestRepository
- file path

* Methods:
- ReadFromFile
- WriteToFile
 
data.GuestRepository (interface)

* Methods:
- findGuestByID()
- List all Guests
 

domain.Result

* Fields:
- Message (error messages/success messages)
- Status (boolean success/failure)

* Methods:
- isSuccess
- addErrorMessage
- getErrorMessage

domain.ReservationService

* Fields:
- ReservationRepository
- HostRepository
- GuestRepository

* Methods:
- addReservation
- editReservation
- deleteReservation
- viewReservationsByHost
- validate
- validateNulls
- validateFields

domain.HostService

* Methods:
- findAll
- findByEmail

domain.GuestService

* Methods:
- findAll
- findByEmail

ui.ConsoleIO

* Fields:
- Scanner scanner
- DateTimeFormatter
- print
- println
- printf
- readString
- readRequiredString
- readInt
- readBoolean
- readLocalDate

ui.Controller

* Fields:
- Reservation Service
- View

* Methods:
- View
- ReservationService service
- Run 
- runMenu
- addReservation
- editReservation
- deleteReservation
- displayReservationsByHost
- getHost
- getGuest
- readInt
- readString

ui.View

* Fields:
- ConsoleIO

* Methods:
- displayHeader
- displayMessage
- displayReservations
- displayHosts
- displayGuests
- selectMainMenuOption
- addReservation
- editReservation
- deleteReservation 


#### STEPS

#### Designing Data Model **7 Hours Total**
    * [ ] Defining Guest, Host, and Reservation **2 Hours**
    * [ ] Defining relationships between Classes **2 Hours**
    * [ ] Create classes to handle database operations (CRUD) **3 Hours**

#### Setup Required Dependencies **1 Hour Total**
    * [ ] Adding dependencies on pom.xml, keeping file updated as dependencies are added & removed **1 Hour**

#### Implementing Data Access Layer **6 Hours Total**
    * [ ] Create & Implement Data Access Interface **3 Hours**
        -getReservation()
        -editReservation()
        -deleteReservation()
        -addReservation()
    * [ ] Write code for using data access object in application **3 Hours**
        -Use data access interface in application

#### Implementing Service Layer **10 - 11 Hours Total**
    * [ ] Implement service classes for Guest, Host, and Reservation **3-4 Hours**
    * [ ] Create Service Interface **1 Hour**
    * [ ] Implement Service Interface **3 Hours**
        Create service classes for guest, host, and reservation and implement service interface
    * [ ] Write code for using service object(s) in application **3 Hours**

#### Build the UI Layer **12 - 14 Hours Total**
    * [ ] Build a main menu for the application **2 Hours**
        public enum MainMenuOption
        - View Existing Reservations
        - Create a Reservation with a Host
        - Edit Existing Reservations
        - Cancel a Future Reservation
    * [ ] Build View and Controller classes **3-4 Hours Each**
        public class Controller
        -public void run() with Switch Case statements
        -private void viewReservation()
        -private void createReservation()
        -private void editReservation()
        -private void cancelReservation()
        public class View
        -public MainMenuOption
        -private int readInt(String prompt)
    * [ ] Implement UI for all of project requirements **4 Hours**
        Implement viewReservation, createReservation, editReservation
        -additional methods to handle user input (read date range for reservation, read guest ID)

#### Add Testing (While Working on Project) **8 Hours Total**
    * [ ] Add testing throughout building the application **4 Hours**
        Sample tests
        -shouldAddGuest()
        -shouldMakeReservation()
        -shouldCalculateTotal()
    * [ ] Manually Test Features **2 Hours**
    * [ ] Add any missing tests at the end **2 Hours**

#### Debugging and Error Handling **3 Hours Total**
    * [ ] Work out any bugs in application - find the source & take corrective action **3 Hours**

#### Final Testing Requirements **4 Hours Total**
    * [ ] View Reservations - User may enter a value that uniquely identifies a host and/or search for a host and pick one out of a list.
        * [ ] Does a message display if host is not found?
        * [ ] Does a message display if a host has no reservations available?
        * [ ] Does all reservations show for host?
    * [ ] Make a Reservation - Is the user able to enter a value that uniquely identifies a host and/or search for a host and pick one out of a list.
        * [ ] Are all future reservations for a host shown?
        * [ ] Calculate the total, display a summary, and ask the user to confirm. The reservation total is based on the host's standard rate and weekend rate. For each day in the reservation, determine if it is a weekday (Sunday, Monday, Tuesday, Wednesday, or Thursday) or a weekend (Friday or Saturday). If it's a weekday, the standard rate applies. If it's a weekend, the weekend rate applies
        * [ ] On confirmation, is the reservation successfully saved?
        * [ ] Validation - guest, host, and start and end dates are required.
            * [ ] Does an error display if the guest/host do not exist in database?
            * [ ] Does an error display if start date is after end date?
            * [ ] Does an error display if reservation overlaps existing reservation dates?
            * [ ] Does an error display if start date is in the past?
    * [ ] Edit a Reservation - Start and end date can be edited. No other data can be edited.
        * [ ] Recalculate the total, display a summary, and ask the user to confirm.
        * [ ] Validation - Guest, host, and start and end dates are required.
            * [ ] Does an error display if the guest/host do not exist in database?
            * [ ] Does an error display if start date is after end date?
            * [ ] Does an error display if reservation overlaps existing reservation dates?
    * [ ] Cancel a Reservation - can the application find an existing reservation?
        * [ ] Are only future reservations shown?
        * [ ] On success, display a message.
        * [ ] If an attempt is made to cancel a reservation from the past, display error message

    
#### Initial Project Structure (Excluding Tests) - Subject to Change

```
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   │
│   │   │   ├── masteryproject
│   │   │   │   ├── App.java
│   │   │   │   ├── data
│   │   │   │   │   ├── DataException.java
│   │   │   │   │   ├── ReservationRepository.java
│   │   │   │   │   ├── ReservationFileRepository.java
│   │   │   │   │   ├── GuestFileRepository.java
│   │   │   │   │   ├── GuestRepository.java
│   │   │   │   │   ├── HostRepository.java
│   │   │   │   │   └── HostFileRepository.java
│   │   │   │   ├── domain
│   │   │   │   │   ├── ReservationResult.java
│   │   │   │   │   ├── ReservationService.java
│   │   │   │   │   ├── GuestService.java
│   │   │   │   │   └── HostService.java
│   │   │   │   ├── models
│   │   │   │   │   ├── Guest.java
│   │   │   │   │   ├── Host.java
│   │   │   │   │   └── Reservation.java
│   │   │   │   └── ui
│   │   │   │       ├── View.java
│   │   │   │       ├── Controller.java
│   │   │   │       ├── MainMenuOption.java
│   │   │   │       └── ConsoleIO.java
│   │   ├── resources
│   └───test
|    └───java
|       └───masteryproject
|           └───├───data
|               |
|               │       ReservationRepositoryDouble.java
|               │       ReservationFileRepositoryTest.java
|               │       GuestFileRepositoryTest.java
|               │       GuestRepositoryDouble.java
|               │       HotelFileRepositoryTest.java
|               │       HotelRepositoryDouble.java
|               │
|               └───domain
|                       ReservationServiceTest.java
|                       GuestServiceTest.java
|                       HostServiceTest.java
```
