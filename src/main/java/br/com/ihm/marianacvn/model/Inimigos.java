package br.com.ihm.marianacvn.model;

public class Inimigos {
	private static Inimigo[] inimigo = new Inimigo[10];
	
	public static Inimigo[] iniciarInimigos() {
		inimigo[0] = new Inimigo(10, 100, "0");
		inimigo[1] = new Inimigo(20, 200, "1");
		inimigo[2] = new Inimigo(30, 300, "2");
		inimigo[3] = new Inimigo(10, 400, "3");
		inimigo[4] = new Inimigo(65, 100, "4");
		inimigo[5] = new Inimigo(130, 200, "5");
		inimigo[6] = new Inimigo(400, 100, "6");
		inimigo[7] = new Inimigo(480, 90, "7");
		inimigo[8] = new Inimigo(380, 100, "8");
		inimigo[9] = new Inimigo(280, 100, "9");
		return inimigo;
	}
}
