# ToDoAndroidApp-Kotlin
This app is to illustrate Android’s Room in Kotlin ft. MVVM Architecture and Coroutines. This is the ToDo app which performs all CURD opertions using room with MVVM design pattern.

What is MVVM
----------------------------------------------------------------------------
Each component has its own responsibilities to do their task

- The **View** receives user action and send it to the **ViewModel**, or listen live data stream from **ViewModel** and shows it to user.
- The **ViewModel** receives user actions from the **View** or provides data to View. 
- The **Model** abstract the data sourec. We write our business logic here and both **View** and **ViewModel** uses that to stream data.

Room Database
-----------------------------------------------------------------------------
Database layer on top of SQLite database that takes care of mundane tasks that you used to handle with an SQLiteOpenHelper. Database holder that serves as an access point to the underlying SQLite database. The Room database uses the DAO to issue queries to the SQLite database.

There are 3 major components in Room

- **Entity**: Represents a table within the database.
- **Dao**: Contains the methods used for accessing the database.
- **Database**: Contains the database holder and serves as the main access point for the underlying connection to your app's persisted, relational data.

Coroutines
------------------------------------------------------------------------------
A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously. Coroutines were added to Kotlin in version 1.3 and are based on established concepts from other languages.
Coroutines is our recommended solution for asynchronous programming on Android. Noteworthy features include the following:

- **Lightweight**: You can run many coroutines on a single thread due to support for suspension, which doesn't block the thread where the coroutine is running. Suspending saves memory over blocking while supporting many concurrent operations.
- **Fewer memory leaks**: Use structured concurrency to run operations within a scope.
- **Built-in cancellation support**: Cancellation is propagated automatically through the running coroutine hierarchy.
- **Jetpack integration**: Many Jetpack libraries include extensions that provide full coroutines support. Some libraries also provide their own coroutine scope that you can use for structured concurrency.
