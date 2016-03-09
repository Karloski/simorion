package org.simorion.ui.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps the coordinate of a matrix button to its character representation.
 * @author Karl Brown
 *
 */
public class CharacterMap {

	private static Map<Integer, Character> chars = new HashMap<Integer, Character>() {
		{
			put(50, '1');
			put(51, '2');
			put(52, '3');
			put(53, '4');
			put(54, '5');
			put(55, '6');
			put(56, '7');
			put(57, '8');
			put(58, '9');
			put(59, '0');
			put(60, '-'); 
			put(61, '='); 
			put(34, 'q'); 
			put(35, 'w'); 
			put(36, 'e'); 
			put(37, 'r'); 
			put(38, 't');
			put(39, 'y'); 
			put(40, 'u');
			put(41, 'i'); 
			put(42, 'o');
			put(43, 'p');
			put(44, '[');
			put(45, ']');
			put(18, 'a');
			put(19, 's');
			put(20, 'd');
			put(21, 'f'); 
			put(22, 'g');
			put(23, 'h');
			put(24, 'j');
			put(25, 'k');
			put(26, 'l');
			put(27, ';'); 
			put(28, '\'');
			put(29, '#');
			put(2, '\\');
			put(3, 'z'); 
			put(4, 'x'); 
			put(5, 'c'); 
			put(6, 'v'); 
			put(7, 'b'); 
			put(8, 'n'); 
			put(9, 'm'); 
			put(10, ','); 
			put(11, '.'); 
			put(12, '/'); 
		}
	};
	
	private static Map<Integer, Character> shiftChars = new HashMap<Integer, Character>() {
		{
			put(50, '!'); 
			put(51, '"'); 
			put(52, '#'); 
			put(53, '$');
			put(54, '%'); 
			put(55, '^'); 
			put(56, '&'); 
			put(57, '*'); 
			put(58, '('); 
			put(59, ')'); 
			put(60, '_'); 
			put(61, '+'); 
			put(34, 'Q'); 
			put(35, 'W');
			put(36, 'E'); 
			put(37, 'R');
			put(38, 'T');
			put(39, 'Y');
			put(40, 'U'); 
			put(41, 'I');
			put(42, 'O'); 
			put(43, 'P');
			put(44, '{'); 
			put(45, '}'); 
			put(18, 'A'); 
			put(19, 'S'); 
			put(20, 'D'); 
			put(21, 'F'); 
			put(22, 'G'); 
			put(23, 'H'); 
			put(24, 'J'); 
			put(25, 'K');
			put(26, 'L');
			put(27, ':'); 
			put(28, '@');
			put(29, '~');
			put(2, '|'); 
			put(3, 'Z'); 
			put(4, 'X'); 
			put(5, 'C'); 
			put(6, 'V'); 
			put(7, 'B'); 
			put(8, 'N'); 
			put(9, 'M'); 
			put(10, '<'); 
			put(11, '>'); 
			put(12, '?');
		}
	};
	
	public static char getCharacter(int loc, boolean shift) {	
		return shift ? shiftChars.get(loc) : chars.get(loc);		
	}
	
	private CharacterMap() { throw new RuntimeException("This class is not for instantiation"); }
	
}
