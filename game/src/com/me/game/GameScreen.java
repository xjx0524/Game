package com.me.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.game.G.TAG;
import com.me.map.XFlowManager;
import com.me.map.XTiledLoader;
import com.me.map.XTiledMap;
import com.me.screens.LoadingScreen;
import com.me.screens.SelectScreen;

public class GameScreen implements Screen,InputProcessor{
	
	Stage stg;
	Stage handlestg;
	SkillButtonGroup skillstg;
	Actor hero;
	SpriteBatch batch;
	XTiledMap tmx;
	XFlowManager flowManager;
	private int mission;
	private boolean screenHolded = false;
	private int lholdx;
	private int lholdy;
	private int mapH,mapW;
	
	public boolean getIsScreenHoleded(){ return screenHolded;}
	
	public GameScreen(int mission) {
		super();
		this.mission=mission;
		load();
		G.loadfinished=true;		
	}
	
	public void load() {
		G.Log("Load Screen "+mission);
		dispose();
		G.batch=batch = new SpriteBatch();
		G.dialogstg=null;
		
		
		
		tmx=XTiledLoader.initMap(Gdx.files.internal("map/"+getMission()+".tmx"), "map/");
		mapH=(int)(tmx.getSize().y*32);
		mapW=(int)(tmx.getSize().x*32);
		
		G.toggleGroup=new ToggleGroup();
		flowManager=new XFlowManager(0.1f);
		
		stg=new MapObjectGroup();
		hero=new Hero();
		stg.addActor(hero);
		G.camera=stg.getCamera();
		
		handlestg=new Handle(G.ScreenWidth, G.ScreenHeight,true,batch);
		Gdx.input.setInputProcessor(this);
		
		skillstg=new SkillButtonGroup(G.ScreenWidth, G.ScreenHeight,true,batch);
		SkillButton b=new SkillButton(G.TAG.SKILL_MUSIC, SkillButton.TYPE.MUSIC);
		skillstg.addActor(b);
		b=new SkillButton(G.TAG.SKILL_RESTART, SkillButton.TYPE.RESTART);
		skillstg.addActor(b);
		b=new SkillButton(G.TAG.SKILL_LOCKMOVE, SkillButton.TYPE.LOCKMOVE);
		skillstg.addActor(b);	
		b=new SkillButton(G.TAG.BUTTON_BACK_SMALL, SkillButton.TYPE.BACK);
		skillstg.addActor(b);
		
		if (mission>=3){
			b=new SkillButton(G.TAG.SKILL_PUSH, SkillButton.TYPE.SELECT);
			skillstg.addActor(b);
			if (G.selectedSkill==G.TAG.SKILL_PUSH) G.skillBottonGroup.select(b); 
		}
		if (mission>=8){
			b=new SkillButton(G.TAG.SKILL_PULL, SkillButton.TYPE.SELECT);
			skillstg.addActor(b);
			if (G.selectedSkill==G.TAG.SKILL_PULL) G.skillBottonGroup.select(b);
		}
		if (mission>=15){
			b=new SkillButton(G.TAG.SKILL_JUMP, SkillButton.TYPE.ACTIVE);
			skillstg.addActor(b);
		}
		if (mission>=9){
			b=new SkillButton(G.TAG.SKILL_FREEZE, SkillButton.TYPE.ACTIVE);
			skillstg.addActor(b);
		}
		if (mission>=14){
			b=new SkillButton(G.TAG.SKILL_THAW, SkillButton.TYPE.ACTIVE);
			skillstg.addActor(b);
		}
		
		skillstg.oganize();
		
		G.gameScreen=this;
		
		G.markToResetCamera=true;
		
		G.Log("Load Screen "+mission+" finished");
	}

	@Override
	public void show() {
		G.Log("Show Screen "+mission);
		G.lockInput=G.hero.lock=0;
		G.markToBack=G.markToRestart=G.markToWin=false;
		G.markToResetCamera=true;
		G.generateTip(mission);
		if (G.curTip<=G.maxTip){
			Dialog dialog=new Dialog(G.curTip);
			dialog.show();
		}
		G.playMusic(TAG.MUSIC_BGM2);
	}

	public void dispose() {
		if (G.gameScreen==this) G.gameScreen=null;
		G.Log("Dispose Screen "+mission+" "+G.gameScreen);
		if (stg!=null) {stg.clear();stg.dispose();}
		if (handlestg!=null) {handlestg.clear();handlestg.dispose();}
		if (skillstg!=null) {skillstg.clear();skillstg.dispose();}
		if (batch!=null) batch.dispose();
		flowManager=null;
		stg=null;
		handlestg=null;
		skillstg=null;
		tmx=null;
		hero=null;
		batch=null;		
		if (G.gameScreen==null){
			if (G.objectGroup!=null) {
					G.objectGroup.clear();
					G.objectGroup.dispose();
			}
			if (G.skillBottonGroup!=null) {
					G.skillBottonGroup.clear();
					G.skillBottonGroup.dispose();
			}
			if (G.flowmanager!=null) 
				G.flowmanager.dispose();
			
			G.hero=null;
			G.tmx=null;
			G.objectGroup=null;
			G.skillBottonGroup=null;
			G.toggleGroup=null;
			G.batch=null;
			G.flowmanager=null;
			G.camera=null;
		}
	}
	
	public void nextMisstion(){
		Gdx.input.setInputProcessor(null);
		G.hero.stopAllActions();
		G.game.setScreen(new LoadingScreen(getMission()+1));
	}

	public void hide() {
		G.Log("Hide Screen "+mission);
		dispose();		
	}

