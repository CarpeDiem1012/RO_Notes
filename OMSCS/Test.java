import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a Fahrenheit value and the day of the week: ");
        int fahrenheit = input.nextInt();
        String day = input.nextLine().trim();
        double celsius = (5.0/9) * (fahrenheit - 32);
        System.out.println(day + " Fahrenheit: " + fahrenheit);
        System.out.println(day + " Celsius: " + celsius);
        // DecimalFormatDemo.test();
    }

    // public static void main(String[] args) {
    //     System.out.printf("%.2f\n", 10.7231);
    //     System.out.printf("%5.2f\n", 10.7231);
    //     System.out.printf("%6.2f", 10.7231);
    // }
}
