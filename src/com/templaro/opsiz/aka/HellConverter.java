/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/* Note that this class was based on the MorseConverter class that was
   borrowed in a unidirectional fashion from the Android developer doc examples
   and hacked on quite a bit.  Hooray for Apache-licensed examples! 
   
   Much of the background for the FH aspect of this project was found on 
   Frank D�renberg (N4SPP)'s encyclopedic site about all things related to 
   that mode: http://www.nonstopsystems.com/radio/frank_radio_hell.htm
   
   In particular, the layout for the original 14x7 element font was adapted
   from a text file on that site: 
   http://www.nonstopsystems.com/radio/hell-keyboard-encoding.txt
*/

package com.templaro.opsiz.aka;

/** Class that implements the text to Feld Hellscreiber conversion */
class HellConverter {

    /** The characters from 'A' to 'Z' */
    private static final boolean[][] LETTERS = new boolean[][] {
    	/* A */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,true,true,false,false,true,true,true,false,false,false,false,false,false,false,true,true,false,false,false,true,true,false,false,false,false,false,false,false,true,true,false,false,true,true,true,false,false,false,false,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* B */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,false,false,true,true,true,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* C */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* D */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,false,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* E */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* F */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,true,true,false,false,true,true,false,false,false,false,false,false,false,false,true,true,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* G */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,false,true,true,true,true,true,true,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* H */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* I */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* J */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* K */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,false,false,false,false,false,false,false,false,false,true,true,false,false,true,true,false,false,false,false,false,false,true,true,true,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* L */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* M */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* N */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* O */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* P */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,false,false,false,false,false,true,true,false,false,true,true,false,false,false,false,false,false,false,false,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* Q */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,false,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,true,true,true,true,true,true,true,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* R */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,true,true,false,false,true,true,false,false,false,false,false,false,false,false,true,true,false,false,true,true,false,false,false,false,false,false,true,true,true,true,false,false,true,true,false,false,false,false,true,true,true,false,false,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* S */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,true,true,true,true,false,false,false,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,false,false,true,true,true,true,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* T */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* U */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* V */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* W */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* X */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,true,true,true,false,false,false,false,false,false,true,true,false,false,true,true,false,false,false,false,false,false,false,false,false,true,true,true,true,false,false,false,false,false,false,false,false,false,true,true,false,false,true,true,false,false,false,false,false,false,true,true,true,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* Y */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* Z */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,true,true,false,false,false,false,true,true,true,true,true,false,false,false,true,true,false,false,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,false,true,true,false,false,false,true,true,true,true,true,false,false,false,false,true,true,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false }
    };

    /** The characters from '0' to '9' */
    private static final boolean[][] NUMBERS = new boolean[][] {
    	/* 1 */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* 2 */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,true,true,false,false,false,false,false,true,true,true,true,false,false,false,false,true,true,false,false,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,false,true,true,false,false,false,true,true,true,true,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* 3 */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,true,true,false,false,false,true,true,false,false,false,true,true,false,false,true,true,false,false,false,false,true,true,false,false,true,true,true,true,true,true,false,false,false,false,false,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* 4 */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* 5 */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,true,true,true,true,true,true,false,false,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,false,false,true,true,true,true,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* 6 */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,false,false,false,false,true,true,false,false,true,true,false,false,false,true,true,false,false,false,true,true,false,false,false,true,true,false,false,false,true,true,false,false,true,true,false,false,false,true,true,false,false,false,false,false,false,false,false,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* 7 */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,true,true,false,false,false,false,false,true,true,true,false,false,false,false,true,true,false,false,false,false,false,false,false,true,true,true,false,false,true,true,false,false,false,false,false,false,false,false,false,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* 8 */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,false,false,true,true,true,true,false,false,false,true,true,false,false,true,true,true,true,false,false,true,true,false,false,true,true,false,false,false,true,true,false,false,false,true,true,false,false,true,true,false,false,true,true,true,true,false,false,true,true,false,false,false,true,true,true,true,false,false,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* 9 */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,false,false,false,true,true,false,false,false,true,true,false,false,false,true,true,false,false,false,true,true,false,false,true,true,false,false,false,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,false,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* 0 */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,false,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,false,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false }
    };
  
    /** Punctuation  */
    /* TODO: Add additional punctuation that was not part of the original font, keeping to the two element rule */
    /* TODO: Probably can eliminate the pause character or at least repurpose it */
    private static final boolean[][] PUNCTUATION = new boolean[][] {
    	/* + */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* - */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* ? */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,true,true,false,false,true,true,true,true,true,true,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* / */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false },
    	/* pause */ { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,false,false,false,false,false,false,true,true,false,false,false,false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false }
    };
    
    /** SPACE  */
    /* Note that space actually has some signal to keep a vox happy */
    private static final boolean[] GAP = new boolean[] {
    	/* <space> */ false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false 
    };

    /** Return the pattern data for a given character */
    static boolean[] pattern(char c) {
        if (c >= 'A' && c <= 'Z') {
            return LETTERS[c - 'A'];
        }
        if (c >= 'a' && c <= 'z') {
            return LETTERS[c - 'a'];
        }
        else if (c >= '0' && c <= '9') {
            return NUMBERS[c - '0'];
        }
        else {
            return GAP;
        }
    }

    static boolean[] pattern(String str) {
    	// Generate the pattern array. 

        // First three characters sent are <space> to open vox 
        // possibly to allow some synch.
        // Since all FH characters start with a row of null bits
        // the audio stream begins with silence rather than tone

    	str = " " + " " + " " + str;
        int strlen = str.length();
        boolean[] result = new boolean[strlen*98];
        int pos = 1;
        for (int i=0; i<strlen; i++) {
        	char c = str.charAt(i);
        	boolean[] letter = pattern(c);
        	System.arraycopy(letter, 0, result, pos, 98);
        	pos += 98;
        }
        return result;
    }
}