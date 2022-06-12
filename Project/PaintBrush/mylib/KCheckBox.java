package mylib;

import java.awt.*;
import java.awt.event.MouseEvent;

public class KCheckBox extends KAbstractButton{
    boolean isClicked=false;
    public KCheckBox(String text) {
        super(text);
        this.name="CheckBox";
    }
    @Override
    public void Click(MouseEvent e){
        isClicked= !isClicked;
        super.Click(e);
    }

    @Override
    public void draw(Graphics g){
        if(isClicked){
            g.setColor(Color.green);
            g.fillRect(this.x, this.y, this.width, this.height);
        }
        g.setColor(new Color(212,212,255));
        g.drawRect(this.x, this.y, this.width, this.height);
        g.setColor(Color.black);
        g.drawString(this.text, this.x+15, this.y+10);
    }
}
