import java.util.Arrays;

public class BlueAstronaut extends Player implements Crewmate {
    private int numTasks;
    private int taskSpeed; // positive and nonzero
    
    public BlueAstronaut(String name, int susLevel, int numTasks, int taskSpeed) {
        super(name, susLevel);
        this.numTasks = numTasks;
        this.taskSpeed = taskSpeed;
    }

    public BlueAstronaut(String name) {
        this(name, 15, 6, 10);
    }

    @Override
    public void emergencyMeeting() {
        if (isFrozen()) {
            return;
        }

        Player[] players = getPlayers();
        Arrays.sort(players); // ascending order
        for (int i=players.length-1; i>=0 ; i--) {
            // frozen players cannot be accused
            if (players[i].isFrozen()) {
                continue;
            }

            // two players have highest susLevel
            if (i>0 && players[i].getSusLevel()==players[i-1].getSusLevel()) {
                return;
            }

            players[i].setFrozen(true);
            break;
        }
        boolean gameOver = gameOver();
    }

    @Override
    public void completeTask() {
        if (this.isFrozen()) {
            return;
        }

        if (taskSpeed>20) {
            numTasks -= 2;
        }
        else if (taskSpeed>0) {
            numTasks -= 1;
        }

        if (numTasks<0) {
            numTasks=0;
        }

        if (numTasks==0 && taskSpeed>0) {
            System.out.println("I have completed all my tasks");
            setSusLevel((int)(0.5*getSusLevel()));
            taskSpeed=-1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BlueAstronaut) {
            BlueAstronaut anotherBlue = (BlueAstronaut) o;
            return super.equals(anotherBlue) && this.numTasks==anotherBlue.numTasks && this.taskSpeed==anotherBlue.taskSpeed;
        }
        return false;
    }

    @Override
    public String toString() {
        String call = super.toString() + " I have " + numTasks + " left over.";
        if (getSusLevel()>15) {
            call = call.toUpperCase();
        }
        return call;
    }

    public int getNumTasks() {
        return numTasks;
    }
    public void setNumTasks(int numTasks) {
        this.numTasks = numTasks;
    }
    public int getTaskSpeed() {
        return taskSpeed;
    }
    public void setTaskSpeed(int taskSpeed) {
        this.taskSpeed = taskSpeed;
    }
}
