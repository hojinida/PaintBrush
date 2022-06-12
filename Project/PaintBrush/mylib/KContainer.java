package mylib;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class KContainer extends KComponent{
	public ArrayList<KComponent> compoList = new ArrayList<>();
	public KContainer(){
		this.name="Container";
	}

	public void add(KComponent component){
		compoList.add(component);
	}
	@Override
	public void Click(MouseEvent e){
		if(visible&&!menuClicked)
			menuClick(e);
		else {
			for (KComponent component : compoList) {
				component.processMouseEvent(e);
			}
		}
	}
	@Override public void menuClick(MouseEvent e){
		for(KComponent component:compoList) {
			if (!component.name.equals("MenuItem")) {
				component.menuClick(e);
			} else {
				for (KComponent menu : compoList) {
					menu.processMouseEvent(e);
				}
				break;
			}
		}
	}

	@Override
	public void paint(Graphics g){
		for(KComponent component:compoList){
			component.draw(g);
		}
	}


}