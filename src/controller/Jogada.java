package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Jogada extends Thread {

	private String jogadorTimeA;
	private String jogadorTimeB;
	private static int postosA = 0;
	private static int postosB = 0;
	private static boolean finalJogo = false;
	private Semaphore controle;
	private Random num;
	private final int NUMPARAVITORIA = 3;
	private String[] lance = { "Tesoura", "Papel", "Pedra" };

	public Jogada(String jogadorTimeA, String jogadorTimeB, Semaphore controle) {
		super();
		this.jogadorTimeA = jogadorTimeA;
		this.jogadorTimeB = jogadorTimeB;
		this.controle = controle;
		num = new Random();
	}

	@Override
	public void run() {

		jogar();

		super.run();
	}

	private void jogar() {

		String jogador1;
		String jogador2;

		do {
			jogador1 = lance[num.nextInt(3)];
			jogador2 = lance[num.nextInt(3)];
		} while (jogador1.equals(jogador2));

		if (jogador1.equals(lance[0])) {
			if (jogador2.equals(lance[1])) {
				// jogador 1 ganha
				ganhaTimeA(jogador1, jogador2);
			} else {
				// jogador 2 ganha
				ganhaTimeB(jogador1, jogador2);
			}
		}
		if (jogador1.equals(lance[1])) {
			if (jogador2.equals(lance[2])) {
				// jogador 1 ganha
				ganhaTimeA(jogador1, jogador2);
			} else {
				// jogador 2 ganha
				ganhaTimeB(jogador1, jogador2);
			}
		}

		if (jogador1.equals(lance[2])) {
			if (jogador2.equals(lance[0])) {
				// jogador 1 ganha;
				ganhaTimeA(jogador1, jogador2);
			} else {
				// jogador 2 ganha
				ganhaTimeB(jogador1, jogador2);
			}
		}

	}

	private void ganhaTimeA(String jogador1, String jogador2) {
		try {

			controle.acquire();
			
			if (!finalJogo) {
				System.err.println(jogadorTimeA + " ganhou com " + jogador1 + " contra " + jogador2 + " " + jogadorTimeB);
				postosA++;
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (postosA == NUMPARAVITORIA && !finalJogo) {
				System.err.println("Time A ganhou com " + postosA + " Contra " + postosB + " do time B" + " TOTAL DE JOGOS " + (postosA + postosB));
				finalJogo = true;
			}

			controle.release();
		}
	}

	private void ganhaTimeB(String jogador1, String jogador2) {
		try {

			controle.acquire();
			
			if (!finalJogo) {

				System.out.println(jogadorTimeB + " ganhou com " + jogador2 + " contra " + jogador1 + " " + jogadorTimeA);
				postosB++;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (postosB == NUMPARAVITORIA && !finalJogo) {
				System.out.println("Time B ganhou com " + postosB + " Contra " + postosA + " do time A"  + " TOTAL DE JOGOS " + (postosA + postosB));
				finalJogo = true;
			}
			controle.release();
		}
	}

}
