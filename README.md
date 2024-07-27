# Homework #2: OOP in Java

## Technical Details

To submit the assignment to the contest, you need to upload a compiled Jar file instead of the code. This Jar should also contain a `MANIFEST.MF` file specifying the class with the `main()` method.

Use [this template](https://github.com/atp-mipt/homework-quickstart) to prepare the Maven project. It will help partially automate the code review and avoid common mistakes.

## Task: Russian Checkers

Given the composition of the beginning or middle of a game of Russian checkers, followed by a list of one or more moves. The output should be the final composition or an error message if any of the moves in the list is impossible.

In this implementation, checkers can not only capture but also move backward.

### Types of Error Messages:
- `busy cell` - the target cell is occupied
- `white cell` - the target cell is white (checkers are only placed on black cells and cannot end up on white cells due to the rules)
- `invalid move` - capturing is mandatory in checkers. You must capture the entire chain to the end. An error is displayed if a player has a capture opportunity but instead moves to another cell. If there are multiple capture options, any can be taken.
- `general error` - other errors.

All initial positions are valid.

### Code Requirements
- When writing code, create your own exception classes for errors. Exceptions should be caught at the highest level (the `main()` method) and the corresponding error message should be displayed. Catching checked exceptions “locally” (e.g., `e.printStackTrace`) is prohibited.
- Implement JavaDoc for non-trivial public methods and classes.
- Include at least 5 unit tests.
- (Optional: Create a Checkstyle Rule) It is prohibited to use outdated APIs in Java that have been replaced in newer versions. This primarily applies to `File` and `FileInputStream` (these classes are often used by beginners even though there is Java NIO API), as well as `Date`, `Calendar`, `DateFormat`, `StringBuffer`, `Vector`, and `Hashtable`.
- Parse input data using regular expressions, finite state machines, or something similar. “Naive” parsing using `split`, `indexOf`, `replace` is not encouraged.

### Input Data Format
- Coordinates of white checkers on the board and moves are recorded in standard checkers notation. Move characteristics ("!" - good move, "?" - bad move, etc.) are not used. If a checker is a king, its coordinate is written in uppercase (e.g., not `d4` but `D4`). If multiple captures occur in one move, they are recorded through ":" like this: `e5:c3:a1`.
- Line with the coordinates of white checkers
- Line with the coordinates of black checkers
- List of moves. A pair of moves (white + black) in a line.

### Output Data Format
- Line with the coordinates of white checkers
- Line with the coordinates of black checkers
- Or an error message

#### Example Input Data
a1 a3 b2 c1 c3 d2 e1 e3 f2 g1 g3 h2
a7 b6 b8 c7 d6 d8 e7 f6 f8 g7 h6 h8
g3-f4 f6-e5
c3-d4 e5
b2
d6-c5
d2-c3 g7-f6
h2-g3 h8-g7
c1-b2 f6-g5
g3-h4 g7-f6
f4-e5 f8-g7

#### Example Output Data
a1 a3 b2 c3 d4 e1 e3 e5 f2 g1 h4
a7 b6 b8 c5 c7 d8 e7 f6 g5 g7 h6

Assignment in Russian: https://docs.google.com/document/d/1D6YtyBnX8rPm-3LDMjTsc-2Ig9VbBkQszK9d7zXKEYg/edit
