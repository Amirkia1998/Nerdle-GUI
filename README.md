
# Nerdle

**Nerdle** is the numerical version of the popular 
game **Wordle**. The game is based on guessing
randomly generated equations.  
I created 
this game as a final project for my OOP university course.
## Requirements
- Java JDK or JRE   

## Tech Stack

**Language:** Java

**GUI:** Swing

**JavaScript Engine**: Nashorn


## How to Run
Simply double-click the 
Nerdle.jar file or execute the following command:

```bash
java -jar Nerdle.jar
```

## How To Play
A random mathematical equation is generated at the 
start of each round. The equation's length could be 
7, 8, or 9. The equation operators are chosen at random 
from the following operators: +, -, *, /  
You have six chances to correctly guess the equation. 
Your answer will be evaluated at the end of each guess 
(calculation). We have 3 cases to evaluate:   
- If the operator or the number is in the correct location, the box will be colored green. 
- If that operator/number appears in the equation but is in the wrong place, its box will turn yellow. 
- If that operator/number is not present in the equation, the box turns red.
[![P3.jpg](https://i.postimg.cc/BnbKDgg6/P3.jpg)](https://postimg.cc/bZXrf14c) 



Some useful statistics about the players are displayed on the 
game's main page:

[![P12.jpg](https://i.postimg.cc/mkcrkXRf/P12.jpg)](https://postimg.cc/ZvSmDLCV)

When the round begins, a timer will appear on the right down 
side of the page. If the player wishes to continue playing 
the game later, they can do so by clicking the "Play Later" 
button, which stops the timer and returns to the main page.

[![P32.jpg](https://i.postimg.cc/YC7mD7Mv/P32.jpg)](https://postimg.cc/gnMJrfwp)

You can resume playing the game from where you 
left off by clicking the continue button on the main page:

[![P22.jpg](https://i.postimg.cc/NjGjrjQk/P22.jpg)](https://postimg.cc/dLXYbvkh)
## General Information

In order to evaluate two sides of the equation , 
I used JavaScript **eval()** method. With the help of **Nashorn** 
JavaScript Engine , I executed  JavaScript code on JVM.  
It should be noted that the 
Nashorn has been removed from JDK 15 and later.
Nashorn dependencies are added to the POM file.


## Contact 
You can reach me at
amirkia.rafiei@gmail.com
## Authors

- [@Amirkia1998](https://github.com/Amirkia1998)

