package org.ciprite.ugungame.util;

import java.util.ArrayList;
import java.util.List;

import org.ciprite.ugungame.game.Arena;

/**
 * @info Countdown
 * @author Ciprite
 */

public class Countdown implements Runnable {

	private int time;
	private String msg;
	private Arena arena;
	private ArrayList<Integer> countingNumbs;
	private boolean isDone = false;
	  
	public Countdown(int time, String msg, Arena arena, List<Integer> countingNumbs) {
		this.time = (time + 1);
	    this.msg = msg;
	    this.arena = arena;
	    this.countingNumbs = new ArrayList<Integer>();
	    for (int i : countingNumbs) {
	    	this.countingNumbs.add(Integer.valueOf(i));
	    }
	}
	
	public void run() {
		this.time -= 1;
	    if (this.time == 0) {
	    	this.isDone = true;
	    	return;
	    }
	
	    if (this.countingNumbs.contains(Integer.valueOf(this.time))) {
	    	this.arena.sendMessage(this.msg.replaceAll("%time", "" + this.time));
	    }
	}
	
	public boolean isDone() {
		return this.isDone;
	}

}
