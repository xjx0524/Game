package com.me.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.me.aaction.AActionInterval;
import com.me.aaction.ACall;
import com.me.aaction.ADelay;
import com.me.aaction.AFiniteTimeAction;
import com.me.aaction.AForever;
import com.me.aaction.AMoveBy;
import com.me.aaction.ASequence;
import com.me.aaction.ASprite;
import com.me.aaction.AWaitByTag;
import com.me.aaction.ICallFunc;
import com.me.inerface.IGTMX;

public class Hero extends ASprite{
	
	private Animation ani[]=new Animation[4];
	public static AActionInterval move[]=new AActionInterval[4];
	private float time = 0;
	private int direction = 0;
	int curdirection = 0;
	private boolean ismoving = false;
	private boolean iscurmoving = false;
	private Texture tex;
	private IGTMX tmx;
	private int mapx,mapy;
	private float sy;
	private float sx;
	private float oy;
	private float ox;
	public Skill skill = new Skill();
	public G.TAG skillindex = G.TAG.SKILL_NULL;
	private int lx = 0,ly = 0;

	Hero(){
		super();
		G.hero=this;
		tex=new Texture("hero.png");
		TextureRegion[][] texs=TextureRegion.split(tex, 32, 48);
		move[0]=AMoveBy.$(0.5f, 0,-32);
		move[1]=AMoveBy.$(0.5f, -32,0);
		move[2]=AMoveBy.$(0.5f, 32,0);
		move[3]=AMoveBy.$(0.5f, 0,32);
		
		for (int i=0;i<4;++i){
			ani[i]=new Animation(0.25f,texs[i]);
		}
		runAction(AForever.$(ASequence.$(
				ACall.$(new ICallFunc() {
					
					public void onCall(Object[] params) {						
						AFiniteTimeAction a;
						curdirection=direction;
						iscurmoving=ismoving;
						if (ismoving){

							int gx=mapx,gy=mapy;
							switch(curdirection){
							case 0:gy=mapy-1;break;
							case 1:gx=mapx-1;break;
							case 2:gx=mapx+1;break;
							case 3:gy=mapy+1;break;							
							}
							
							a=ASequence.$(move[direction],ACall.$(new ICallFunc() {
								 
								public void onCall(Object[] params) {
									if (G.hasmap) tmx.getTile(lx, ly).unactive(skill.generate(G.TAG.GEN_STAY));
									if (G.hasmap) tmx.getTile(mapx, mapy).active(skill.generate(G.TAG.GEN_STAY));
									lx=mapx;ly=mapy;
								}
							}));
							
							if (G.hasmap){
								if (tmx.getTile(gx, gy)!=null){
									if (!tmx.getTile(gx, gy).getIsAvaliable()){
										tmx.getTile(gx, gy).active(skill.generate(G.TAG.GEN_PUSH));
										a=ADelay.$(0.1f);
									}else{
										mapx=gx;mapy=gy;
										if (G.log) System.out.println("HeroPos:("+mapx+","+mapy+")");
									}
								}else{
									a=ADelay.$(0.1f);			
								}
							}else {mapx=gx;mapy=gy;if (G.log) System.out.println("HeroPos:("+mapx+","+mapy+") !!!There is no map!!!");}
						}else
						{
							a=ADelay.$(0.1f);
						}
						a.setTag(G.TAG.AM_MOVE);
						Hero.this.runAction(a);			
					}
					
				}),
				AWaitByTag.actionWithTag(G.TAG.AM_MOVE)
				)));
		init();
	}
	
	private void init() {
		tmx=G.tmx;
		Vector2 v;
		sx=160-16;sy=120-24;
		if (G.hasmap){
			v=tmx.getStartPointIndex();
			mapx=(int)v.x;mapy=(int)v.y;
			x=mapx*32;y=mapx*32;
	//		v=XTiledMap.toScreenCoordinate(v.x, v.y);
		}
	}
	
	public void startMove(int dir){
		direction=dir;
		ismoving=true;
	}
	
	public void stopMove(){
		ismoving=false;
	}

	 
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch,parentAlpha);
		//if (G.hasmap)tmx.setPosition(ox-ax,oy-ay);
		time+=Gdx.graphics.getDeltaTime();
		//System.out.println("("+ax+","+ay+")");
		if (iscurmoving)
			batch.draw(ani[curdirection].getKeyFrame(time,true), x, y);
		else{
			batch.draw(ani[curdirection].getKeyFrame(0),x,y);
			time=0;
		}
		float ax=x-sx-ox;float ay=y-sy-oy;
		ox=x-sx;oy=y-sy;
		getStage().getCamera().translate(new Vector3(ax,ay,0));
		getStage().getCamera().update();
		getStage().getCamera().apply(Gdx.gl10);
		//System.out.println(getStage().getCamera().position);			
	}

	 
	public Actor hit(float x, float y) {
		
		return null;
	}

	 
	public boolean keyDown(int keycode) {
		if (G.lockInput) return false;
		switch (keycode){
		case Input.Keys.DPAD_RIGHT:startMove(2);break;
		case Input.Keys.DPAD_DOWN:startMove(0);break;
		case Input.Keys.DPAD_UP:startMove(3);break;
		case Input.Keys.DPAD_LEFT:startMove(1);break;
		default : return super.keyDown(keycode);
		}
		return true;
	}
	
	 
	public boolean keyUp(int keycode) {
		switch (keycode){
		case Input.Keys.DPAD_RIGHT:
		case Input.Keys.DPAD_DOWN:
		case Input.Keys.DPAD_UP:
		case Input.Keys.DPAD_LEFT:stopMove();break;
		default : return super.keyUp(keycode);
		}
		return true;
	}
	
	

}
