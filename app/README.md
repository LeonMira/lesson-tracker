## Beyond the course material

Some parts of this project go beyond what was covered in class. I added them
after researching (mainly through AI-powered 
tools like Claude), and each one solves a specific
problem in the app.

### Foreign keys and cascading deletes (Room)

- **What:** `Topic` and `Session` declare a `ForeignKey` to `Subject`, with
  an index on the foreign key column.
- **Why:** with `onDelete = CASCADE`, deleting a subject automatically
  deletes its topics and sessions. This keeps the database consistent and
  avoids writing manual cleanup code that could leave orphan rows behind.
- **Related design choice:** `Topic.sessionId` is nullable and uses
  `SET_NULL`. A topic with no session is pending, and deleting a session
  returns its topics to that state instead of deleting them.
- **Source:** Android Developers, Room documentation ("Define relationships
  between objects").

## Use of AI tools

I used Claude (Anthropic) as a study and review assistant during this
project. Specifically, it helped me to:

- understand the course concepts (state, lists, navigation, architecture,
  coroutines, persistence) using the provided sample projects as reference;
- plan the project structure and the data model;
- learn the Room foreign key syntax, which is outside the course material;
- review my code and correct mistakes.

I wrote and tested the code myself, and I can more or less 
explain every part of it.