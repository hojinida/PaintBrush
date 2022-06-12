package mylib;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class KComponent{
	static boolean visible = false;
	int x, y, width, height;
	static boolean menuClicked,toolBarClicked,contentpaneClicked;

	String name="Component";
	String text = null;
	ArrayList<KMouseListener> kMouseListeners=new ArrayList<>();
	ArrayList<KMouseMotionListener> kMouseMotionListeners=new ArrayList<>();
	ArrayList<KKeyListener> kKeyListeners=new ArrayList<>();
	KMouseListener kMouseListener=new KMouseListener() {
		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent e){
			MouseClick(e);
		}
	};

	public void draw(Graphics g){}
	public void Click(MouseEvent e){
	}
	public void paint(Graphics g){}
	public void menuClick(MouseEvent e){
	}


	public void MouseClick(MouseEvent e){
		// 클릭된경우 처리
		if(contains(e))
			Click(e);
	}
	public void addKMouseListeners(KMouseListener kMouseListener){
		kMouseListeners.add(kMouseListener);
	}
	public void addKMouseMotionListeners(KMouseMotionListener kMouseMotionListener){kMouseMotionListeners.add(kMouseMotionListener);}
	public void addKKeyListener(KKeyListener keyListener){kKeyListeners.add(keyListener);}
	public void setBounds(int x,int y,int width,int height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	public boolean contains(MouseEvent e) { // 컴포넌트위에 좌표가 있는지 확인
		int x=e.getX();
		int y=e.getY();
		return this.x <= x && x <= this.x + this.width && this.y <= y && y <= this.y + height;
	}
	protected void processMouseEvent(MouseEvent e){
		int id = e.getID();
		KMouseListener listener = kMouseListener;
		ArrayList<KMouseListener> listeners=kMouseListeners;
		if(menuClicked||toolBarClicked||visible) {
			if (listener != null) {
				if (id == MouseEvent.MOUSE_CLICKED) {
					listener.mouseClicked(e);
				}
			}
		}else if (contentpaneClicked&&listeners != null) {
			for(KMouseListener mouseListener:listeners) {
				switch (id) {
					case MouseEvent.MOUSE_PRESSED -> mouseListener.mousePressed(e);
					case MouseEvent.MOUSE_RELEASED -> mouseListener.mouseReleased(e);
					case MouseEvent.MOUSE_CLICKED -> mouseListener.mouseClicked(e);
				}
			}
		}
	}

	protected void processMouseMotionEvent(MouseEvent e){
		ArrayList<KMouseMotionListener> listeners=kMouseMotionListeners;
		int id=e.getID();
		if(contentpaneClicked&&listeners != null){
			for(KMouseMotionListener mouseMotionListener:listeners) {
				if (id == MouseEvent.MOUSE_DRAGGED) {
					mouseMotionListener.mouseDragged(e);
				}
			}
		}
	}

	protected void processKeyEvent(KeyEvent e) {
		ArrayList<KKeyListener> listeners=kKeyListeners;
		int id=e.getID();
		if(listeners != null){
			for(KKeyListener keyListener:listeners) {
				if (id == KeyEvent.KEY_TYPED) {
					keyListener.keyTyped(e);
				}
			}
		}
	}

}
