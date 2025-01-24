import java.util.Scanner;

public class Calculator{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);    
        System.out.println("List of operations: add subtract multiply divide alphabetize");
        System.out.println("Enter an operation:");
        String operation = input.next().toLowerCase();

        switch (operation) {
            case "add":
                System.out.println("Enter two integers:");
                int add_1, add_2;

                // input type check
                if (input.hasNextInt()) {
                    add_1 = input.nextInt();
                }
                else{
                    System.out.println("Invalid input entered. Terminating...");
                    return;   
                }
                if (input.hasNextInt()) {
                    add_2 = input.nextInt();
                }
                else{
                    System.out.println("Invalid input entered. Terminating...");
                    return;   
                }

                int add = add_1 + add_2;
                System.out.println("Answer: " + add);
                break;
            
            case "subtract":
                System.out.println("Enter two integers:");
                int subtract_1, subtract_2;

                if (input.hasNextInt()) {
                    subtract_1 = input.nextInt();
                }
                else{
                    System.out.println("Invalid input entered. Terminating...");
                    return;    
                }
                if (input.hasNextInt()) {
                    subtract_2 = input.nextInt();
                }
                else{
                    System.out.println("Invalid input entered. Terminating...");
                    return;
                }

                int subtract = subtract_1 - subtract_2;
                System.out.println("Answer: " + subtract);
                break;
            
            case "multiply":
                System.out.println("Enter two doubles:");
                double multiply_1, multiply_2;

                if (input.hasNextDouble()) {
                    multiply_1 = input.nextDouble();
                }
                else{
                    System.out.println("Invalid input entered. Terminating...");
                    return;    
                }
                if (input.hasNextDouble()) {
                    multiply_2 = input.nextDouble();
                }
                else{
                    System.out.println("Invalid input entered. Terminating...");
                    return;
                }

                double multiply = multiply_1 * multiply_2;
                System.out.printf("Answer: %.2f", multiply);
                break;
                    
            case "divide":
                System.out.println("Enter two doubles:");
                double divide_1, divide_2;

                if (input.hasNextDouble()) {
                    divide_1 = input.nextDouble();
                }
                else{
                    System.out.println("Invalid input entered. Terminating...");
                    return;    
                }
                if (input.hasNextDouble()) {
                    divide_2 = input.nextDouble();
                    if (divide_2==0) {
                        System.out.println("Invalid input entered. Terminating...");
                        return;
                    }
                }
                else{
                    System.out.println("Invalid input entered. Terminating...");
                    return;
                }

                double divide = divide_1 / divide_2;
                System.out.printf("Answer: %.2f", divide);
                break;

            case "alphabetize":
                System.out.println("Enter two words:");
                String alpha_1, alpha_2;

                if (input.hasNext()) {
                    alpha_1 = input.next();
                }
                else{
                    System.out.println("Invalid input entered. Terminating...");
                    return;    
                }
                if (input.hasNext()) {
                    alpha_2 = input.next();
                }
                else{
                    System.out.println("Invalid input entered. Terminating...");
                    return;
                }
                if (alpha_1.toLowerCase().compareTo(alpha_2.toLowerCase()) == 0) {
                    // System.out.print(alpha_1.compareTo(alpha_2));
                    System.out.println("Answer: Chicken or Egg.");
                    return;
                }
                if (alpha_1.toLowerCase().compareTo(alpha_2.toLowerCase()) > 0) {
                    // System.out.print(alpha_1.compareTo(alpha_2));
                    System.out.printf("Answer: %s comes before %s alphabetically.\n", alpha_2, alpha_1);
                    return;
                }
                if (alpha_1.toLowerCase().compareTo(alpha_2.toLowerCase()) < 0) {
                    // System.out.print(alpha_1.compareTo(alpha_2));
                    System.out.printf("Answer: %s comes before %s alphabetically.\n", alpha_1, alpha_2);
                    return;
                }

            default:
                System.out.println("Invalid input entered. Terminating...");
        }
    }
}