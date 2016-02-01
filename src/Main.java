import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;


public class Main {
	ArrayList<Card> deck = new ArrayList<Card>();
	ArrayList<Card> p1 = new ArrayList<Card>();
	ArrayList<Card> p2 = new ArrayList<Card>();
	Timer t = new Timer();
	int x=0;
	int n=0;
	
	JFrame frame = new JFrame("War");
	JPanel one = new JPanel(new GridLayout(2,1));
	JPanel two = new JPanel(new GridLayout(2,1));
	JTextField pl1 = new JTextField(10);
	JTextField pl2 = new JTextField(10);
	JTextField pla = new JTextField(10);
	JTextField plb = new JTextField(10);
	JTextField turnCount = new JTextField(n);
	
	public Main() {
		GUI();
		
		for(int i=2; i<=14; i++) {
			for(int j=0; j<4; j++) {
				deck.add(new Card(i));
			}
		}
		
		Deal();
		
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				n++;
				Play();
				if(hasWon()==true) {
					t.cancel();
				}
			}
		}, 500, 250);
		
		hasWon();
		
	}
	
	public void Deal() {
		Random r = new Random();
		for(int i=0; i<26; i++) {
			int left = 51-(i*2);
			int a = r.nextInt(left);
			int b = r.nextInt(left);
			p1.add(deck.get(a));
			p2.add(deck.get(b));
			deck.remove(a);
			deck.remove(b);
		}
	}
	
	public boolean hasWon() {
		if(p1.size()==0) {
			return true;
		}
		if(p2.size()==0) {
			return true;
		}
		return false;
	}
	
	public void Play() {
		turnCount.setText("Turn Count: "+n);
		pl1.setText("# of Cards: "+p1.size());
		pla.setText("Card Played: "+p1.get(0).name);
		pl2.setText("# of Cards: "+p2.size());
		plb.setText("Card Played: "+p2.get(0).name);
		
		if(p1.get(0).value()>p2.get(0).value()) {
			p1.add(p1.get(0));
			p1.add(p2.get(0));
			p1.remove(0);
			p2.remove(0);
		}
		else if (p1.get(0).value()<p2.get(0).value()) {
			p2.add(p2.get(0));
			p2.add(p1.get(0));
			p2.remove(0);
			p1.remove(0);
		}
		else {
			while(x>=0) {
				if(p1.get(0).value()==p2.get(0).value()) {
					if(p1.size()<(4+(4*x))) {
						if(p1.size()==1) {
							p2.add(p2.get(0));
							p2.add(p1.get(0));
							p1.remove(0);
							p2.remove(0);
							
						}
						else if(p2.size()==1) {
							p1.add(p2.get(0));
							p1.add(p1.get(0));
							p1.remove(0);
							p2.remove(0);
						}
						else if(p1.get(p1.size()-1).value()>p2.get(4+(4*x)).value()) {
							for(int i=0; i<(p1.size()-1); i++) {
								p1.add(p1.get(i));
								p1.add(p2.get(0));
								p2.remove(0);
								p1.remove(0);
							}
							x=-1;
						}
						else if(p1.get(p1.size()-1).value()<p2.get(4+(4*x)).value()) {
							for(int i=0; i<(p1.size()-1); i++) {
								p2.add(p2.get(i));
								p2.add(p1.get(0));
								p1.remove(0);
								p2.remove(0);
							}
							x=-1;
						}
						else {
							x++;
						}
					}
					else if(p2.size()<(4+(4*x))) {
						if(p1.size()==1) {
							p2.add(p2.get(0));
							p2.add(p1.get(0));
							p1.remove(0);
							p2.remove(0);
							
						}
						else if(p2.size()==1) {
							p1.add(p2.get(0));
							p1.add(p1.get(0));
							p1.remove(0);
							p2.remove(0);
						}
						else if(p1.get(4+(4*x)).value()>p2.get(p2.size()-1).value()) {
							for(int i=0; i<=(p2.size()-1); i++) {
								p1.add(p1.get(i));
								p1.add(p2.get(0));
								p2.remove(0);
								p1.remove(0);
							}
							x=-1;
						}
						else if(p1.get(4+(4*x)).value()<p2.get(p2.size()-1).value()) {
							for(int i=0; i<=(p2.size()-1); i++) {
								p2.add(p2.get(i));
								p2.add(p1.get(0));
								p1.remove(0);
								p2.remove(0);
							}
							x=-1;
						}
						else {
							x++;
						}
					}
					else {
						if(p1.get(4+(4*x)).value()>p2.get(4+(4*x)).value()) {
							for(int i=0; i<=(4+(4*x)); i++) {
								p1.add(p1.get(i));
								p1.add(p2.get(0));
								p2.remove(0);
								p1.remove(0);
							}
							x=-1;
						}
						else if(p1.get(4+(4*x)).value()<p2.get(4+(4*x)).value()) {
							for(int i=0; i<=(4+(4*x)); i++) {
								p2.add(p2.get(i));
								p2.add(p1.get(0));
								p1.remove(0);
								p2.remove(0);
							}
							x=-1;
						}
						else {
							x++;
						}
					}
				}
			}
		}
		x=0;
	}
	
	public void GUI() {
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(400, 400);
		
		one.add(pl1);
		one.add(pla);
		two.add(pl2);
		two.add(plb);
		
		frame.add(one, BorderLayout.WEST);
		frame.add(two, BorderLayout.EAST);
		frame.add(turnCount, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}
}
		
