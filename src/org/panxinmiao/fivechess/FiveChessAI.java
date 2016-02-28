package org.panxinmiao.fivechess;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class FiveChessAI {
	public static final List<Point>[] winState;
	static{
		winState=new ArrayList[572];
		int n=0;
		for(int i=0;i<15;i++){
			for(int j=0;j<11;j++){
				winState[n]=new ArrayList<Point>();
				for(int m=0;m<5;m++){
					winState[n].add(new Point(i,j+m));
				}
				n++;
			}
		}
		for(int i=0;i<15;i++){
			for(int j=0;j<11;j++){
				winState[n]=new ArrayList<Point>();
				for(int m=0;m<5;m++){
					winState[n].add(new Point(j+m,i));
				}
				n++;
			}
		}
		for(int i=0;i<11;i++){
			for(int j=0;j<11;j++){
				winState[n]=new ArrayList<Point>();
				for(int m=0;m<5;m++){
					winState[n].add(new Point(i+m,j+m));
				}
				n++;
			}
		}
		for(int i=0;i<11;i++){
			for(int j=0;j<11;j++){
				winState[n]=new ArrayList<Point>();
				for(int m=0;m<5;m++){
					winState[n].add(new Point(i+4-m,j+m));
				}
				n++;
			}
		}
	}
	
	private boolean[] pWinFlag=new boolean[572];
	private boolean[] cWinFlag=new boolean[572];
	private int[][] chessState;
	
	public FiveChessAI(int[][] chessState){
		this.chessState=chessState;
		initWinFlag();
	}
	
	private void initWinFlag(){
		for(int i=0;i<572;i++){
			cWinFlag[i]=true;
			pWinFlag[i]=true;
		}
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++){
				if(chessState[i][j]==FiveChessPanel.BLACK_CHESS){
					for(int n=0;n<572;n++){
						if(winState[n].contains(new Point(i,j))){
							cWinFlag[n]=false;
						}
					}
				}
				if(chessState[i][j]==FiveChessPanel.WHITE_CHESS){
					for(int n=0;n<572;n++){
						if(winState[n].contains(new Point(i,j))){
							pWinFlag[n]=false;
						}
					}
				}
			}
		}
	}
	
	public void playerDrop(Point p){
		for(int n=0;n<572;n++){
			if(winState[n].contains(p)){
				cWinFlag[n]=false;
			}
		}
	}
	
	public void aiDrop(Point p){
		for(int n=0;n<572;n++){
			if(winState[n].contains(p)){
				pWinFlag[n]=false;
			}
		}
	}
	
	public Point getBestPoint(){
		int[] cg=new int[572];
		int[][] cScr=new int[15][15];
		for(int i=0;i<572;i++){
			if(cWinFlag[i]==true){
				if(i<165){
					if(winState[i].get(0).y>0&&chessState[winState[i].get(0).x][winState[i].get(0).y-1]!=1){
						cg[i]++;
					}
					if(winState[i].get(4).y<14&&chessState[winState[i].get(4).x][winState[i].get(4).y+1]!=1){
						cg[i]++;
					}
				}else if(i>=165&&i<330){
					if(winState[i].get(0).x>0&&chessState[winState[i].get(0).x-1][winState[i].get(0).y]!=1){
						cg[i]++;
					}
					if(winState[i].get(4).x<14&&chessState[winState[i].get(4).x+1][winState[i].get(4).y]!=1){
						cg[i]++;
					}
				}else if(i>=330&&i<451){
                	if(winState[i].get(0).x>0&&winState[i].get(0).y>0&&chessState[winState[i].get(0).x-1][winState[i].get(0).y-1]!=1){
						cg[i]++;
					}
                	if(winState[i].get(4).x<14&&winState[i].get(4).y<14&&chessState[winState[i].get(4).x+1][winState[i].get(4).y+1]!=1){
						cg[i]++;
					}
                }else if(i>=451&&i<572){
                	if(winState[i].get(0).x<14&&winState[i].get(0).y>0&&chessState[winState[i].get(0).x+1][winState[i].get(0).y-1]!=1){
						cg[i]++;
					}
                	if(winState[i].get(4).x>0&&winState[i].get(4).y<14&&chessState[winState[i].get(4).x-1][winState[i].get(4).y+1]!=1){
						cg[i]++;
					}
                }
                int ct=0;
        
                for(Point p:winState[i]){
                	if(chessState[p.x][p.y]==-1){
                		ct++;
                	}
                }
				switch(ct){
				case 2:
					for(int m=0;m<15;m++){
						for(int n=0;n<15;n++){
							if(winState[i].contains(new Point(m,n))&& (chessState[m][n]==0)){
								cScr[m][n]+=(4+Math.pow(2, cg[i]));
							}
						}
					}
					break;
				case 3:
					for(int m=0;m<15;m++){
						for(int n=0;n<15;n++){
							if(winState[i].contains(new Point(m,n))&& (chessState[m][n]==0)){
								cScr[m][n]+=(15+Math.pow(6, cg[i]));
							}
						}
					}
					break;
				case 4:
					for(int m=0;m<15;m++){
						for(int n=0;n<15;n++){
							if(winState[i].contains(new Point(m,n))&& (chessState[m][n]==0)){
								return new Point(m, n);
							}
						}
					}
				}
			}
		}
		
		int[] pg=new int[572];
		int[][] pScr=new int[15][15];
		for(int i=0;i<572;i++){
			if(pWinFlag[i]==true){
				if(i<165){
					if(winState[i].get(0).y>0&&chessState[winState[i].get(0).x][winState[i].get(0).y-1]!=-1){
						pg[i]++;
					}
					if(winState[i].get(4).y<14&&chessState[winState[i].get(4).x][winState[i].get(4).y+1]!=-1){
						pg[i]++;
					}
				}else if(i>=165&&i<330){
					if(winState[i].get(0).x>0&&chessState[winState[i].get(0).x-1][winState[i].get(0).y]!=-1){
						pg[i]++;
					}
					if(winState[i].get(4).x<14&&chessState[winState[i].get(4).x+1][winState[i].get(4).y]!=-1){
						pg[i]++;
					}
				}else if(i>=330&&i<451){
                	if(winState[i].get(0).x>0&&winState[i].get(0).y>0&&chessState[winState[i].get(0).x-1][winState[i].get(0).y-1]!=-1){
						pg[i]++;
					}
                	if(winState[i].get(4).x<14&&winState[i].get(4).y<14&&chessState[winState[i].get(4).x+1][winState[i].get(4).y+1]!=-1){
						pg[i]++;
					}
                }else if(i>=451&&i<572){
                	if(winState[i].get(0).x<14&&winState[i].get(0).y>0&&chessState[winState[i].get(0).x+1][winState[i].get(0).y-1]!=-1){
						pg[i]++;
					}
                	if(winState[i].get(4).x>0&&winState[i].get(4).y<14&&chessState[winState[i].get(4).x-1][winState[i].get(4).y+1]!=-1){
						pg[i]++;
					}
                }
                int pt=0;
        
                for(Point p:winState[i]){
                	if(chessState[p.x][p.y]==1){
                		pt++;
                	}
                }
				switch(pt){
				case 2:
					for(int m=0;m<15;m++){
						for(int n=0;n<15;n++){
							if(winState[i].contains(new Point(m,n))&& (chessState[m][n]==0)){
								pScr[m][n]+=(2+Math.pow(2, pg[i]));
							}
						}
					}
					break;
				case 3:
					for(int m=0;m<15;m++){
						for(int n=0;n<15;n++){
							if(winState[i].contains(new Point(m,n))&& (chessState[m][n]==0)){
								pScr[m][n]+=(10+Math.pow(6, pg[i]));
							}
						}
					}
					break;
				case 4:
					for(int m=0;m<15;m++){
						for(int n=0;n<15;n++){
							if(winState[i].contains(new Point(m,n))&& (chessState[m][n]==0)){
								return new Point(m, n);
							}
						}
					}
				}
			}
		}
				
		for(int i=0;i<572;i++){
			if (cWinFlag[i]==true){
				for(Point p:winState[i]){
					if(chessState[p.x][p.y]==0){
						for(Point p1:winState[i]){
							if(chessState[p1.x][p1.y]==-1){
								cScr[p.x][p.y]+=cg[i];
							}
						}
					}
				}
			}
			
			if (pWinFlag[i]==true){
				for(Point p:winState[i]){
					if(chessState[p.x][p.y]==0){
						for(Point p1:winState[i]){
							if(chessState[p1.x][p1.y]==1){
								pScr[p.x][p.y]+=pg[i];
							}
						}
					}
				}
			}
		}
			
		int cScrMax=0;int pScrMax=0;int cx=0;int cy=0;
		int px=0;int py=0;
		
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++){
				if(cScr[i][j]>cScrMax){
					cScrMax=cScr[i][j];
					cx=i;
					cy=j;
				}
				if(pScr[i][j]>pScrMax){
					pScrMax=pScr[i][j];
					px=i;
					py=j;
				}
			}
		}
		
		if(cScrMax>pScrMax){
			return new Point(cx,cy);
		}else{
			return new Point(px,py);
		}
		
	}
	
}
