package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.mygdx.game.Notes.Crash;
import com.mygdx.game.Notes.HiHat;
import com.mygdx.game.Notes.Ride;
import com.mygdx.game.Notes.Snare;

public class Boot extends ApplicationAdapter {

	int state = 0;

	float wiggle = 0f;
	int iter = 0;
	int timer=  0;

	Song song = new Song();
	int notesHit = 0;
	int percentHit;
	int speedPercent = 100;
	Texture start;
	Texture venue;
	Texture playTrack;
	Texture noteNet;
	Texture songSelect;
	Texture mavis;
	Texture endBad;
	Texture endGood;

	Sound metronome;
	Sound menu;
	Sound select;
	Sound back;

	public static Sound snare;
	public static Sound hiHat;
	public static Sound ride;

	ArrayList<String> songs = new ArrayList<String>();
	ArrayList<String> pMenuItems = new ArrayList<String>();
	int focus = 0;
	int focusP = 0;
	BitmapFont font;
	BitmapFont font2;

	SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();

		start = new Texture("Start Screen.png");
		venue = new Texture("Venue.png");
		playTrack = new Texture("Play Track 2.png");
		noteNet = new Texture("Note Net.png");
		songSelect = new Texture("Song Select.png");
		mavis = new Texture("Menu Mavis.png");
		endBad = new Texture("End Screen Bad.png");
		endGood = new Texture("End Screen Good.png");

		metronome = Gdx.audio.newSound(Gdx.files.internal("Sounds\\metronome.mp3"));
		menu = Gdx.audio.newSound(Gdx.files.internal("Sounds\\menu.mp3"));
		select = Gdx.audio.newSound(Gdx.files.internal("Sounds\\select.mp3"));
		back = Gdx.audio.newSound(Gdx.files.internal("Sounds\\back.mp3"));

		snare = Gdx.audio.newSound(Gdx.files.internal("Sounds\\snare.mp3"));
		hiHat = Gdx.audio.newSound(Gdx.files.internal("Sounds\\hihat.mp3"));
		ride = Gdx.audio.newSound(Gdx.files.internal("Sounds\\ride.mp3"));
/*
		FileHandle [] filehandles;
		filehandles = Gdx.files.internal("Songs").list();
		for (FileHandle entry: filehandles) 
			songs.add(entry.name());
*/
		
		songs.add("8th Note Hats");
		songs.add("Surfin'");
		songs.add("Sawyer's Hats");
		songs.add("2 Hand Swing");
		songs.add("Latin Rhythm");
		songs.add("Big Band");
		
		
		pMenuItems.add("Resume");
		pMenuItems.add("Song Select");

		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 85;
		font = new FreeTypeFontGenerator(Gdx.files.internal("Fonts\\editundo.ttf")).generateFont(parameter);

		FreeTypeFontParameter parameter2 = new FreeTypeFontParameter();
		parameter2.size = 72;
		font2 = new FreeTypeFontGenerator(Gdx.files.internal("Fonts\\edunline.ttf")).generateFont(parameter2);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		if (state == 0)
			start();
		else if (state == 1)
			menu();
		else if (state == 2 || state == 3)
			play();
		else if (state == 4)
			pauseMenu();
		else if (state == 5)
			howToPlay();
		else if (state == 6)
			end();

