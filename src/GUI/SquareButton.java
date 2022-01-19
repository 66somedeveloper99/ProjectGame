package GUI;

import javax.swing.*;
import java.awt.*;

class SquareButton extends JButton {

    SquareButton(String s) { super(s); }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        int s = (int) (d.getWidth() < d.getHeight() ? d.getHeight() : d.getWidth());
        return new Dimension(s, s);
    }

}