package org.simorion.ui.view;

import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

/**
 * Maps the coordinate of a matrix button to its character representation.
 * @author Karl Brown
 *
 */
public class CharacterMap {

	private static Map<Integer, Character> chars = new HashMap<Integer, Character>() {
		{
			put(50, '\060'); // 0
			put(51, '\061'); // 1
			put(52, '\062'); // 2
			put(53, '\063'); // 3
			put(54, '\064'); // 4
			put(55, '\065'); // 5
			put(56, '\066'); // 6
			put(57, '\067'); // 7
			put(58, '\070'); // 8
			put(59, '\071'); // 9
			put(60, '\055'); // -
			put(61, '\075'); // =
			put(34, '\161'); // q
			put(35, '\167'); // w
			put(36, '\145'); // e
			put(37, '\162'); // r
			put(38, '\164'); // t
			put(39, '\171'); // y
			put(40, '\165'); // u
			put(41, '\151'); // i
			put(42, '\157'); // o
			put(43, '\160'); // p
			put(44, '\133'); // [
			put(45, '\135'); // ]
			put(18, '\141'); // a
			put(19, '\163'); // s
			put(20, '\144'); // d
			put(21, '\146'); // f
			put(22, '\147'); // g
			put(23, '\150'); // h
			put(24, '\152'); // j
			put(25, '\153'); // k
			put(26, '\154'); // l
			put(27, '\073'); // ;
			put(28, '\047'); // '
			put(29, '\043'); // #
			put(2, '\134'); // \
			put(3, '\172'); // z
			put(4, '\170'); // x
			put(5, '\143'); // c
			put(6, '\166'); // v
			put(7, '\142'); // b
			put(8, '\156'); // n
			put(9, '\155'); // m
			put(10, '\054'); // ,
			put(11, '\056'); // .
			put(12, '\057'); // /
		}
	};
	
	private static Map<Integer, Character> shiftChars = new HashMap<Integer, Character>() {
		{
			put(50, '\041'); // !
			put(51, '\042'); // "
			put(52, '\043'); // #
			put(53, '\044'); // $
			put(54, '\045'); // %
			put(55, '\136'); // ^
			put(56, '\046'); // &
			put(57, '\052'); // *
			put(58, '\050'); // (
			put(59, '\051'); // )
			put(60, '\137'); // _
			put(61, '\053'); // +
			put(34, '\121'); // Q
			put(35, '\127'); // W
			put(36, '\105'); // E
			put(37, '\122'); // R
			put(38, '\124'); // T
			put(39, '\131'); // Y
			put(40, '\125'); // U
			put(41, '\111'); // I
			put(42, '\117'); // O
			put(43, '\120'); // P
			put(44, '\173'); // {
			put(45, '\175'); // }
			put(18, '\101'); // A
			put(19, '\123'); // S
			put(20, '\104'); // D
			put(21, '\106'); // F
			put(22, '\107'); // G
			put(23, '\110'); // H
			put(24, '\112'); // J
			put(25, '\113'); // K
			put(26, '\114'); // L
			put(27, '\072'); // :
			put(28, '\100'); // @
			put(29, '\176'); // ~
			put(2, '\174'); // |
			put(3, '\132'); // Z
			put(4, '\130'); // X
			put(5, '\103'); // C
			put(6, '\126'); // V
			put(7, '\102'); // B
			put(8, '\116'); // N
			put(9, '\115'); // M
			put(10, '\074'); // <
			put(11, '\076'); // >
			put(12, '\077'); // ?
		}
	};
	
	public static char getCharacter(int loc, boolean shift) {		
		return shift ? shiftChars.get(loc) : chars.get(loc);		
	}
	
	private CharacterMap() { throw new RuntimeException("This class is not for instantiation"); }
	
}
