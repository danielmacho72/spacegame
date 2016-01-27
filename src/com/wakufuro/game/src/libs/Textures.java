package com.wakufuro.game.src.libs;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.wakufuro.game.src.main.Game;

public class Textures {
  
	public ArrayList<BufferedImage> player = new ArrayList<>();
	public ArrayList<BufferedImage> bullet = new ArrayList<>();
	public ArrayList<BufferedImage> enemy = new ArrayList<>();
	private SpriteSheet ss=null;
	private final int WIDTH = 32;
	private final int HEIGHT = 32;
	private final int NUM_SPRITES = 3;
	
  public Textures(Game game) {
	 ss = new SpriteSheet(game.getSpriteSheet());
	 getTextures();
  }
  
  public void getTextures() {
	  for(int i=0;i<NUM_SPRITES;i++)
	  {
		 player.add(ss.grabImage(1, i+1, WIDTH, HEIGHT));
		 bullet.add(ss.grabImage(2, i+1, WIDTH, HEIGHT));
		 enemy.add(ss.grabImage(3, i+1, WIDTH, HEIGHT));
	  }
  }
}
