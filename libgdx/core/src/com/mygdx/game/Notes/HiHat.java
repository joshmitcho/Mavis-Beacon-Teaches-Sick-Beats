package com.mygdx.game.Notes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Boot;
import com.mygdx.game.Note;

public class HiHat extends Note {

	public HiHat(int py){
		px = Gdx.graphics.getWidth() / 2 - 120;
		this.py = py;
		graphic = new Texture("Notes\\HiHat.png");
		sound = Boot.hiHat;
	}
	public String toString(){
		return "HiHat ";
	}
	
}
