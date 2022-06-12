package app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import mylib.*;

import javax.imageio.ImageIO;

//여기는 응용
class PainterKFrame extends KFrame{
	private final ArrayList<Figure> FigureRepository;
	private Point firstPoint,secondPoint,start,property;
	private Figure selectThing,copyThing;
	private boolean isFill,isMoved,isLine,isComplex,isClicked;
	private ArrayList<Point> off,Loff;
	private Point off_c,poff_c;
	private Color color;
	private String flag;
	private int index;

	public PainterKFrame() {
		addMenuBar();
		addToolBar();
		this.FigureRepository=new ArrayList<>();
		this.addKMouseListeners(new MyMouseListener());
		this.addKMouseMotionListeners(new MyMotion());
		this.addKKeyListener(new MyKey());
		this.color=Color.black;
		this.isMoved=false;
		this.flag=null;
		this.isFill=false;
		/*
		어떤 메뉴를 더할 지, 그것은 여기에서 하면 된다.
		*/
	}
	public void setColor(Color color){
		this.color=color;
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		for(Figure figure:FigureRepository){
			figure.draw(g);
		}
	}
	public void addMenuBar(){
		KMenuBar menuBar=new KMenuBar();
		setKMenuBar(menuBar);
		KMenu saveMenu=new KMenu("File");
		KMenuItem save=new KMenuItem("SAVE");
		save.setActionCommand("파일");
		save.addKActionListener(new MenuItemActionListener());
		saveMenu.add(save);
		KMenuItem saveAs=new KMenuItem("SAVE AS");
		saveAs.setActionCommand("다른 이름으로 저장");
		saveAs.addKActionListener(new MenuItemActionListener());
		saveMenu.add(saveAs);
		KMenuItem get=new KMenuItem("LOADING");
		get.setActionCommand("불러오기");
		get.addKActionListener(new MenuItemActionListener());
		saveMenu.add(get);
		KMenuItem saveImage=new KMenuItem("IMAGE");
		saveImage.setActionCommand("이미지 저장");
		saveImage.addKActionListener(new MenuItemActionListener());
		saveMenu.add(saveImage);
		menuBar.add(saveMenu);
	}

	public void addToolBar(){
		KToolBar toolBar=new KToolBar();
		setTheToolBar(toolBar);
		KButton rectButton=new KButton("ㅁ");
		rectButton.setActionCommand("Rect");
		rectButton.addKActionListener(new ButtonActionListener());
		toolBar.add(rectButton);
		KButton ovalButton=new KButton("ㅇ");
		ovalButton.setActionCommand("Oval");
		ovalButton.addKActionListener(new ButtonActionListener());
		toolBar.add(ovalButton);
		KButton lineButton=new KButton("/");
		lineButton.setActionCommand("Line");
		lineButton.addKActionListener(new ButtonActionListener());
		toolBar.add(lineButton);
		KButton groupButton=new KButton("Group");
		groupButton.setActionCommand("Group");
		groupButton.addKActionListener(new ButtonActionListener());
		toolBar.add(groupButton);
		KCheckBox moveButton=new KCheckBox("Move");
		moveButton.setActionCommand("Move");
		moveButton.addKActionListener(new ButtonActionListener());
		toolBar.add(moveButton);
		KCheckBox FillButton=new KCheckBox("Fill");
		FillButton.setActionCommand("Fill");
		FillButton.addKActionListener(new ButtonActionListener());
		toolBar.add(FillButton);
		KButton redButton=new KButton(Color.red);
		redButton.setActionCommand("Red");
		redButton.addKActionListener(new ButtonActionListener());
		toolBar.add(redButton);
		KButton blueButton=new KButton(Color.blue);
		blueButton.setActionCommand("Blue");
		blueButton.addKActionListener(new ButtonActionListener());
		toolBar.add(blueButton);
	}

