package com.wakufuro.game.src.main.controllers;

import java.util.LinkedList;

import com.wakufuro.game.src.libs.Textures;
import com.wakufuro.game.src.main.Game;
import com.wakufuro.game.src.main.classes.EntityFriendly;

public class BulletsController extends Controller<EntityFriendly> {

	public BulletsController(Game game, Textures tex) {
		super(game, tex);
	}
	
	public void tick() {  
	  EntityFriendly item;
	  LinkedList<EntityFriendly> list = getList();
	  
	  synchronized(list) {
		  for(int i=0;i < list.size();i++)
		  {
		    item = list.get(i);
		    
		    if(item.getY() < 0)
			  remove(item);  
		    else
		      item.tick();
		  }
	  }
	}
}
