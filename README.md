# Java Input Reader

### Description
This repository contains an input reader implemented in java that can read from files or
folders (ie all files inside a folder). The main purpose of this project is to take over
tedious Input- and Output Management in Coding competitions. This repository can be used 
as a template to build your solution on.  
Made by **Philippe Zorman**

### Usage
**IOManager.java:** This class takes over the required logic and error handling for inputs and outpus, it should not be changed.  
**Main.java:** This class serves as the interface for the user to implement their code. The code of all methods can be changed, unless comments say otherwise.  
**String[] Main.getLines():** This function is responsible for parsing each case of a file of test cases. As the input format varies for every assignment, the user can implement 
the required format.  
**T Main.solve():** This is the function where the code for the required algorithm belongs. The return type **T** is converted with **.toString()** for the output file. The given parameter is 
one test case line, note that the line may need to be seperated into different inputs.  
Input test cases passed as a parameter can look like the following:
```
aaabaa
23 3 abc
5 abcde
```

### TODO
- [ ] Support for recursive folder search (just one level at the moment)  
- [ ] Support for specification of file types or names to be used or ignored as input  
- [ ] A Debugger to prettify debug output and show more information with specific data types  
reverse engineering?

