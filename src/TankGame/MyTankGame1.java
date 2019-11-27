package TankGame;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

 
public class MyTankGame1  extends JFrame {
	MyPanel mp=null;
	Hero hero=null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
MyTankGame1 mtk1=new MyTankGame1();
	}
	public MyTankGame1(){
		mp=new MyPanel();
		Thread t=new Thread(mp);
		t.start();
		this.add(mp);
		this.addKeyListener(mp);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400,300);
		this.setVisible(true);
		
		
	}


}
class MyPanel extends JPanel implements KeyListener,Runnable{
	Hero hero=null;
	Vector<Enemy> ets=new Vector<Enemy>();
	Vector<Boom> booms=new Vector<Boom>();
	int enSize=3;
	Image image1=null;
	Image image2=null;
	public MyPanel(){
		hero=new Hero(100,100,3);
	for (int i = 0;i<enSize;i++){
		Enemy et=new Enemy((i+1)*50, 0, 1);
		et.setDirection(2);
		et.setColor(1);
		Thread t=new Thread(et);
		t.start();
		shot s=new shot(et.x+9,et.y+33,2, 3);
		et.ss.add(s);
		Thread t2=new Thread(s);
		t2.start();
		ets.add(et);
		
	}
		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom.gif"));
		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/dcc3e574e5a3ab360ae4bae1d66050f5.gif"));
		
		
		
	}
	public void paint(Graphics g){
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		if(hero.isLive==true){
		this.drawTank(hero.getX(), hero.getY(), g, this.hero.direction, this.hero.color);}
		for(int i=0;i<this.hero.ss.size();i++){
			shot myshot=hero.ss.get(i);
			
		
		if(myshot!=null&&myshot.isLive==true){
			g.draw3DRect(myshot.x,myshot.y,1,1,true);
		
		}
		if(myshot.isLive==false){
			hero.ss.remove(myshot);
		}
		if(myshot.isLive==false){
			hero.ss.remove(myshot);
		}
		
		}
		//draw Bombs
		for(int i=0;i<booms.size();i++){
			Boom b=booms.get(i);
			if(b.life>0){
				g.drawImage(image1, b.x, b.y, 30,30,this);
				
			}
			b.lifeDown();
			if(b.life==0){
				booms.remove(i);
			}
			
		}
		//draw Enemy tanks
		for(int i=0;i<ets.size();i++){
			Enemy et=ets.get(i);
			
			if(et.isLive){
				this.drawTank(et.getX(),et.getY(), g, et.getDirection(),et.getColor());
				for(int j=0;j<et.ss.size();j++){
					shot enemyShot=et.ss.get(j);
					g.draw3DRect(enemyShot.x,enemyShot.y,1,1,true);
				}
		}
		}
		
		
		
		
	}
	public void hitMe(){
		for(int i=0;i<this.ets.size();i++){
			Enemy et=ets.get(i);
			for(int j=0;j<et.ss.size();j++){
				shot enemyshot=et.ss.get(j);
				this.hitTank(enemyshot,hero);
			}
				
			
		}
	}
	public void hitEnemyTank(){
		for(int i=0;i<hero.ss.size();i++){
			shot myshot=hero.ss.get(i);
			if (myshot.isLive){
				for(int j=0;j<ets.size();j++){
					Enemy et=ets.get(j);
					if(et.isLive){
						this.hitTank(myshot, et);
						
					}
				}
			}
		}
		
	}
	public void hitTank(shot s,Tank et){
		switch(et.direction){
		case 0:
		case 2:
			if(s.x>et.x&&s.x<et.x+20&&s.y>et.y&&s.y<et.y+30){
				s.isLive=false;
				et.isLive=false;
				Boom b=new Boom(et.x,et.y);
				booms.add(b);
			}
		case 1:
		case 3:
			if(s.x>et.x&&s.x<et.x+20&&s.y>et.y&&s.y<et.y+30){
				s.isLive=false;
				et.isLive=false;
				Boom b=new Boom(et.x,et.y);
				booms.add(b);
			}
		}
		
	}

	
	public void drawTank(int x,int y,Graphics g,int direction,int tankType){
		switch (tankType){
		case 0:
			g.setColor(Color.CYAN);
			break;
		case 1:
			g.setColor(Color.PINK);
			break;
		}
		switch (direction){
		case 0:
			//w
			g.fill3DRect(x, y, 5, 30,false);
			g.fill3DRect(x+15, y, 5, 30,false);
			g.fill3DRect(x+5,y+5,10 , 20,false);
			g.fillOval(x+4, y+8, 10, 10);
			g.drawLine(x+9, y+15, x+9, y-3);
			break;
			//d
		case 1:
			g.fill3DRect(x, y, 30, 5, false);
			g.fill3DRect(x, y+15, 30, 5, false);
			g.fill3DRect(x+5, y+5,20, 10, false);
			g.fillOval(x+9, y+4, 10, 10);
			g.drawLine(x+15, y+9, x+33, y+9);
			break;
		case 2:
			//s
			g.fill3DRect(x, y, 5, 30, false);
			g.fill3DRect(x+15, y, 5, 30, false);
			g.fill3DRect(x+5, y+5,10 , 20, false);
			g.fillOval(x+4, y+9, 10, 10);
			g.drawLine(x+9, y+15, x+9, y+33);
			break;
			//a
		case 3:
			g.fill3DRect(x, y, 30, 5, false);
			g.fill3DRect(x, y+15, 30, 5, false);
			g.fill3DRect(x+5, y+5,20 , 10, false);
			g.fillOval(x+9, y+4, 10, 10);
			g.drawLine(x+15, y+10, x-3, y+10);
			break;
		}
		
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_W){
		this.hero.setDirection(0);
		this.hero.moveUp();
		}else if(e.getKeyCode()==KeyEvent.VK_D){
			this.hero.setDirection(1);
			this.hero.moveRight();
		}else if(e.getKeyCode()==KeyEvent.VK_S){
		this.hero.setDirection(2);
		this.hero.moveDown();
	}else if(e.getKeyCode()==KeyEvent.VK_A){
		this.hero.setDirection(3);
		this.hero.moveLift();
	}
		if(e.getKeyCode()==KeyEvent.VK_J&&this.hero.ss.size()<5){
			this.hero.shotEnemy();
			
			
		
	}
		
