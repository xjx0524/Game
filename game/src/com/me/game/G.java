package com.me.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.inerface.IGObjectGroup;
import com.me.inerface.IGTMX;

public final class G {
	
	public static final int ScreenHeight = 240;
	public static final int ScreenWidth  = 320;
	public static enum TAG{
		AM_MOVE,
		DIR_LEFT,DIR_RIGHT,DIR_DOWN,DIR_UP,
		SKILL_NULL,SKILL_PUSH, SKILL_OBJECTMOVEDON,
		GEN_STAY,GEN_PUSH};
	public static float dis(float x1,float y1,float x2,float y2){
		return (float)Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		
		}
	public static boolean log = false;
	public static boolean hasmap = true;
	public static boolean lockInput = false;
	public static Hero hero;
	public static IGTMX tmx;
	public static MapObjectTextureProvider motp;
	public static SkillButtonTextureProvider sbtp;
	public static SpriteBatch batch;
	public static IGObjectGroup objectGroup;
	public static SkillButtonGroup skillBottonGroup;
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
		public final static String OAvaliable="Avaliable";
		public final static String OArg0="OArg0";
		public final static String OArg1="OArg1";
		public final static String OArg2="OArg2";
		public final static String OArg3="OArg3";
		public final static String OArg4="OArg4";	
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
	
	public static boolean skillisactived(TAG tag){
		return skillBottonGroup.getIsActived(tag);
	}

}
