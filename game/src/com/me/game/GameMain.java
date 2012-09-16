package com.me.game;


import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.me.screens.HelloScreen;

public class GameMain extends Game {
	Screen curScreen;
	public boolean fin = false;
	public boolean SDcardExists;
	public File file = null;
	
	public void create() {
				G.game=this;
				Gdx.graphics.setDisplayMode(G.ScreenWidth, G.ScreenHeight,false);
				G.initSound();
				G.motp=new MapObjectTextureProvider();
				G.sbtp=new SkillButtonTextureProvider();
				G.lsTitle=new LabelStyle(
						new BitmapFont(Gdx.files.internal("title.fnt"), Gdx.files.internal("title.png"),
								false),Color.WHITE);
				G.lsText=new LabelStyle(
						new BitmapFont(Gdx.files.internal("text.fnt"), Gdx.files.internal("text.png"), 
								false),Color.WHITE);
				load();
				setScreen(curScreen=new HelloScreen());
			}
	
	@Override
	public void dispose() {
		G.motp=null;
		G.sbtp=null;
		G.lsText=null;
		G.lsTitle=null;
		G.disposeSound();
		super.dispose();
	}
	
	public void save(){
		//if (!SDcardExists) return;
		if (file==null) {G.Log("Error! No file found!");return;}
		FileHandle f=new FileHandle(file);
		byte[] bytes=new byte[4];
		bytes[0]=(byte)G.maxMission;
		bytes[1]=(byte)G.maxTip;
		if (G.soundOn)
			bytes[2]=1;
		else 
			bytes[2]=0;
		bytes[3]=(byte)G.curTip;
		f.writeBytes(bytes, false);
		G.Log("Save:"+bytes[0]+" "+bytes[1]+" "+bytes[2]+" "+bytes[3]);
	}
	
	public void load(){
		if (file==null) {G.Log("Error! No file found!");return;}
		
		FileHandle f=new FileHandle(file);
		byte[] bytes=new byte[4];
		f.readBytes(bytes,0,4);
		G.maxMission=bytes[0];
		G.maxTip=bytes[1];
		G.soundOn=(1==bytes[2]);
		G.curTip=bytes[3];
		G.Log("Load:"+bytes[0]+" "+bytes[1]+" "+bytes[2]+" "+bytes[3]);
	}
	
	public void reset(){
		if (file==null) {G.Log("Error! No file found!");return;}
		G.Log("-Reset-");
		FileHandle f=new FileHandle(file);
		f.writeBytes(new byte[]{1,1,1,2}, false);
		G.maxMission=1;
		G.maxTip=1;
		G.curTip=2;
	}
}
