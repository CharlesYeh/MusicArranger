package tests;

import java.util.*;
import music.*;
import logic.Analyzer;

public class unitTest{
        public static void main(String args[]) {

                KeySignature CMajor = new KeySignature(new Rational(4, 4), 0, true);
                KeySignature DMajor = new KeySignature(new Rational(4, 4), 2, true);
                int C = 60;
                int D = 62;
                int bA = 56;

                Analyzer analyzer = new Analyzer();
                List<ChordSymbol> matchingchords = analyzer.findMatchingChords(C, CMajor);

                for(ChordSymbol cs : matchingchords){

                	System.out.println(cs.getSymbolText() + cs.getTopInversionText() + cs.getBotInversionText());
        		}
        }
}