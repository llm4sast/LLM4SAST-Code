import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import org.w3c.dom.NodeList;

public class XMLSignatureValidator {
  public boolean validateSignature(org.w3c.dom.Document doc) throws Exception {
    NodeList signatureElement = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
    XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
    DOMValidateContext valContext =
        new DOMValidateContext(new KeyValueKeySelector(), signatureElement.item(0));
    XMLSignature signature = fac.unmarshalXMLSignature(valContext);
    return signature.validate(valContext);
  }

  private static class KeyValueKeySelector extends KeySelector {
    @Override
    public KeySelectorResult select(
        KeyInfo keyInfo, Purpose purpose, AlgorithmMethod method, XMLCryptoContext context)
        throws KeySelectorException {
      return null;
    }
  }
}
