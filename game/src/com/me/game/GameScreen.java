package com.me.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.map.XFlowManager;
import com.me.map.XTiledLoader;
import com.me.map.XTiledMap;

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
	
	public boolean getIsScreenHoleded(){ return screenHolded;}
	
	public GameScreen(int mission) {
		super();
		this.mission=mission;
	}
	
	public void show() {
		G.Log("Show Screen "+mission);
		dispose();
		G.sbtp=new SkillButtonTextureProvider();
		G.motp=new MapObjectTextureProvider();
		G.batch=batch = new SpriteBatch();
		G.dialogstg=null;
		
		
		
		tmx=XTiledLoader.initMap(Gdx.files.internal("map/"+getMission()+".tmx"), "map/");
		
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
		Dialog dialog=new Dialog("");
		dialog.show();
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
			if (G.objectGroup!=null) {G.objectGroup.clear();G.objectGroup.dispose();}
			if (G.skillBottonGroup!=null) {G.skillBottonGroup.clear();G.skillBottonGroup.dispose();}
			G.hero=null;
			G.tmx=null;
			G.motp=null;
			G.sbtp=null;
			G.objectGroup=null;
			G.skillBottonGroup=null;
			G.toggleGroup=null;
			G.batch=null;
			G.flowmanager=null;	
			G.camera=null;
		}
	}
	
	public void nextMisstion(){
		G.hero.stopAllActions();
		G.game.setScreen(new GameScreen(getMission()+1));
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
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);		
		stg.act(Gdx.graphics.getDeltaTime());
		G.flowmanager.update();
		tmx.draw((OrthographicCamera)stg.getCamera());
		handlestg.draw();
		skillstg.draw();
		
		if (G.markToWin){
			nextMisstion();
			G.markToWin=false;
		}
		
		if (G.dialogstg!=null)
			G.dialogstg.draw();
	}

	public void resize(int x, int y) {
		// TODO Auto-generated method stub

	}

	public void resume() {
		
	}

	public boolean keyDown(int keycode) { G.markToResetCamera=true; return hero.keyDown(keycode);}
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
		if (!G.markToResetCamera) return;
		Vector3 v=G.camera.position;
		if (Math.abs(G.hero.x+16-v.x)<=0.1&&Math.abs(G.hero.y+16-v.y)<=0.1) return;
		G.Log("("+G.hero.x+","+G.hero.y+"),("+v+")");
		G.camera.translate((G.hero.x+16)-v.x,(G.hero.y+16)-v.y,0);
		G.camera.update();
		G.camera.apply(Gdx.gl10);
	}
	
}
