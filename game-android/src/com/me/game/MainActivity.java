package com.me.game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
	
	private GameMain game;
	private String path;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        path=getFilesDir()+"\\";
        File file=new File(path+"Mystemple.sav");
        if (!file.exists()){
        	try {
				file.createNewFile();
				FileOutputStream s=new FileOutputStream(file);
				s.write(new byte[]{1,1,1,2}, 0, 4);
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        game=new GameMain();
        game.file=file;
        //game.SDcardExists=existSDcard();
        //if (!game.SDcardExists) {
        //  Toast toast = Toast.makeText(this, "未找到SD卡,无法存取游戏进度", Toast.LENGTH_SHORT);
        //	toast.show();
        //}
        initialize(game, cfg);
        
    }
    
    public static boolean existSDcard()
    {
        if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState()))
        {
            return true;
        }
        else
            return false;
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	//System.exit(0);
    }
    
    @Override
    public void onBackPressed() {
    	if (game.fin){
    		super.onBackPressed();
    	}
    }
   
}