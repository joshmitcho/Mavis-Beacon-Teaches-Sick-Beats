package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public abstract class Note {

	protected Texture graphic;
	protected int px;
	protected int py = Gdx.graphics.getHeight();
	protected Sound sound;
		
	public int getPx(){
		return px;
	}
	
	public int getPy(){
		return py;
	}
	
	public Texture getTexture(){
		return graphic;
	}
	
	public void moveDown(int delta){
		py -= delta;
	}
	
	public void playSound(){
		sound.play(1.0f);
	}
	
}
