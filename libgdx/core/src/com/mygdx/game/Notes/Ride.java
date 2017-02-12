package com.mygdx.game.Notes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Boot;
import com.mygdx.game.Note;

public class Ride extends Note {

	public Ride(int py){
		px = Gdx.graphics.getWidth() / 2 + 10;
		this.py = py;
		graphic = new Texture("Notes\\Ride.png");
		sound = Boot.ride;
	}
	public String toString(){
		return "Ride ";
	}
	
}
