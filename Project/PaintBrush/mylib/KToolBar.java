package mylib;

import java.awt.*;

public class KToolBar extends KContainer{
    int posX = 0;
    public KToolBar(){
        setBounds(0,55,4096,25);
        this.name="ToolBar";
    }
    @Override
    public void add(KComponent c) { // 왼쪽으로 정렬
        if(c.name.equals("CheckBox")){
            c.setBounds(posX+15,62,10,10);
        }else {
            c.setBounds(posX, 55, 70, 25);
        }
        posX += 75;
        super.add(c);
    }
    @Override
    public void draw(Graphics g){
        g.setColor(new Color(237,237,255));
        g.fillRect(0,55 ,width,25);
        g.setColor(Color.black);
        g.drawRect(0,54,width,27);
        for(KComponent component:compoList){
            component.draw(g);
        }
    }
}
