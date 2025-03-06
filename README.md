# weather-service
A Spring Boot-based weather service that provides the rest endpoints to 
1. Post the weather information: POST: /weather
2. Get the weather information by ID: GET: /weather/<id>
3. Get the weather information with the date (or) city details: GET: /weather

### Commit: repository layer with local HashMap implementation
- Implemented WeatherRepository using ConcurrentHashMap for in-memory data storage  
- Added methods for saving, retrieving, and filtering weather records  
- Included support for querying by date and city  
- Implemented deleteAll() method for clearing data 