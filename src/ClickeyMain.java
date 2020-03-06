import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ClickeyMain implements NativeKeyListener, NativeMouseListener {

    public static final int BACK = 14;
    public static final int SPACE = 57;

    public void playSoundForKey(NativeKeyEvent e) {
        if (e.getKeyCode() == BACK) {
        	playSound("key_press_delete.wav");
        } else if (e.getKeyCode() == SPACE) {
        	playSound("key_press_modifier.wav");
        } else {
            playSound("key_press_click.wav");
        }
    }

    void playSound(String filePath) {
    	try
    	{
	    	filePath = "/sounds/"+filePath;
	        InputStream audioSrc = getClass().getResourceAsStream(filePath);
	        InputStream bufferedIn = new BufferedInputStream(audioSrc);
	        AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
	        final Clip clip = AudioSystem.getClip();
            clip.addLineListener(new LineListener()
            {
                @Override
                public void update(LineEvent event)
                {
                    if (event.getType() == LineEvent.Type.STOP)
                    {
                        clip.close();
                    }
                }
            });
	        clip.open(audioIn);
	        clip.start();
	    } catch (Exception e)
	    {
	    	e.printStackTrace();
	    }
    }


    public void nativeKeyPressed(NativeKeyEvent e) {

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e1) {
                e1.printStackTrace();
            }
        } else {
        	playSoundForKey(e);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        
    }

    public void nativeKeyTyped(NativeKeyEvent e) {

    }

    public void nativeMouseClicked(NativeMouseEvent e) {

	}

	public void nativeMousePressed(NativeMouseEvent e) {
		playSound("mouse_pressed.wav");
	}

	public void nativeMouseReleased(NativeMouseEvent e) {
		playSound("mouse_released.wav");
	}

	public void nativeMouseMoved(NativeMouseEvent e) {
		
	}

	public void nativeMouseDragged(NativeMouseEvent e) {
		
	}

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        ClickeyMain instance = new ClickeyMain();
        GlobalScreen.addNativeKeyListener(instance);
        GlobalScreen.addNativeMouseListener(instance);

    }

}

