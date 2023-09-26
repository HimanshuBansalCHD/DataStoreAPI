This sample code contains below concepts:
1. Java Spring Application
2. Gson Mapper
3. Data Compression
4. MongoDB Connection setup
5. How to expose various API endpoints in spring app


Follow the steps below to build and run your application

One Time Setup:
1. install Homebrew
2. brew install maven

Project build and run::
1. mvn clean install
2. mvn spring-boot:run

Details:

Data base storage structure:

There are 4 Collections
1. BankDetailsCollection -> Stores information related to bank account.

'
{
    "objectId": "646f9133fceca31f02ce4550",
    "accountNumber": "22334455",
    "bankName": "MorganStanley"
}
'

2. CustomerDataCollection

'
{
    "name" : "HarryRam",
    "age" : "230",
    "contactNumber" : "667788",
    "email" : "pkm@pkm.com"
}
'

3. FacialFeaturesCollection

'
{
    "objectId": "646dce625e1321656675eeb8",
    "FingerPrintPatternArray": []
}
'

4. FingerPrintPatternCollection

'
{
    "objectId": "6470b72faf5d1156f25df978",
    "FingerPrintPatternId": 44
}
'

Path: Controller -> Processor -> Executor -> Util -> Dao

Entry Point: DatabaseController
Processor: QueryProcessor which uses Executor underneath. It also wraps the response in particular response Type and compress the data if needed.
Executor -> Gats data from different collections based on the API request.
Util -> Contains various java classes for various standard operations.
Dao -> Pure database CRUD operation.

