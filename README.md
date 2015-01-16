# Comway's Game Of Life in JAVA

An attempt to model the comway's game of life.

The "game" is a zero-player game, meaning that its evolution is determined by its initial state, requiring no further input. One interacts with the Game of Life by creating an initial configuration and observing how it evolves or, for advanced players, by creating patterns with particular properties.

Rules of the game:
-  Any live cell with fewer than two live neighbours dies, as if caused by under-population.
-  Any live cell with two or three live neighbours lives on to the next generation.
-  Any live cell with more than three live neighbours dies, as if by overcrowding. 
-  Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

How to run ?
- Compile the code and export as run time executable jar
- Run java -jar <jarname>.jar in cmd prompt
- Enter the generation count you want
- Select one of the patterns
  * 0: 10 Cell Input
  * 1: Glider
  * 2: Exploder
  * 3: Blinker
  * 4: Lightweight Spaceship


Future improvements
- Improve algo
- Improve UI
- Add capability of custom pattern input


