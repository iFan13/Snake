package Snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable, KeyListener {
	static final long serialVersionUID = 1L;
	public static final int WIDTH = 800, HEIGHT = WIDTH * 3/4, TILESIZE = 10;
	private Thread gameThread;
	private boolean running = false;
	private int speed=250000000; 
	
	private BodyPart b;
	private ArrayList<BodyPart> snake;
	private int xPos = 10, yPos = 10; //snake initial position
	private int size = 5; //snake initial length
	private boolean right =true, left = false, up = false, down = false;
	
	private Food food;
	private ArrayList<Food> foods; 
	
	private Random r;
	
	
	private Key key;
	
	public Screen() {
		setFocusable(true);
		key = new Key();
		addKeyListener(key);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		r = new Random();
		
		snake = new ArrayList<BodyPart>();
		foods = new ArrayList<Food>();
			
		
		start();
	}
	
	public void start() {
		running = true;
		gameThread = new Thread(this, "Game Loop");
		gameThread.start();
		
	}
	
	public void stop() {
		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0; //desired frame rate
		double ns = 1000000000/amountOfTicks; //number of nanoseconds allowed per frame
		double delta = 0;
		long timer = System.nanoTime();
		//long timer0 = System.currentTimeMillis();
		//int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if (delta >=1) {
				
				delta--;
				repaint();		//repaint at rate 60 frames per second
				//frames++;
			}
			
			if (System.nanoTime()-timer > speed) { //every speed nano s, tick 		
				tick();			//move at every speed
				timer+=speed;
				}
			
			/*		
			if (System.currentTimeMillis()-timer0 > 1000) {
					System.out.println("FPS: "+frames);
					frames = 0;	
					timer0 +=1000;
				}
			*/
				
			}
					
	}
	

	private void tick() {
		if(snake.size()==0) { //creates new body if new game
			b = new BodyPart(xPos,yPos, TILESIZE);
			snake.add(b);
			
		}
		
		if(foods.size() == 0) { //creates new food if no food
			int xPos = r.nextInt(WIDTH/10-1);
			int yPos = r.nextInt(HEIGHT/10-1);
			food = new Food(xPos, yPos, TILESIZE);
			foods.add(food);
		}
		
		for(int i = 0; i<foods.size();i++) { //checks snake collision with food 
			if(xPos == foods.get(i).getxPos()&&yPos==foods.get(i).getyPos()) {
				size++;
				foods.remove(i);
				i--;
				if (speed > 10000000) {
					speed-=10000000;
					}
				else if (speed == 0) {
					speed = 1; 
				}
				else if (speed <= 10000000) {
					speed-=1000000;
				}
				

				System.out.println("speed: "+speed);
				
			}
		}
		
		for(int i = 0; i<snake.size();i++) { //checks snake collision with itself
			if(xPos == snake.get(i).getxPos() && yPos == snake.get(i).getyPos() ) {
				if(i != snake.size()-1) {
					stop();
					}
				}
			}
		
		
		if (xPos <0 || xPos > WIDTH/TILESIZE-1 || yPos <0 || yPos > HEIGHT/TILESIZE-1) {
			stop();
		}
		
		//movement of the snake; ie: adds to head, removes from back
		if(right) {xPos++;}
		if(left) {xPos--;}
		if(up) {yPos--;}
		if(down) {yPos++;}
			b = new BodyPart(xPos,yPos,TILESIZE);
			snake.add(b);
			
			if(snake.size() > size) {
				snake.remove(0);
			}
		}
	
	
	public void paint(Graphics g) { 
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		//draw grid w black background
		for (int i =0; i < WIDTH/TILESIZE; i++) {
			g.drawLine(i*TILESIZE, 0, i*TILESIZE, HEIGHT);
		}
		for (int i =0; i < HEIGHT/TILESIZE; i++) {
			g.drawLine(0, i*TILESIZE, WIDTH, i*TILESIZE);
		}
		g.setColor(Color.black);
		g.fillRect(0,0, WIDTH, HEIGHT);
		

		
		//draw the apple(s)
		for(int i = 0; i<foods.size();i++) {
			foods.get(i).draw(g);
			}
		
		//draw the snake
		for (int i = 0; i < snake.size(); i++) {
			snake.get(i).draw(g);
		}
		
		
	}
	
	private class Key implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
					
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_RIGHT && !left) {
				up= false;
				down = false;
				right = true;
			}
			if(key == KeyEvent.VK_LEFT && !right) {
				up= false;
				down = false;
				left = true;
			}
			if(key == KeyEvent.VK_UP && !down) {
				up= true;
				left = false;
				right = false;
			}
			if(key == KeyEvent.VK_DOWN && !up) {
				down= true;
				left = false;
				right = false;
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int key = e.getKeyCode();
	if(key == KeyEvent.VK_RIGHT && !left) {
		up= false;
		down = false;
		right = true;
	}
	if(key == KeyEvent.VK_LEFT && !right) {
		up= false;
		down = false;
		left = true;
	}
	if(key == KeyEvent.VK_UP && !down) {
		up= true;
		left = false;
		right = false;
	}
	if(key == KeyEvent.VK_DOWN && !up) {
		down= true;
		left = false;
		right = false;
	}
		
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		/*
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT && !left) {
			up= false;
			down = false;
			right = true;
		}
		if(key == KeyEvent.VK_LEFT && !right) {
			up= false;
			down = false;
			left = true;
		}
		if(key == KeyEvent.VK_UP && !down) {
			up= true;
			left = false;
			right = false;
		}
		if(key == KeyEvent.VK_DOWN && !up) {
			down= true;
			left = false;
			right = false;
		}
		*/
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	}

	
		
