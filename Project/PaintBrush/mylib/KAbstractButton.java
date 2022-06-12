package mylib;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public abstract class KAbstractButton extends KComponent{
    protected Color color;
    public KAbstractButton(String text){
        this.text=text;
        color=null;
    }
    public KAbstractButton(Color color){
        this.color=color;
    }
    String actionCommand;
    KActionListener actionListener;

    @Override
    public void Click(MouseEvent e) {
        if(actionListener!=null) {
            actionListener.actionPerformed(new ActionEvent(this, e.getID(), this.actionCommand));
            e.getComponent().repaint();
        }
    }
    @Override
    public void draw(Graphics g){
        if(color==null) {
            g.setColor(new Color(212,212,255));
            g.fillRoundRect(this.x, this.y, this.width, this.height,20,20);
            g.setColor(Color.black);
            g.drawString(this.text, this.x+5, this.y+17);
        }else{
            g.setColor(color);
            g.fillRoundRect(this.x, this.y, this.width, this.height,20,20);
            g.setColor(Color.black);
        }
    }
    public void setActionCommand(String actionCommand){
        this.actionCommand=actionCommand;
    }
    public String getActionCommand(){
        return this.actionCommand;
    }
    public void addKActionListener(KActionListener actionListener){
        this.actionListener=actionListener;
    }

}