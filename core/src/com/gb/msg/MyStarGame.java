package com.gb.msg;

import com.badlogic.gdx.Game;
import com.gb.msg.screen.MenuScreen;

public class MyStarGame extends Game {

	@Override
	public void create () {
		setScreen(new MenuScreen(this));
	}
}
