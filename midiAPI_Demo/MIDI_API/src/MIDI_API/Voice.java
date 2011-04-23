/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MIDI_API;

/**
 *
 * @author Kaijian Gao
 */
import java.util.LinkedList;

public class Voice {
    private LinkedList<multiNote> multinotes;
    private int size = 0; //number of multinotes in the voice

    public Voice(LinkedList<multiNote> mns){
        multinotes = mns;
        size = mns.size();
    }

    public Voice(){

    }

    public void addMultiNote(multiNote mn){
        if(multinotes == null)
            multinotes = new LinkedList<multiNote>();

        multinotes.add(mn);
        size ++;
    }

    public int size(){
        return size;
    }

    public LinkedList<multiNote> getMultiNotes(){
        return multinotes;
    }

}
