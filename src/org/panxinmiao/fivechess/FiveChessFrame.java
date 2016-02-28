package org.panxinmiao.fivechess;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class FiveChessFrame extends JFrame{
	private static final long serialVersionUID = -6010447391880869203L;
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenu helpMenu;
	private JMenuItem newGame;
	private JMenuItem exitGame;
	private JMenuItem about;
	private JMenuItem operation;
	
	private FiveChessPanel fiveChessPanel;
	
	public FiveChessFrame(){
		init();
	}

	private void init() {
		menuBar=new JMenuBar();
		gameMenu = new JMenu("菜单");
		helpMenu = new JMenu("帮助");
		newGame=new JMenuItem("新游戏");
		exitGame = new JMenuItem("退出");
		about = new JMenuItem("作者");
		operation=new JMenuItem("操作说明");
		
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		gameMenu.add(newGame);
		gameMenu.add(exitGame);
		helpMenu.add(about);
		helpMenu.add(operation);
		setJMenuBar(menuBar);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		fiveChessPanel=new FiveChessPanel();
		setContentPane(fiveChessPanel);
		
		newGame.addActionListener(fiveChessPanel);
		exitGame.addActionListener(fiveChessPanel);
		about.addActionListener(fiveChessPanel);
		operation.addActionListener(fiveChessPanel);
		
		setSize(450,420);
		setResizable(false);
		setTitle("五子棋");
		setLocation(400, 150);
		setVisible(true);
		setFocusable(true);		
		
	}

}
