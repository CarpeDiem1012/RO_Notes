public class Dog extends Pet{
    private double droolRate;

    public Dog(String name, double health, int painLevel, double droolRate) {
        super(name, health, painLevel);
        droolRate = droolRate<=0? 0.5:droolRate;
        this.droolRate = droolRate;
    }
    public Dog(String name, double health, int painLevel) {
        this(name, health, painLevel, 5.0);
    }

    public double getDroolRate() {
        return droolRate;
    }

    @Override
    public int treat() {
        int time;
        if (droolRate<3.5) {
            time = (int)((getPainLevel()*2)/getHealth()); // rounded up
        }
        else if (droolRate<=7.5) {
            time = (int)(getPainLevel()/getHealth()); // rounded up
        }
        else {
            time = (int)(getPainLevel()/(getHealth()*2)); // rounded up
        }
        heal();
        return time;
    }

    @Override
    public void speak() {
        super.speak();
        String word="bark ";
        if (getPainLevel()>5) {
            word = word.toUpperCase();
        }
        for (int i=getPainLevel(); i>0 ; i--) {
            System.out.print(word);
        }
        System.out.println("");
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        if (o instanceof Dog) {
            Dog another = (Dog)o;
            return another.droolRate==this.droolRate;
        }
        return false;
    }
}