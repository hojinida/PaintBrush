package mylib;

import java.awt.*;
import java.awt.event.MouseEvent;

public class KMenu extends KContainer{
	int posY=55;
	public KMenu(String text) {
		this.text=text;
		this.name="Menu";
	}

	@Override
	public void add(KComponent c) { // 메뉴 아이템 추가
		c.setBounds(x, posY, 70, 25);
		posY += 25;
		super.add(c);
	}
	@Override
	public void Click(MouseEvent e){
		visible= !visible;
		for(KComponent component:compoList){
			if(!visible)
				e.getComponent().repaint();
			else
				component.draw(e.getComponent().getGraphics());
		}
	}
	@Override
	public void draw(Graphics g){
		g.drawRect(this.x, this.y, this.width, this.height-1);
		g.drawString(this.text, this.x+5, this.y+17);
	}
}