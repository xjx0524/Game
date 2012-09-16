package com.me.game;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.me.inerface.IGObjectGroup;
import com.me.map.XFlowManager;
import com.me.map.XTiledMap;
import com.me.screens.LoadingScreen;

public final class G {
	
	public static final int ScreenHeight = 240;
	public static final int ScreenWidth  = 320;
	public static enum TAG{
		AM_MOVE, AA_DOOROPEN, AA_DOORCLOSE,
		
		DIR_LEFT, DIR_RIGHT, DIR_DOWN, DIR_UP, DIR_NONE,
		
		SKILL_PUSH, SKILL_PULL, SKILL_JUMP, SKILL_FREEZE, SKILL_THAW, SKILL_FOOTSTEP,
		SKILL_NULL, SKILL_OBJECTMOVEDON,SKILL_MOVE,SKILL_TELE,SKILL_OBJECTTELE, 
		SKILL_RESTART, SKILL_MUSIC, SKILL_LOCKMOVE,
		
		GEN_STAY, GEN_PUSH, GEN_PULL,
		
		OBJ_PUSHABLE, OBJ_PULLABLE, OBJ_DOOR, OBJ_WATER, OBJ_ICE, OBJ_BLOCK, OBJ_WALL,
		
		TILE_GROUND1,TILE_GROUND2,TILE_WATER,TILE_TELEPORT,TILE_HOLE,TILE_ICE,TILE_DESTINATION,TILE_OTHER,
		TILE_PEDAL,TILE_PEDAL_1,TILE_PEDAL_2,TILE_PEDAL_3,TILE_PEDAL_4,TILE_PEDAL_DOWN,
		TILE_STREAM_U1,TILE_STREAM_U2,TILE_STREAM_U3,TILE_STREAM_U4,
		TILE_STREAM_D1,TILE_STREAM_D2,TILE_STREAM_D3,TILE_STREAM_D4,
		TILE_STREAM_L1,TILE_STREAM_L2,TILE_STREAM_L3,TILE_STREAM_L4,
		TILE_STREAM_R1,TILE_STREAM_R2,TILE_STREAM_R3,TILE_STREAM_R4,
		TILE_SAND_U1,TILE_SAND_U2,TILE_SAND_U3,TILE_SAND_U4,
		TILE_SAND_D1,TILE_SAND_D2,TILE_SAND_D3,TILE_SAND_D4,
		TILE_SAND_L1,TILE_SAND_L2,TILE_SAND_L3,TILE_SAND_L4,
		TILE_SAND_R1,TILE_SAND_R2,TILE_SAND_R3,TILE_SAND_R4, 
		
		DOOR_OPEN,DOOR_CLOSE,DOOR_WAIT, 
		HS_ALIVE,HS_RELIVING,HS_DEAD, 
		
		SOUND_JUMP, SOUND_FREEZE, SOUND_PUSH, SOUND_PULL, SOUND_THAW, SOUND_FALL, SOUND_WATER, 
		SOUND_PEDAL, SOUND_DOOR, SOUND_TELEPORT, SOUND_MOVE, SOUND_SLIDE, SOUND_FLOW, 
		SOUND_PAGE, SOUND_NO_NAME, SOUND_WIN, 
		MUSIC_BGM1, MUSIC_BGM2, MUSIC_BGM3,
		
		BUTTON_BACK_BIG, BUTTON_BACK_SMALL, 
		};
	public static float dis(float x1,float y1,float x2,float y2){
		return (float)Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		
		}
	
	public final static boolean log = true;
	@SuppressWarnings("unused")
	public static void Log(String s){
		if (!log) return; 
		System.out.println(s);
	}
	
