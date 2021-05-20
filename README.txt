=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: olz
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Collections - I used array lists to store the main components of the game including the rectangle columns, poisons, and potions. Collections was an appropriate choice in this case since I have to be able to easily remove and add to the number of columns/poisons/potions at each timestep.

  2. Inheritance - Poison and Potion extend the GameObj class and inherit the shared functions of the game objects. Since both poison and potion share similar structure and functions, having both of them extend one class was useful to eliminate repeated code that would have had to be written otherwise. Poison and Potion each do differ in how they are drawn and how they change the state of the cat.

  3. IO - I use IO to read and write to a file that stores the players and their scores in the game. IO makes sense to implement the leaderboard because I need to store the data somewhere outside of each instance of the game. Thus, reading from a txt file allows me to then output the leaderboard to the screen, and after each play of the game, I update the file.

  4. JUnit Testing - I used JUnit testing to test the functions of cat and the state of the game. If the cat intersected a poison or potion, the state of the cat (including its score and invincibility should change). Additionally, I tested to see if the cat intersecting a rectangle would change the state of the game to be over.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

Cat - a class that stands in place for each player. At each instance of a game, a new cat, or player, is created. A cat has a score and invincibility status that can be updated or changed during the game. Additionally, update() is a method that allow the cat to be affected by a constant representing gravity at each timestep, and jump() allows the cat to fly up when the player presses space.

Poison - a new subtype of game object that makes the cat invincible and increases score if the cat intersects it.

Potion - a new subtype of game object that decreases the cat's score if the cat intersects it.

GameCourt - The class that holds most of the logic of the game and how objects should interact with each other at each timestep. During each timestep, I update the movements of the columns/poisons/potions to simulate an animated scrolling effect, and I check for changes to the state of the game. This class also updates the painting of the component.

Game - The higher level class that deals with the GUI of the game and allows you to run the game

Leaderboard - A class that handles the reading of the LEADERBOARD.txt file, extracts the scores from each string in order to rank the players, and also concatenates each string into a big string to display the leaderboard to the screen.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

It was hard for me to figure out how to design my game with a testable component and also to do the IO portion. For IO, I tried multiple different strategies, including storing the entries as an ArrayList of TreeMaps, before getting the one I have now.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I use getters and setters to not break encapsulation, and I think my overall design does separate functionality accordingly. If given the chance, I would consider creating a User class that contains a cat, score, and name rather than trying to store all the information in the cat class and using strings. This makes more sense internally and would allow me to display the leaderboard more easily, without having to extract integers from strings.

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.

I used images from Google for my cat, poison, and potion classes. 

