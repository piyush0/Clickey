import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class ClickeyMain implements NativeKeyListener {

    Clip clip;
    AudioInputStream audioIn;

    public static final int KEY = 0;
    public static final int BACK = 1;
    public static final int SPACE = 2;

    public void load(NativeKeyEvent e) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        String filePath = new File("").getAbsolutePath();

        if (e.getKeyCode() == 14) {
            filePath = filePath.concat("/src/sounds/key_press_delete.wav");
        } else if (e.getKeyCode() == 57) {
            filePath = filePath.concat("/src/sounds/key_press_modifier.wav");
        } else {
            filePath = filePath.concat("/src/sounds/key_press_click.wav");
        }
        File soundFile = new File(filePath);
        audioIn = AudioSystem.getAudioInputStream(soundFile);
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

