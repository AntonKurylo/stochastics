import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final Scanner scan = new Scanner(System.in);
    private static int numberOfParticles;
    private static int x;
    private static int y;
    private static int M;
    private static int N;
    private static final Map<String, Integer> outputResult = setOutputResult();

    public static void main(String[] args) {
        System.out.println("\nSimulation of random particle movement.");

        try {
            while (numberOfParticles == 0) {
                System.out.print("\nEnter number of particles: ");
                numberOfParticles = scan.nextInt();
            }
        } catch (InputMismatchException ex) {
            System.err.println("\nInput only Integer value.");
            System.exit(-1);
        }

        setRectangleCoordinates();
        setStartCoordinates();
        setProbs();
        simulationOfMovement();
        printResult();
    }

    private static Map<String, Integer> setOutputResult() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("North", 0);
        map.put("South", 0);
        map.put("East", 0);
        map.put("West", 0);
        map.put("Stopped", 0);

        return map;
    }

    public static void printResult() {
        System.out.println("\n________________________________\n");
        System.out.println("Results of random particle movement.");
        System.out.println("\nAmount:");

        AtomicInteger atomicInteger = new AtomicInteger(1);

        for (Map.Entry<String, Integer> entry : outputResult.entrySet()) {
            System.out.println("\t" + atomicInteger.getAndIncrement() + ") " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nRatio && Relative Error:");

        atomicInteger.set(1);
        for (Map.Entry<String, Integer> entry : outputResult.entrySet()) {
            double q = (double) entry.getValue() / numberOfParticles;
            BigDecimal u = BigDecimal.valueOf(Math.sqrt(q * (1 - q) / numberOfParticles)).setScale(3, RoundingMode.HALF_UP);
            System.out.println("\t" + atomicInteger.getAndIncrement() + ") Q_" + entry.getKey() + ": " + q);
            System.out.println("\t   Uns_" + entry.getKey() + ": " + u);
        }
    }

    public static void setProbs() {
        double[] probs = new double[5];

        while (true) {
            System.out.println("\nEnter probabilities: ");

            try {
                for (int i = 0; i < probs.length - 1; i++) {
                    System.out.printf("p%d = ", i + 1);
                    probs[i] = scan.nextDouble();
                }

                System.out.print("pa = ");
                probs[probs.length - 1] = scan.nextDouble();
            } catch (InputMismatchException ex) {
                System.err.println("\nInput only Float numbers.");
                System.exit(-1);
            }

            if (Arrays.stream(probs).sum() != 1) {
                System.err.println("\nTotal probability should be = 1. Try again!\n");
            } else {
                break;
            }
        }

        Particle.probs = probs;
    }

    public static void setRectangleCoordinates() {
        while (true) {
            try {
                System.out.println("\nRectangle square lines:");
                System.out.print("Enter N lines: ");
                N = scan.nextInt();
                System.out.print("Enter M lines: ");
                M = scan.nextInt();
            } catch (InputMismatchException ex) {
                System.err.println("\nInput only Integer numbers.");
                System.exit(-1);
            }

            if (N <= 0 || M <= 0) {
                System.out.println("\nShould be N > 0 && M > 0.\n");
            } else {
                break;
            }
        }
    }

    public static void setStartCoordinates() {
        while (true) {
            System.out.println("\nEnter the starting of the particle trajectory:");

            try {
                System.out.print("Enter x coordinate: ");
                x = scan.nextInt();
                System.out.print("Enter y coordinate: ");
                y = scan.nextInt();
            } catch (InputMismatchException ex) {
                System.err.println("\n Input only Integer numbers.");
                System.exit(-1);
            }

            if (!(x >= 0 && x <= N) || !(y >= 0 && y <= M)) {
                System.out.printf("\n Should be (x >= 0 AND x <= %d) && (y >= 0 AND y <= %d).\n\n", N, M);
            } else {
                break;
            }
        }
    }

    public static void simulationOfMovement() {
        for (int i = 0; i < numberOfParticles; i++) {
            Particle point = new Particle(x, y);

            while (point.getXCord() < N && point.getXCord() > 0 && point.getYCord() < M && point.getYCord() > 0 && !point.isStopped()) {
                point.move();
            }

            boolean isStopped = point.isStopped();

            if (isStopped) {
                outputResult.computeIfPresent("Stopped", (key, value) -> value + 1);
            } else {
                int pointX = point.getXCord();
                int pointY = point.getYCord();

                if (pointX == N) {
                    outputResult.computeIfPresent("East", (key, value) -> value + 1);
                } else if (pointX == 0) {
                    outputResult.computeIfPresent("West", (key, value) -> value + 1);
                } else if (pointY == M) {
                    outputResult.computeIfPresent("North", (key, value) -> value + 1);
                } else if (pointY == 0) {
                    outputResult.computeIfPresent("South", (key, value) -> value + 1);
                }
            }
        }
    }
}
