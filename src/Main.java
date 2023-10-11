import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final String inputPath = "src/input";
        final String outputPath = "src/output";

        IOManager ioManager = new IOManager(inputPath, outputPath, false);

        String[][] inputs = ioManager.readInputs();
        ioManager.writeOutputs(inputs);
    }

    public static String solve(String s) {
        char a = s.charAt(0);
        char b = s.charAt(1);

        if(a == 'S' && b == 'S') return "S";
        if(a == 'S' && b == 'R') return "R";
        if(a == 'S' && b == 'P') return "S";
        if(a == 'R' && b == 'S') return "R";
        if(a == 'R' && b == 'R') return "R";
        if(a == 'R' && b == 'P') return "P";
        if(a == 'P' && b == 'S') return "S";
        if(a == 'P' && b == 'R') return "P";
        if(a == 'P' && b == 'P') return "P";

        return "0";
    }
}