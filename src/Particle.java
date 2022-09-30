public class Particle {
    private int xCord;
    private int yCord;
    private boolean isStopped;

    public static double[] probs = new double[5];

    public Particle(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
        this.isStopped = false;
    }

    public int getXCord() {
        return xCord;
    }

    public int getYCord() {
        return yCord;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void move() {
        double rand = Math.random();

        if (rand < probs[0]) {
            this.yCord++;
        } else if (rand < probs[0] + probs[1]) {
            this.yCord--;
        } else if (rand < probs[0] + probs[1] + probs[2]) {
            this.xCord++;
        } else if (rand < probs[0] + probs[1] + probs[2] + probs[3]) {
            this.xCord--;
        } else isStopped = true;
    }
}
