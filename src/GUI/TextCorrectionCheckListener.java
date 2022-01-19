package GUI;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface TextCorrectionCheckListener extends KeyListener
{
    public boolean isCorrect(String text);

    public void OnSuccess(String text);

    public void OnFail(String text);

    public JTextField GetTextField();

    @Override
    default public void keyTyped(KeyEvent e) { }

    @Override
    default public void keyPressed(KeyEvent e) { }

    @Override
    default public void keyReleased(KeyEvent e) {
        String txt = GetTextField().getText();
        if (isCorrect(txt)) OnSuccess(txt);
        else OnFail(txt);
    }
}