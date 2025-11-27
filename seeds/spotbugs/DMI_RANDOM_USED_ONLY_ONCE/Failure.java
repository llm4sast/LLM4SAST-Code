import java.util.Random;
public class Failure {
    public static void main(String[] args) {
        Random x = new Random();
        int choice = x.nextInt();
        choice = choice % 2;
        System.out.println(choice);
    }
}