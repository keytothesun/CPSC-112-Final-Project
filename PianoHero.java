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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PianoHero {

    public static void main(String[] args) {
    	//NOTE: maybe we can incorporate the drawing into the keyboard graphic
    	
    	
    	
	int    DRAW_SAMPLE_RATE = 20;    // draw at a rate of 20/sec
	int    AUDIO_PER_DRAW   = StdAudio.SAMPLE_RATE / DRAW_SAMPLE_RATE;
	int    PLAY_TIME        = 10;    // target 60 seconds display window
	int    XWIDTH           = DRAW_SAMPLE_RATE * PLAY_TIME;
	final int    NO_STRINGS       = 37;
	final String MUSIC_FILE = "music.txt";
	final double CONCERT_A = 440.0;


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
	
	Scanner input;
	

	
	//File IO with exception handling
	try{
		input = new Scanner(new File(MUSIC_FILE));
	} catch (FileNotFoundException e){
		System.err.println("Could not open "+ MUSIC_FILE);
		return ; // exit program
	}

	int pluckTime;
	String keys;
	try{
		pluckTime = input.nextInt(); // trigger time
		keys = input.next(); //first note
	}catch(InputMismatchException e){
		System.err.println("Music file format error. Expecting an integer.");
		return ; // exit program
	}catch (NoSuchElementException e){
		System.err.println("Music file is empty.");
		return ; // exit program
	}

	final long START_TIME = System.currentTimeMillis(); //start time of the main loop
	//main input loop
	while (true) {
		long currentTime = System.currentTimeMillis();

		if (currentTime - START_TIME > pluckTime) {
			for (int i = 0; i < keys.length(); i++){ //for loop to read in chords
				char key = keys.charAt(i);
                String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
				int index = keyboard.indexOf(key);
				if (index == -1){
					System.err.println("Wrong key!"); // We print to err.
				} else {
					double frequency = 440 * Math.pow(2, ((index - 24.0) / 12.0));
					sounds[index] = new HarpsichordString(frequency);
					sounds[index].pluck();
				}
			}

			if (input.hasNextInt()){
				pluckTime = input.nextInt(); //update for next loop
				keys = input.next(); 		
			}else{
				keys = ""; // input file ends
			}

		} // end of if



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
        
        double sample1 = 0;
        
	    for (int i = 0; i < 37; i++) {
	    	sample1 += sounds[i].sample();
	    }
	    
	    // send the result to standard audio
	    StdAudio.play(sample1);

	    // advance the simulation of each guitar string by one step
	    for (int i = 0; i < 37; i++) {
	    	sounds[i].tic();
	    }

	    //   Decide if we need to draw. 
	    //   Audio sample rate is StdAudio.SAMPLE_RATE per second
	    //   Draw sample rate is DRAW_SAMPLE_RATE
	    //   Hence, we draw every StdAudio.SAMPLE_RATE / DRAW_SAMPLE_RATE
	    if (sounds[0].time() % AUDIO_PER_DRAW == 0) {
		StdDraw.line(xprev, yprev, xprev+1, sample1);
		xprev ++;
		yprev = sample1;
		// check if wrapped around
	    } // end of if
	    
	    //resets StdDraw if sound wave reaches end of window
	    if (xprev > XWIDTH) {
	    	StdDraw.clear();
	    	xprev = 0;
	    }

	 // end of while

    } // end of main

} 
}// end of class