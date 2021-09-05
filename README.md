# hanoi-tower-cli🧮🧠
This is a simulation of the mathematical puzzle "Tower of Hanoi" in command line interface, made with Java 16. I could choose to use a more OOP structure, but I did it like this to understand more the bidimensional arrays.

# What is the Tower of Hanoi?🔍
The Tower of Hanoi is a mathematical puzzle. It consists of 3 rods and a number of disks of different diameters, which can slide onto any rod. It starts with the disks stacked on the 1st rod in order of decreasing size, the smallest at the top.

The aim is to move all the disks to the 3rd rod, but without putting a bigger disk on a smaller one and only moving one disk at a time.
To extend this information and understand how to solve it, check out <a href="https://en.wikipedia.org/wiki/Tower_of_Hanoi">its entry on Wikipedia</a>.

# Instructions📄
- ❗⚠️IMPORTANT⚠️❗ You should use a terminal that supports colorized ASCII characters, so you will see the prints colorized and all will be more readable!

- To execute it <a href="https://adoptium.net/?variant=openjdk16&jvmVariant=hotspot">get Java☕</a> - build 16 or newer version. Then download the game <a href="https://github.com/ericmp33/hanoi-tower-cli/raw/main/out/artifacts/hanoi_tower_cli_jar/hanoi-tower-cli.jar">here</a>. Execute the file via terminal inputting `java -jar ./hanoi-tower-cli.jar`, where `.` is the current folder containing the `jar`.

- When the game starts, you'll be asked about the number of disks. The minimum number of disks to be able to play is 3, and I did set a maximum of 9 disks in this case. The more disks the tower has, the more difficult it is to game over.

- While solving it, move the disks following the designed way to do it; Inputting only 2 numbers -> `1`, `2` or `3`, not repeated and without other characters.

For example:

> Inputting `12` means to move the top disk of column 1 to column 2

> Inputting `13` means to move the top disk of column 1 to column 3

> And so on.
