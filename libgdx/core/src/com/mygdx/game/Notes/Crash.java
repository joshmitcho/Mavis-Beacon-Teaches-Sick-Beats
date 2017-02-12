package com.mygdx.game.Notes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Note;

public class Crash extends Note {

	public Crash(int py){
		px = Gdx.graphics.getWidth() / 2 + 140;
		this.py = py;
		graphic = new Texture("Notes\\Crash.png");
		sound = Gdx.audio.newSound(Gdx.files.internal("Sounds\\crash.mp3"));
	}
	public String toString(){
		return "Crash";
	}
	
}