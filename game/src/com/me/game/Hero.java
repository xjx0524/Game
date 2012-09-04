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
import com.me.aaction.ABlink;
import com.me.aaction.ACall;
import com.me.aaction.ADelay;
import com.me.aaction.AFadeOut;
import com.me.aaction.AFiniteTimeAction;
import com.me.aaction.AForever;
import com.me.aaction.AMoveBy;
import com.me.aaction.AScaleTo;
import com.me.aaction.ASequence;
import com.me.aaction.ASpawn;
import com.me.aaction.ASprite;
import com.me.aaction.AWaitByTag;
import com.me.aaction.ICallFunc;
import com.me.game.G.TAG;
import com.me.inerface.IGObject;
import com.me.inerface.IGTMX;

public class Hero extends ASprite{
	
	private Animation ani[]=new Animation[4];
	private AActionInterval move[]=new AActionInterval[4];
	private float time = 0;
	private int direction = 0;
	int curdirection = 0;
	private boolean ismoving = false;
	private boolean iscurmoving = false;
	private IGTMX tmx;
	public int mapx,mapy;
	private float sy;
	private float sx;
	private float oy;
	private float ox;
	private int lstablex,lstabley;
	public Skill skill = new Skill();
	public G.TAG skillindex = G.TAG.SKILL_NULL;
	/**禁止移动*/
	public boolean lockInput = false;
	int lx = 0;
	int ly = 0;
	private TAG state = TAG.HS_ALIVE;

	Hero(){
		super();
		rotation=0;
		height=48;
		width=32;
		originX=16;
		originY=16;
		G.hero=this;
		Texture tex=new Texture("hero.png");
		TextureRegion[][] texs=TextureRegion.split(tex, 32, 48);
		move[0]=AMoveBy.$(0.5f, 0,-32);
		move[1]=AMoveBy.$(0.5f, -32,0);
		move[2]=AMoveBy.$(0.5f, 32,0);
		move[3]=AMoveBy.$(0.5f, 0,32);
		
		for (int i=0;i<4;++i){
			ani[i]=new Animation(0.25f,texs[i]);
		}
		

		tmx=G.tmx;
		Vector2 v;
		sx=160-16;sy=120-24;
		if (G.hasmap){
			v=tmx.getStartPointIndex();
			mapx=(int)v.x;mapy=(int)v.y;
			x=mapx*32;y=mapx*32;
	//		v=XTiledMap.toScreenCoordinate(v.x, v.y);
		}
		init();
	}
	
	private void init() {
		runAction(AForever.$(ASequence.$(
				ACall.$(new ICallFunc() {
					
					public void onCall(Object[] params) {	
						if (lockInput) ismoving=false;
						switch(state){
						case HS_DEAD:relive();break;
						case HS_ALIVE:if (lockInput) break;
							if (lstablex!=mapx||lstabley!=mapy){
								lstablex=mapx;lstabley=mapy;
								if (tmx!=null)	tmx.save();
							}
							break;
						case HS_RELIVING:break;
						}
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
										int kx=mapx,ky=mapy;
										switch(3-curdirection){
										case 0:ky=mapy-1;break;
										case 1:kx=mapx-1;break;
										case 2:kx=mapx+1;break;
										case 3:ky=mapy+1;break;							
										}
										mapx=gx;mapy=gy;
										tmx.getTile(kx, ky).active(skill.generate(G.TAG.GEN_PULL));									
										if (G.log) {
											String s="HeroPos:("+mapx+","+mapy+") "+tmx.getTile(mapx, mapy).getTag();
											if (tmx.getObject(mapx, mapy)!=null) 
												for (IGObject p:tmx.getObject(mapx, mapy)) s+=p.getId()+",";
											else s+=" No Object";
											s+=" CameraPos("+G.hero.getStage().getCamera().position.x+","+G.hero.getStage().getCamera().position.y+")";
											System.out.println(s);										
										}
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
		
	}
	
	public void startMove(int dir){
		direction=dir;
		G.Log("----------------------------------------------------------("+lstablex+","+lstabley+")");
		ismoving=true;			
	}
	
	public void stopMove(){
		ismoving=false;
	}

	 
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch,parentAlpha);
		//if (G.hasmap)tmx.setPosition(ox-ax,oy-ay);
		time+=Gdx.graphics.getDeltaTime();
		if (iscurmoving){
			if (visibleForPlayer){
				batch.setColor(color);
				batch.draw(ani[curdirection].getKeyFrame(time,true), x, y, originX, originY, width, height,scaleX, scaleY, rotation);
			}
		}else{
			if (visibleForPlayer){
				batch.setColor(color);
				batch.draw(ani[curdirection].getKeyFrame(time,true), x, y, originX, originY, width, height,scaleX, scaleY, rotation);
			}
			time=0;
		}
		float ax=x-sx-ox;float ay=y-sy-oy;
		ox=x-sx;oy=y-sy;
		//forward();
		getStage().getCamera().translate(new Vector3(ax,ay,0));
		getStage().getCamera().update();
		getStage().getCamera().apply(Gdx.gl10);		
	}
	
	/**向指定方向前进*/
	public void forward(G.TAG dir){
		if (this.lockInput) return;
		direction=G.parseDirection(dir);
		int gx=mapx,gy=mapy;
		switch(dir){
		case DIR_DOWN:gy=mapy-1;break;
		case DIR_LEFT:gx=mapx-1;break;
		case DIR_RIGHT:gx=mapx+1;break;
		case DIR_UP:gy=mapy+1;break;
		}
		final int GX=gx,GY=gy;
		if (tmx.getTile(gx, gy)==null||!tmx.getTile(gx, gy).getIsAvaliable()) return;
		mapx=GX;mapy=GY;
		this.lockInput=G.lockInput=true;
		runAction(ASequence.$(
				move[G.parseDirection(dir)],
				ACall.$(new ICallFunc() {
					public void onCall(Object[] params) {						
						Hero.this.lockInput=G.lockInput=false;
						if (G.hasmap) tmx.getTile(lx, ly).unactive(skill.generate(G.TAG.GEN_STAY));
						if (G.hasmap) tmx.getTile(mapx, mapy).active(skill.generate(G.TAG.GEN_STAY));
						lx=mapx;ly=mapy;
						G.Log("StablePoint("+lstablex+","+lstabley+")");
					}
				})
				));
	}
	
	/**向当前方向前进*/
	public void forward(){
		forward(G.parseDirection(curdirection));
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

	@Override
	public TextureRegion getTextureRegion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTextureRegion(TextureRegion textureRegion) {
		// TODO Auto-generated method stub
		
	}

	public void dead() {
		state=TAG.HS_DEAD;
		lockInput=G.lockInput=true;
	}
	
	public void relive(){
		state=TAG.HS_RELIVING;
		tmx.load();
		runAction(
				ASequence.$(
						ASpawn.$(
								AFadeOut.$(1f),
								AScaleTo.$(0.5f,0.5f,0.5f),
								AMoveBy.$(1f, 0, -10)
								),
						ADelay.$(0.5f),
						ASpawn.$(
							ABlink.$(1.5f, 6),
							ACall.$(new ICallFunc() {
							public void onCall(Object[] params) {
								color.a=1.0f;
								state=TAG.HS_ALIVE;
								mapx=lstablex;mapy=lstabley;
								x=mapx*32;y=mapy*32;	
								G.lockInput=lockInput=false;
								scaleX=scaleY=1;
							}
						}))
						));
		//runAction(ABlink.$(0.5f, 6));
		//color.a=0.0f;
		//action(FadeIn.$(5.0f));
	}

}
