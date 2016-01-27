package com.wakufuro.game.src.main.elements;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.wakufuro.game.src.libs.Textures;
import com.wakufuro.game.src.main.Game;

public abstract class GameElement {	
   protected double x;
   protected double y;
   
   protected double velX = 0;
   protected double velY = 0;
   protected double topX = 0;
   protected double topY = 0;
	  
   public static final int WIDTH = 32;
   public static final int HEIGHT = 32;
   
   protected Game game=null;
   protected Textures tex=null;
   
   protected BufferedImage sprite;
   private static Random r = null;
   
   public GameElement(double x, double y, Game game, Textures tex) {
      this.x = x;
	  this.y = y;
	  this.tex = tex;
	  this.game = game;
	  
	  init();
   }
   
   public GameElement(Game game, Textures tex) {
	  this((double)getRandomX(),(double)getRandomY(),game,tex);	  
   }
   
   public GameElement(double y, Game game, Textures tex) {
	  this((double)getRandomX(),(double)y,game,tex);	  
   }
   
   protected void init() {	  
 	  topX = game.getWidth() - WIDTH;
 	  topY = game.getHeight() - HEIGHT;
   }
   
   public abstract void tick();
   
   public void render(Graphics g) {
	  g.drawImage(sprite, (int) x, (int) y, null);
   }
   
   public void setSprite(BufferedImage sprite) {
	  this.sprite = sprite;
   }
   
   public double getX() {
	  return x;
   }
	  
  public double getY() {
	  return y;
  }
  
  public static int getWidth() {
	  return WIDTH;
  }
  
  public static int getHeight() {
	  return HEIGHT;
  }
  
  public void setX(double x) {
	  this.x = x;
  }
  
  public void setY(double y) {
	  this.y = y;
  }
  
  public void moveX(double deltaX) {
	  this.x += deltaX;
  }
  
  public void moveY(double deltaY) {
	  this.y += deltaY;
  }
  
  public void setVelX(double velX) {
	  this.velX = velX;
  }
  
  public void setVelY(double velY) {
	  this.velY = velY;
  }
  
  public static Random generateRandom() {
	  if (r == null)
		  r = new Random();
	  return r;
  }
  
  public static int getRandomX() {
	  Random r = generateRandom();
	  return r.nextInt((Game.WIDTH * Game.SCALE) - getWidth());
  }
  
  public static int getRandomY() {
	  Random r = generateRandom();
	  return r.nextInt((Game.HEIGHT * Game.SCALE) - getHeight());
  }
  
  public void setRandomX() {
	  this.x = getRandomX();
  }
  
  public void setRandomY() {
	  this.y = getRandomY();
  }
  
  public Rectangle getBounds() {
	  return new Rectangle((int)x, (int)y, getWidth(),getHeight());
  }
}
