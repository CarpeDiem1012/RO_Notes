import java.util.Arrays;

public class RedAstronaut extends Player implements Impostor {
    private String skill;

    public RedAstronaut(String name, int susLevel, String skill) {
        super(name, susLevel);
        this.skill = skill.toLowerCase();
    }

    public RedAstronaut(String name) {
        this(name, 15, "experienced");
    }

    @Override
    public void emergencyMeeting() {
        if (isFrozen()) {
            return;
        }

        Player[] players = getPlayers();
        Arrays.sort(players); // ascending order
        for (Player p : players) {
            System.out.println(p.getName()); // BUG:
        }
        for (int i=players.length-1; i>=0 ; i--) {
            // 1. this imposter cannot be accused
            // 2. frozen players cannot be accused
            if (players[i] == this || players[i].isFrozen()) {
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
    public void freeze(Player player) {
        if (this.isFrozen() || player.isFrozen() || player instanceof Impostor) {
            return;
        }

        if (this.compareTo(player) < 0) {
            player.setFrozen(true);
        }
        else {
            setSusLevel(2*getSusLevel());
        }

        boolean gameOver = gameOver();
    }

    @Override
    public void sabotage(Player player) {
        if (this.isFrozen() || player.isFrozen() || player instanceof Impostor) {
            return;
        }

        if (getSusLevel()<20) {
            player.setSusLevel((int)(1.5*player.getSusLevel()));
        }
        else {
            player.setSusLevel((int)(1.25*player.getSusLevel()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RedAstronaut) {
            RedAstronaut anotherRed = (RedAstronaut) o;
            return super.equals(anotherRed) && this.skill.equals(anotherRed.skill);
        }
        return false;
    }

    @Override
    public String toString() {
        String call = super.toString() + " I am an " + skill + " player!";
        if (getSusLevel()>15) {
            call = call.toUpperCase();
        }
        return call;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill.toLowerCase();
    }
}