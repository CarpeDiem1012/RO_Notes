public class Frog {
    private String name;
    private int age;
    private double tongueSpeed;
    private boolean isFroglet; // 1 < age < 7
    private static String species = "Rare Pepe";

    public Frog(String name, int age, double tongueSpeed) {
        this.name = name;
        this.age = age;
        this.tongueSpeed = tongueSpeed;
        this.isFroglet = 1<age && age<7;
    }

    public Frog(String name, double ageInYears, double tongueSpeed) {
        this(name, (int)(ageInYears*12), tongueSpeed);
    }

    public Frog(String name) {
        this(name, 5, 5.0);
    }

    public void grow(int ageAdd) {
        int ageTemp = age + ageAdd;
        isFroglet = 1<ageTemp && ageTemp<7;

        if (age<12) {
            if (ageTemp<=12) {
                tongueSpeed += ageAdd;
            }
            else if (ageTemp<30) {
                tongueSpeed += (12-age);
            }
            else {
                tongueSpeed += (12-age);
                tongueSpeed -= (ageTemp-30);
            }
        }
        else if (age>=30) {
            tongueSpeed -= ageAdd;
        }
        else if (ageTemp>=30) {
                tongueSpeed -= (ageTemp-30);
        }
        tongueSpeed = tongueSpeed<5.0? 5.0:tongueSpeed;
        age = ageTemp;
    }

    public void grow() {
        this.grow(1);
    }

    public void eat(Fly fly) {
        if (fly.isDead()) return;
        if (tongueSpeed>fly.getSpeed()) {
            fly.setMass(0);
            if (fly.getMass()>=0.5*age) {
                this.grow(1);
            }
        }
        else {
            fly.grow(1);
        }
    }

    public String toString() {
        if (isFroglet) {
            return String.format("My name is %s and I'm a rare froglet! I'm %d months old and my tongue has a speed of %.2f.", name, age, tongueSpeed);
        }
        else {
            return String.format("My name is %s and I'm a rare frog. I'm %d months old and my tongue has a speed of %.2f.", name, age, tongueSpeed);
        }
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String newSpecies) {
        species = newSpecies;
    }
}