package com.templaro.opsiz.aka;

import android.util.Log;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

/* A bunch of this code is copied from
 * http://marblemice.blogspot.com/2010/04/generate-and-play-tone-in-android.htm
 * 
 * All of the public methods should be called within a new Thread.
 */


public class MorsePlayer {
	private String TAG = "MorsePlayer";
	private int sampleRate = 8000;
	private double duration = .1;  // in seconds
	private int numSamples;
	private double sample[];
	private byte ditSnd[];
	private byte dahSnd[];
	private byte pauseInnerSnd[];
	// TODO: pauseLetterSnd, pauseWordSnd
	private AudioTrack audioTrack;
	

	// Prepare to play morse code at SPEED wpm and HERTZ frequency,
	// by pre-generating 'dit' and 'dah' sinewave tones of the proper length.
	public MorsePlayer(int hertz, int speed) {
		Log.i(TAG, "Generating dit and dah tones.");
		
		// duration should be derived from SPEED, 
		// where (1200 / wpm) = element length in milliseconds
		numSamples = 800; // duration * sampleRate;
		sample = new double[numSamples];
		ditSnd = new byte[2 * numSamples];
		dahSnd = new byte[6 * numSamples];
		pauseInnerSnd = new byte[2 * numSamples];
		
		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, 10 * numSamples,
                AudioTrack.MODE_STREAM);
		audioTrack.play();  // begin asynchronous playback of anything streamed to the track
		
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/hertz));
        }
        // convert to 16 bit pcm sound array; assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            final short val = (short) ((dVal * 32767)); // scale to maximum amplitude
            // in 16 bit wav PCM, first byte is the low order byte
            ditSnd[idx++] = (byte) (val & 0x00ff);
            ditSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
        for (int i = 0; i < (dahSnd.length); i++) {
        	dahSnd[i] = ditSnd[i % ditSnd.length];
        }
        for (int i = 0; i < (pauseInnerSnd.length); i++) {
        	pauseInnerSnd[i] = 0;
        }
	}
	
	public void playMorse(String message) {
		// check to make sure sine data is already generated
		Log.i(TAG, "Now playing morse code...");
		for (int i = 0; i < 4; i++) {
			try {
				audioTrack.write(ditSnd, 0, ditSnd.length);
				audioTrack.write(pauseInnerSnd, 0, pauseInnerSnd.length);
				audioTrack.write(dahSnd, 0, dahSnd.length);
			    Log.i(TAG, "HIT PLAY");
			    Thread.sleep(1000);
			    Log.i(TAG, "SLEPT 1 sec");
			} catch (InterruptedException e) {
				Log.i(TAG, "Interrupted, stopping all sound...");
				audioTrack.stop(); // make sure no sound is playing
				return;
			}
		}
	}
	
}