	public static Boolean loadfinished;	
	public static GameMain game;
	public static GameScreen gameScreen;	
	public static boolean hasmap = true;
	public static int lockInput = 0;
	public static Hero hero;
	public static XTiledMap tmx;
	public static MapObjectTextureProvider motp;
	public static SkillButtonTextureProvider sbtp;
	public static SpriteBatch batch;
	public static IGObjectGroup objectGroup;
	public static SkillButtonGroup skillBottonGroup;
	public static Stage dialogstg;
	public static ToggleGroup toggleGroup;
	public static XFlowManager flowmanager;
	public static Map<TAG,Sound> soundEntrance;
	public static Map<TAG,Music> musicEntrance;
	public static boolean signToSave = false;
	public static TAG selectedSkill = null;
	public static boolean markToWin = false;
	public static boolean markToResetCamera = true;
	public static Camera camera = null;
	public static boolean mapChanged = false; 
	public static int maxTip = 0;
	public static int curTip = 0;
	public static int maxMission = 1;
	public static boolean musicOn = false , soundOn = true;
	public static LabelStyle lsTitle;
	public static LabelStyle lsText;
	public static boolean markToRestart = false;
	public static boolean markToBack = false;
	public static TAG musicPlaying = null;
	
	public static void generateTip(int i){
		switch(i){
		case 1:setTip(2,3);return;
		case 2:setTip(4,5);return;
		case 3:setTip(6,7);return;
		case 4:setTip(8,8);return;
		case 5:setTip(9,10);return;
		case 6:setTip(11,11);return;
		case 7:setTip(12,12);return;
		case 8:setTip(13,14);return;
		case 9:setTip(15,15);return;
		case 10:setTip(16,16);return;
		case 11:setTip(17,17);return;
		case 12:setTip(18,18);return;
		case 13:setTip(19,19);return;
		case 14:setTip(20,20);return;
		case 15:setTip(21,21);return;
		case 16:setTip(22,21);return;
		case 17:setTip(22,22);return;
		case 18:setTip(23,22);return;
		}
	}
	
	private static void setTip(int i, int j) {
		if (i>curTip) curTip=i;
		if (j>maxTip) maxTip=j;	
		
	}

	public static final class Label{
		public final static String Name="Name";
		public final static String Avaliable="Avaliable";
		public final static String Block="Block";
		public final static String Arg0="Arg0";
		public final static String Arg1="Arg1";
		public final static String Arg2="Arg2";
		public final static String Arg3="Arg3";
		public final static String Arg4="Arg4";
		public final static String OName="Name";
		public final static String OAvaliable="OAvaliable";
		public final static String OArg0="OArg0";
		public final static String OArg1="OArg1";
		public final static String OArg2="OArg2";
		public final static String OArg3="OArg3";
		public final static String OArg4="OArg4";
		public final static String DIR_LEFT="left";
		public final static String DIR_RIGHT="right";
		public final static String DIR_UP="up";
		public final static String DIR_DOWN="down";
	}
	
	public static int parseDirection(TAG tag) {
		switch (tag){
		default: return -1;
		case DIR_LEFT:return 1;
		case DIR_RIGHT:return 2;
		case DIR_DOWN:return 0;
		case DIR_UP:return 3;
		}		
	}
	
	public static TAG parseDirection(int dir) {
		switch (dir){
		default: return null;
		case 1:return TAG.DIR_LEFT;
		case 2:return TAG.DIR_RIGHT;
		case 0:return TAG.DIR_DOWN;
		case 3:return TAG.DIR_UP;		
		}		
	}
	
	public static boolean skillisactived(TAG tag){
		return skillBottonGroup.getIsActived(tag);
	}
	
	public static void save() {
		if (tmx!=null)
			tmx.save();
		if (toggleGroup!=null)
			toggleGroup.save();
		signToSave =false;
	}
	
	public static void load() {
		if (tmx!=null)
			tmx.load();
		if (toggleGroup!=null)
			toggleGroup.load();
	}

