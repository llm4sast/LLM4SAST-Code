import ghIssues.*;
import java.security.SecureRandom;
import java.util.Random;
public class Issue1464{
    long m1(){ return new SecureRandom().nextLong(); }
    long m2(){ return new Random().nextLong(); }
}