import sfBugsNew.*;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class Bug1392 {
    static enum TrafficLight {
        RED, YELLOW, GREEN;
        public static final TrafficLight GO = GREEN;
    }
    @NoWarning("SF_SWITCH_NO_DEFAULT")
    public void allCovered(TrafficLight light) {
        switch (light) {
            case RED:
                System.out.println("red");
                break;
            case YELLOW:
                System.out.println("yellow");
                break;
            case GREEN:
                System.out.println("green");
                break;
        }
    }
    @ExpectWarning("SF_SWITCH_NO_DEFAULT")
    public void notAllCovered(TrafficLight light) {
        switch (light) {
        case YELLOW:
            System.out.println("yellow");
            break;
        case GREEN:
            System.out.println("green");
            break;
        }
    }
}