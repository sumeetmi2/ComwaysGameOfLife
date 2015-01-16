import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


public class ComwayTest {

	final static int GRID_SIZE=30;
	static boolean [][] state = new boolean[GRID_SIZE][GRID_SIZE];
	static Set<String> cellsToBeKeptAlive = new HashSet<String>();
	static Set<String> cellsToBeKeptDead = new HashSet<String>();
	static Set<String> cellsToBeChecked = new HashSet<String>();
	static Set<String> input = new HashSet<String>();
	static JFrame frame = new JFrame("FrameDemo");
	static JTextPane txtArea = new JTextPane();
	public static void main(String[] args) {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    System.out.println("Welcome to Comways game of life simulation");
	    System.out.println("-------------------------------------------");
	   
	    int generation=0;
	    int pattern=0;
	    try{
	    	System.out.println("Set the generation of your life system:");
	    	generation=Integer.parseInt(br.readLine());
	    	System.out.println("Select your initial Pattern");
	    	System.out.println("0-->10 Cell Input \n1-->GLIDER \n2-->EXPLODER \n3-->Blinker \n4-->Lightweight Spaceship");
	    	pattern =Integer.parseInt(br.readLine());
	    }catch(NumberFormatException e){
	    	System.err.println("Invalid format");
	    } catch (IOException e) {
	    	System.err.println("Error occured");
		}
	     
	    initialInput(pattern);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new JLabel("Output"), BorderLayout.CENTER);
		
		frame.add(txtArea);
		frame.pack();
		frame.setSize(500,600);
		frame.setVisible(true);
		//----------------------
		for(int i=0;i<generation;i++){
			printState();
			createInput();
			iterateOverNeighbors(input,false);
			updateCellState();
			for(String s :cellsToBeKeptAlive){
				changeState(s,true);
			}
			for(String s:cellsToBeKeptDead){
				changeState(s,false);
			}
//			System.out.println("");
//			System.out.println("");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resetSystem();
		}
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	
	private static void initialInput(int pattern) {
		if(pattern==0){
			for(int i=14;i<24;i++){  //For 10 cell input
				state[14][i]=true;
			}
		}
		if(pattern==1){
			state[0][1]=true;//glider
			state[1][2]=true;
			state[2][0]=true;
			state[2][1]=true;
			state[2][2]=true;
		}
		if (pattern == 2) {
			for (int i = 12; i < 17; i++) {
				state[i][12] = true; // exploder
				state[i][16] = true;
			}
			state[12][14] = true;
			state[16][14] = true;
		}
		if(pattern==3){
			state[5][5]=true;//blinker
			state[6][5]=true;
			state[7][5]=true;
		}
		if(pattern==4){
			state[25][1]=true;
			state[27][1]=true;
			for(int i=2;i<6;i++){
				state[24][i]=true;
			}
			state[25][5]=true;
			state[26][5]=true;
			state[27][4]=true;
		}
	}

	private static void printState() {
		for(int i=0;i<GRID_SIZE;i++){
			for(int j=0;j<GRID_SIZE;j++){
				if(!state[i][j]){
//					result.append("O");
					appendToPane(txtArea,"O",Color.YELLOW);
//					System.out.print(".");
				}else{
//					result.append("D");
					appendToPane(txtArea,"D",Color.BLUE);
//					System.out.print("O");
				}
			}
			appendToPane(txtArea,System.getProperty("line.separator"),Color.white);
//			System.out.println("");
		}
//		txtArea.setText(result.toString());
	}

	private static void appendToPane(JTextPane tp, String msg, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
				StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily,
				"Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment,
				StyleConstants.ALIGN_JUSTIFIED);

		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}

	private static void changeState(String s,boolean alive){
		String [] tmp = s.split("XX");
		int x = Integer.parseInt(tmp[0]);
		int y = Integer.parseInt(tmp[1]);
		state[x][y]=alive;
	}
	
	private static void createInput() {
		for(int i=0;i<GRID_SIZE;i++){
			for(int j=0;j<GRID_SIZE;j++){
				if(state[i][j]){
					input.add(i+"XX"+j);
				}
			}
		}
	}

	private static void resetSystem() {
		input.clear();
		cellsToBeChecked.clear();
		cellsToBeKeptAlive.clear();
		cellsToBeKeptDead.clear();
		txtArea.setText(null);
	}

	private static void updateCellState() {
		Map<String,Integer> cellMap = iterateOverNeighbors(cellsToBeChecked,true);
		for(String s : cellsToBeChecked){
			String [] tmp = s.split("XX");
			int x = Integer.parseInt(tmp[0]);
			int y = Integer.parseInt(tmp[1]);
			checkAlive(x, y,cellMap.get(s));
		}
	}

	private static Map<String,Integer> iterateOverNeighbors(Set<String> input,boolean flag) {
		Map<String,Integer>result= new HashMap<String,Integer>();
		for(String s: input){
			int count=0;
			String [] tmp = s.split("XX");
			int x = Integer.parseInt(tmp[0]);
			int y = Integer.parseInt(tmp[1]);
			int top = x-1>=0?x-1:x;
			int bottom = x+1<GRID_SIZE?x+1:x;
			int left = y-1>=0?y-1:y;
			int right = y+1<GRID_SIZE?y+1:y;
			for(int i=top;i<=bottom;i++){
				for(int j=left;j<=right;j++){
					if(state[i][j]==true){
						if(!(i+"XX"+j).equals(s)){
							count++;
						}
					}
					if(!flag){
						cellsToBeChecked.add(i+"XX"+j); //not putting flag will throw concurrent modification
					}
				}
			}
			result.put(s,count);
		}
		return result;
	}

	static void checkAlive(int x,int y,int noOfAlive){
		if(state[x][y]){
			if(noOfAlive<2 || noOfAlive>3){
				cellsToBeKeptDead.add(x+"XX"+y);
			}else if(noOfAlive==2 || noOfAlive==3){
				cellsToBeKeptAlive.add(x+"XX"+y);
			}
		}else{
			if(noOfAlive==3){
				cellsToBeKeptAlive.add(x+"XX"+y);
			}
		}
		
		
	}

}
