import java.util.Calendar;
import java.util.Collection;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.core.Authentication;

public class WeakNightVoter implements AccessDecisionVoter {
  @Override
  public int vote(Authentication authentication, Object object, Collection collection) {
    Calendar calendar = Calendar.getInstance();
    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

    if (currentHour >= 8 && currentHour <= 19) {
      return ACCESS_GRANTED;
    }

    return ACCESS_ABSTAIN; // Noncompliant: when users connect during the night, no decision is made
  }
}
