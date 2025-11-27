import javax.persistence.Entity;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Entity
class Wish {
  Long productId;
  Long quantity;
  Client client;
}

@Entity
class Client {
  String clientId;
  String name;
  String password;
}

@Controller
public class PurchaseOrderController {
  @RequestMapping(path = "/saveForLater", method = RequestMethod.POST)
  public String saveForLater(Wish wish, HttpSession session) { // Noncompliant
    session.save(wish);
  }
}
