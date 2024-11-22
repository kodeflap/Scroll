# **Scroll: User List Application**

**Scroll** is an Android application designed to display a list of users retrieved from a public API with an elegant and user-friendly interface. The app is built using modern Android development practices, leveraging the MVVM architecture to maintain clean separation of concerns and ensure smooth user interaction with network data. Users can view a list of users, search through the list, see detailed information about each user, and handle network caching effectively.

---

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Demo](#demo)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- **User List Display**: 
  - Fetches a list of users from a public API and displays it using `RecyclerView`.
  - Pagination to load more users as the user scrolls down.
  
- **User Search**: 
  - Allows users to search through the displayed list with a content search feature.

- **Detailed View**: 
  - Displays detailed information about a user when a list item is clicked.

- **Network Handling and Caching**:
  - Efficient network requests using `Retrofit` and caching strategies to manage network state.

- **MVVM Architecture**:
  - **Model**: Fetches data from the remote API.
  - **ViewModel**: Handles data manipulation and communicates between UI and Model layer.
  - **View**: Displays data in `RecyclerView` and observes the ViewModel for real-time updates.

- **Pagination**: 
  - Loads data in chunks with proper pagination support to ensure a smooth and responsive user experience.

---

## Tech Stack
- **Language**: Kotlin
- **Networking**: Retrofit
- **Architecture**: MVVM (Model-View-ViewModel)
- **Pagination**: Implemented using `RecyclerView` and a custom pagination logic
- **Caching**: Retrofit with caching strategies
- **UI Components**: RecyclerView, SearchView, ViewBinding
- **Coroutines**: For asynchronous operations and network calls

---

## Architecture
The project follows the MVVM (Model-View-ViewModel) architecture, ensuring separation of concerns and making it easier to scale:

- **Model**: Handles fetching data from the API (using Retrofit) and represents user data.
- **ViewModel**: Acts as a bridge between the View and the Model, providing data to the UI and handling business logic (such as pagination and search).
- **View**: Composed of UI elements that display the data, such as the `RecyclerView` for the user list and the detailed view screen.

---

## Demo
Here’s a walkthrough of the app in action:

![Record_2024-11-22-17-17-48](https://github.com/user-attachments/assets/9b985ed8-3fbe-42d5-876d-b6793d456ea4)

---

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/kodeflap/Scroll.git
   ```

2. **Open in Android Studio**: Import the project into Android Studio.

3. **Build the Project**: Sync Gradle files and build the project.

4. **Run the App**: Install and run on an emulator or physical device.

---

## Usage

- **View Users**: The main screen will show a list of users fetched from the API.
- **Search**: Type in the search bar to filter users.
- **Pagination**: Scroll down to load more users automatically.
- **View Details**: Tap on any user to view detailed information.

---

## Contributing
Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](https://github.com/kodeflap/Scroll/issues) to report bugs or suggest improvements.

1. Fork the project.
2. Create a feature branch (`git checkout -b feature/YourFeature`).
3. Commit changes (`git commit -m 'Add your feature'`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Open a Pull Request.

---

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

Let me know if you need any further edits or details!
