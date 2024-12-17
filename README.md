# FleetScheduler

An app that will provide different options for scheduling the charging of trucks on available chargers. 

Details:
- A tabbed view to display three screens
  -  Scheduler
    - Display list of charging with assigned vehicle
    - An UI option to toggle scheduling algorithms
  -  Chargers
    -  Show the status of chargers with the rate
    -  User can add new chargers
  -  Vehicles
    -  Display a List of vehicles with battery state

Design Specs:
- MVVM - used the standard design pattern to address the separation of concerns. UI is independent of viewmodel population
Repository—We introduced a repository to fetch and manage data. Currently, we are supporting a local repository. The entire data fetching is encapsulated in the repository, which can be switched to any other data source as and when required.
- ScheduleManager - to manage different scheduler strategies

- Design patterns
- Strategy - The strategy design pattern is implemented to switch between different scheduling algorithms. Allowing users to switch or add new strategies to improve scheduling

- Unit tests
  -  Added essential tests to ensure the quality of scheduling algorithms
     
- Installations instructions
  - git clone git@github.com:rahul-rn/FleetScheduler
  - Open the project in Android Studio and press on Run
    
- Note
  - Fixed all lint warnings except locale-related warnings
  - Newly added chargers or trucks are not added to scheduling algorithms
  - Switching between strategies is reflected on the second charger as the first charger queue is unchanged
  - Local caching is not implemented; all data is available in memory
  - As UI is simlle used default xml layout, optimization will be to use jetpack compose to make complex UI