		batch.end();

	}

	public void start(){	
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)){
			state = 1;
			focus = 0;
			select.play();
			return;
		}

		batch.draw(start, 0, 0);

		wiggle(wiggle);

		font.setColor(Color.BLACK);
		font.draw(batch, "Press ENTER", 400, 115 + wiggle);
		font.setColor(Color.WHITE);
		font.draw(batch, "Press ENTER", 400, 120 + wiggle);

	}

	public void menu(){

		batch.draw(songSelect, 0, 0);

		int menuY = Gdx.graphics.getHeight() - (180 - focus * 100);

		wiggle(wiggle);

		GlyphLayout glyphLayoutS = new GlyphLayout();
		glyphLayoutS.setText(font,"< " + speedPercent + "% >");
		float fWidthS = glyphLayoutS.width;
		font.setColor(Color.BLACK);
		font.draw(batch,"Speed", 524, 710);
		font.draw(batch, "< " + speedPercent + "% >", (Gdx.graphics.getWidth() / 2) - (fWidthS / 2) + 150, 645);
		font.setColor(Color.WHITE);
		font.draw(batch,"Speed", 524, 715);
		font.draw(batch, "< " + speedPercent + "% >", (Gdx.graphics.getWidth() / 2) - (fWidthS / 2) + 150, 650);

		for (int i = 0; i < songs.size(); i++){
			if (menuY <= Gdx.graphics.getHeight() - 180 && menuY >= Gdx.graphics.getHeight() - 480){
				if (i == focus){		
					GlyphLayout glyphLayout = new GlyphLayout();
					glyphLayout.setText(font,songs.get(i));
					float fWidth = glyphLayout.width;

					font.setColor(Color.BLACK);
					font.draw(batch, songs.get(i), (Gdx.graphics.getWidth() / 2) - (fWidth / 2) + 150, menuY + wiggle - 5);
					font.setColor(Color.WHITE);
					font.draw(batch, songs.get(i), (Gdx.graphics.getWidth() / 2) - (fWidth / 2) + 150, menuY + wiggle);
				}
				else{		
					GlyphLayout glyphLayout = new GlyphLayout();
					glyphLayout.setText(font2,songs.get(i));
					float fWidth = glyphLayout.width;

					font2.draw(batch, songs.get(i), (Gdx.graphics.getWidth() / 2) - (fWidth / 2) + 150, menuY);
				}
			}
			batch.draw(mavis, 0, 0);

			menuY -= 100;
		}

		if (Gdx.input.isKeyJustPressed(Keys.UP) && focus > 0){
			focus --;	
			menu.play();
		}

		else if (Gdx.input.isKeyJustPressed(Keys.DOWN) && focus < songs.size() - 1){
			focus ++;
			menu.play();
		}

		else if (Gdx.input.isKeyJustPressed(Keys.LEFT) && speedPercent > 50){
			speedPercent -= 10;
			menu.play();
		}
		else if (Gdx.input.isKeyJustPressed(Keys.RIGHT) && speedPercent < 200){
			speedPercent += 10;
			menu.play();
		}

		else if (Gdx.input.isKeyJustPressed(Keys.ENTER)){
			song.loadSong(songs.get(focus));
			notesHit = 0;
			percentHit = 0;
			state = 2;
			timer = 0;
			select.play();
			return;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			state = 0;
			back.play();
			return;
		}



	}

	public void play(){	

		batch.draw(venue, 0, 0);
		batch.draw(playTrack, 0, 0);
		batch.draw(noteNet, 0, 0);

		percentHit = (int) (100 * notesHit / (float)song.getVisibleNumNoteGroups());

		font.setColor(Color.BLACK);
		font.draw(batch, "Score", 25, 705);
		font.draw(batch, percentHit + "%", 25, 645);
		font.draw(batch, "Speed", 725, 705);
		font.draw(batch, speedPercent + "%", 725, 645);
		font.setColor(Color.WHITE);
		font.draw(batch, "Score", 25, 710);
		font.draw(batch, percentHit + "%", 25, 650);
		font.draw(batch, "Speed", 725, 710);
		font.draw(batch, speedPercent + "%", 725, 650);

		if (timer < 60){

			GlyphLayout glyphLayoutReady = new GlyphLayout();
			glyphLayoutReady.setText(font,"Ready");
			float fWidthReady = glyphLayoutReady.width;

			font.setColor(Color.BLACK);
			font.draw(batch, "Ready", (Gdx.graphics.getWidth() / 2) - fWidthReady / 2, Gdx.graphics.getHeight() - 85);
			font.setColor(Color.WHITE);
			font.draw(batch, "Ready", (Gdx.graphics.getWidth() / 2) - fWidthReady / 2, Gdx.graphics.getHeight() - 80);

		}
		else if (timer >= 60 && timer < 120){

			GlyphLayout glyphLayoutSet = new GlyphLayout();
			glyphLayoutSet.setText(font,"Set");
			float fWidthSet = glyphLayoutSet.width;

			font.setColor(Color.BLACK);
			font.draw(batch, "Set", (Gdx.graphics.getWidth() / 2) - fWidthSet / 2, Gdx.graphics.getHeight() - 85);
			font.setColor(Color.WHITE);
			font.draw(batch, "Set", (Gdx.graphics.getWidth() / 2) - fWidthSet / 2, Gdx.graphics.getHeight() - 80);

		}
		else
			state = 3;

		timer ++;

		if(song.getNotes().size() <= 0){
			state = 6;
			return;
		}

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			state = 4;
			focusP = 0;
			back.play();
			return;
		}
		if (state == 3){
			if (Gdx.input.isKeyJustPressed(Keys.J)){
				notesHit += song.hit(new HiHat(0))? 1 : -1;
			}
			if (Gdx.input.isKeyJustPressed(Keys.B)){
				notesHit += song.hit(new Snare(0))? 1 : -1;
			}
			if (Gdx.input.isKeyJustPressed(Keys.K)){
				notesHit += song.hit(new Ride(0))? 1 : -1;
			}
			if (Gdx.input.isKeyJustPressed(Keys.L)){
				notesHit += song.hit(new Crash(0))? 1 : -1;
			}
		}

		Iterator<Note> it = song.getNotes().iterator();
		while(it.hasNext())
		{
			Note n = (Note) it.next();

			if (n.getPy() < -50)
				it.remove(); 


			else if (n.getPy() < Gdx.graphics.getHeight())
				batch.draw(n.getTexture(), n.getPx(), n.getPy());

			if (state == 3)
				n.moveDown((int)(song.getSpeed() * (speedPercent/100.0)));
		}

	}

	public void pauseMenu(){

		batch.draw(venue, 0, 0);
		
		font.setColor(Color.BLACK);
		font.draw(batch, "Paused", 100, Gdx.graphics.getHeight() - 100);
		font.setColor(Color.WHITE);
		font.draw(batch, "Paused", 100, Gdx.graphics.getHeight() - 95);

		int menuY = Gdx.graphics.getHeight() - 250;

		wiggle(wiggle);

		for (int i = 0; i < pMenuItems.size(); i++){
			if (menuY <= Gdx.graphics.getHeight() - 180 && menuY >= Gdx.graphics.getHeight() - 480){
				if (i == focusP){		
					GlyphLayout glyphLayout = new GlyphLayout();
					glyphLayout.setText(font,pMenuItems.get(i));
					float fWidth = glyphLayout.width;

					font.setColor(Color.BLACK);
					font.draw(batch, pMenuItems.get(i), (Gdx.graphics.getWidth() / 2) - (fWidth / 2) + 150, menuY + wiggle);
					font.setColor(Color.WHITE);
					font.draw(batch, pMenuItems.get(i), (Gdx.graphics.getWidth() / 2) - (fWidth / 2) + 150, menuY + 5 + wiggle);
				}
				else{		
					GlyphLayout glyphLayout = new GlyphLayout();
					glyphLayout.setText(font2, pMenuItems.get(i));
					float fWidth = glyphLayout.width;

					font.draw(batch, pMenuItems.get(i), (Gdx.graphics.getWidth() / 2) - (fWidth / 2) + 150, menuY);
				}
			}
			menuY -= 150;
		}

		if (Gdx.input.isKeyJustPressed(Keys.UP) && focusP > 0){
			focusP --;	
			menu.play();
		}

		else if (Gdx.input.isKeyJustPressed(Keys.DOWN) && focusP < pMenuItems.size() - 1){
			focusP ++;		
			menu.play();
		}

		else if (Gdx.input.isKeyJustPressed(Keys.ENTER)){

			if (focusP == 0){
				percentHit = 0;
				state = 2;
				timer = 0;
			}
			else if (focusP == 1){
				state = 1;
			}
			else if (focusP == 2){
				state = 5;
				timer = 0;
			}
			select.play();
			return;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			state = 2;
			percentHit = 0;
			timer = 0;
			back.play();
			return;
		}

	}

	public void howToPlay(){
		wiggle(wiggle);

		font.setColor(0, 0, 0, .8f);
		font.draw(batch, "Press ENTER", 400, 115 + wiggle);
		font.setColor(Color.WHITE);
		font.draw(batch, "Press ENTER", 400, 120 + wiggle);

		if (Gdx.input.isKeyJustPressed(Keys.ENTER)){
			state = 4;
			select.play();
		}
	}

	public void end(){
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)){
			state = 1;
			select.play();
			return;
		}

		

		wiggle(wiggle);

		font.setColor(Color.BLACK);
		
		if (percentHit == 100){
			batch.draw(endGood, 0, 0);
			font.draw(batch, "Congratulations!", 200, 620);
		}
		else {
			batch.draw(endBad, 0, 0);
			font.draw(batch, "Almost There!", 200, 620);
		}
		
		font.draw(batch, songs.get(focus), 200, 525);
		
		font.draw(batch, "Percentage:     Speed:", 25, 420);
		
		font.draw(batch, percentHit + "%" + "           " + speedPercent + "%", 100, 325);
		
		font.draw(batch, "Press ENTER", 250, 115 + wiggle);

		if (percentHit == 100){
			font.setColor(Color.GREEN);
			font.draw(batch, "Congratulations!", 200, 625);
		}
		else{ 
			font.setColor(Color.RED);
			font.draw(batch, "Almost There!", 200, 625);
		}
		
		font.setColor(Color.WHITE);

		font.draw(batch, songs.get(focus), 200, 530);
		
		font.draw(batch, "Percentage:     Speed:", 25, 425);
		
		font.draw(batch, percentHit + "%" + "           " + speedPercent + "%", 100, 330);
		
		font.draw(batch, "Press ENTER", 250, 120 + wiggle);
	}

	public void wiggle(float n){
		wiggle = (float) (5 * Math.sin(iter / 3.0f));
		iter ++;
	}


	@Override
	public void dispose(){
		font.dispose();
		font2.dispose();
		start.dispose();
		venue.dispose();
		playTrack.dispose();
		noteNet.dispose();
		songSelect.dispose();
		mavis.dispose();
		batch.dispose();
	}

}
