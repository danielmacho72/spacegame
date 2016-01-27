package com.wakufuro.game.src.main.elements;

import java.awt.Graphics;
import java.util.LinkedList;

import com.wakufuro.game.src.libs.Animation;
import com.wakufuro.game.src.libs.Textures;
import com.wakufuro.game.src.main.Game;
import com.wakufuro.game.src.main.classes.EntityEnemy;
import com.wakufuro.game.src.main.classes.EntityFriendly;

public class Bullet extends GameElement implements EntityFriendly {   
   Animation anim;
	
   public Bullet(double x, double y, Game game, Textures tex){
	   super(x,y,game,tex);
	   anim = new Animation(5, tex.bullet);
   }
   
   @Override 
   protected void init() {
 	super.init();
 	this.setSprite(tex.bullet.get(0));
   }

   @Override
   public void tick() {
	   y-=10;
	   
	   anim.runAnimation();
   } 
   
   @Override
   public void render(Graphics g) {
 	  anim.drawAnimation(g, x, y, 0);
   }
}
