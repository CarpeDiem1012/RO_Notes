public abstract class Pet {
    private String name;
    private double health;
    private int painLevel;

    public Pet(String name, double health, int painLevel) {
        health = health>1.0? 1.0:health;
        health = health<0.0? 0.0:health;
        painLevel = painLevel>10? 10:painLevel;
        painLevel = painLevel<1? 1:painLevel;
        this.name = name;
        this.health = health;
        this.painLevel = painLevel;
    }

    public String getName() {
        return name;
    }
    public double getHealth() {
        return health;
    }
    public int getPainLevel() {
        return painLevel;
    }

    public abstract int treat();

    public void speak() {
        String word = "Hello! My name is " + name;
        if (painLevel>5) {
            word = word.toUpperCase();
        }
        System.out.println(word);
    }

    public boolean equals(Object o) {
        if (o instanceof Pet) {
            Pet another = (Pet)o;
            return another.name.equals(this.name);
        }
        return false;
    }

    protected void heal() {
        health = 1.0;
        painLevel = 1;
    }
}