		this.repaint();
}
	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	this.hitEnemyTank();
	this.hitMe();
			this.repaint();
		}
	}
}
class Boom{
	int x;
	int y;
	int life=9;
	boolean isLive=true;
	public Boom(int x,int y){
		this.x=x;
		this.y=y;
	}
	public void lifeDown(){
		if(life>0){
			life--;
		}else{
			this.isLive=false;
		}
	}
}
class shot implements Runnable{
	int x;
	int y;
	int direction;
	int shotSpeed;
	boolean isLive=true;
	public shot(int x,int y,int direction,int shotSpeed){
		this.x=x;
		this.y=y;
		this.direction=direction;
		this.shotSpeed=shotSpeed;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch(direction){
			case 0:
				y-=shotSpeed;
				break;
			case 1:
				x+=2*shotSpeed;
				break;
			case 2:
				y+=shotSpeed;
				break;
			case 3:
				x-=shotSpeed;
				break;
 		
			}
			System.out.println("LOcation x="+x+"Location y="+y);
			//shots died???
			if(x<-1||x>401||y<-1||y>301){
				this.isLive=false;
				break;
			}
		}
	}
}
class Tank{
	int x=0;
	int y=0;
	int direction=0;
    int speed=1;
    int color;
    boolean isLive=true;
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public Tank(int x,int y,int speed){
		this.x=x;
		this.y=y;
		this.speed=speed;
		
		
		
	}
}
class Enemy extends Tank implements Runnable{
	
    Vector<shot> ss=new Vector<shot>();
    
	public Enemy(int x, int y, int espeed) {
		super(x, y, espeed);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
			
			switch(this.direction){
			case 0:
				for(int i=0;i<30;i++){
					if(y>0){
				y-=speed;}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				break;
			case 1:
				for(int i=0;i<30;i++){
					if(x<350){
						x+=speed;}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case 2:
				for(int i=0;i<30;i++){
					if(y<225){
						y+=speed;
					}
					
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}				
				break;
			case 3:
				for(int i=0;i<30;i++){
					if(x>0){
						x-=speed;}
					
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				break;
			}
			
			this.direction=(int)(Math.random()*4);
			if(this.isLive==false){
				break;
			}
		}
	}
	
}
class Hero extends Tank{
	Vector<shot> ss=new Vector<shot>();
	shot s=null;
	
	public Hero(int x,int y,int speed){
		super(x,y,speed);
	}
	public void shotEnemy(){
		
		switch(this.direction){
		case 0:
			s=new shot(x+9,y-3,0,6);
			ss.add(s);
			break;
		case 1:
			s=new shot(x+33, y+9,1,6);
			ss.add(s);
			break;
		case 2:
			s=new shot(x+9, y+33,2,6);
			ss.add(s);
			break;
		case 3:
			s=new shot(x-3, y+9,3,6);
			ss.add(s);
			break;
		}
		Thread t=new Thread(s);
		t.start();
	}
	public void moveUp(){
		if(y>0){
		y-=speed;}
	}
	public void moveRight(){
		if(x<350){x+=speed;}
		
		
		
	}
	public void moveDown(){
		if(y<225){
		y+=speed;}
	}public void moveLift(){
		if(x>0){
		x-=speed;
	}}
}
