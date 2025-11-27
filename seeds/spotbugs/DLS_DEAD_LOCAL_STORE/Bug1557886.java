import sfBugs.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class Bug1557886 {
    @NoWarning("BC")
    public void actionPerformed(ActionEvent e) {
        Component c = (Component) e.getSource();
        JPopupMenu jpm = (JPopupMenu) SwingUtilities.getAncestorOfClass(JPopupMenu.class, c);
    }
}