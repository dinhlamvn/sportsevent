# SPORT EVENT

This is home test source for Inspius

## Topic

Build an Android App for a hypothetical sports event.

## Functional Requirements:

* Ability to show all participating teams.
* Ability to show all previous and upcoming matches.
* Ability for a user to select a team and filter matches per team.
* Watch previous match highlights.
* Users can set a reminder for an upcoming match
* Notify the user when the match is about to start.

## Coding Requirements

* Application should be written using Kotlin Programming Language
* UI Design must follow Android’s Material Design Guidelines
* Only use appropriate libraries needed based on the functional requirements
* Integrate unit tests on the project
* Support for Tablet design (Bonus)
* Integrate UI test on the project (Bonus)

## Building

* **Architecture**:
  We are using MVVM architecture to build this app. The MVVM arch help clearly separate
  application's business logic from its user interface. Maintaining a clean separation between
  business logic
  and the UI help development process easier to test, maintain and evolve.

* **Framework and Library**:

- Retrofit: The Retrofit library is a type-safe REST client for Android, Java, and Kotlin,
  developed by Square. With the help of the Retrofit library, we can have access to a powerful
  framework
  that helps us in authenticating and interacting with APIs and sending network requests with OkHttp

- Glide: The image loader library, it help control the app lifecycle, auto cancel when
  activity/fragment destroyed.
  Fit the bitmap to the ImageView's size to reduce application memory usage and more features to
  custom the image.

## Functionalities and Technical (Add if had additional time)

* **Functionalities**:

- Buy ticket to go to the game
- Team profile
- Predict the winning team to receive rewards

* **Technical**:

- DI: Dependencies Injection to reduce dependent modules
- Compose: The modern Android UI, it clean and better performance
- Unit and UI test