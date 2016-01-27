package com.wakufuro.game.src.main.controllers;

import java.util.LinkedList;

import com.wakufuro.game.src.libs.Textures;
import com.wakufuro.game.src.main.Game;
import com.wakufuro.game.src.main.classes.Entity;
import com.wakufuro.game.src.main.classes.EntityEnemy;
import com.wakufuro.game.src.main.elements.Enemy;

public class EnemiesController extends Controller<EntityEnemy> {

	public EnemiesController(Game game, Textures tex) {
		super(game, tex);
	}
	
	public void tick() {  
	  Entity item;
	  LinkedList<EntityEnemy> list = getList();
	  
	  synchronized(list) {
		  for(int i=0;i < list.size();i++)
		  {
		    item = list.get(i);

		    item.tick();
		  }
	  }
	}
	
	public void createEnemies(int enemyCount) {
		for(int i=0;i< enemyCount;i++)
		{
		  add(new Enemy(-10,game,tex));
		}
	}
	
	public void resetEnemies(int enemyCount) {
		this.resetList();
		this.createEnemies(enemyCount);
	}
}

