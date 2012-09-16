package com.me.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.me.aaction.ACall;
import com.me.aaction.ADelay;
import com.me.aaction.AFadeIn;
import com.me.aaction.AFadeOut;
import com.me.aaction.AForever;
import com.me.aaction.ASequence;
import com.me.aaction.ASprite;
import com.me.aaction.ICallFunc;
import com.me.game.G.TAG;

public class Dialog extends ASprite {
	

	
	private Texture tex;
	private TextureRegion[][] texs;
	private Button close;
	private Button back;
	private Image next;
	private Image touchToContinue;
	private int page;

	@Override
	public TextureRegion getTextureRegion() {
		return null;
	}
	
	private ClickListener backListener=new ClickListener() {
		@Override
		public void click(Actor actor, float x, float y) {
			prevPage();				
		}
	};
	
	public Dialog(int mission) {
		super();
		y=40;
		x=60;
		width=256;
		height=256;
		tex=new Texture("dialog.png");
		texs=TextureRegion.split(new Texture("dialog_button.png"),32,32);
		
		close=new Button(texs[0][5],texs[0][4]);
		close.x=160;close.y=120;
		addActor(close);
		
		if (mission<G.maxTip){
			next=new Image(texs[0][3]);
			next.x=160;next.y=12;
			runAction(AForever.$(ASequence.$(
				ADelay.$(0.5f),
				ACall.$(new ICallFunc() {public void onCall(Object[] params) {next.y+=3;}}),
				ADelay.$(0.5f),
				ACall.$(new ICallFunc() {public void onCall(Object[] params) {next.y-=3;}})
				)));
			addActor(next);
		
			touchToContinue=new Image(new Texture("touchtocontinue.png"));
			touchToContinue.x=40;touchToContinue.y=24;//touchToContinue.color.a=128;
			addActor(touchToContinue);
		}
		
		if (mission>2){
			back=new Button(texs[0][1],texs[0][0]);
			back.x=4;back.y=4;
			addActor(back);
		}
		
		
		
		close.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y)  {
				G.playSound(TAG.SOUND_JUMP);
				if (G.curTip<=G.maxTip) {G.curTip++;G.game.save();}
				hide();
			}
		});
		
		
		if (back!=null) back.setClickListener(backListener);
		
		DialogText.oganize(this, page=mission);
		
		
	}

	@Override
	public void setTextureRegion(TextureRegion textureRegion) {		
	}

	@Override
	public Actor hit(float x, float y) {
		return this;
			
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha,tex);
	}
	
	public void show(){
		G.Log("Dialog show");
		++G.lockInput;
		G.Log("LocInput:"+G.lockInput+" HeroLock:"+G.hero.lock);
		G.dialogstg=new Stage(G.ScreenWidth, G.ScreenHeight,true);
		G.dialogstg.addActor(this);
		runAction(AFadeIn.$(0.5f));
	}
	
	private void hide(){
		runAction(ASequence.$(
				AFadeOut.$(0.5f),
				ACall.$(new ICallFunc() {					
					@Override
					public void onCall(Object[] params) {
						remove();
						G.dialogstg.dispose();
						G.dialogstg=null;
						--G.lockInput;
						G.Log("LocInput:"+G.lockInput+" HeroLock:"+G.hero.lock);
						G.Log("dialog hide");
					}
				})));
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer) {
		//G.Log("Dialog touchdown");
		return super.touchDown(x, y, pointer)||true;
	}
	
	@Override
	public boolean touchMoved(float x, float y) {
		return super.touchMoved(x, y);
	}
	
	@Override
	public void touchUp(float x, float y, int pointer) {
		//G.Log("Dialog touchup");
		nextPage();
		super.touchUp(x, y, pointer);
	}
	
	private void clean(){
		ArrayList<Actor> l=new ArrayList<Actor>(); 
		for (Actor p:getActors()){
			if (p==back) continue;
			if (p==close) continue;
			if (p==touchToContinue) continue;
			if (p==next) continue;
			l.add(p);
		}
		for (Actor p:l){
			p.remove();
		}
		l.clear();
		l=null;
	}
	
	public void nextPage(){
		G.Log("Next");
		if (G.curTip<=G.maxTip) {G.curTip++;G.game.save();}
		if (page>=G.maxTip) return;
		
		G.playSound(TAG.SOUND_PAGE);
		clean();
		DialogText.oganize(this, ++page);
		if (page>=G.maxTip){
			if (touchToContinue!=null) { touchToContinue.remove();touchToContinue=null;}
			if (next!=null) { stopAllActions(); next.remove();next=null;}
		}
		if (page>2&&back==null){
			back=new Button(texs[0][1],texs[0][0]);
			back.x=4;back.y=4;
			back.setClickListener(backListener);
			addActor(back);
		}
	}
	
	public void prevPage(){
		G.Log("Back");
		if (page<=2) return;
		
		G.playSound(TAG.SOUND_PAGE);
		clean();
		DialogText.oganize(this, --page);
		if (page<=2){
			back.remove();
			back=null;
		}
		if (page<G.maxTip){
			if (next==null){
				next=new Image(texs[0][3]);
				next.x=160;next.y=12;
				runAction(AForever.$(ASequence.$(
						ADelay.$(0.5f),
						ACall.$(new ICallFunc() {public void onCall(Object[] params) {next.y+=3;}}),
						ADelay.$(0.5f),
						ACall.$(new ICallFunc() {public void onCall(Object[] params) {next.y-=3;}})
						)));
				addActor(next);
			}
			if (touchToContinue==null){
				touchToContinue=new Image(new Texture("touchtocontinue.png"));
				touchToContinue.x=40;touchToContinue.y=24;//touchToContinue.color.a=128;
				addActor(touchToContinue);
			}
		}
	}
}


