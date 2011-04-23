/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MIDI_API;

/**
 *
 * @author Kaijian Gao
 */

import java.util.*;

public class multiNote {

    private ArrayList<Integer> notes;
    private int duration;

    //default constructor
    public multiNote(){

    }

    //constructor used when the arraylist of notes and the duration is already known
    public multiNote(ArrayList notes, int duration){
        this.notes = notes;
        this.duration = duration;
    }

    public ArrayList getNotes(){
        return notes;
    }

    public int getDuration(){
        return duration;
    }

    public void addNote(int note){
        if(notes == null){
            notes = new ArrayList();
        }

        notes.add(note);
    }

    public void setDuration(int duration){
        this.duration = duration;
    }
}
