package com.wakufuro.game.src.main.elements;

import java.awt.Graphics;
import java.util.Random;

import com.wakufuro.game.src.libs.Animation;
import com.wakufuro.game.src.libs.Physics;
import com.wakufuro.game.src.libs.Textures;
import com.wakufuro.game.src.main.Game;
import com.wakufuro.game.src.main.classes.EntityEnemy;
import com.wakufuro.game.src.main.classes.EntityFriendly;

public class Enemy extends GameElement implements EntityEnemy {
   Random r = null;
   private int speed;
   Animation anim;
   
   public Enemy(double x, double y, Game game, Textures tex) {
	  super(x,y,game,tex);
   }

   public Enemy(Game game, Textures tex) {
	 super(game,tex);   
   }
   	   
   public Enemy(double y, Game game, Textures tex) {
	  super(y,game,tex);	  
   }
	   
   
   @Override 
   protected void init() {
 	super.init();
 	this.setSprite(tex.enemy.get(0));
 	r = new Random();
 	speed = r.nextInt(3) + 1;
 	
 	anim = new Animation(5, tex.enemy);
   }

   @Override
   public void tick() {
	  y+=speed;
	  
	  if(y>=topY) {
		y=-Enemy.HEIGHT;
	    setRandomX();
	  }
	  
	  EntityFriendly bullet = Physics.collision(this, game.getBullets());
	  // What to do when there is a collision with a bullet...
	  if(bullet != null) {
		   game.increaseEnemyKilled(this);
		   game.getBulletsController().remove(bullet);
	  }
	  
	  anim.runAnimation();
   }
   
   @Override
   public void render(Graphics g) {
 	  anim.drawAnimation(g, x, y, 0);
   }
}
