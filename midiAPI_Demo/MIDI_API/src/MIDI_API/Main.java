/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MIDI_API;

/**
 *
 * @author Kaijian Gao
 */
public class Main {

    public static void main(String[] args) throws Exception {

        int[] mary_had_a_little_lamb = {66, 64, 62, 64, 66, 66, 66, 64, 64, 64, 66, 69, 69, 66, 64, 62, 64, 66, 66, 66, 64, 64, 66, 64, 62};
        int[] mary_had_a_little_lamb2 = {69, 67, 66, 67, 69, 69, 69, 67, 67, 67, 69, 73, 73, 69, 67, 66, 67, 69, 69, 69, 67, 67, 69, 67, 66};

        // no error checking

        //this section is just for generating the sample music "Mary Had a Little Lamb"

        Voice v = new Voice();
        for (int i = 0; i < mary_had_a_little_lamb.length; i++) {
            int pitch1 = mary_had_a_little_lamb[i];
            int pitch2 = mary_had_a_little_lamb2[i];

            multiNote mn = new multiNote();
            mn.addNote(pitch1);
            mn.addNote(pitch2);
            mn.setDuration(300);

            if (i == 6 || i == 9 || i == 12 || i == 19 || i == 24) {
                mn.setDuration(600);
            }

            v.addMultiNote(mn);
        }

        midiAPI.playVoice(v);
    }
}
