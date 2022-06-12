package mylib;

import java.awt.*;
import java.awt.event.MouseEvent;

public class KFrame extends KContainer{
	protected KMenuBar theMenuBar;
	protected KToolBar theToolBar;
	protected KContainer contentPane;
	public void setKMenuBar(KMenuBar mb) {
		// 원래 메뉴바는 frame에 있는게 정상이지만...
		add(mb);
		theMenuBar=mb;
	}
	public void setTheToolBar(KToolBar tb){
		add(tb);
		theToolBar=tb;
	}
	public KFrame() {
		setBounds(0,0,4096,4096);
		contentPane= new KPanel();
		add(contentPane);
	}
	public void paint(Graphics g){
		super.paint(g);
	}
	@Override
	public void processMouseEvent(MouseEvent e){
		if (theMenuBar.contains(e)) {
			menuClicked = true;
			toolBarClicked=false;
			contentpaneClicked=false;
		}
		else if (theToolBar.contains(e)) {
			toolBarClicked = true;
			menuClicked=false;
			contentpaneClicked=false;
		}
		else if (contentPane.contains(e)) {
			contentpaneClicked = true;
			toolBarClicked=false;
			menuClicked=false;
		}

		super.processMouseEvent(e);
	}

}
