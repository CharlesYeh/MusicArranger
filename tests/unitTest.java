package tests;

import java.util.*;
import music.*;
import logic.Analyzer;

public class unitTest{
        public static void main(String args[]) {

                KeySignature CMajor = new KeySignature(new Rational(4, 4), 0, true);
                int D = 62;

                Analyzer analyzer = new Analyzer();
                List<ChordSymbol> matchingchords = analyzer.findMatchingChords(D, CMajor);

                for(ChordSymbol cs : matchingchords){

                	System.out.println(cs.getSymbolText() + cs.getTopInversionText() + cs.getBotInversionText());
        		}
        }
}