package com.nerdle;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.swing.*;
import javax.swing.border.*;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;



public class Main {
	
	// GUI variables for game frame
	static JFrame gameFrame = new JFrame();
	static int frame_width = 850;
	static int frame_height = 800;
	static int frame_x = 720-frame_width/2;
	static int frame_y = 30;
	static Color background_color = new Color(5,0,30);
	static Color sqColor = Color.gray.brighter();
	static int sqSize = 70;
	static int square_gap = ((700-sqSize*8)/9);
	static JLabel zero = new JLabel("0");
	static int buttonWidth = (int)(sqSize*.65);
	static int buttonGap = 5;
	static int buttons_yOffset = 30+(sqSize*6)+(square_gap*6)+15;
	static int buttons_xOffset = (frame_width-(buttonWidth*10)-(buttonGap*10))/2;
	static String button_vals = "1234567890+-*/=ED";  //charchters to be used as a button
	static boolean[] buttonsGreen = new boolean[20];
	static JPanel game_panel = new JPanel();
	static JLabel timer_label = new JLabel();

	// GUI variables for menu frame
	static JFrame menuFrame = new JFrame();
	static JPanel menu_panel = new JPanel();
	static JLabel footer1 = new JLabel();
	static JLabel footer2 = new JLabel();
	static JLabel header = new JLabel();
	static JButton home_btn = new JButton();
	static JButton continue_btn = new JButton();
	static JButton test_btn = new JButton();
	static JButton newGame_btn = new JButton();
	static JButton cheat_btn = new JButton();
	static Color btn_Color = Color.decode("#0035f4");
	static JLabel stats_label = new JLabel();

	// Used Data Structures and Variables
	static List<List<JTextField>> lines = new ArrayList<List<JTextField>>();  //2D array for the text fields
	static List<JButton> buttons = new ArrayList<JButton>();			// 1D array for buttons
	static int linesCompleted = 0;  //to keep track of the last row of the lines when creating buttons
	
	// Timer Variables
	static Timer timer = new javax.swing.Timer(1000, Main.tc);
	static TimeClass tc = new TimeClass(100);
	static boolean pause = false;
	static int elapsedTime;

	// Statistics Variables
	static Statistics stats = new Statistics("stats");
	static String stats_string = new String();
	static int currentUsedLines;
	
	// Generator Variabels
	static Generator generator = new Generator("myGenerator");
	static String answer;										// current answer for the current game
	static int answerLength;


