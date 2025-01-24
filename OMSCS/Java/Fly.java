public class Fly {
    private double mass;
    private double speed;

    public Fly(double mass, double speed) {
        this.mass = mass;
        this.speed = speed;
    }

    public Fly(double mass) {
        this(mass, 10.0);
    }

    public Fly() {
        this(5.0);
    }

    public double getMass() {
        return mass;
    }
    
    public void setMass(double mass) {
        this.mass = mass;
    }
    
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {

        this.speed = speed;
    }

    public String toString() {
        if (mass==0) {
            return String.format("I'm dead, but I used to be a fly with a speed of %.2f.", speed);
        }
        else {
            return String.format("I'm a speedy fly with %.2f speed and %.2f mass.", speed, mass);
        }
    }

    public void grow(int massAdd) {
        double massTemp = mass + (double)massAdd;
        if (mass<20 && massTemp<20) {
            speed += massAdd;
        }
        else if (mass<20 && massTemp>=20) {
            speed += 20-mass;
            speed -= 0.5*(massTemp-20);
        }
        else {
            speed -= 0.5*massAdd;
        }
        mass = massTemp;
    }

    public boolean isDead() {
        if (mass==0) {
            return true;
        }
        return false;
    }
}
