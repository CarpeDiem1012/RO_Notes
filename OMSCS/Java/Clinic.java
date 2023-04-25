import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Clinic {
    private File patientFile;
    private int day;

    public Clinic(File file) {
        patientFile = file;
        day = 1;
    }
    public Clinic(String fileName) {
        this(new File(fileName));
    }

    public String nextDay(File f) throws FileNotFoundException {
        // File Input
        day ++;
        Scanner fileIn = new Scanner(f);
        Scanner input = new Scanner(System.in);
        String[] dataIn;
        String output = "";

        while (fileIn.hasNextLine()) {
            String lineIn = fileIn.nextLine();
            dataIn = lineIn.split(",");

            String name = dataIn[0];
            String type = dataIn[1];
            String feature = dataIn[2];
            String timeIn = dataIn[3];
    
            if (!type.equals("Dog") && !type.equals("Cat")) {
                throw new InvalidPetException();
            }
            
            // User Input
            System.out.println(String.format("Consultation for %s the %s at %s.\nWhat is the health of %s?", name, type, timeIn, name));
            double health;
            int painLevel;
            while (true) {
                if (input.hasNextDouble()) {
                    health = input.nextDouble();
                    input.nextLine();
                    break;
                }
                else {
                    System.out.println("Please enter a number");
                    input.nextLine();
                }
            }
            System.out.println(String.format("On a scale of 1 to 10, how much pain is %s in right now?", name));
            while (true) {
                if (input.hasNextInt()) {
                    painLevel = input.nextInt();
                    input.nextLine();
                    break;
                }
                else {
                    System.out.println("Please enter a number");
                    input.nextLine();
                }
            }
    
            // Create Pet Object
            Pet pet;
            switch (type) {
                case "Dog":
                    pet = new Dog(name, health, painLevel, Double.parseDouble(feature));
                    break;
                case "Cat":
                    pet = new Cat(name, health, painLevel, Integer.parseInt(feature));
                    break;
                default:
                    throw new InvalidPetException();
            }
            pet.speak();
            int time = pet.treat();
            String timeOut = addTime(timeIn, time);
            output += String.format("%s,%s,%s,Day %d,%s,%s,%f,%d\n", name, type, feature, day, timeIn, timeOut, health, painLevel);
        }

        input.close();
        fileIn.close();
        return output;
    }

    public String nextDay(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        return this.nextDay(f);
    }

    public boolean addToFile(String patientInfo) {
        Scanner fileIn;
        PrintWriter fileOut;
        String stringIn;
        String stringOut = "";
        String[] arrayIn;

        // Parse the string patientInfo
        // [name, type, feature, day, timeIn, timeOut, health, painLevel]
        String name = patientInfo.split(",")[0];
        boolean newPet = true;
        try {
            fileIn = new Scanner(patientFile);
            while (fileIn.hasNextLine()) {
                stringIn = fileIn.nextLine();
                arrayIn  = stringIn.split(",");
                if (arrayIn[0].equals(name)) {
                    newPet = false;
                    String[] info =  patientInfo.split(",");
                    stringOut += stringIn;
                    for (int i=3;i<info.length;i++) {
                        stringOut += "," + info[i];
                    }
                    stringOut += "\n";
                }
                else {
                    stringOut += stringIn + "\n";
                }
            }
            if (newPet) {
                stringOut += patientInfo + "\n";
            }
            fileIn.close();
            fileOut = new PrintWriter(patientFile);
            fileOut.print(stringOut);
            fileOut.close();
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private String addTime(String timeIn, int treatmentTime) {
        int hour = Integer.parseInt(timeIn.substring(0,2));
        int minute = Integer.parseInt(timeIn.substring(2));

        hour = (hour + (minute + treatmentTime)/60)%24;
        minute = (minute + treatmentTime)%60;

        String timeOut = String.format("%2d%02d", hour, minute);
        return timeOut;
    }
}