import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class KdTreeGenerator {

    public static void main(String[] args) throws IOException {
    	PrintWriter out = new PrintWriter(new FileWriter("assets/input1000.txt"));
    	
        int n = 1000;
        for (int i = 0; i < n; i++) {
            double x = Math.random();
            double y = Math.random();
            out.printf("%8.6f %8.6f\n", x, y);
        }
        
        out.close();
    }
}
