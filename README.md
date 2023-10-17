# Java Input Reader

### Description
This repository contains an input reader implemented in java that can read from files or
folders (ie all files inside a folder). The main purpose of this project is to take over
tedious Input- and Output Management in Coding competitions. This repository can be used 
as a template to build your solution on.  
Made by **Philippe Zorman**

### Usage
**IOManager.java:** This class takes over the required logic and error handling for inputs and outputs, it should not be changed.  
**Main.java:** This class serves as the interface for the user to implement their code.  
**void Main.solve():** This method gets run for every file in the established inputPath. 
A working program should contain the following aspects:
- Read lines of input file with given java Scanner (see [Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html))  
- Compute result for input
- Write result to given java FileWriter (using ```writer.write(result + "\n");```);

### TODO
- [x] Support for 2d array inputs
- [ ] Support for CCC level detection
- [ ] Support for recursive folder search (just one level at the moment)  
- [ ] Support for specification of file types or names to be used or ignored as input  
- [ ] A Debugger to prettify debug output and show more information with specific data types  
reverse engineering?

