package com.wakufuro.game.src.main.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.wakufuro.game.src.main.Game;

public class KeyInput extends KeyAdapter {
   private Game game;
   
   public KeyInput(Game game) {
	   this.game = game;
   }
	
	@Override
   public void keyPressed(KeyEvent e) {
	   game.keyPressed(e);
   }
   
	@Override
   public void keyReleased(KeyEvent e) {
	   game.keyReleased(e);
   }
}
