package com.mygdx.game.Notes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Boot;
import com.mygdx.game.Note;

public class Snare extends Note {
	
	public Snare(int py){
		px = Gdx.graphics.getWidth() / 2 - 240;
		this.py = py;
		graphic = new Texture("Notes\\Snare.png");
		sound = Boot.snare;
	}
	public String toString(){
		return "Snare ";
	}
	
}
