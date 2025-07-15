# AI-Powered Task Manager

An intelligent task management application that allows users to manage their daily tasks using both text and voice commands. Powered by Natural Language Processing (NLP) to understand user intents and seamlessly integrate with a backend for persistent storage and management of tasks.

## ✨ Features

* **Voice Control:** Add, complete, delete, and list tasks using natural voice commands (e.g., "Add a task to buy groceries", "Complete the meeting minutes task").
* **Text Input:** Traditional text-based input for task management.
* **Intelligent Command Processing:** Utilizes a Node.js NLP backend to understand user intents and extract relevant task details.
* **Task Management:**
    * Add new tasks.
    * Mark tasks as completed.
    * Delete existing tasks.
    * View pending and completed tasks separately.
* **Responsive UI:** A clean and modern user interface built with Tailwind CSS.
* **Confirmation Modals:** Interactive modals for critical actions like task deletion.

## 🛠️ Technologies Used

This project is built using a combination of frontend and backend technologies:

### Frontend
* **HTML5**
* **CSS3 (Tailwind CSS)**
* **JavaScript:** For interactive elements, voice recognition, and API communication.
    * Uses `webkitSpeechRecognition` for voice input.

### Node.js NLP Backend
* **Node.js:** Runtime environment.
* **Express.js:** Web framework for handling API requests.
* **Node-NLP:** For Natural Language Processing, intent classification, and entity extraction. This processes raw user commands into structured intent data.

### Spring Boot Backend
* **Java 17+**
* **Spring Boot:** Framework for building robust RESTful APIs for task management.
* **Maven/Gradle:** Dependency management (assuming Maven for this template).
* **Database:** (Specify your database here, e.g., H2, PostgreSQL, MySQL) - *You should replace this with your actual database.*

## 🚀 Getting Started

Follow these steps to get your AI-Powered Task Manager up and running.

### Prerequisites

Before you begin, ensure you have the following installed:
* [Node.js](https://nodejs.org/) (LTS version recommended)
* [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) (version 17 or higher)
* [Maven](https://maven.apache.org/download.cgi) or [Gradle](https://gradle.org/install/) (if using Gradle for Spring Boot)

### 1. Spring Boot Backend Setup

This is your main task management API.

1.  **Clone the Repository:**
    ```bash
    git clone <your-repo-url>
    cd <your-repo-name>/backend-springboot # Adjust this path if your Spring Boot project is in a different directory
    ```
2.  **Configure Database:**
    * Update `src/main/resources/application.properties` (or `application.yml`) with your database configuration.
    * Example for H2 (in-memory, good for development):
        ```properties
        spring.datasource.url=jdbc:h2:mem:taskdb
        spring.datasource.driverClassName=org.h2.Driver
        spring.datasource.username=sa
        spring.datasource.password=
        spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
        spring.h2.console.enabled=true
        spring.jpa.hibernate.ddl-auto=update
        ```
3.  **Build and Run:**
    * Using Maven:
        ```bash
        mvn clean install
        mvn spring-boot:run
        ```
    * Using Gradle:
        ```bash
        ./gradlew bootJar
        java -jar build/libs/your-app-name.jar # Replace with your actual jar name
        ```
    *The Spring Boot application should now be running on `http://localhost:8080` (or your configured port).*

### 2. Node.js NLP Backend Setup

This server handles the NLP processing of user commands and forwards them to your Spring Boot backend.

1.  **Navigate to the Node.js NLP directory:**
    ```bash
    cd <your-repo-name>/node-nlp-backend # Adjust this path
    ```
2.  **Install Dependencies:**
    ```bash
    npm install
    ```
3.  **Train the NLP Model:**
    * Ensure your `train_nlp_model.js` file is in this directory.
    * Run the training script to generate `model.nlp`:
        ```bash
        node train_nlp_model.js
        ```
    * This will create a `model.nlp` file which is essential for the NLP server.
4.  **Run the Node.js NLP Server:**
    ```bash
    node server.js
    ```
    *The Node.js NLP server should now be running on `http://localhost:3001`.*

### 3. Frontend Setup

1.  **Navigate to the Frontend directory:**
    ```bash
    cd <your-repo-name>/frontend # Adjust this path if index.html is in a different directory
    ```
2.  **Serve the `index.html` file:**
    * For `webkitSpeechRecognition` to work reliably, it's best to serve `index.html` via a local web server (even a simple HTTP one is usually sufficient for `localhost`, but HTTPS is ideal).
    * **Recommended:** Use `live-server` (if you installed it earlier):
        ```bash
        live-server
        ```
        This will automatically open `index.html` in your browser, typically at `http://127.0.0.1:8080` or `http://localhost:8080`.
    * **Alternatively:** You can open the `index.html` file directly in a Chromium-based browser (like Chrome, Brave, Edge) by double-clicking it. However, be aware that microphone access via `file://` URLs can sometimes be restricted by the browser.

## 💡 Usage

1.  Ensure both your Spring Boot Backend and Node.js NLP Backend are running.
2.  Open the `index.html` file in your browser (preferably served via a local server).
3.  **Text Input:** Type your command into the input field and click "Send" or press Enter.
    * Example: "Add a task to call mom"
    * Example: "Mark buy groceries as done"
    * Example: "Delete call mom task"
    * Example: "Show my tasks"
4.  **Voice Input:** Click the microphone icon to start speaking your command. Click it again to stop.
    * You may be prompted to allow microphone access by your browser. Grant the permission.
5.  Tasks will appear in the "Pending" or "Completed" columns. You can manually check/uncheck tasks or click the trash icon to delete them.


## 🤝 Contributing

Contributions are welcome! If you have suggestions for improvements or find any bugs, please feel free to open an issue or submit a pull request.

## 📄 License

This project is licensed under the [MIT License](LICENSE) - see the `LICENSE` file for details.
