package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.Notes.Crash;
import com.mygdx.game.Notes.HiHat;
import com.mygdx.game.Notes.Ride;
import com.mygdx.game.Notes.Snare;


public class Song {

	ArrayList<Note> notes;
	int spacing;
	int speed = 12;
	String name;
	private int numNoteGroups = 0;
	private int numVisibleNoteGroups = 0;

	public Song(){

	}

	public void loadSong(String filename){
		name = filename;
		notes = new ArrayList<Note>();
		numNoteGroups = 0;
		numVisibleNoteGroups = 0;

		FileHandle file = Gdx.files.internal("Songs\\" + filename);
		String text = file.readString();

		String[] words = text.split(" ");

		spacing = Integer.parseInt(words[0]);

		for (int i = 1; i < words.length; i++){
			if (words[i].equals("s")){
				notes.add(new Snare(Gdx.graphics.getHeight() + (spacing * numNoteGroups)));
			}
			else if (words[i].equals("h")){
				notes.add(new HiHat(Gdx.graphics.getHeight() + (spacing * numNoteGroups)));
			}
			else if (words[i].equals("sh")){
				notes.add(new Snare(Gdx.graphics.getHeight() + (spacing * numNoteGroups)));
				notes.add(new HiHat(Gdx.graphics.getHeight() + (spacing * numNoteGroups)));
				numVisibleNoteGroups ++;
			}
			else if (words[i].equals("r")){
				notes.add(new Ride(Gdx.graphics.getHeight() + (spacing * numNoteGroups)));
			}
			else if (words[i].equals("sr")){
				notes.add(new Snare(Gdx.graphics.getHeight() + (spacing * numNoteGroups)));
				notes.add(new Ride(Gdx.graphics.getHeight() + (spacing * numNoteGroups)));
				numVisibleNoteGroups ++;
			}
			else if (words[i].equals("hr")){
				notes.add(new HiHat(Gdx.graphics.getHeight() + (spacing * numNoteGroups)));
				notes.add(new Ride(Gdx.graphics.getHeight() + (spacing * numNoteGroups)));
				numVisibleNoteGroups ++;
			}
			else if (words[i].equals("c")){
				notes.add(new Crash(Gdx.graphics.getHeight() + (spacing * numNoteGroups)));
			}
			else if (words[i].equals("sc")){
				notes.add(new Snare(Gdx.graphics.getHeight() + (spacing * numNoteGroups)));
				notes.add(new Crash(Gdx.graphics.getHeight() + (spacing * numNoteGroups)));
				numVisibleNoteGroups ++;
			}
			numNoteGroups ++;
			if (!words[i].equals("-"))
				numVisibleNoteGroups ++;
		}
	}

	public boolean hit(Note n){

		Iterator<Note> it = notes.iterator();
		while(it.hasNext())
		{
			Note m = (Note) it.next();

			if (m.py >= 60 && m.py <= 120 && m.getClass() == n.getClass()){
				it.remove(); 
				m.playSound();
				return true;
			}
		}
		return false;
	}

	public ArrayList<Note> getNotes(){
		return notes;
	}

	public int getVisibleNumNoteGroups(){
		if (numVisibleNoteGroups > 0)
			return numVisibleNoteGroups;
		else
			return 1;
	}

	public int getSpeed(){
		return speed;
	}

	public void removeNote(int i){
		notes.get(i).getTexture().dispose();
		notes.remove(i);
		numNoteGroups --;
	}

}
