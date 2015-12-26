# PhoneBuzz
A tiny web application using Twilio services

The project consists of three parts, each one is more progressively complicated than the previous one and have more functionalities.

Part one is a simple server to which you can point a twilio phone number at and play Fizz Buzz game.

Part two adds a web interface to it.

Part three adds a delay functionality to the previous part.

The most convenient way to build the classes is probably import them inside a project in eclipse IDE and build them from there. If using eclipse IDE, you possibly need to configure some library access rules for the programs to compile. To configure the library access rules, follow the steps below:

1. Go to the project’s properties(by right clicking on the project and choose “Properties”)
2. Go to Java Build Path shown on the left and expand “JRE System Library” 
3. Double click “Access rules” that is right below “JRE System Library”
4. Click “Add”, select “Resolution” to be “Accessible”
5. Enter “com/sun/net/httpserver/**” in the “Rule Pattern” box
6. Click “OK”

Afterwards you can run the programs as normal.
