package com.wakufuro.game.src.main.classes;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public interface Entity {
	public void tick();
	public void render(Graphics g);
	public void setSprite(BufferedImage sprite);
	public Rectangle getBounds();
	public double getX();
	public double getY();
}