	public void pause() {		
	}

	public void render(float t) {
		if (G.gameScreen==null) return;
		if (stg==null||tmx==null||handlestg==null||skillstg==null) return;
		
		G.flowmanager.update();
		
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);		


		
		if (G.gameScreen!=null)	G.gameScreen.resetScreen();
		tmx.draw((OrthographicCamera)stg.getCamera());
		
		handlestg.draw();
		skillstg.draw();
		
		if (G.markToWin){
			nextMisstion();
			G.markToWin=false;
		}
		
		if (G.markToRestart){
			G.restart();
			G.markToRestart=false;
		}
		
		if (G.markToBack){
			Gdx.input.setInputProcessor(null);
			G.game.setScreen(new SelectScreen());
			G.markToBack=false;
		}
		
		if (G.dialogstg!=null)
			G.dialogstg.draw();
	}

	public void resize(int x, int y) {
		// TODO Auto-generated method stub
	}

	public void resume() {
		
	}

	public boolean keyDown(int keycode) 
	{
		if (keycode==Input.Keys.BACK||keycode==Input.Keys.BACKSPACE){
			 G.markToBack=true;
			 return true;
		}
		G.markToResetCamera=true; return hero.keyDown(keycode);
	}
	public boolean keyTyped(char arg0) {return false;	}
	public boolean keyUp(int keycode) { return hero.keyUp(keycode);}
	public boolean scrolled(int arg0) {	return false;}
	public boolean touchDown(int x, int y, int pointer, int button) 
	{	
		if (G.dialogstg!=null){
			return G.dialogstg.touchDown(x, y, pointer, button);
		}
		boolean b1=handlestg.touchDown(x, y, pointer,button);
		boolean b2=skillstg.touchDown(x, y, pointer, button);
		G.Log("handle "+b1+",  skill "+b2);
		if (!(b1||b2)){
			if (screenHolded){
				G.Log("ScreenPos("+x+","+y+")");
				if (G.camera==null) return false;
				G.camera.translate(lholdx-x,y-lholdy,0);
				G.camera.update();
				G.camera.apply(Gdx.gl10);
				lholdx=x;lholdy=y;
				return true;
			}else
			{
				Vector2 v1=new Vector2();
				stg.toStageCoordinates(x, y, v1);
				Vector2 v2=new Vector2();
				stg.toStageCoordinates(0, Gdx.graphics.getHeight(), v2);
				if (v1.x-v2.x<=G.ScreenWidth/4) return false;
				if (v1.x-v2.x>=G.ScreenWidth*3/4) return false;
				if (v1.y-v2.y<=G.ScreenHeight/4) return false;
				if (v1.y-v2.y>=G.ScreenHeight*3/4) return false;
				G.Log("screenHold("+x+","+y+")");
				screenHolded=true;
				G.markToResetCamera=false;
				lholdx=x;lholdy=y;
				return true;
			}
		}else{
			G.markToResetCamera=true;
		}
		return b1||b2;
	}
	public boolean touchDragged(int x, int y, int pointer) 
	{
		if (G.gameScreen!=this) return false;
		if (G.dialogstg!=null){
			return G.dialogstg.touchDragged(x, y, pointer);
		}
		boolean b;
		if (screenHolded) b=false;
		else{
			b=handlestg.touchDown(x, y, pointer,pointer);
		}
		if (!b){
			if (screenHolded){
				G.Log("ScreenPos("+x+","+y+")");
				if (G.camera==null) return false;
				Vector2 lv= new Vector2();
				Vector2 v= new Vector2();
				//stg.toStageCoordinates(lholdx-x,y-lholdy, v);
				//stg.toStageCoordinates(lholdx-x,y-lholdy, lv);
				stg.toStageCoordinates(x,y, v);
				stg.toStageCoordinates(lholdx,lholdy, lv);
				G.Log("["+(lholdx-x)+","+(y-lholdy)+"]:"+v);
				G.camera.translate(lv.x-v.x, lv.y-v.y, 0);
				G.camera.update();
				G.camera.apply(Gdx.gl10);
				lholdx=x;lholdy=y;
				return true;
			}
		}else{
			//resetScreen();
		}
		return b;
	}
	public boolean touchMoved(int arg0, int arg1) {	return false;}
	public boolean touchUp(int x, int y, int pointer, int button) 
	{
		if (G.gameScreen!=this) return false;
		if (G.dialogstg!=null){
			return G.dialogstg.touchUp(x, y, pointer, button);
		}
		boolean b1=handlestg.touchUp(x,y,pointer,button);
		boolean b2=skillstg.touchUp(x, y, pointer, button);
		screenHolded=false;
		lholdx=x;lholdy=y;
		return b1||b2;
	}

	public int getMission() {
		return mission;
	}
	
	
	public void resetScreen(){
		Vector3 v=G.camera.position;
		if (G.markToResetCamera){
			if (Math.abs(G.hero.x+16-v.x)<=0.1&&Math.abs(G.hero.y+16-v.y)<=0.1) return;
			//G.Log("("+G.hero.x+","+G.hero.y+"),("+v+")");
			G.camera.translate((G.hero.x+16)-v.x,(G.hero.y+16)-v.y,0);
		}
		v=G.camera.position;
		if (v.x<0) G.camera.translate(-v.x,0,0);
		if (v.y<0) G.camera.translate(0,-v.y,0);
		if (v.x>mapW) G.camera.translate(mapW-v.x,0,0);
		if (v.y>mapH) G.camera.translate(0,mapH-v.y,0);
		G.camera.update();
		G.camera.apply(Gdx.gl10);
	}
	
}