final class DialogText{
	
	static String[][] text=new String[][]{
			//0
			{"SKILL_MUSIC","���ڿ�","��������"},//0
			{"SKILL_LOCKMOVE","����ʱ,ֻ","ת���ƶ�."},//1
			{"SKILL_RESTART","���¿�ʼ","��ǰ�ؿ���"},//2
			//1
			{"TILE_DESTINATION","�յ�","������ҵ�Ŀ�ĵأ�"},//3
			//2
			{"OBJ_DOOR","��","����취������"},//4
			{"TILE_PEDAL","̤��","����ȥ�ƺ��ᷢ��ʲô������¡�"},//5
			//3
			{"SKILL_PUSH","��","�ҿ����ƶ�һЩ���塣"},//6
			{"OBJ_PUSHABLE","����","�ⶫ���ƺ����Ƶö���"},//7
			//4
			{"TILE_GROUND2","�����","����ƺ�û���Ѷ�������ȥ��"},//8
			//5
			{"TILE_HOLE","��","��ط��ɱ����ȥ���������������ɶ���ϡ�"},//9
			{"TILE_WATER","ˮ","�Բ�����Ӿ������˵����Ǹ���û������"},//10
			//6
			{"TILE_ICE","����","�û���"},//11
			//7
			{"TILE_TELEPORT","������","�����˴��͵����أ�"},//12
			//8
			{"SKILL_PULL","��","�ҿ�������һЩ���塣"},//13
			{"OBJ_PULLABLE","����","�ⶫ�����񻹿�������"},//14
			//9
			{"SKILL_FREEZE","����","�ҿ��԰�һЩˮ��������"},//15
			//10
			{"TILE_SAND_U1","��ɳ","����ȥ�Ļ���֪���ᱻ�嵽�ġ�"},//16
			//11
			{"TILE_STREAM_U1","��ˮ","����ȥ�Ļ���֪���ֻᱻ�嵽�ġ�"},//17
			//12
			{"OBJ_ICE","����","�������󰡡�"},//18
			//13			
			{"OBJ_WATER","Сˮ��","���ò�������߹�ȥ��"},//19
			//14
			{"SKILL_THAW","�ڻ�","�ҿ��԰ѱ��ڻ�����"},//20
			//15
			{"SKILL_JUMP","��Ծ","�ҿ�������һЩ������"},//21
			//16
			//17			
			{"OBJ_BLOCK","��ʯ","�ޣ�������ʯͷ��������·�ˡ�"},//22
			
	};
			
			
			
	
	
	
	
	static TextureRegion getPic(int i){
		TAG tag=TAG.valueOf(text[i][0]);
		switch(tag){
		case OBJ_PUSHABLE:case OBJ_PULLABLE:case OBJ_WATER:case OBJ_ICE:case OBJ_DOOR:case OBJ_BLOCK:
			return G.motp.getTexture(tag);
		case TILE_DESTINATION:case TILE_GROUND2:case TILE_HOLE:case TILE_WATER:case TILE_ICE:
		case TILE_PEDAL:case TILE_TELEPORT:case TILE_SAND_U1:case TILE_STREAM_U1:		
		case SKILL_PUSH:case SKILL_PULL:case SKILL_FREEZE:case SKILL_THAW:case SKILL_JUMP:
		case SKILL_MUSIC:case SKILL_RESTART:case SKILL_LOCKMOVE:
			return G.sbtp.getTexture(tag, true);
		}		
		return null;		
	}
	
	static String getTitle(int i){
		return text[i][1];
	}
	
	static String getText(int i){
		return text[i][2];
	}
	
	static void oganize(Group g,int i){
		if (i<3){
			 for (int j=0;j<3;++j){
				 Image image=new Image(DialogText.getPic(j));
				 image.x=32;image.y=102-32*j;
				 g.addActor(image);
				 Label label=new Label(getTitle(j), G.lsText);
				 label.x=64;label.y=117-32*j;
				 g.addActor(label);
				 label=new Label(getText(j), G.lsText);
				 label.x=64;label.y=103-32*j;
				 g.addActor(label);
			 }
			return;
		}
		Image image=new Image(DialogText.getPic(i));
		image.x=32;image.y=98;
		g.addActor(image);
		
		String s=getTitle(i);
		Label label=new Label(s,G.lsTitle);
		label.x=100-16*(s.length()-1);label.y=100;
		g.addActor(label);
		
		
		s=getText(i);
		String ss;
		if (s.length()>=6){
			ss= s.substring(0, 6);
		}else{
			ss=s;
		}
		int line=70;
		int k=6;
		label=new Label(ss,G.lsText);label.x=64;label.y=line;
		g.addActor(label);
		
		while (s.length()>k){
			int p=k+8;
			if (p>s.length()) p=s.length();
			ss= s.substring(k, p);
			k+=8;line-=16;
			label=new Label(ss,G.lsText);label.x=32;label.y=line;
			g.addActor(label);			
		}
		
	//	label=new Label(getText(i), lsText);
	///	label.x=40;label.y=32;
	//	g.addActor(label);
	}
}


