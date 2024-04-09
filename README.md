### *The foreign exchange application is a simple Java web application made with Spring Boot. Utilizing technologies such as MySQL for data storage, Redis for caching, and Spring Boot with Spring MVC for RESTful API development, the app is created for demonstrative purposes.* 

# CurrencyController:   
Endpoint: /currencies  
Description: Retrieves a list of all currencies supported by the application.

# CurrencyConverterController:  
Endpoint: /currency-converter  
Description: Converts an amount from one currency to another, while implementing rate limiting to prevent abuse.  

# ConversionHistoryController:  
Endpoints:  
/conversion-history/all  
/conversion-history/filter  
/conversion-history/filtered-data  
Description: Provides functionality to view all conversion history, filter conversion history by date or transaction IDs, and display filtered results.  

# CurrencyConverterWebController:  
Endpoint: /  
Description: Serves the index page of the web application.  

# ExchangeRatesController:  
Endpoint: /exchange-rates  
Description: Displays exchange rates between currencies.