	class MenuItemActionListener implements KActionListener,AutoCloseable{
		@Override
		public void actionPerformed(ActionEvent e){
			try {
				Scanner sc = new Scanner(System.in);
				KAbstractButton button = (KAbstractButton) e.getSource();
				System.out.println(button.getActionCommand());
				switch (button.getActionCommand()) {
					case "저장" -> setOutputStream("draw.xxx");
					case "다른 이름으로 저장" -> {
						System.out.print("파일 이름을 입력하세요:");
						String FileName = sc.nextLine();
						setOutputStream(FileName);
					}
					case "불러오기" -> {
						System.out.print("파일 이름을 입력하세요:");
						String FileName = sc.nextLine();
						try {
							setInputStream(FileName);
						} catch (EOFException ignored) {
						} catch (ClassNotFoundException ex) {
							throw new RuntimeException(ex);
						}
					}
					case "이미지 저장" -> {
						BufferedImage bi = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
						Graphics2D g=bi.createGraphics();
						g.drawImage(bi,0,0,bi.getWidth(),bi.getHeight(),Color.white,null);
						for (Figure figure : FigureRepository) {
							figure.draw(g);
						}
						g.dispose();
						try {
							ImageIO.write(bi, "png", new File("/Users/hojin/Desktop/Study/JAVA/Project/PaintBrush/image.png"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		public void setOutputStream(String FileName) throws IOException {
			FileOutputStream fo= new FileOutputStream(FileName);
			BufferedOutputStream bos= new BufferedOutputStream(fo);
			ObjectOutputStream out= new ObjectOutputStream(bos);
			for(Figure figure:FigureRepository){
				out.writeObject(figure);
			}
			out.flush();
		}

		public void setInputStream(String FileName) throws IOException, ClassNotFoundException {
			FileInputStream fi=new FileInputStream(FileName);
			BufferedInputStream bis = new BufferedInputStream(fi);
			ObjectInputStream in = new ObjectInputStream(bis);
			FigureRepository.clear();
			while (true){
				FigureRepository.add((Figure) in.readObject());
			}
		}

		@Override
		public void close(){
		}
	}
	class ButtonActionListener implements KActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			KAbstractButton button=(KAbstractButton) e.getSource();
			System.out.println(button.getActionCommand());
			switch (button.getActionCommand()) {
				case "Red" -> {
					Color selectedColor = Color.RED;
					if (selectedColor == color) {
						setColor(Color.black);
					} else if (selectedColor != null) {
						setColor(selectedColor);
					}
				}
				case "Blue" -> {
					Color selectedColor = Color.BLUE;
					if (selectedColor == color) {
						setColor(Color.black);
					} else if (selectedColor != null) {
						setColor(selectedColor);
					}
				}
				case "Move" -> isMoved = !isMoved;
				case "Fill" -> isFill = !isFill;
				default -> flag = e.getActionCommand();
			}
		}
	}

	class MyMouseListener implements KMouseListener {
		@Override
		public void mousePressed(MouseEvent e) {
			off=new ArrayList<>();
			Loff=new ArrayList<>();
			firstPoint=e.getPoint();
			if(isMoved) {
				for (Figure figure : FigureRepository) {
					if (figure.isClicked(firstPoint)) {
						isClicked = true;
						selectThing = figure;
						if (figure.getText().equals("Group")) {
							isComplex = true;
							for (Figure Groupt : ((Group) figure).getList()) {
								if (Groupt.getText().equals("Group"))
									set(Groupt);
								else {
									off.add(new Point(firstPoint.x - Groupt.start.x, firstPoint.y - Groupt.start.y));
									Loff.add(new Point(Groupt.property.x - firstPoint.x, Groupt.property.y - firstPoint.y));
								}
							}
							off_c = new Point(firstPoint.x - figure.start.x, firstPoint.y - figure.start.y);
							poff_c = new Point(figure.property.x - firstPoint.x, figure.property.y - firstPoint.y);
						} else if (figure.getText().equals("Line")) {
							isLine = true;
							off.add(new Point(firstPoint.x - figure.start.x, firstPoint.y - figure.start.y));
							Loff.add(new Point(figure.property.x - firstPoint.x, figure.property.y - firstPoint.y));
						} else {
							off.add(new Point(firstPoint.x - figure.start.x, firstPoint.y - figure.start.y));
						}
						break;
					}
				}
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			secondPoint=e.getPoint();
			property=new Point(Math.abs(secondPoint.x-firstPoint.x),Math.abs(secondPoint.y-firstPoint.y));
			start=new Point(Math.min(firstPoint.x, secondPoint.x),Math.min(firstPoint.y,secondPoint.y));
			if(!isMoved) {
				draw(e);
			}
			e.getComponent().repaint();
			isLine=false;
			isComplex=false;
			isClicked=false;
			off=null;
			Loff=null;
		}
		@Override
		public void mouseClicked(MouseEvent e) {

		}
		public void set(Figure figure){
			for(Figure figures:((Group)figure).getList()) {
				if(figures.getText().equals("Group"))
					set(figures);
				else {
					off.add(new Point(firstPoint.x - figures.start.x, firstPoint.y - figures.start.y));
					Loff.add(new Point(figures.property.x - firstPoint.x, figures.property.y - firstPoint.y));
				}
			}
		}
	}
	class MyMotion implements KMouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			if (isClicked) {
				index = 0;
				if (isComplex) {
					moveComplex(e, selectThing);
					selectThing.start.x = e.getX() - off_c.x;
					selectThing.start.y = e.getY() - off_c.y;
					selectThing.property.x = e.getX() + poff_c.x;
					selectThing.property.y = e.getY() + poff_c.y;
				} else if (isLine) {
					moveLine(e);
				} else {
					move(e);
				}
			}
		}

		public void moveComplex(MouseEvent e, Figure selectThing) {
			for (Figure figure : ((Group) selectThing).getList()) {
				if (figure.getText().equals("Group"))
					moveComplex(e, figure);
				else if (figure.getText().equals("Line")) {
					figure.start.x = e.getX() - off.get(index).x;
					figure.start.y = e.getY() - off.get(index).y;
					figure.property.x = e.getX() + Loff.get(index).x;
					figure.property.y = e.getY() + Loff.get(index).y;
					index++;
				} else {
					figure.start.x = e.getX() - off.get(index).x;
					figure.start.y = e.getY() - off.get(index).y;
					index++;
				}
			}
		}

		public void move(MouseEvent e) {
			selectThing.start.x = e.getX() - off.get(0).x;
			selectThing.start.y = e.getY() - off.get(0).y;
		}

		public void moveLine(MouseEvent e) {
			selectThing.start.x = e.getX() - off.get(0).x;
			selectThing.start.y = e.getY() - off.get(0).y;
			selectThing.property.x = e.getX() + Loff.get(0).x;
			selectThing.property.y = e.getY() + Loff.get(0).y;
		}
	}

	class MyKey implements KKeyListener,AutoCloseable{
		@Override
		public void keyTyped(KeyEvent e){
			if(isMoved){
				if(e.getKeyChar()=='c'){
					String id=selectThing.text;
					switch (id) {
						case "Group" -> copyThing = new Group((Group) selectThing);
						case "Rect" -> copyThing = new Rect((Rect) selectThing);
						case "Oval" -> copyThing = new Oval((Oval) selectThing);
						case "Line" -> copyThing = new Line((Line) selectThing);
					}
				}else if(e.getKeyChar()=='v'&&copyThing!=null){
					FigureRepository.add(copyThing);
				}else if(e.getKeyChar()=='d'){
					FigureRepository.remove(selectThing);
				}
			}
			if(e.getKeyChar()=='s'){
				try {
					FileOutputStream fo = new FileOutputStream("draw.xxx");
					BufferedOutputStream bos = new BufferedOutputStream(fo);
					ObjectOutputStream out = new ObjectOutputStream(bos);
					for (Figure figure : FigureRepository) {
						out.writeObject(figure);
					}
					out.flush();
				}catch (IOException ex){
					throw new RuntimeException(ex);
				}
			}
		}

		@Override
		public void close(){

		}
	}
	public void draw(MouseEvent e){
		if(flag!=null) {
			switch (this.flag) {
				case "Rect" : FigureRepository.add(new Rect(start, property, color, isFill));
				case "Oval" : FigureRepository.add(new Oval(start, property, color, isFill));
				case "Line" : FigureRepository.add(new Line(firstPoint, secondPoint, color));
				case "Group" : FigureRepository.add(new Group(firstPoint, secondPoint, FigureRepository));
			}
			e.getComponent().repaint();
		}
	}
}

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KAdapterFrame frame = new KAdapterFrame();
		frame.setSize(800,600);
		frame.setKFrame(new PainterKFrame());
		frame.setVisible(true);
	}
}
