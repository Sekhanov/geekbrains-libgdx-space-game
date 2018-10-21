package ru.skhanov;

import com.badlogic.gdx.Game;

import ru.skhanov.screen.MenuScreen;

public class Start2DGame extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen());
    }
}
