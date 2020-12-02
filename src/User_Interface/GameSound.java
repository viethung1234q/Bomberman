package User_Interface;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;

public class GameSound {
    public static GameSound instance;

    public static final String MENU = "menu.wav";
    public static final String PLAY_GAME = "play_game.wav";
    public static final String BOMB = "new_bomb.wav";
    public static final String BOMBER_DIE = "bomber_die.wav";
    public static final String MONSTER_DIE = "monster_die.wav";
    public static final String BOMB_EXPLODE = "bomb_explode.wav";
    public static final String ITEM = "item.wav";
    public static final String WIN = "win.wav";
    public static final String LOSE = "lose.mid";
    private HashMap<String, Clip> audio;


    public GameSound() {
        audio = new HashMap<>();
        loadAllAudio();
    }

    public static GameSound getInstance() {
        if (instance == null) {
            instance = new GameSound();
        }

        return instance;
    }

    public void loadAllAudio() {
        putClip(MENU);
        putClip(PLAY_GAME);
        putClip(BOMB);
        putClip(MONSTER_DIE);
        putClip(BOMBER_DIE);
        putClip(BOMB_EXPLODE);
        putClip(ITEM);
        putClip(WIN);
        putClip(LOSE);
    }

    public void stop() {
        getClip(MENU).stop();
        getClip(PLAY_GAME).stop();
        getClip(BOMB).stop();

        getClip(BOMB_EXPLODE).stop();
        getClip(WIN).stop();
        getClip(LOSE).stop();
    }

    public void putClip(String name) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(name)));
            audio.put(name, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Clip getClip (String name) {
        return audio.get(name);
    }
}


