# Java Input Reader

### Description
This repository contains an input reader implemented in java that can read from files or
folders (ie all files inside a folder). The main purpose of this project is to take over
tedious Input- and Output Management in Coding competitions. This repository can be used 
as a template to build your solution on.\
This template was created specificly for the [CCC](https://codingcontest.org).

Made by **[Philippe Zorman](https://github.com/zor-cell)**, **[Lutu](https://github.com/Lutu-gl)**

### Usage
**src/IOManager.java:** This class takes over the required logic and error handling for inputs and outputs, it should not be changed.  

**src/Main.java:** This class serves as the interface for the user to implement their code.  

**void Main.solve():** This method gets run for every file in the established inputPath. 
A working program should contain the following aspects:
- Read lines of input file with given java Scanner (see [Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html))  
- Compute result for input
- Write result to given java FileWriter (using ```writer.write(result + "\n");```);
**resources/config.xml:** In this file you can make configuration to the IOManager. There exist a few which are described here and in the file

#### config.xml settings:
**inputPath:** Specifies the directory path where the input files are located. For instance, ./input/ means that the application will look for input files in the input directory relative to the application's execution path. (recommended)

**outputPath:** Defines the directory path where the output files should be placed after processing. For example, ./output/ places the output files in the output directory relative to where the application runs. (recommended)

**allowedExtensions:** This allows you to specify which file extensions should be processed by the application. Only files that match the extensions listed will be processed. For example, including .in and .txt means that only files ending with .in and .txt will be considered.

**targetSpecificLevel (Optional):** Specifies a target level. This is specific for CCC. The format '<number>e' indicates the number part and an 'e' (both optional). If 'e' is present only example files are considered. Number -1 represents all levels.

**cleanupOutput (Optional):** A boolean value indicating whether the output directory should be cleaned up (i.e., existing files removed) before writing new output files.

**isDebug (Optional):** Enables or disables debug mode. When set to true, the application will provide additional debug information during its execution.

### TODO
- [x] Support for 2d array inputs
- [x] Support for CCC level detection
- [x] Support for recursive folder search
- [x] Support for specification of file types or names to be used or ignored as input  
- [ ] A Debugger to prettify debug output and show more information with specific data types  
reverse engineering?

