package com.me.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.inerface.IGObjectGroup;
import com.me.map.XFlowManager;
import com.me.map.XTiledMap;

public final class G {
	
	public static final int ScreenHeight = 240;
	public static final int ScreenWidth  = 320;
	public static enum TAG{
		AM_MOVE, AA_DOOROPEN, AA_DOORCLOSE,
		
		DIR_LEFT, DIR_RIGHT, DIR_DOWN, DIR_UP, DIR_NONE,
		
		SKILL_PUSH, SKILL_PULL, SKILL_JUMP, SKILL_FREEZE, SKILL_THAW, SKILL_FOOTSTEP,
		SKILL_NULL, SKILL_OBJECTMOVEDON,SKILL_MOVE,SKILL_TELE,
		
		GEN_STAY, GEN_PUSH, GEN_PULL,
		
		OBJ_PUSHABLE, OBJ_PULLABLE, OBJ_DOOR, OBJ_WATER, OBJ_ICE, OBJ_BLOCK, OBJ_WALL,
		
		TILE_GROUND1,TILE_GROUND2,TILE_WATER,TILE_TELEPORT,TILE_HOLE,TILE_ICE,TILE_OTHER,
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
		};
	public static float dis(float x1,float y1,float x2,float y2){
		return (float)Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		
		}
	public static void Log(String s){
		if (!log) return; 
		System.out.println(s);
	}
	public static boolean log = true;
	public static boolean hasmap = true;
	public static boolean lockInput = false;
	public static Hero hero;
	public static XTiledMap tmx;
	public static MapObjectTextureProvider motp;
	public static SkillButtonTextureProvider sbtp;
	public static SpriteBatch batch;
	public static IGObjectGroup objectGroup;
	public static SkillButtonGroup skillBottonGroup;
	public static ToggleGroup toggleGroup;
	public static XFlowManager flowmanager;
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
	}

}