	public static void initSound(){
		
		soundEntrance=new HashMap<TAG, Sound>();
		soundEntrance.put(TAG.SOUND_FALL  , Gdx.audio.newSound(Gdx.files.internal("sound/hole.ogg")));
		soundEntrance.put(TAG.SOUND_WATER , Gdx.audio.newSound(Gdx.files.internal("sound/water.ogg")));
		soundEntrance.put(TAG.SOUND_FREEZE, Gdx.audio.newSound(Gdx.files.internal("sound/freeze.ogg")));
		soundEntrance.put(TAG.SOUND_THAW  , Gdx.audio.newSound(Gdx.files.internal("sound/thaw.ogg")));
		soundEntrance.put(TAG.SOUND_PUSH  , Gdx.audio.newSound(Gdx.files.internal("sound/push.ogg")));
		soundEntrance.put(TAG.SOUND_PEDAL , Gdx.audio.newSound(Gdx.files.internal("sound/pedal.ogg")));
		soundEntrance.put(TAG.SOUND_DOOR  , Gdx.audio.newSound(Gdx.files.internal("sound/door.ogg")));
		soundEntrance.put(TAG.SOUND_JUMP  , Gdx.audio.newSound(Gdx.files.internal("sound/jump.ogg")));
		soundEntrance.put(TAG.SOUND_TELEPORT  , Gdx.audio.newSound(Gdx.files.internal("sound/teleport.ogg")));
		soundEntrance.put(TAG.SOUND_SLIDE , Gdx.audio.newSound(Gdx.files.internal("sound/slide.ogg")));
		soundEntrance.put(TAG.SOUND_FLOW  , Gdx.audio.newSound(Gdx.files.internal("sound/flow.ogg")));
		soundEntrance.put(TAG.SOUND_PAGE  , Gdx.audio.newSound(Gdx.files.internal("sound/page.ogg")));
		soundEntrance.put(TAG.SOUND_NO_NAME  , Gdx.audio.newSound(Gdx.files.internal("sound/no_name.ogg")));
		soundEntrance.put(TAG.SOUND_WIN  , Gdx.audio.newSound(Gdx.files.internal("sound/win.ogg")));
		
		
		musicEntrance=new HashMap<TAG,Music>();
		musicEntrance.put(TAG.MUSIC_BGM1, Gdx.audio.newMusic(Gdx.files.internal("music/bgm1.ogg")));
		musicEntrance.put(TAG.MUSIC_BGM2, Gdx.audio.newMusic(Gdx.files.internal("music/bgm2.ogg")));
		musicEntrance.put(TAG.MUSIC_BGM3, Gdx.audio.newMusic(Gdx.files.internal("music/bgm3.ogg")));
	}
	
	
	public static void playSound(TAG tag){
		if (!soundOn) return;
		G.Log("PlaySound: "+tag);
		Sound s;
		if ((s=soundEntrance.get(tag))!=null)
			s.play();
		else 
			G.Log("!!!Error!!! No sound found!!!");
	}
	
	public static void playMusic(TAG tag){
		if (musicPlaying==tag) return;
		if (!soundOn) {
			musicPlaying=tag;
			G.Log("PlayMisic: "+tag+" Sound off");
			return;
		}
		G.Log("PlayMisic: "+tag);
		Music s;
		if ((s=musicEntrance.get(musicPlaying))!=null){
			s.stop();
		}else
			G.Log("!!!Error!!! No music found!!!");
		
		if ((s=musicEntrance.get(tag))!=null){
			s.setLooping(true);
			s.play();
		}else 
			G.Log("!!!Error!!! No music found!!!");
		
		musicPlaying=tag;
	}
	
	public static void SoundOn(boolean on){
		G.Log("Sound on "+on);		
		soundOn=on;
		if (on){
			Music s;
			s=musicEntrance.get(musicPlaying);
			s.setLooping(true);
			s.play();
		}else
		{
			Music s;
			s=musicEntrance.get(musicPlaying);
			if (s!=null)
				s.stop();
			else
				G.Log("!!!Error!!! No music found!!!");
		}
	}
	
	public static void restart(){
		Gdx.input.setInputProcessor(null);
		game.setScreen(new LoadingScreen(G.gameScreen.getMission()));
		lockInput=0;
		G.Log("Restart");
		markToWin=false;
		markToResetCamera=true;
		markToRestart=false;
		markToBack=false;
		//playMusic(TAG.MUSIC_BGM1);
	}
	
	public static void disposeSound(){
		for (TAG p:soundEntrance.keySet()){
			soundEntrance.get(p).dispose();
		}
		for (TAG p:musicEntrance.keySet()){
			musicEntrance.get(p).dispose();
		}
		soundEntrance.clear();
		soundEntrance=null;
		musicEntrance.clear();
		musicEntrance=null;
	}
}