	//###################################################################################################################
	//################################# MAIN  ###########################################################################
	//###################################################################################################################
	public static void main(String[] args) throws AWTException, FileNotFoundException, IOException {


		// Menu frame---------------------------------------------------------
		menuFrame.setBounds(frame_x,frame_y,frame_width,frame_height);
		menuFrame.setBackground(background_color);
		menuFrame.setDefaultCloseOperation(3);
		menuFrame.setTitle("Home");
		//-------------------------------------------------------------------------


		// Header Label----------------------------------------------------------------
		header.setBounds(45, 35, 800, 100);
		header.setForeground(Color.magenta);
		header.setFont(new Font("SansSerif", Font.BOLD, 80));
		header.setText("--------- Nerdle ---------");
		// ------------------------------------------------------------------------


		// Footer1 Label---------------------------------------------------------------
		footer1.setBounds(20, 720, 350, 35);
		footer1.setForeground(Color.magenta);
		footer1.setBorder(null);
		footer1.setFont(new Font("SansSerif", Font.BOLD, 14));
		footer1.setText("by Amirkia Rafiei Oskooei");
		//--------------------------------------------------------------------------


		// Footer2 Label---------------------------------------------------------------
		footer2.setBounds(340, 720, 350, 35);
		footer2.setForeground(Color.magenta);
		footer2.setBorder(null);
		footer2.setFont(new Font("SansSerif", Font.BOLD, 15));
		footer2.setText("by Amirkia Rafiei Oskooei");
		//--------------------------------------------------------------------------


		// Timer Label---------------------------------------------------------------
		timer_label.setBounds(760, 630, 90, 60);
		timer_label.setForeground(Color.magenta);
		timer_label.setBorder(null);
		timer_label.setHorizontalTextPosition(SwingConstants.CENTER);
		timer_label.setFont(new Font("SansSerif", Font.BOLD, 28));
		timer_label.setText("0");
		//--------------------------------------------------------------------------


		// String for Statistics Label------------------------------------------------------------
		stats_string = new String("Rounds Won: " + Main.stats.getWin_no() + 
		"\nRounds Lost: " + Main.stats.getLoose_no() + 
		"\nCanceled Rounds: " + Main.stats.getCanceled_rounds() + 
		"\nAverage of Winners Used Lines: " + Main.stats.getWinner_line_average() + 
		"\nAverage of Winners Elapsed Time: " + Main.stats.getWinner_elapsedTime_average());
		//----------------------------------------------------------------------------------------

		
		// Statistic Label-------------------------------------------------------------
		stats_label.setBounds(280, 300, 350, 130);
		stats_label.setForeground(Color.white);
		stats_label.setBorder(null);
		stats_label.setFont(new Font("Arial", Font.PLAIN, 18));

		stats_label.setText("<html>" + stats_string.replaceAll("<","&lt;")
		.replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
		//----------------------------------------------------------------------------


		// Home Button-------------------------------------------------------------------------
		home_btn.setBounds(620, 720, 180, 35);
		home_btn.setBackground(btn_Color);
		home_btn.setForeground(Color.white);
		home_btn.setText("Home (Play Later)");
		home_btn.setFont(new Font("SansSerif", Font.BOLD, 15));
		
		home_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				gameFrame.setVisible(false);
				menuFrame.setVisible(true);
				
				Main.pause = true;
			}

		});
		//-------------------------------------------------------------------------------------


		// New game button-------------------------------------------------------------------------
		newGame_btn.setBounds(270, 550, 160, 40);
		newGame_btn.setBackground(btn_Color);
		newGame_btn.setForeground(Color.white);
		newGame_btn.setText("New Game");
		newGame_btn.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		newGame_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				if the game is canceled(clicking on new game button while continue_btn is visible) increment canceled_rounds
				*/
				if(isCanceled(menu_panel)) {
					stats.setCanceled_rounds(stats.getCanceled_rounds() + 1);
				}
				Main.updateStatsLabels(menu_panel);
				

				/*
				clear the frame and start a new game
				 */
				gameFrame.getContentPane().removeAll();
				gameFrame.removeAll();
				newGame();
				gameFrame.setVisible(true);
				
				/* 
				set the visibility of frames and buttons
				*/
				continue_btn.setVisible(true);
				menuFrame.setVisible(false);
				gameFrame.setVisible(true);

				/*
				Start a new Timer
				 */
				Main.timer.stop();
				Main.pause = false;
				Main.tc = new TimeClass(999);
				Main.timer = new Timer(1000, tc);
				timer.start();
			}

		});
		//----------------------------------------------------------------------------


		// Test button-----------------------------------------------------------------------
		test_btn.setBounds(450, 550, 160, 40);
		test_btn.setBackground(btn_Color);
		test_btn.setForeground(Color.white);
		test_btn.setText("Sample Equation");
		test_btn.setFont(new Font("SansSerif", Font.BOLD, 15));
		test_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Generator testGenerator = new Generator("test");
				JFrame f = new JFrame();

				String tmp = "Random Equation:  " + testGenerator.generateEquation() + "\nLength: " + testGenerator.getEquationLength();
				tmp = "<html>" + tmp.replaceAll("<","&lt;")
				.replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>";
				
				JOptionPane.showMessageDialog(f,tmp);
			}

		});
		//------------------------------------------------------------------------------------


		// Continue button-------------------------------------------------------------------
		continue_btn.setBounds(370, 610, 140, 40);
		continue_btn.setBackground(btn_Color);
		continue_btn.setForeground(Color.white);
		continue_btn.setText("Continue");
		continue_btn.setFont(new Font("SansSerif", Font.BOLD, 18));
		continue_btn.setVisible(false);
		
		continue_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuFrame.setVisible(false);
				gameFrame.setVisible(true);

				Main.pause = false;
			}

		});
		//----------------------------------------------------------------------------------


		// Cheat Button----------------------------------------------------------------------
		cheat_btn.setBounds(530, 720, 80, 35);
		cheat_btn.setBackground(btn_Color);
		cheat_btn.setForeground(Color.white);
		cheat_btn.setText("Cheat");
		cheat_btn.setFont(new Font("SansSerif", Font.BOLD, 15));
		cheat_btn.setVisible(true);

		cheat_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame f = new JFrame();
				JOptionPane.showMessageDialog(f,"Answer:  " + Main.answer); 
				
			}

		});
		//------------------------------------------------------------------------------------
	
				
		//########################################################################
		// Add Components to the menu frame
		//########################################################################
		
		/*
		menu panels layout set to null so that i can set position of component by setBound()
		*/
		menu_panel.setLayout(null);
		menu_panel.setBackground(new Color(5,0,30));
		menu_panel.add(header);
		menu_panel.add(stats_label);	// menu_panel.components[1] = stats_label (dont change theese labels order)
		menu_panel.add(footer2);
		menu_panel.add(newGame_btn);
		menu_panel.add(continue_btn);	// menu_panel.components[4] = continue_btn (dont change theese buttons order)
		menu_panel.add(test_btn);
		menuFrame.add(menu_panel);
		menuFrame.setVisible(true);
		
		


		
	}
	
	//###################################################################################################################
	//################################# METHODS  ###########################################################################
	//###################################################################################################################
	
	public static void winLosePopup(boolean win) {
		
		String[] newGameOps = {"Home","Exit"};
		int n;

		if(win) {
			JLabel wLabel = new JLabel("Winner! \n (Elapsed Time: " +Main.elapsedTime + ")");
			wLabel.setHorizontalAlignment(SwingConstants.CENTER);
			n = JOptionPane.showOptionDialog(gameFrame,
					wLabel,
					"WIN",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.CLOSED_OPTION,
					null,     //do not use a custom Icon
					newGameOps,  //the titles of buttons
					newGameOps[0]);
			
		}
		else {
			String tmp = "Loser! \nAnswer was: "+ answer + "\n(Elapsed Time: " + Main.elapsedTime + ")";
			tmp = "<html>" + tmp.replaceAll("<","&lt;")
			.replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>";

			JLabel wLabel = new JLabel(tmp);
			wLabel.setHorizontalAlignment(SwingConstants.CENTER);
			n = JOptionPane.showOptionDialog(gameFrame,
					wLabel,
					"LOSE",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.CLOSED_OPTION,
					null,     //do not use a custom Icon
					newGameOps,  //the titles of buttons
					newGameOps[0]);
			
		}

		if(n == JOptionPane.YES_OPTION){
			Main.updateStatsLabels(menu_panel);
			gameFrame.setVisible(false);
			menuFrame.setVisible(true); 
		}	
		else {
			gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
		}

	}
	
	
	public static void newGame() {
		
		answer = generator.generateEquation();
		answerLength = answer.length();
		
		System.out.println("Equation Length: " + answerLength);

		buildGameFrame();

	}

		
	// Compares s to answer and returns the colors corresponding to each letter in s
	public static String[] compareToAnswer(String s) {
		String[] out = new String[s.length()];
		char[] sa = s.toCharArray();
		
		for(int i = 0; i < sa.length; i++) {
			char c = sa[i];
			if(answer.charAt(i) == c) {
				out[i] = "green";
			}
			else if(answer.indexOf(c) == -1) {
				out[i] = "red";
			}
			else {
				out[i] = "yellow";
			}
		}
		
		return out;
	}
	
	
	// Returns true if the string is a valid equation
	public static boolean isValidEquation(String s) throws ScriptException {
		
		if(s.indexOf("=") == -1) {
			System.out.println("Error1 " + linesCompleted);
			return false;
		}
		
		if(s.length() != answerLength) {

			System.out.println("Error2");
			return false;
		}
		
		for(int i = 0; i < s.length(); i++) {
			char curr = s.charAt(i);
			if(!isNumeric(curr) && !isOp(curr)) {
				System.out.println("Error3");
				return false;
			}
		}
		
		String left = s.split("=")[0];
		String right = s.split("=")[1];

		ScriptEngineManager sem = new ScriptEngineManager();
    	ScriptEngine se = sem.getEngineByName("JavaScript");

		if(!String.valueOf(se.eval(left)).equals(right)) {
			System.out.println("Error4");
			return false;
		}
		
		return true;
	}
	

	// Returns true if c is numeric
	public static boolean isNumeric(char c) {
		String ns = "0123456789";
		return (ns.indexOf(c) != -1);
	}
	

	// Returns true if c is an operation
	public static boolean isOp(char c) {
		String ops = "*/-+=";
		return (ops.indexOf(c) != -1);
	}
	

	public static void updateStatsLabels(JPanel menu_panel) {

		Main.stats_string = new String("Rounds Won: " + Main.stats.getWin_no() + 
		"\nRounds Lost: " + Main.stats.getLoose_no() + 
		"\nRounds Canceled: " + Main.stats.getCanceled_rounds() + 
		"\nAverage of Winners Used Lines: " + Main.stats.getWinner_line_average() + 
		"\nAverage of Winners Elapsed Time: " + Main.stats.getWinner_elapsedTime_average());
		
		// temporary Jlabel
		JLabel stats_label = (JLabel)(menu_panel.getComponent(1)); 
		stats_label.setText("<html>" + Main.stats_string.replaceAll("<","&lt;")
		.replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");

	}


	public static boolean isCanceled(JPanel menu_panel) {
		return menu_panel.getComponent(4).isVisible();
	}


	public static void buildGameFrame() {
		
		// Initialize game frame
		gameFrame = new JFrame();
		gameFrame.setBounds(frame_x,frame_y,frame_width,frame_height);
		gameFrame.setBackground(background_color);
		gameFrame.setDefaultCloseOperation(3);
		gameFrame.setTitle("Nerdle");
		
		// Initialize lines 
		lines = new ArrayList<List<JTextField>>();
		Main.linesCompleted = 0;

		//################################################################################
		// Add Text Fields to the lines array
		//###############################################################################
		int currX = square_gap;
		int currY = 30;
		for(int r = 0; r < 6; r++) {		// 6 Chances to guess
			
			List<JTextField> line = new ArrayList<JTextField>();
			for(int c = 0; c < answerLength; c++) {				//create text fields in the size of answer length
			
				JTextField curr = new JTextField();

				curr.setBounds(currX+2, currY+2, sqSize-5, sqSize-5);
				curr.setBackground(Color.gray.brighter());
				curr.setBorder(null);
				curr.setHorizontalAlignment(JTextField.CENTER);
				curr.setFont(new Font("SansSerif", Font.BOLD, 35));

				if(r ==0) {
					curr.setEnabled(true);
				}
				else {
					curr.setEnabled(false);
				}
				
				line.add(curr);
				currX += (square_gap+sqSize);

			}

			// add new line to the lines array
			lines.add(line);
			currX = square_gap;
			currY += (square_gap+sqSize);
		}
		

		//#####################################################################################
		// Create panel for game_panel
		//####################################################################################
		JPanel game_panel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				
				int currX = square_gap;
				int currY = 30;
				g.setColor(sqColor);
				
				for(int r = 0; r < 6; r++) {				// 6 chances to guess
					for(int c = 0; c < answerLength; c++) {		//create squares in the size of answer length
						
						// converts square text fields to round squares
						g.fillRoundRect(currX, currY, sqSize, sqSize, 8, 8);
						currX += (square_gap+sqSize);
						
					}
					
					currX = square_gap;
					currY += (square_gap+sqSize);
				}
				
				
			}
		};
		

		//########################################################################
		// Create Buttons for game frame
		//########################################################################
		int i = 0;
		currX = buttons_xOffset;
		currY = buttons_yOffset;

		for(int r = 0; r < 2; r++) {
			for(int c = 0; c < 10; c++) {
				
				/*
				* second row of buttons has less buttons(6) than first row(10),
				* so we break the loop after adding the 6th button to the second row
				*/
				if(r == 1 && c > 6)
					break;	 
						
				
				String l = button_vals.substring(i,i+1);
				
				final JButton b = new JButton(l);

				if(b.getText().equals("E")) {
					b.setText("Enter");
					b.setBounds(currX, currY, (buttonWidth+buttonGap)*2, sqSize);
				}
				else if(b.getText().equals("D")) {
					b.setText("Delete");
					b.setBounds(currX+buttonWidth+buttonGap*2, currY, buttonWidth*2+buttonGap, sqSize);
				}
				else {
					b.setBounds(currX, currY, buttonWidth, sqSize);
				}
				
				b.setBackground(Color.gray.brighter());
				b.setBorder(new RoundBtn(10));

				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//################################################################
						// Numbers (NOT Enter or Delete Actions)
						//################################################################
						if(b.getText().equals("Enter") == false && b.getText().equals("Delete") == false) {

							boolean found = false;
							for(List<JTextField> line: lines) {
								for(JTextField tf: line) {
									if(tf.isEnabled()) {
										if(tf.getText().equals("")) {
											found = true;
											tf.setText(b.getText());
											break;
										}
									}
								}
								if(found == true)
									break;
							}
						}
						//##################################################################
						// Enter Action
						//####################################################################
						else if(b.getText().equals("Enter")){

							List<JTextField> line = lines.get(linesCompleted);	// get last line(current line)

							String eq = "";
							for(JTextField tf: line) {
								eq += tf.getText();		// create equation of the player as a String
							}
							
							// check if both sides of the equation is equal to each other
							try {
								if(isValidEquation(eq)) {		//if(isValidEquation(eq)) has some bugs
									
									//HIGHLIGHT COMPARISONS:
									
									/* 
									when players clicks on Enter button , if the equation is valid , 
									player goes to the new line of text fields. so we increment u
									*/
									Main.currentUsedLines++;

									String[] colors = compareToAnswer(eq);

									/*
									* color the text fields
									*/
									int i = 0;
									for(JTextField tf: line) {

										if(colors[i].equals("red"))
											tf.setBackground(Color.red.darker());
										else if(colors[i].equals("yellow"))
											tf.setBackground(Color.yellow.darker());
										else if(colors[i].equals("green"))
											tf.setBackground(Color.green.darker());

										tf.setDisabledTextColor(Color.white);
										tf.setEnabled(false);
										i++;
									}
									
									/*
									* color the buttons
									*/
									for(int c = 0; c < eq.length(); c++) {

										String curr = eq.substring(c,c+1);
										for(int z = 0; z < buttons.size(); z++) {

											JButton b = buttons.get(z);
											if(b.getText().equals(curr)) {
												if(b.getName() == null) {

													if(colors[c].equals("yellow"))
														b.setBackground(Color.yellow.darker());
													else if(colors[c].equals("red"))
														b.setBackground(Color.red.darker());
													else if(colors[c].equals("green")) {
														b.setBackground(Color.green.darker());
														b.setName("green");
													}
													b.setForeground(Color.white);

												}
											}
										}
									}
									
									// if player wins
									if(eq.equals(answer)) {
										//UPDATE STATISTICS
										Main.stats.setWin_no(Main.stats.getWin_no()+1);
										Main.stats.updateTimeAverage(Main.elapsedTime);
										Main.stats.updateLineAverage(currentUsedLines);
										Main.updateStatsLabels(menu_panel);
									
										//WINNING MESSAGE
										winLosePopup(true);
										continue_btn.setVisible(false);
									}
									else {
										
										// if the game is not finished yet
										if(linesCompleted != 5) {
											linesCompleted++;
											for(JTextField tf: lines.get(linesCompleted)) {
												tf.setEnabled(true);
											}
										}
										// if the game is finished(all 6 rows are complete and still no win)
										else {
											Main.stats.setLoose_no(Main.stats.getLoose_no()+1);
											Main.updateStatsLabels(menu_panel);
											winLosePopup(false);
											continue_btn.setVisible(false);
										}
									
									}
								}

								//INVALID EQUATION
								else {
									JLabel invalidLabel = new JLabel("Invalid Input");
									invalidLabel.setHorizontalAlignment(SwingConstants.CENTER);
									JOptionPane.showMessageDialog(gameFrame, invalidLabel, "Invalid", JOptionPane.PLAIN_MESSAGE, null);
								}
							} catch (HeadlessException e1) {
								e1.printStackTrace();

							} catch (ScriptException e1) {
								e1.printStackTrace();

							}
								
						}
						//####################################################################
						// Delete Action
						//####################################################################
						else if(b.getText().equals("Delete")) {
							List<JTextField> line = lines.get(linesCompleted);
							for(int i = line.size()-1; i >= 0; i--) {
								JTextField curr = line.get(i);
								if(curr.getText().equals("") == false) {
									curr.setText("");
									break;
								}
							}
						}
					}
				});

				b.setOpaque(true);

				// add new button to the buttons array
				buttons.add(b);
				// add button to the frame
				gameFrame.add(b);
				
				currX += buttonWidth+buttonGap;
				i++;
				
			}
			currX = buttons_xOffset+buttonWidth/2;
			currY += (buttonGap+sqSize);

		}
		
		// Add text fields to the Frame (we add text fields after adding panel so that
		// panel squared go backward and text fields come forward)
		for(List<JTextField> tfl: lines) {
			for(JTextField tf: tfl) {
				gameFrame.add(tf);
			}
		}

		/*
		components of the game frame are added to the frame directly (frame layout is null) and they position is set by setBoud()
		so there is a pannel for text fields and buttons in the upper side and other components outside of the game panel in the
		lower side
		*/
		gameFrame.add(footer1);
		gameFrame.add(home_btn);
		gameFrame.add(timer_label);
		gameFrame.add(zero);
		gameFrame.add(cheat_btn);
		gameFrame.add(game_panel);
	
	}

}


//################################################################################
//################################################################################
// Classes
//################################################################################
//################################################################################

class RoundBtn implements Border 
{
    private int r;
    RoundBtn(int r) {
        this.r = r;
    }
    public Insets getBorderInsets(Component c) {
        return new Insets(this.r+1, this.r+1, this.r+2, this.r);
    }
    public boolean isBorderOpaque() {
        return true;
    }
    public void paintBorder(Component c, Graphics g, int x, int y, 
    int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, r, r);
    }
}


class TimeClass implements ActionListener {
	int counter;
	int end;

	public TimeClass(int end) {
		this.end = end;
		this.counter = 0;
	}


	public void actionPerformed(ActionEvent tc) {

		if (counter <= end && !Main.pause) {
			counter++;
			Main.timer_label.setText(String.valueOf(counter));
		}
		else if (Main.pause) {
			Main.timer_label.setText("...");
		}
		else {
			Main.timer.stop();
			Main.timer_label.setText("0");
		}

		Main.elapsedTime = counter;

	}

}
