> **Note**
> I'm no longer working on this project. 
> This was a lesson on thread pools and tcp connections.

# MyIRC - Java Chat Server

MyIRC is a Java-based chat server that allows multiple clients to connect to a server and communicate with each other in real-time. 

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- Multiple clients can connect to the server
- Users can send messages to all connected clients
- Users can send private messages to specific clients
- Users can see a list of all connected clients
- Server uses a thread pool to manage multiple clients
- Simple command-line interface

## Technologies Used

- Java 8

## Getting Started

To get started with MyIRC, you'll need to clone the repository and run the `ChatServer` class. 

```
git clone https://github.com/pedroascenso/my-irc.git
cd my-irc/src
javac -cp . silva/ascenso/pedro/*.java
java -cp . silva.ascenso.pedro.ChatServer
```

## Usage

Once the server is running, you can connect to it using a TCP client such as Telnet or PuTTY. Connect to the server using the IP address and port number of the machine running the server.

```
telnet localhost 6666
```

Once connected, you can start sending messages to other clients on the server.

### Commands

- `/help` - Displays a list of all available commands.
- `/users` - Displays a list of all connected clients.
- `/whisper <username> <message>` - Sends a private message to a specific user.
- `/username`- Prompts the user to enter a new username.
- `/quit`- Closes the client session and disconnects from the server.

## Contributing

Contributions to MyIRC are always welcome! If you find a bug or want to suggest an enhancement, please submit an issue. If you'd like to contribute code, please fork the repository and create a pull request.

## License

MyIRC is licensed under the MIT License. See [LICENSE](https://opensource.org/licenses/MIT) for details.
