http://localhost:8080/SmartKanteen/admin.html#/
http://localhost:8080/SmartKanteen/rest/service/


D:\apache-maven-3.0.3\apache-maven-3.0.3\bin\mvn

 archetype:generate -DgroupId=com.test.rest -DartifactId=RESTExample -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false



CREATE TABLE MENU_MASTER(itemID INT PRIMARY KEY, itemName VARCHAR(255), description VARCHAR(255), price NUMBER(10,2), prepTime NUMBER(3));

SELECT itemID ,itemName  ,description , price  ,prepTime  FROM MENU_MASTER
INSERT INTO MENU_MASTER VALUES(1,'pizza','veg pizza',50.50,15)

