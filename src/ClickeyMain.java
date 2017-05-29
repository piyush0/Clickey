import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ClickeyMain implements NativeKeyListener {

    Clip clip;
    AudioInputStream audioIn;

    public static final int BACK = 14;
    public static final int SPACE = 57;

    public void load(NativeKeyEvent e) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        String filePath = "";
        if (e.getKeyCode() == BACK) {
            filePath = "/sounds/key_press_delete.wav";
        } else if (e.getKeyCode() == SPACE) {
            filePath = "/sounds/key_press_modifier.wav";
        } else {
            filePath = "/sounds/key_press_click.wav";
        }
        InputStream audioSrc = getClass().getResourceAsStream(filePath);
        InputStream bufferedIn = new BufferedInputStream(audioSrc);
        audioIn = AudioSystem.getAudioInputStream(bufferedIn);
        clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    }


    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println(e.getKeyCode());

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e1) {
                e1.printStackTrace();
            }
        } else {
            try {
                load(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (LineUnavailableException e1) {
                e1.printStackTrace();
            } catch (UnsupportedAudioFileException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        clip.stop();
    }

    public void nativeKeyTyped(NativeKeyEvent e) {

    }

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new ClickeyMain());
    }

}

