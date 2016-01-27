package com.wakufuro.game.src.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import com.wakufuro.game.src.libs.BufferedImageLoader;
import com.wakufuro.game.src.libs.Textures;
import com.wakufuro.game.src.main.classes.EntityEnemy;
import com.wakufuro.game.src.main.classes.EntityFriendly;
import com.wakufuro.game.src.main.controllers.BulletsController;
import com.wakufuro.game.src.main.controllers.EnemiesController;
import com.wakufuro.game.src.main.elements.Bullet;
import com.wakufuro.game.src.main.elements.Player;
import com.wakufuro.game.src.main.input.KeyInput;
import com.wakufuro.game.src.main.input.MouseInput;

public class Game extends Canvas implements Runnable {
  
  private static final long serialVersionUID = 1L;
  public static final int WIDTH = 320;
  public static final int HEIGHT = WIDTH / 12 * 9;
  public static final int SCALE = 2;
  public final String TITLE = "2D Space Game";
  
  private boolean running = false;
  private boolean isShooting = false;

  private static Game instance;
  
  private Thread thread;
  private BufferedImage spriteSheet = null;
  private BufferedImage background = null;
  
  private Player p;
  private BulletsController bulletsController;
  private EnemiesController enemiesController;
  private Textures tex;
  private Menu menu;
  
  private int enemyCount = 5;
  private int enemyKilled = 0;
  private int score = 0;
  private String scoreTxt = String.format("%05d",score);
  private int bgY1 = 0;
  private int bgY2 = -(HEIGHT * SCALE);
  private int bgSpeed = 10;
  
  public static enum STATE {
	MENU,
	GAME
  };
  
  private Font fnt0;
  
  private static STATE state = STATE.MENU;
  
  // Private constructor as this is a singleton
  private Game() {
	  
  }
  
  public void init() {
	requestFocus(); // So the game gains focus just at starting point.
	
	// Load spriteSheet and Background just once
	BufferedImageLoader loader = new BufferedImageLoader();
	try {
		spriteSheet = loader.loadImage("/sprite_sheet.png");
		background = loader.loadImage("/background.png");
		
	}catch(IOException e) {
		e.printStackTrace();
	}
	
	// Add input listeners
	this.addKeyListener(new KeyInput(this));
	this.addMouseListener(new MouseInput());
	
	// Create textures object
	tex = new Textures(this); // TODO: Convert in a Singleton
	
	// Create Player. Then bullets and enemies controllers
	p = new Player(200,200, this, tex);
	bulletsController = new BulletsController(this,tex);
	enemiesController = new EnemiesController(this, tex);
	enemiesController.createEnemies(enemyCount); // Generate "n" enemies
	
	// Create Menu
	menu = new Menu(this);
	
	// Create font for score
	fnt0 = new Font("arial", Font.BOLD,30);
  }
  
  // Start method for Thread. Triggers the game to start and avoids
  // new instances if already playing.
  private synchronized void start() {
	  if(running)
		  return;
	  
	  running = true;
	  
	  thread = new Thread(this);
	  thread.start();
  }
  
  // It performs stop actions when thread has finished 
  private synchronized void stop() {
	if(!running)
	  return;
	  
	running = false;
	  
	try {
		thread.join();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	  
	System.exit(1);
  }
  
  /*
   * Main block for the game. 
   * - initializes objects
   * - main loop for the game
   */
	@Override
	public void run() {
		init();
		
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0; // 60 Ticks per second
		double ns = 1_000_000_000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(running) {
			long now = System.nanoTime();
			
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1)
			{
				tick(); // Calculates
				updates++;
				delta--;
			}
			
			render(); // Shows 
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){ // Waits
				timer += 1000;
				System.out.println(updates + " Ticks, Fps " + frames);
				updates = 0;
				frames = 0;
			}
		}
		
