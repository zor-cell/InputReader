# Java Input Reader

### Description
This repository contains an input reader implemented in Java that can read from files or
folders (ie all files inside a folder). The purpose of this project is to take over
tedious input- and output management in coding competitions. This repository can be used 
as a template to build your solution on.\
This template was created specifically for the [CCC](https://codingcontest.org).

Made by **[Philippe Zorman](https://github.com/zor-cell)**, **[Lutu](https://github.com/Lutu-gl)**

### Usage
The application provides an interface in `Solver.java` with two methods, which are both run for 
every file in the configured `inputPath`:

- `solve()`: Computes the result of each testcase. A working program should contain
    the following aspects:
  - Read lines of an input file with a given Java Scanner (see [Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html))
  - Compute result for the input
  - Write the result to an output file with a given Java FileWriter
    
    **Example:**
    ```java
    public static void solve(Scanner reader, FileWriter writer) throws IOException {
        int maxRuns = reader.nextInt();
        reader.nextLine();
    
        for(int run = 0;run < maxRuns;run++) {
            //compute sum of each line
            List<Integer> list = IOUtils.readList(reader, " ", Integer::parseInt);
            int sum = list.stream().mapToInt(Integer::intValue).sum();
    
            writer.write(sum + "\n");
        }
    }
    ```

- `verify()`: Is run after `solve()` to verify the solution of your output specific to [CCC](https://codingcontest.org).
For this to work a file `visualizer.html` has to be present, which can verify results for given inputs.

### Configuration
The application can be configured by changing the `resources/config.xml` file accordingly.
For configuration details, look inside the file.

### Getting Started

**Prerequisites**
- [Java JDK 21 (or newer)](https://www.oracle.com/de/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)

**Run with IntelliJ (Recommended)**

This project is optimized for IntelliJ IDEA.
1.  Open the project in IntelliJ.
2.  Navigate to `src/main/java/solver/Solver.java`.
3.  Click the green **Run** arrow (▶) next to the `main` method.

**Run with Maven (CLI)**

Alternatively, you can run the solution directly from your terminal:
```bash
mvn clean compile exec:java -Dexec.mainClass="solver.Solver"
```