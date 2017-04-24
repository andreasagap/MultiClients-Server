# MultiClients-Server

### A project for how to connect multiple clients to one server with socket - Java

**Server:** 
> - main: Create new Thread for each client. Server can receive requests from many clients at the same time. 
> - Handler : In this class is the communication of a client with the server. It contains various options such as    log in, sign up.
> - Email 
> - Account 

**Client:** 
> - main 
> - Client 

**Args:**
> - Client: IP and Port 
> - Server: Port
    
### You can run the programs from different command line, so:

    Server side's command line: java -jar Server.jar 1234

    Client side's command line: java -jar Client.jar localhost 1234
