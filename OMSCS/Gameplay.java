public class Gameplay {
    public static void main(String[] args) {
        BlueAstronaut bob = new BlueAstronaut("Bob", 20, 6, 30);
        BlueAstronaut heath = new BlueAstronaut("Heath", 30, 3, 21);
        BlueAstronaut albert = new BlueAstronaut("Albert", 44, 2, 0);
        BlueAstronaut angel = new BlueAstronaut("Angel", 0, 1, 0);
        RedAstronaut liam = new RedAstronaut("Liam", 19, "eXperienCed");
        RedAstronaut suspicousPerson = new RedAstronaut("Suspicious Person", 100, "EXPerT");
        liam.sabotage(bob);
        System.out.println(bob);
        liam.freeze(suspicousPerson);
        System.out.println(suspicousPerson);
        liam.freeze(albert);
        System.out.println(liam);
        System.out.println(albert);
        albert.emergencyMeeting();
        suspicousPerson.emergencyMeeting();
        System.out.println(bob);
        bob.emergencyMeeting();
        System.out.println(suspicousPerson);
        heath.completeTak();
        System.out.println(heath);
        heath.completeTak();
        System.out.println(heath);
        heath.completeTak();
        System.out.println(heath);
        liam.freeze(angel);
        System.out.println(angel);
        System.out.println(liam);
        liam.sabotage(bob);
        System.out.println(bob);
        liam.sabotage(bob);
        System.out.println(bob);
        liam.freeze(bob);
        System.out.println(bob);
        //
        // angel.emergencyMeeting();
        // System.out.println(liam);
        // System.out.println(heath);
        //
        for (int i=0; i<5; i++){
            liam.sabotage(heath);
            System.out.println(heath);
        }
        liam.freeze(heath);
    }
}
