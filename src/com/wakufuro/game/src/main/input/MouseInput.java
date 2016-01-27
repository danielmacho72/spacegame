package com.wakufuro.game.src.main.input;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.wakufuro.game.src.main.Game;
import com.wakufuro.game.src.main.Menu;
import com.wakufuro.game.src.main.Game.STATE;

public class MouseInput implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(Game.getState() == Game.STATE.MENU)
		{
			int mx = e.getX();
			int my = e.getY();
			
			if(Menu.playButton.contains(mx, my))
			{
			    // Pressed Play Button
				Game.setState(Game.STATE.GAME);
				Game.getInstance().resetLives();
			} else if(Menu.quitButton.contains(mx,my)) {
				// Pressed Quit Button
				System.exit(1);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
