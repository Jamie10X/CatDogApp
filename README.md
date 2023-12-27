The Cat and Dog Fact App is an engaging mobile application designed to provide users with fascinating facts and trivia about cats and dogs. Aimed at pet enthusiasts and general users alike, the app serves as an educational and entertaining tool, enriching users' knowledge about their favorite pets. With its user-friendly interface, the app allows users to choose between two categories: cats or dogs. Depending on the selection, it displays interesting facts or trivia questions related to the chosen animal. The app also features a visually appealing design with accompanying images, enhancing the learning experience. Its intuitive navigation ensures a seamless and enjoyable user interaction, making it suitable for all age groups interested in learning more about these beloved animals.


**Technical Documentation for Cat and Dog Fact App**

### Architecture Overview:
- **Platform:** Android
- **Programming Language:** Java
- **Framework:** Android SDK
- **Networking:** Retrofit for API calls
- **Image Loading:** Picasso for loading and caching images
- **JSON Parsing:** Gson

### Key Components:

1. **MainActivity:**
   - Entry point of the app.
   - Handles user's selection between cat and dog facts.
   - Navigates to Fact/Trivia Display Screen.

2. **Generator Activity:**
   - Displays cat or dog facts or trivia based on user selection.
   - Integrates Retrofit to fetch facts from external APIs.
   - Uses Picasso to load and display images.

3. **multiplechoiceActivity:**
   - Displays trivia questions with multiple-choice answers.
   - Reads questions from a local JSON file.
   - Manages user interactions and validates answers.

### Key Methods and Algorithms:

- **API Integration:**
  - `loadCatFact` and `loadDogFact`: Fetches facts from respective APIs using Retrofit.
- **Image Loading:**
  - `loadCatImage` and `loadDogImage`: Loads images with URLs fetched from APIs using Picasso.
- **Trivia Display:**
  - `loadTriviaQuestions`: Reads trivia questions from a JSON file.
  - `displayNextQuestion`: Randomly selects and displays a trivia question with options.
- **Answer Validation:**
  - `checkAnswer`: Validates user's selected answer against the correct answer.



### API Documentation for Cat and Dog Fact App

#### Cat Facts API:
- **Base URL:** `https://cat-fact.herokuapp.com`
- **Endpoint:** `/facts/random`
- **Method:** GET
- **Response Format:** JSON
- **Purpose:** Fetches a random fact about cats.
- **Example Response:**
  ```json
  {
    "text": "Cats have five toes on each front paw, but only four toes on each back paw."
  }
  ```

#### Dog Facts API:
- **Base URL:** `https://dogapi.dog`
- **Endpoint:** `/api/v2/facts`
- **Method:** GET
- **Response Format:** JSON
- **Purpose:** Provides random facts about dogs.
- **Example Response:**
  ```json
  {
    "data": [{
      "fact": "Dogs have a sense of time. It's been proven that they know the difference between one hour and five."
    }]
  }
  ```

These APIs are crucial for the app's functionality, supplying users with informative and interesting facts about cats and dogs. The JSON responses are parsed and displayed within the app to enhance user knowledge and engagement.


Future Scope and Maintenance:
Enhancements:
User-Generated Content: Allow users to submit their own trivia questions and facts.
Social Sharing: Integrate features to share facts and trivia scores on social media.
Personalization: Offer customizable themes and settings for user preferences.
Language Support: Add multilingual support to cater to a wider audience.
Quiz Mode: Implement a timed quiz feature to increase engagement.

Feel free download and edit this project ! 
