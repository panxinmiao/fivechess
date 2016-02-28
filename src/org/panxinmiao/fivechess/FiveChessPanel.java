package org.panxinmiao.fivechess;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FiveChessPanel extends JPanel implements MouseListener,MouseMotionListener,ActionListener{
	private static final long serialVersionUID = 6701717209232107209L;
	
	public static final int BLACK_CHESS=1;
	public static final int WHITE_CHESS=-1;
	public static final int NO_CHESS=0;
	
	private static final int PLAYER_TURN=1;
	private static final int AI_TURN=-1;
	
	private boolean gameOver;
	private int gameTurn=PLAYER_TURN;
	
	private Point mousePos=new Point(-1,-1);
	
	private int[][] chessState;
	private Point lastPlayerDrop;
	private Point lastAIDrop;
	
	private Point cross;
	private Point lastCross;
	
	private FiveChessAI fcAI;
	
	private String gameInfo="游戏开始";
	
	private JButton[] but=new JButton[3];
	
	private List<Point> winPoints;

	public FiveChessPanel(){
		init();
	}
	
	
	
	private void init() {
		setLayout(null);
		chessState=new int[15][15];
		fcAI=new FiveChessAI(chessState);
		String[] butStr={"重新开始","悔棋","退出游戏"};
		for(int i=0;i<3;i++){
			but[i]=new JButton(butStr[i]);
			but[i].addActionListener(this);
			but[i].setBounds(330, 20+30*i, 90, 25);
			add(but[i]);
		}
		but[1].setEnabled(false);
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	private void reset() {
		chessState=new int[15][15];
		fcAI=new FiveChessAI(chessState);
		but[1].setEnabled(false);
		lastPlayerDrop=null;
		lastAIDrop=null;
		cross=null;
		lastCross=null;
		winPoints=null;
		gameInfo="游戏开始";
		gameOver=false;
		gameTurn=PLAYER_TURN;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("宋体",Font.PLAIN,12));
		for(int i=0;i<15;i++){
			g.drawString(Character.toString((char)('A'+i)), 28+i*20, 20);
			g.drawString(Integer.toString(i+1), 5, 36+i*20);
			g.drawLine(30+20*i, 30, 30+20*i, 310);
			g.drawLine(30, 30+20*i,310 , 30+20*i);
		}
		g.fillOval(87, 87, 6, 6);
		g.fillOval(87, 247, 6, 6);
		g.fillOval(247, 87, 6, 6);
		g.fillOval(247, 247, 6, 6);
		g.fillOval(167, 167, 6, 6);
		
		if(mousePos.x>=0&&mousePos.x<15&&mousePos.y>=0&&mousePos.y<15&&chessState[mousePos.x][mousePos.y]==NO_CHESS){
			g.setColor(Color.red);
	        g.drawLine(mousePos.x*20+30 - 8, mousePos.y*20+30 - 8, mousePos.x*20+30 - 4, mousePos.y*20+30 - 8);
	        g.drawLine(mousePos.x*20+30 - 8, mousePos.y*20+30 - 8, mousePos.x*20+30 - 8, mousePos.y*20+30 - 4);
	        g.drawLine(mousePos.x*20+30 - 8, mousePos.y*20+30 + 8, mousePos.x*20+30 - 8, mousePos.y*20+30 + 4);
	        g.drawLine(mousePos.x*20+30 - 8, mousePos.y*20+30 + 8, mousePos.x*20+30 - 4, mousePos.y*20+30 + 8);
	        g.drawLine(mousePos.x*20+30 + 8, mousePos.y*20+30 - 8, mousePos.x*20+30 + 4, mousePos.y*20+30 - 8);
	        g.drawLine(mousePos.x*20+30 + 8, mousePos.y*20+30 - 8, mousePos.x*20+30 + 8, mousePos.y*20+30 - 4);
	        g.drawLine(mousePos.x*20+30 + 8, mousePos.y*20+30 + 8, mousePos.x*20+30 + 4, mousePos.y*20+30 + 8);
	        g.drawLine(mousePos.x*20+30 + 8, mousePos.y*20+30 + 8, mousePos.x*20+30 + 8, mousePos.y*20+30 + 4);
		}
		
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++){
				if(chessState[i][j]==WHITE_CHESS){
					g.setColor(Color.white);
					g.fillOval(i*20+30-8, j*20+30-8, 16, 16);
					g.setColor(Color.black);
					g.drawOval(i*20+30-8, j*20+30-8, 16, 16);
				}
				if(chessState[i][j]==BLACK_CHESS){
					g.setColor(Color.black);
					g.fillOval(i*20+30-8, j*20+30-8, 16, 16);
					g.drawOval(i*20+30-8, j*20+30-8, 16, 16);
				}
			}
		}
		if(winPoints!=null){
			for(Point p:winPoints){
				g.setColor(Color.yellow);
				g.fillOval(p.x*20+30-8, p.y*20+30-8, 16, 16);
				g.setColor(Color.black);
				g.drawOval(p.x*20+30-8, p.y*20+30-8, 16, 16);
			}
		}
		if(cross!=null){
			g.setColor(Color.black);
			g.drawLine(cross.x*20+30-4, cross.y*20+30,cross.x*20+30+4, cross.y*20+30);
			g.drawLine(cross.x*20+30, cross.y*20+30-4,cross.x*20+30, cross.y*20+30+4);

		}
		g.setFont(new Font("微软雅黑",Font.BOLD,20));
		g.setColor(Color.BLUE);
		g.drawString(gameInfo, 30, 350);
	}
	
	
	
	
	private void aiTurn() {
		if(!gameOver){
			Point p=fcAI.getBestPoint();
			chessState[p.x][p.y]=WHITE_CHESS;
			lastAIDrop=p;
			lastCross=cross;
			cross=lastAIDrop;
			fcAI.aiDrop(p);
			winPoints=checkOver();
			gameTurn=PLAYER_TURN;
			repaint();
		}
	}
	private List<Point> checkOver() {
		for(List<Point> points:FiveChessAI.winState){
			int sum=0;
			for(Point p:points){
				sum+=chessState[p.x][p.y];
			}
			if(sum==5){
				gameOver=true;
				but[1].setEnabled(false);
				gameInfo="游戏结束，玩家获胜";
				return points;
			}
			if(sum==-5){
				gameOver=true;
				but[1].setEnabled(false);
				gameInfo="游戏结束，电脑获胜";
				return points;
			}
		}
		return null;
	}
		
	@Override
	public void mouseClicked(MouseEvent e) {
		int x=Math.round((e.getPoint().x-30)/20f);
		int y=Math.round((e.getPoint().y-30)/20f);
		synchronized(this){
			if(!gameOver&&gameTurn==PLAYER_TURN){
				if(x>=0&&x<15&&y>=0&&y<15&&chessState[x][y]==NO_CHESS){
					chessState[x][y]=BLACK_CHESS;
					lastPlayerDrop=new Point(x,y);
					fcAI.playerDrop(lastPlayerDrop);
					repaint();
					winPoints=checkOver();
					but[1].setEnabled(true);
					gameTurn=AI_TURN;
					aiTurn();
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameOver){
			mousePos.x=Math.round((e.getPoint().x-30)/20f);
			mousePos.y=Math.round((e.getPoint().y-30)/20f);
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("重新开始")||e.getActionCommand().equals("新游戏")){
			reset();
			repaint();
			return;
		}
		if(e.getActionCommand().equals("悔棋")){
			chessState[lastPlayerDrop.x][lastPlayerDrop.y]=NO_CHESS;
			chessState[lastAIDrop.x][lastAIDrop.y]=NO_CHESS;
			cross=lastCross;
			fcAI=new FiveChessAI(chessState);
			but[1].setEnabled(false);
			repaint();
			return;
		}
		if(e.getActionCommand().equals("作者")){
			JOptionPane.showMessageDialog(this, " 作者:  PanXinmiao\n 邮箱:  1092djhfg@163.com");
			return;
		}
		if(e.getActionCommand().equals("操作说明")){
			JOptionPane.showMessageDialog(this," 玩家黑子，电脑白子\n 玩家可以悔一步棋");
			return;
		}
		if(e.getActionCommand().equals("退出游戏")||e.getActionCommand().equals("退出")){
			System.exit(JFrame.EXIT_ON_CLOSE);
			return;
		}
		
	}


}
