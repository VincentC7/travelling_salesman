package GUI;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FocusListenerMutator implements FocusListener {

    private String actualValue;
    private int roundingDigit;

    public FocusListenerMutator(int roundingDigit){
        this.roundingDigit = roundingDigit;
    }


    @Override
    public void focusGained(FocusEvent e) {
        actualValue = ((JTextField)e.getSource()).getText();
    }

    @Override
    public void focusLost(FocusEvent e) {

        JTextField j = (JTextField)e.getSource();
        if(isDouble(j.getText())){
            actualValue = j.getText();

        }
        else{
            j.setText(actualValue);
        }
    }

    private boolean isDouble(String value) {
        try {
            double d = Double.parseDouble(value);

            return (value.length() <= roundingDigit+2 &&  d >= 0 && d <= 1 );
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
