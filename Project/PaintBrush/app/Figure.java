package app;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Figure implements Serializable {
    protected Point start, property;
    protected Color color;
    protected String text;
    protected boolean isFill;
    public Figure(){}
    public Figure(Point p1, Point p2, Color color,boolean b) {
        this.start = p1;
        this.property = p2;
        this.color = color;
        this.isFill=b;
    }
    public Figure(Figure figure){

    }
    String getText(){
        return this.text;
    }
    void draw(Graphics g){}
    boolean isClicked(Point c){
        if(this.start.x<=c.x&&this.start.y<=c.y)
            return ((this.property.x+this.start.x) >= c.x) && ((this.property.y+this.start.y) >= c.y);
        return false;
    }
}
class Group extends Figure{
    private ArrayList<Figure> list;
    public Group(Point p1, Point p2,ArrayList<Figure> figures) {
        this.list=new ArrayList<>();
        this.text="Group";
        this.start=new Point(10000,10000);
        this.property=new Point(0,0);
        for(Figure figure:figures){
            if((p1.x<=figure.start.x&&p1.y<=figure.start.y)&&
                    (p2.x>=figure.property.x&&p2.y>=figure.property.y)){
                this.list.add(figure);
            }
        }

        for(Figure figure:list){
            if(figure.getText().equals("Group")){
                set(figure);
            }else if(figure.getText().equals("Line")) {
                Point checkLine1=new Point(Math.min(figure.start.x,figure.property.x),Math.min(figure.start.y,figure.property.y));
                Point checkLine2=new Point(Math.max(figure.start.x,figure.property.x),Math.max(figure.start.y,figure.property.y));
                if (this.start.x > checkLine1.x)
                    this.start.x = checkLine1.x;
                if (this.start.y > checkLine1.y)
                    this.start.y = checkLine1.y;
                if (this.property.x < checkLine2.x)
                    this.property.x = checkLine2.x;
                if (this.property.y < checkLine2.y)
                    this.property.y = checkLine2.y;
            } else {
                if (this.start.x > figure.start.x)
                    this.start.x = figure.start.x;
                if (this.start.y > figure.start.y)
                    this.start.y = figure.start.y;
                if (this.property.x < (figure.property.x+figure.start.x))
                    this.property.x = (figure.property.x+figure.start.x);
                if (this.property.y < (figure.property.y+figure.start.y))
                    this.property.y = (figure.property.y+figure.start.y);
            }
        }
        if(list.size()!=0) {
            for(Figure figure:list){
                figures.remove(figure);
            }
        }else{
            figures.remove(this);
        }
    }

    public Group(Group group){
        this.start=new Point(group.start);
        this.property=new Point(group.property);
        this.text=group.text;
        this.list=new ArrayList<>();
        for(Figure figures:group.list){
            String id=figures.text;
            switch (id){
                case "Group" :
                    this.list.add(new Group((Group)figures));
                    break;
                case "Rect"	:
                    this.list.add(new Rect((Rect)figures));
                    break;
                case "Oval"	:
                    this.list.add(new Oval((Oval)figures));
                    break;
                case "Line"	:
                    this.list.add(new Line((Line)figures));
                    break;
            }
        }
    }

    public void set(Figure figure){
        for(Figure figures:((Group)figure).getList()) {
            if (figures.getText().equals("Group")) {
                set(figures);
            }else if(figures.getText().equals("Line")) {
                Point checkLine1=new Point(Math.min(figures.start.x,figures.property.x),Math.min(figures.start.y,figures.property.y));
                Point checkLine2=new Point(Math.max(figures.start.x,figures.property.x),Math.max(figures.start.y,figures.property.y));
                if (this.start.x > checkLine1.x)
                    this.start.x = checkLine1.x;
                if (this.start.y > checkLine1.y)
                    this.start.y = checkLine1.y;
                if (this.property.x < checkLine2.x)
                    this.property.x = checkLine2.x;
                if (this.property.y < checkLine2.y)
                    this.property.y = checkLine2.y;
            } else {
                if (this.start.x > figures.start.x)
                    this.start.x = figures.start.x;
                if (this.start.y > figures.start.y)
                    this.start.y = figures.start.y;
                if (this.property.x < (figures.property.x+figures.start.x))
                    this.property.x = (figures.property.x+figures.start.x);
                if (this.property.y < (figures.property.y+figures.start.y))
                    this.property.y = (figures.property.y+figures.start.y);
            }
        }
    }
    public ArrayList<Figure> getList() {
        return this.list;
    }
    @Override
    public void draw(Graphics g){
        for(Figure figure:this.list){
            figure.draw(g);
        }
    }
    @Override
    boolean isClicked(Point c) {
        if(c.x>=this.start.x&&c.y>=this.start.y)
            return c.x <= this.property.x && c.y <= this.property.y;
        return false;
    }
}
class Rect extends Figure {
    public Rect(Point p1,Point p2,Color color,boolean b){
        super(p1,p2,color,b);
        this.text="Rect";
    }
    public Rect(Rect rect){
        this.start=new Point(rect.start);
        this.property=new Point(rect.property);
        this.color=rect.color;
        this.isFill=rect.isFill;
        this.text=rect.text;
    }

    @Override
    void draw(Graphics g){
        g.setColor(this.color);
        if(isFill){
            g.fillRect(this.start.x, this.start.y, this.property.x, this.property.y);
        }else {
            g.drawRect(this.start.x, this.start.y, this.property.x, this.property.y);
        }
    }
}

class Oval extends Figure {
    public Oval(Point p1,Point p2,Color color,boolean b) {
        super(p1,p2,color,b);
        this.text="Oval";
    }
    public Oval(Oval oval){
        this.start=new Point(oval.start);
        this.property=new Point(oval.property);
        this.color=oval.color;
        this.isFill=oval.isFill;
        this.text=oval.text;
    }

    @Override
    void draw(Graphics g){
        g.setColor(this.color);
        if(isFill){
            g.fillOval(this.start.x, this.start.y, this.property.x, this.property.y);
        }else {
            g.drawOval(this.start.x, this.start.y, this.property.x, this.property.y);
        }
    }
}

class Line extends Figure {
    public Line(Point p1,Point p2,Color color) {
        this.start = p1;
        this.property = p2;
        this.color = color;
        this.text="Line";
    }
    public Line(Line line){
        this.start=new Point(line.start);
        this.property=new Point(line.property);
        this.color=line.color;
        this.text=line.text;
    }
    @Override
    void draw(Graphics g){
        g.setColor(this.color);
        g.drawLine(this.start.x, this.start.y, this.property.x, this.property.y);
    }
    @Override
    public boolean isClicked(Point c) {
        Point check1=new Point(Math.min(this.start.x,this.property.x),Math.min(this.start.y,this.property.y));
        Point check2=new Point(Math.max(this.start.x,this.property.x),Math.max(this.start.y,this.property.y));
        if(check1.x<=c.x&&check1.y<=c.y)
            return ((check2.x) >= c.x) && ((check2.y) >= c.y);
        return false;
    }
}
