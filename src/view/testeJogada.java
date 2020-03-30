package view;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import controller.Jogada;

public class testeJogada {

	public static void main(String[] args) {
		

		ArrayList<String> timeA = new ArrayList<String>(5);
		ArrayList<String> timeB = new ArrayList<String>();
		timeA = carregaTime(timeA, "A");
		timeB = carregaTime(timeB, "B");
		
		Semaphore controle = new Semaphore(1);
		Random num = new Random();
		
		while(!timeA.isEmpty()) {
			
			Jogada j = new Jogada(timeA.remove(num.nextInt(timeA.size())),timeB.remove(num.nextInt(timeB.size())), controle);
			j.start();
			
		}
		

	}

	private static ArrayList<String> carregaTime(ArrayList<String> jogadores, String time) {

		for (int i = 1; i < 5 + 1; i++) {

			if (time.equals("A")) {

				jogadores.add("JOGADOR DO TIME A " + i);
			} else {
				jogadores.add("JOGADOR DO TIME B " + i);
			}

		}

		return jogadores;

	}

}
