package mylib;

import java.awt.*;
import java.awt.event.MouseEvent;

public class KMenuItem extends KAbstractButton{

	public KMenuItem(String text) {
		super(text);
		this.name="MenuItem";
	}
	@Override
	public void draw(Graphics g) { // 메뉴아이템 그리기
		g.drawRect(x, y, width, height);
		g.setColor(new Color(237,237,255));
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawString(text, x + 2, y + 17);
	}
}