package com.wakufuro.game.src.main.elements;

import java.awt.Color;
import java.awt.Graphics;

import com.wakufuro.game.src.libs.Animation;
import com.wakufuro.game.src.libs.Physics;
import com.wakufuro.game.src.libs.Textures;
import com.wakufuro.game.src.main.Game;
import com.wakufuro.game.src.main.Game.STATE;
import com.wakufuro.game.src.main.classes.EntityEnemy;
import com.wakufuro.game.src.main.classes.EntityFriendly;

public class Player extends GameElement implements EntityFriendly {
  Animation anim;
  private static final int MAX_HEALTH = 200;
  private int health = MAX_HEALTH;
  public static final int INIT_LIVES = 4;
  private int lives = INIT_LIVES;

  public Player(double x, double y, Game game, Textures tex) {
	super(x,y,game,tex);
	anim = new Animation(5, tex.player);
  }

  @Override 
  protected void init() {
	super.init();
	this.setSprite(tex.player.get(0));
  }
  
  @Override
  public void tick() {
	  x+=velX;
	  y+=velY;
	  
	  if(x<=0)
		x=0;
	  if(x>=topX)
		x=topX;
	  if(y<=0)
		y=0;
	  if(y>=topY)
		y=topY;
	  
	  // What to do when there is a collision with a bullet...
	  EntityEnemy enemy=Physics.collision(this, game.getEnemies());
	  
	  if( enemy != null) {
		  game.increaseEnemyKilled(enemy);
		  this.decreaseHealth();
	  }
	  
	  anim.runAnimation();
  }
  
  @Override
  public void render(Graphics g) {
	  anim.drawAnimation(g, x, y, 0);
   }
  
  public void renderLives(Graphics g,int x, int y) {
	  for(int i=0;i<lives;i++)
	    g.drawImage(sprite, x + ((WIDTH)*i), y, null);
   }
  
  public void renderHealth(Graphics g, int x, int y) {
	 g.setColor(Color.GRAY);
	 g.fillRect(x,y,MAX_HEALTH,25);
	 
	 if(health >= (MAX_HEALTH/4)*3)
	   g.setColor(Color.GREEN);
	 else if (health >= (MAX_HEALTH/4)*2)
	   g.setColor(Color.ORANGE);
	 else 
	   g.setColor(Color.RED);
	 
	 g.fillRect(x,y,health,25);
	 
	 g.setColor(Color.WHITE);
	 g.drawRect(x,y,MAX_HEALTH,25);
  }
  
  public int getHealth() {
    return health;
  }
  
  public void setHealth(int health) {
	  this.health = health;
  }
  
  protected void decreaseHealth() {
	  
	  health -= (MAX_HEALTH/4);
	  
	  if(health <= 0)
	  {
	   decreaseLives();
	   health=MAX_HEALTH;
	  }
  }
  
  public int getLives() {
	return lives;
  }
	
  public void resetLives() {
		lives = INIT_LIVES;
  }
  
  public void decreaseLives() {
	  lives--;
	  if(lives == 0)
	  {
		  // GAME OVER
		  Game.setState(Game.STATE.MENU);
	  }
  }
}
