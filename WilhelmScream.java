//************************************************************************
//
//	File: WilhelmScream.java     CPSC 112 Final
// 
//	Author: Hudson Lee           Email: hudson.lee@yale.edu
//
//	Class: WilhelmScream
//	Dependencies: RingBuffer, Sound
//
//	Time Spent on this Problem: 
//  ---------------------
//		This program is a child class of Sound and creates a data type
//		to model an "instrument" that plays the Wilhelm Scream at various
//		pitches.
//
//************************************************************************
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class WilhelmScream extends Sound {

    private String WilhelmNote;
    File scream;
	// create a guitar string of the given frequency
    public WilhelmScream (double frequency) {

    	super(frequency);
    	
    	WilhelmNote = Double.toString(frequency) + ".wav";
    }
    public WilhelmScream(int note){
    	super();
    	scream = new File(note + ".wav");
    }
   
    
    
    public void pluck() {
    	Playsound();
    	//System.out.println(StdAudio.read(WilhelmNote));
    	
    }
    
    public void tic() {
    	//tic method needs to be overriden, not needed for wilhelm screams
    	tics++;
    }
    
    //***trying to figure out how to import wav files into sample arrays.
    // 1.) need to find the length of the array
    // 2.) need to read the samples from the wav file and import them into the array
    
    public void wilhelm() {
    		//creating 37 arrays
	    	File[] scream = new File [37];
	    	// for loop to correspond each array with wav files
	        for ( int i = 0; i < 37; i++ ) {
	        	scream[i] = new File (i + ".wav");
	        }
	    	Playsound();
        }
	        
         void Playsound()
        {
        	try {
        		Clip clip = AudioSystem.getClip();
        		//testing code:
        		//getAudioInputStream(scream[])) should be updated.
        		//scream[1] will only play the wave file assigned for array [1]
        		//which is 1.wav
        		clip.open(AudioSystem.getAudioInputStream(scream));
        		clip.start();
        	}
        	catch (Exception e)
        	{
        	}
        	
        }
        }
