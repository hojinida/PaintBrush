package mylib;

import java.awt.*;

public class KMenuBar extends KContainer{
	int posX=0;
	public KMenuBar() {
		setBounds(0,30,4096,25);
		this.name="MenuBar";
	}

	@Override
	public void add(KComponent c) { // 왼쪽으로 정렬
		c.setBounds(posX, 30, 70, 25);
		posX += 70;
		super.add(c);
	}

	@Override
	public void draw(Graphics g) { // 메뉴바 그리기
		g.setColor(new Color(237,237,255));
		g.fillRect(0, 30,width, 25);
		g.setColor(Color.black);
		g.drawRect(0,30,width,24);
		for(KComponent component:compoList){
			component.draw(g);
		}
	}
}
