# Concurrent-Java
Test application for learning about concurrency in Java.

Application will be multi-threaded and handle multiple clients on a single port. All threads will deliver information to be stored in an in-memory
data structure that is designed to handle concurrency.

1. Application will store all unique data given by clients, keeping track of the number of unique values and number of duplicates given. 
2. All unique values will also be written to a log.
3. Periodically the number of unique values and number of duplicates received will also be logged.
