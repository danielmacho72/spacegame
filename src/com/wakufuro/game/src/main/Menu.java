package com.wakufuro.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {
	
	public static Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120,150,100,50);
	public static Rectangle helpButton = new Rectangle(Game.WIDTH / 2 + 120,250,100,50);
	public static Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120,350,100,50);

	int numLives;
	Game game;
	String title= "SPACE GAME";
	
	public Menu(Game game) {
		this.game = game;
	}
	
	public void tick() {
		numLives = game.getLives();
		title = "SPACE GAME";
		
		if(numLives==0) {
		  title = "GAME OVER";
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font fnt0 = new Font("arial", Font.BOLD,50);
		g.setFont(fnt0);
		g.setColor(Color.white);
		
		g.drawString(title, Game.WIDTH/2, 100);
		
		Font fnt1 = new Font("arial", Font.BOLD,30);
		g.setFont(fnt1);
		
		g.drawString("Play", playButton.x + 20, playButton.y + 35);
		g.drawString("Help", helpButton.x + 20, helpButton.y + 35);
		g.drawString("Quit", quitButton.x + 20, quitButton.y + 35);
		
		g2d.draw(playButton);
		g2d.draw(helpButton);
		g2d.draw(quitButton);
	}
}
