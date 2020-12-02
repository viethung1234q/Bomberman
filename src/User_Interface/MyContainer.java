package User_Interface;

import PlayMode.*;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class MyContainer extends JPanel {
    private final String TAG_MENU = "tag_menu";
    private final String TAG_PLAY_PVP = "tag_play_pvp";
    private final String TAG_PLAY = "tag_play";
    private final String TAG_HTP = "tag_htp";



    private CardLayout myCardLayOut;
    private GUI gui;
    private Menu myMenu;
    private PlayPvP myPlayPvP;
    private PlayVsMonster myPlayVsMonster;
    private HowToPlay myHTP;

    public MyContainer(GUI gui) {
        this.gui = gui;

        setBackground(Color.BLACK);

        myCardLayOut = new CardLayout();
        setLayout(myCardLayOut);

        myMenu = new Menu(this);
        add(myMenu, TAG_MENU);

        myPlayPvP = new PlayPvP(this);
        add(myPlayPvP, TAG_PLAY_PVP);

        myPlayVsMonster = new PlayVsMonster(this);
        add(myPlayVsMonster, TAG_PLAY);

        myHTP = new HowToPlay(this);
        add(myHTP, TAG_HTP);

        showMenu();
    }

    public GUI getGui() {
        return gui;
    }

    public void showMenu() {
        myCardLayOut.show(this, TAG_MENU);
        myMenu.requestFocus();
        GameSound.getInstance().stop();
        GameSound.getInstance().getClip(GameSound.MENU).setFramePosition(0);
        GameSound.getInstance().getClip(GameSound.MENU).loop(5);
    }

    public void showPlayPvP() {
        myCardLayOut.show(this, TAG_PLAY_PVP);
        myPlayPvP.requestFocus();
        GameSound.getInstance().getClip(GameSound.MENU).stop();
        GameSound.getInstance().getClip(GameSound.PLAY_GAME).setFramePosition(0);
        GameSound.getInstance().getClip(GameSound.PLAY_GAME).loop(30);
    }

    public void showPlayVsMonster() {
        myCardLayOut.show(this, TAG_PLAY);
        myPlayVsMonster.requestFocus();
        GameSound.getInstance().getClip(GameSound.MENU).stop();
        GameSound.getInstance().getClip(GameSound.PLAY_GAME).setFramePosition(0);
        GameSound.getInstance().getClip(GameSound.PLAY_GAME).loop(30);
    }

    public void showHowToPlay() {
        myCardLayOut.show(this, TAG_HTP);
        myHTP.requestFocus();
    }
}
