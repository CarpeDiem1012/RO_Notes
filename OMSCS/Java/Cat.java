public class Cat extends Pet {
    private int miceCaught;

    public Cat(String name, double health, int painLevel, int miceCaught) {
        super(name, health, painLevel);
        miceCaught = miceCaught<0? 0:miceCaught;
        this.miceCaught = miceCaught;
    }
    public Cat(String name, double health, int painLevel) {
        this(name, health, painLevel, 0);
    }

    public int getMiceCaught() {
        return miceCaught;
    }

    @Override
    public int treat() {
        int time;
        if (miceCaught<4) {
            time = (int)((getPainLevel()*2)/getHealth());
        }
        else if (miceCaught<=7) {
            time = (int)(getPainLevel()/getHealth());
        }
        else {
            time = (int)((getPainLevel()/(getHealth()*2)));
        }
        heal();
        return time;
    }

    @Override
    public void speak() {
        super.speak();
        String word="meow ";
        if (getPainLevel()>5) {
            word = word.toUpperCase();
        }
        for (int i=getMiceCaught(); i>0 ; i--) {
            System.out.print(word);
        }
        System.out.println("");
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        if (o instanceof Cat) {
            Cat another = (Cat)o;
            return another.miceCaught==this.miceCaught;
        }
        return false;
    }
}