package com.wakufuro.game.src.libs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {

	private int speed;
	private int frames;
	private int index = 0;
	private int count = 0;
	
	private ArrayList<BufferedImage> imgs;
	
	private BufferedImage currentImg;

	public Animation(int speed, ArrayList<BufferedImage> imgs){
		this.speed = speed;
		this.imgs = imgs;
		frames = imgs.size();
	}
	
	public void runAnimation(){
		index++;
		if(index > speed){
			index = 0;
			nextFrame();
		}	
	}
	
	public void nextFrame(){
		currentImg = imgs.get(count);
		count++;
		if(count >= frames)
			count = 0;
	}
	
	public void drawAnimation(Graphics g, double x, double y, int offset){
		g.drawImage(currentImg, (int)x - offset, (int)y, null);
	}
	
	public void setCount(int count){
		this.count = count;
	}
	public int getCount(){
		return count;
	}
	public int getSpeed(){
		return speed;
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
}