		stop();
	}

	/*
	 * Allows calculations and logic to happen on the game and 
	 * game components by calling each object individual tick() 
	 * method
	 */
	private void tick()
	{
		if(state == STATE.GAME) 
		{
		  p.tick(); // Allow player to calculate
		  bulletsController.tick(); // Allow bullets to calculate
		  enemiesController.tick(); // Allow enemies to calculate
		
	      // Some logic for the game: if you ran out of enemies, here you have
		  // 2 more than before!
		  if(enemyKilled >= enemyCount)
		  {
			  enemyCount+= 2;
			  enemyKilled = 0;
			  enemiesController.createEnemies(enemyCount);
		  }
		} else if(state == STATE.MENU) {
			menu.tick();
		}
		
		// Calculate scroll for background
		  bgY1+=bgSpeed;
		  bgY2+=bgSpeed;
		  
		  if(bgY2>=0) {
			  bgY1 = 0;
			  bgY2 = -(HEIGHT * SCALE);
		  }
	}
	
	/*
	 * Show game components in the screen from Buffers
	 */
	private void render()
	{
		// Let's use 3 buffers strategy and access graphics to draw (g)
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}
	
		Graphics g = bs.getDrawGraphics();
	
		/////////// START DRAWING ////////////////
		
		//g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
		// Draw Background scrolling
		g.drawImage(background, 0, bgY1, null); // Draw the first background
		g.drawImage(background, 0, bgY2, null); // Draw the second background

		if(state == STATE.GAME) {
			p.render(g);
			bulletsController.render(g);
			enemiesController.render(g);
			
			g.setFont(fnt0);
			g.setColor(Color.white);
			g.drawString(scoreTxt, (WIDTH * SCALE) - 150, 50);
			
			// Show num lives left
			p.renderLives(g,25, 30);
			p.renderHealth(g,160,35);
			
		}else if(state == STATE.MENU) {
			menu.render(g);
		}
		
		/////////// END DRAWING //////////////////
		g.dispose();
		bs.show();
	}
  
	public static Game getInstance() {
		if(instance==null)
			instance = new Game();
		return instance;
	}
	
  public static void main(String args[]) {
	  
	  // Create 'game' canvas with predefined Dimension
	  Game game = Game.getInstance();
	  game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
	  game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
	  game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
	  
	  // Create Frame with 'game' canvas and size
	  JFrame frame = new JFrame(game.TITLE);
	  frame.add(game);
	  frame.pack();
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.setResizable(false);
	  frame.setLocationRelativeTo(null);
	  frame.setVisible(true);
	  
	  // Game starts here
	  game.start();
  }
  
  public BufferedImage getSpriteSheet() {
	  return spriteSheet;
  }
  
  public int getWidth(){
	  return Game.WIDTH * Game.SCALE;
  }

  public int getHeight(){
	  return Game.HEIGHT * Game.SCALE;
  }
  
  public void keyPressed(KeyEvent e) {
	  int key = e.getKeyCode();
	  
	  if(state == STATE.GAME) {
		  // Use of setVelocity and summing it up at tick time makes the move smoother
		  // We just need to remember to reset velocity back to 0 when releasing the
		  // key
		  
		  if(key == KeyEvent.VK_RIGHT) {
			  p.setVelX(5);
		  } else if (key == KeyEvent.VK_LEFT) {
			  p.setVelX(-5);
		  } else if (key == KeyEvent.VK_DOWN) {
			  p.setVelY(5);
		  } else if (key == KeyEvent.VK_UP) {
			  p.setVelY(-5);
		  } else if (key == KeyEvent.VK_SPACE && !isShooting) {
			  isShooting = true;
			  bulletsController.add(new Bullet(p.getX(), p.getY(), this, tex));
		  } else if (key == KeyEvent.VK_ESCAPE) {
			  state = STATE.MENU;
		  }
	  }
  }
  
  public void keyReleased(KeyEvent e) {
	  int key = e.getKeyCode();
	  
	  if(key == KeyEvent.VK_RIGHT) {
		  p.setVelX(0);
	  } else if (key == KeyEvent.VK_LEFT) {
		  p.setVelX(0);
	  } else if (key == KeyEvent.VK_DOWN) {
		  p.setVelY(0);
	  } else if (key == KeyEvent.VK_UP) {
		  p.setVelY(0);
	  } else if (key == KeyEvent.VK_SPACE) {
		  isShooting = false;
	  }
  }
  
  public int getEnemyCount() {
     return enemyCount;
  }

	public void setEnemyCount(int enemyCount) {
		this.enemyCount = enemyCount;
	}
	
	public int getEnemyKilled() {
		return enemyKilled;
	}
	
	public void setEnemyKilled(int enemyKilled) {
		this.enemyKilled = enemyKilled;
	}
	
	public int increaseEnemyKilled(EntityEnemy killed) {
		this.getEnemiesController().remove(killed); 

		this.enemyKilled++;
		this.score++; // Increase the score when we kill an enemy
		this.scoreTxt = String.format("%05d",score);
		
		return this.enemyKilled;
	}
  
  public LinkedList<EntityEnemy> getEnemies() {
	  return (LinkedList<EntityEnemy>) enemiesController.getList();
  }
  
  public LinkedList<EntityFriendly> getBullets() {
	  return (LinkedList<EntityFriendly>) bulletsController.getList();
  }
  
  public BulletsController getBulletsController() {
	  return bulletsController;
  }

  public EnemiesController getEnemiesController() {
	  return enemiesController;
  }
  
  public static STATE getState() {
	  return state;
  }
  
  public static void setState(STATE state) {
	  Game.state = state;
  }
  
  public Player getPlayer() {
	  return p;
  }
  
  public int getLives() {
	  return p.getLives();
  }
  
  public void resetLives() {
	p.resetLives();
	enemyCount = 5;
	enemyKilled = 0;
	score = 0;
	scoreTxt = String.format("%05d",score);
	enemiesController.resetEnemies(enemyCount); // Reset to "n" enemies
  }
}
