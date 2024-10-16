# Reservation API

This project is a RESTful Reservation API that allows users to create, manage, and cancel reservations. The API also includes functionality to notify users of their reservation status and send reminders before the reservation time.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
    - [Create a New Reservation](#create-a-new-reservation)
    - [Cancel a Reservation](#cancel-a-reservation)
    - [View My Reservations](#view-my-reservations)
    - [Update My Reservation](#update-my-reservation)
- [Stretch Goals](#stretch-goals)
- [Database](#database)
- [Testing](#testing)
- [License](#license)

## Features

- **Create a reservation**: Users can create a reservation by providing their personal information and reservation details.
- **Cancel a reservation**: Users can cancel their reservations using a reservation ID.
- **View reservations**: Users can view a list of all upcoming reservations.
- **Update a reservation**: Users can modify reservation details like the time and number of guests.
- **Notification system**: Users are notified about their reservation status (confirmation, cancellation, and reminders).

## Technologies Used

- **Java 21**: Latest version of Java for building robust and scalable applications.
- **Spring Boot 3.3**: Framework for creating RESTful services and managing application dependencies.
- **H2 Database**: In-memory database used for testing and local development.
- **JPA (Java Persistence API)**: For mapping Java objects to database tables.
- **Maven**: Dependency management and build tool.
- **JUnit**: Testing framework for unit and integration testing.

## Getting Started

### Prerequisites

- Java 21
- Maven 3.6+
- IDE (IntelliJ, Eclipse, etc.)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/zhacky/reservation-api.git
