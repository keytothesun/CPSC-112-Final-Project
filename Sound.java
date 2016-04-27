//************************************************************************
//
//	File: Sound.java      		CPSC 112 Final
// 
//	Author: Hudson Lee           Email: hudson.lee@yale.edu
//
//	Class: Sound
//	Dependencies: RingBuffer
//
//	Time Spent on this Problem: 10 min
//  ---------------------
//		This program creates a base data type to model
//		each of the three sounds.
//
//************************************************************************

public class Sound {

    protected RingBuffer buffer;
    final protected static double DECAY = 0.996;
    protected int tics;
    protected int length;

    // create a sound of the given frequency
    public Sound (double frequency) {
    	
    	length = (int)Math.round(44100.0/frequency);
    	tics = 0;
    			
        RingBuffer buffer = new RingBuffer(length);
        
        for (int i = 0; i < length; i++) {
        	buffer.enqueue(0.0);
        }
        
        this.buffer = buffer;
    }

    public Sound(){}
    // create a guitar string with size & initial values given by the array
    public Sound(double[] init) {
        
    	length = init.length;
    	tics = 0;
    	
    	RingBuffer buffer = new RingBuffer(length);
    	
    	for (int i = 0; i < length; i++) {
    		buffer.enqueue(init[i]);
    	}
    	
    	this.buffer = buffer;
    }

    // pluck the guitar string by replacing the buffer with white noise
    public void pluck() {
    	
        for (int i = 0; i < length; i++) {
        	buffer.dequeue();
        	buffer.enqueue(-0.5 + Math.random());
        }
    }

    // advance the simulation one time step
    public void tic() {
        
    	double x = buffer.dequeue();
    	double y = buffer.peek();
    	
    	double new_sample = 0.5 * (x + y) * DECAY;
    	buffer.enqueue(new_sample);
    	
    	tics++;
    }

    // return the current sample
    public double sample() {
        return buffer.peek();
        
    }

    // return number of times tic was called
    public int time() {
        return tics;
    }
   
    //test method
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        double[] samples = { .2, .4, .5, .3, -.2, .4, .3, .0, -.1, -.3 };  
        Sound testSound = new Sound(samples);
        testSound.pluck();
        for (int i = 0; i < N; i++) {
            int t = testSound.time();
            double sample = testSound.sample();
            System.out.printf("%6d %8.4f\n", t, sample);
            testSound.tic();
        }
    }

}
