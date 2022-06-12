package mylib;

import java.awt.*;

public class KButton extends KAbstractButton {
    public KButton(String text) {
        super(text);
        this.name="Button";
    }
    public KButton(Color color){
        super(color);
        this.name="Button";
    }
}
