//************************************************************************
//
//	File: PianoHero.java         CPSC 112 Final Project
// 
//	Author: Hudson Lee           Email: hudson.lee@yale.edu
//
//	Class: GuitarHero
//	Dependencies: GuitarString, HarpsichordString, WilhelmScream,
//	RingBuffer, StdAudio, StdDraw
//
//	Time Spent on this Problem:
//  ---------------------
//		This program creates 
//
//************************************************************************

public class PianoHero {

    public static void main(String[] args) {
    	//NOTE: maybe we can incorporate the drawing into the keyboard graphic
    	
    	
    	
	int    DRAW_SAMPLE_RATE = 20;    // draw at a rate of 20/sec
	int    AUDIO_PER_DRAW   = StdAudio.SAMPLE_RATE / DRAW_SAMPLE_RATE;

	int    PLAY_TIME        = 10;    // target 60 seconds display window
	int    XWIDTH           = DRAW_SAMPLE_RATE * PLAY_TIME;

        // Create array of Sounds, fill with dummy values
       	Sound[] sounds = new Sound[37];
        for (int i = 0; i < 37; i++) {
        	sounds[i] = new Sound(1);
        }
        
        //setting for which sound is played (harpsichord, guitar, wilhelm)
        int soundSetting = 0;

        // Set up parameters for visualization
	StdDraw.setCanvasSize(768, 256);
	StdDraw.setPenColor(StdDraw.RED);
	StdDraw.setXscale(0, XWIDTH);
	StdDraw.setYscale(-1, 1);



	// fence post
	double xprev = 0, yprev = 0;
	double sample = 0;
		
        while (true) {

            // check if the user has typed a key, and, if so, process it
            if (StdDraw.hasNextKeyTyped()) {
 
                // the user types this character
                char key = StdDraw.nextKeyTyped();

                // set up array to detect key, pluck the corresponding string
                String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
                
                //'1' key changes the piano sound
                if (key == '1') {
                	soundSetting++;
                	if (soundSetting == 3) {
                		soundSetting = 0;
                	}
                	// create new sounds
                	
                	//INSERT change in keyboard graphic to reflect sound change
                	
                }
                
                int note = keyboard.indexOf(key);
                
                if (note >= 0) {
                	// some keys just typed
                	// play different notes.
                	switch (soundSetting) {
                	
                	case 0:
                       
                        double frequency = 440 * Math.pow(2, ((note - 24.0) / 12.0));
						sounds[note] = new HarpsichordString(frequency);
						sounds[note].pluck();
                        
                        break;
                        
                	case 1:
                		
                		frequency = 440 * Math.pow(2, ((note - 24.0) / 12.0));
						sounds[note] = new GuitarString(frequency);
						sounds[note].pluck();
						
                		break;
                		
                	case 2:
                		//for guitar and harpsichord, frequency is needed.
                		//for wilhelm, only note number (0-36) is needed
                		
                		sounds[note] = new WilhelmScream(note);
                		sounds[note].pluck();
                	
                	}
                }
            }

	    // compute the superposition of the samples for duration
        
        sample = 0;
        
	    for (int i = 0; i < 37; i++) {
	    	sample += sounds[i].sample();
	    }
	    
	    // send the result to standard audio
	    StdAudio.play(sample);

	    // advance the simulation of each guitar string by one step
	    for (int i = 0; i < 37; i++) {
	    	sounds[i].tic();
	    }

	    //   Decide if we need to draw. 
	    //   Audio sample rate is StdAudio.SAMPLE_RATE per second
	    //   Draw sample rate is DRAW_SAMPLE_RATE
	    //   Hence, we draw every StdAudio.SAMPLE_RATE / DRAW_SAMPLE_RATE
	    if (sounds[0].time() % AUDIO_PER_DRAW == 0) {
		StdDraw.line(xprev, yprev, xprev+1, sample);
		xprev ++;
		yprev = sample;
		// check if wrapped around
	    } // end of if
	    
	    //resets StdDraw if sound wave reaches end of window
	    if (xprev > XWIDTH) {
	    	StdDraw.clear();
	    	xprev = 0;
	    }

	} // end of while

    } // end of main

} // end of class
