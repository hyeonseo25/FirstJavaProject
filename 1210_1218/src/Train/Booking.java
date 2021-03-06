package Train;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking extends JFrame {
	private TrainTest main;
    private Search search ;
    private String time=""; //시간대
    private int seatnum=1; //선택할 수 있는 좌석 수
    private int seat[] = new int[10]; //현재 db에 남아있는 좌석
    private int myseat[] = new int[10]; //내가 선택한 좌석
    private String train; //db 구분항목
    private int nowSeatnum; //현재 선택할 수 있는 좌석 수
    private int price=25000; //현재 가격
    private final int seatPrice = 25000; //좌석 1개당 가격
    Date date = new Date();
	SimpleDateFormat sdf1 = new SimpleDateFormat("MM"); //월
	SimpleDateFormat sdf2 = new SimpleDateFormat("dd"); //일
	SimpleDateFormat sdf3 = new SimpleDateFormat("HH"); //시
    
    Font f1=new Font("나눔바른고딕",Font.PLAIN,18);//기본폰트
    
    Search s1;
	
    public Booking(Search s1) {
    	this.s1 = s1;
    }

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int[] getMyseat() {
		return myseat;
	}

	public void setMyseat(int[] myseat) {
		this.myseat = myseat;
	}

	public String getTrain() {
		return train;
	}

	public void setTrain(String train) {
		this.train = train;
	}

	public int getNowSeatnum() {
		return nowSeatnum;
	}

	public void setNowSeatnum(int nowSeatnum) {
		this.nowSeatnum = nowSeatnum;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void BookingFrame(Search s1) {
		setTitle("기차표예매"); // 창 제목 설정
		setSize(800,800); // 창 크기 설정
		setResizable(false); // T- 창 크기 조절 o F- 창 크기 조절 x
		setLocation(600,150); // 창 뜨는 위치 설정
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 창 꺼지는거
		
		JPanel panel = new JPanel();
		placeButton(s1, panel);
		add(panel);
		//panel.setBackground(Color.WHITE);
		
		setVisible(true);
	}
	
	public void placeButton(Search s1, JPanel panel) {
		//addDB();
		int today_month=Integer.parseInt(sdf1.format(date));
		int today_day=Integer.parseInt(sdf2.format(date));
		int today_time = Integer.parseInt(sdf3.format(date));
		
		panel.setLayout(null);
		JPanel seatPanel = new JPanel();
		seatPanel.setBackground(Color.WHITE);
		seatPanel.setBounds(280, 40, 420, 640); //흰색 배경
		panel.add(seatPanel);
		
		JLabel timeL = new JLabel();
		timeL.setBounds(400, 5, 500, 30);
        panel.add(timeL);
        
        JLabel timeChoice = new JLabel("▶ 시간 선택");
        timeChoice.setBounds(20, 20, 250, 80);
		panel.add(timeChoice);
		timeChoice.setFont(f1);
		
		JButton timeButton1 = new JButton("6:00");
		timeButton1.setBounds(20, 90, 100, 30);
        panel.add(timeButton1);
        timeButton1.setBackground(Color.white);
        timeButton1.setFont(f1);
        
        timeButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(((s1.getMonth()==today_month)&&(s1.getDay()==today_day))&&(today_time>3)) {
					s1.setWarningMessage("                    예매가 마감되었습니다");
					s1.warningFrame();
				}else {
					time="1";
					makeDB(s1.getTrain());
					try {
						getSeatDB();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"행 "+s1.getMonth()+"월"+s1.getDay()+"일"+timeButton1.getText());
					placeSeatpanel(seatPanel);
					repaint();
				}
			}
		});
        
        JButton timeButton2 = new JButton("8:00");
		timeButton2.setBounds(140, 90, 100, 30);
		timeButton2.setBackground(Color.white);
        panel.add(timeButton2);
        timeButton2.setFont(f1);
        
        timeButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(((s1.getMonth()==today_month)&&(s1.getDay()==today_day))&&(today_time>5)) {
					s1.setWarningMessage("                    예매가 마감되었습니다");
					s1.warningFrame();
				}else {
					time="2";
					makeDB(s1.getTrain());
					try {
						getSeatDB();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"행 "+s1.getMonth()+"월"+s1.getDay()+"일"+timeButton2.getText());
					placeSeatpanel(seatPanel);
					repaint();
				}
			}
		});
        
        JButton timeButton3 = new JButton("10:00");
		timeButton3.setBounds(20, 140, 100, 30);
        panel.add(timeButton3);
        timeButton3.setBackground(Color.white);
        timeButton3.setFont(f1);
        
        timeButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(((s1.getMonth()==today_month)&&(s1.getDay()==today_day))&&(today_time>7)) {
					s1.setWarningMessage("                    예매가 마감되었습니다");
					s1.warningFrame();
				}else {
					time="3";
					makeDB(s1.getTrain());
					try {
						getSeatDB();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"행 "+s1.getMonth()+"월"+s1.getDay()+"일"+timeButton3.getText());
					placeSeatpanel(seatPanel);
					repaint();
				}
			}
		});
        
        JButton timeButton4 = new JButton("12:00");
		timeButton4.setBounds(140, 140, 100, 30);
        panel.add(timeButton4);
        timeButton4.setBackground(Color.white);
        timeButton4.setFont(f1);
        
        timeButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(((s1.getMonth()==today_month)&&(s1.getDay()==today_day))&&(today_time>9)) {
					s1.setWarningMessage("                    예매가 마감되었습니다");
					s1.warningFrame();
				}else {
					time="4";
					makeDB(s1.getTrain());
					try {
						getSeatDB();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"행 "+s1.getMonth()+"월"+s1.getDay()+"일"+timeButton4.getText());
					placeSeatpanel(seatPanel);
					repaint();
				}
			}
		});
        
        JButton timeButton5 = new JButton("14:00");
		timeButton5.setBounds(20, 190, 100, 30);
        panel.add(timeButton5);
        timeButton5.setBackground(Color.white);
        timeButton5.setFont(f1);
        
        timeButton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(((s1.getMonth()==today_month)&&(s1.getDay()==today_day))&&(today_time>11)) {
					s1.setWarningMessage("                    예매가 마감되었습니다");
					s1.warningFrame();
				}else {
					time="5";
					makeDB(s1.getTrain());
					try {
						getSeatDB();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"행 "+s1.getMonth()+"월"+s1.getDay()+"일"+timeButton5.getText());
					placeSeatpanel(seatPanel);
					repaint();
				}
			}
		});
        
        JButton timeButton6 = new JButton("16:00");
		timeButton6.setBounds(140, 190, 100, 30);
        panel.add(timeButton6);
        timeButton6.setBackground(Color.white);
        timeButton6.setFont(f1);
        
        timeButton6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(((s1.getMonth()==today_month)&&(s1.getDay()==today_day))&&(today_time>13)) {
					s1.setWarningMessage("                    예매가 마감되었습니다");
					s1.warningFrame();
				}else {
					time="6";
					makeDB(s1.getTrain());
					try {
						getSeatDB();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"행 "+s1.getMonth()+"월"+s1.getDay()+"일"+timeButton6.getText());
					placeSeatpanel(seatPanel);
					repaint();
				}
			}
		});
        
        JButton timeButton7 = new JButton("18:00");
		timeButton7.setBounds(20, 240, 100, 30);
        panel.add(timeButton7);
        timeButton7.setBackground(Color.white);
        timeButton7.setFont(f1);
        
        timeButton7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(((s1.getMonth()==today_month)&&(s1.getDay()==today_day))&&(today_time>15)) {
					s1.setWarningMessage("                    예매가 마감되었습니다");
					s1.warningFrame();
				}else {
					time="7";
					makeDB(s1.getTrain());
					try {
						getSeatDB();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"행 "+s1.getMonth()+"월"+s1.getDay()+"일"+timeButton7.getText());
					placeSeatpanel(seatPanel);
					repaint();
				}
			}
		});
        
        JButton timeButton8 = new JButton("20:00");
		timeButton8.setBounds(140, 240, 100, 30);
        panel.add(timeButton8);
        timeButton8.setBackground(Color.white);
        timeButton8.setFont(f1);
        
        timeButton8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(((s1.getMonth()==today_month)&&(s1.getDay()==today_day))&&(today_time>17)) {
					s1.setWarningMessage("                    예매가 마감되었습니다");
					s1.warningFrame();
				}else {
					time="8";
					makeDB(s1.getTrain());
					try {
						getSeatDB();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"행 "+s1.getMonth()+"월"+s1.getDay()+"일"+timeButton8.getText());
					placeSeatpanel(seatPanel);
					repaint();
				}
			}
		});
        
        JButton timeButton9 = new JButton("22:00");
		timeButton9.setBounds(20, 290, 100, 30);
        panel.add(timeButton9);        
        timeButton9.setBackground(Color.white);
        timeButton9.setFont(f1);

        
        timeButton9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(((s1.getMonth()==today_month)&&(s1.getDay()==today_day))&&(today_time>19)) {
					s1.setWarningMessage("                    예매가 마감되었습니다");
					s1.warningFrame();
				}else {
					time="9";
					makeDB(s1.getTrain());
					try {
						getSeatDB();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"행 "+s1.getMonth()+"월"+s1.getDay()+"일"+timeButton9.getText());
					placeSeatpanel(seatPanel);
					repaint();
					
				}
				
			}
		});
        
        JLabel num = new JLabel("▶ 좌석 수 선택");
        num.setBounds(20, 330, 250, 80);
		panel.add(num);
		num.setFont(f1);
		
		JButton minusButton = new JButton("-");
        minusButton.setBounds(20, 390, 50, 30);
        panel.add(minusButton);
        minusButton.setBackground(Color.white);
        minusButton.setFont(f1);
		
        JLabel seatNow = new JLabel("1");
        seatNow.setBounds(80, 390, 80, 30);
        panel.add(seatNow);
        seatNow.setFont(f1);
        
        JButton plusButton = new JButton("+");
		plusButton.setBounds(100, 390, 50, 30);
        panel.add(plusButton);
        plusButton.setBackground(Color.white);
        plusButton.setFont(f1);
        
        JLabel priceL = new JLabel("▶ 기차표 가격");
        priceL.setBounds(20, 440, 120, 30);
        panel.add(priceL);
        priceL.setFont(f1);
        
        JLabel priceL_2 = new JLabel("장당 "+Integer.toString(seatPrice)+"원");
        priceL_2.setBounds(20, 470, 120, 30);
        panel.add(priceL_2);
        priceL_2.setFont(f1);
        
        plusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(seatnum>=4) {
					s1.setWarningMessage("              좌석은 4좌석 까지 선택가능합니다.");
					s1.warningFrame();
				}else {
					seatnum++;
					price = seatPrice*seatnum;
					seatNow.setText(Integer.toString(seatnum));
				}
			}
		});
        
        minusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(seatnum<=1) {
					s1.setWarningMessage("                  최소 좌석은 1좌석 부터입니다.");
					s1.warningFrame();
				}else if(time.equals("")){
					seatnum--;
					price = seatPrice*seatnum;
					seatNow.setText(Integer.toString(seatnum));
				}else {
					seatnum--;
					price = seatPrice*seatnum;
					placeSeatpanel(seatPanel);
					repaint();
					seatNow.setText(Integer.toString(seatnum));
				}
			}
		});
        
		JButton before = new JButton("◀ 이전");
		before.setBounds(600, 700, 80, 30);
        panel.add(before);
        before.setBackground(Color.white);
        before.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.showSearch();
			}
		});
        
		JButton next = new JButton("다음 ▶");
		next.setBounds(700, 700, 80, 30);
        panel.add(next);        
        next.setBackground(Color.white);
        
        next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nowSeatnum==seatnum) {
					updateDB();
					PayCheckFrame();
				}else {
					s1.setWarningMessage("                        좌석을 선택하세요.");
					s1.warningFrame();
				}
			}
		});
        
	}
	
	public void makeDB(String train){
		this.train = train;
		this.train+=time;
    }
	
	public void placeSeatpanel(JPanel panel) {
		panel.removeAll();
		for(int i=0; i<myseat.length; i++) {
			myseat[i] = 0;
		}
		nowSeatnum=0;
		Search s1 = new Search();
		panel.setLayout(null);
		
		JToggleButton seat1 = new JToggleButton("1");
		seat1.setBounds(50, 40, 140, 96);
        panel.add(seat1);
        seat1.setBackground(Color.white);
        seat1.setFont(f1);
       
        seat1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(seat1.isSelected()==true) {
					if(nowSeatnum>=seatnum) {
						seat1.setSelected(false);
						s1.setWarningMessage("                    좌석 수를 초과 하였습니다.");
						s1.warningFrame();
					}else {
						nowSeatnum++;
						myseat[0] = 1;
					}
				}else {
					nowSeatnum--;
					myseat[0] = 0;
				}
			}
		});
        
        JToggleButton seat2 = new JToggleButton("2");
		seat2.setBounds(240, 40, 140, 96);
        panel.add(seat2);
        seat2.setBackground(Color.white);
        seat2.setFont(f1);
        
        seat2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(seat2.isSelected()==true) {
					if(nowSeatnum>=seatnum) {
						seat2.setSelected(false);
						s1.setWarningMessage("                    좌석 수를 초과 하였습니다.");
						s1.warningFrame();
					}else {
						nowSeatnum++;
						myseat[1] = 1;
					}
					
				}else {
					nowSeatnum--;
					myseat[1] = 0;
				}
			}
		});
        
        JToggleButton seat3 = new JToggleButton("3");
		seat3.setBounds(50, 156, 140, 96);
        panel.add(seat3);
        seat3.setBackground(Color.white);
        seat3.setFont(f1);
        
        
        seat3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(seat3.isSelected()==true) {
					if(nowSeatnum>=seatnum) {
						seat3.setSelected(false);
						s1.setWarningMessage("                    좌석 수를 초과 하였습니다.");
						s1.warningFrame();
					}else {
						nowSeatnum++;
						myseat[2] = 1;
					}
					
				}else {
					nowSeatnum--;
					myseat[2] = 0;
				}
			}
		});
        
        JToggleButton seat4 = new JToggleButton("4");
		seat4.setBounds(240, 156, 140, 96);
        panel.add(seat4);
        seat4.setBackground(Color.white);
        seat4.setFont(f1);
        
        seat4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(seat4.isSelected()==true) {
					if(nowSeatnum>=seatnum) {
						seat4.setSelected(false);
						s1.setWarningMessage("                    좌석 수를 초과 하였습니다.");
						s1.warningFrame();
					}else {
						nowSeatnum++;
						myseat[3] = 1;
					}
					
				}else {
					nowSeatnum--;
					myseat[3] = 0;
				}
			}
		});
        
        JToggleButton seat5 = new JToggleButton("5");
		seat5.setBounds(50, 272, 140, 96);
        panel.add(seat5);
        seat5.setBackground(Color.white);
        seat5.setFont(f1);
        
        seat5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(seat5.isSelected()==true) {
					if(nowSeatnum>=seatnum) {
						seat5.setSelected(false);
						s1.setWarningMessage("                    좌석 수를 초과 하였습니다.");
						s1.warningFrame();
					}else {
						nowSeatnum++;
						myseat[4] = 1;
					}
					
				}else {
					nowSeatnum--;
					myseat[4] = 0;
				}
			}
		});
        
        JToggleButton seat6 = new JToggleButton("6");
		seat6.setBounds(240, 272, 140, 96);
        panel.add(seat6);
        seat6.setBackground(Color.white);
        seat6.setFont(f1);
        
        seat6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(seat6.isSelected()==true) {
					if(nowSeatnum>=seatnum) {
						seat6.setSelected(false);
						s1.setWarningMessage("                    좌석 수를 초과 하였습니다.");
						s1.warningFrame();
					}else {
						nowSeatnum++;
						myseat[5] = 1;
					}
					
				}else {
					nowSeatnum--;
					myseat[5] = 0;
				}
			}
		});
        
        JToggleButton seat7 = new JToggleButton("7");
		seat7.setBounds(50, 388, 140, 90);
        panel.add(seat7);
        seat7.setBackground(Color.white);
        seat7.setFont(f1);
        
        seat7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(seat7.isSelected()==true) {
					if(nowSeatnum>=seatnum) {
						seat7.setSelected(false);
						s1.setWarningMessage("                    좌석 수를 초과 하였습니다.");
						s1.warningFrame();
					}else {
						nowSeatnum++;
						myseat[6] = 1;
					}
					
				}else {
					nowSeatnum--;
					myseat[6] = 0;
				}
			}
		});
        
        JToggleButton seat8 = new JToggleButton("8");
		seat8.setBounds(240, 388, 140, 96);
        panel.add(seat8);
        seat8.setBackground(Color.white);
        seat8.setFont(f1);
        
        seat8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(seat8.isSelected()==true) {
					if(nowSeatnum>=seatnum) {
						seat8.setSelected(false);
						s1.setWarningMessage("                    좌석 수를 초과 하였습니다.");
						s1.warningFrame();
					}else {
						nowSeatnum++;
						myseat[7] = 1;
					}
					
				}else {
					nowSeatnum--;
					myseat[7] = 0;
				}
			}
		});
        
        JToggleButton seat9 = new JToggleButton("9");
		seat9.setBounds(50, 504, 140, 96);
        panel.add(seat9);
        seat9.setBackground(Color.white);
        seat9.setFont(f1);
        
        seat9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(seat9.isSelected()==true) {
					if(nowSeatnum>=seatnum) {
						seat9.setSelected(false);
						s1.setWarningMessage("                    좌석 수를 초과 하였습니다.");
						s1.warningFrame();
					}else {
						nowSeatnum++;
						myseat[8] = 1;
					}
				}else {
					nowSeatnum--;
					myseat[8] = 0;
				}
			}
		});
        
        JToggleButton seat10 = new JToggleButton("10");
		seat10.setBounds(240, 504, 140, 96);
        panel.add(seat10);
        seat10.setBackground(Color.white);
        seat10.setFont(f1);
        
        seat10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(seat10.isSelected()==true) {
					if(nowSeatnum>=seatnum) {
						seat10.setSelected(false);
						s1.setWarningMessage("                    좌석 수를 초과 하였습니다.");
						s1.warningFrame();
					}else {
						nowSeatnum++;
						myseat[9] = 1;
					}
				}else {
					nowSeatnum--;
					myseat[9] = 0;
				}
			}
		});
        
        for(int i=0; i<seat.length;i++) {
        	if(seat[i]==0) {
        		switch(i)
        		{
        		case 0: seat1.setEnabled(false); seat1.setBackground(Color.LIGHT_GRAY); break;
        		case 1: seat2.setEnabled(false); seat2.setBackground(Color.LIGHT_GRAY); break;
        		case 2: seat3.setEnabled(false); seat3.setBackground(Color.LIGHT_GRAY); break;
        		case 3: seat4.setEnabled(false); seat4.setBackground(Color.LIGHT_GRAY); break;
        		case 4: seat5.setEnabled(false); seat5.setBackground(Color.LIGHT_GRAY); break;
        		case 5: seat6.setEnabled(false); seat6.setBackground(Color.LIGHT_GRAY); break;
        		case 6: seat7.setEnabled(false); seat7.setBackground(Color.LIGHT_GRAY); break;
        		case 7: seat8.setEnabled(false); seat8.setBackground(Color.LIGHT_GRAY); break;
        		case 8: seat9.setEnabled(false); seat9.setBackground(Color.LIGHT_GRAY); break;
        		case 9: seat10.setEnabled(false); seat10.setBackground(Color.LIGHT_GRAY); break;
        		}
        	}
        }
	}
	public void setMain(TrainTest main) {
        this.main = main;
    }

	public void updateDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/seatdb?characterEncoding=UTF-8&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(url, "root", "mirim");
			String sql = "select * from seat";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
					sql = "UPDATE seat SET";
					int a = 0;
					
					for(int i=0; i<myseat.length; i++) {
						if(myseat[i]==1) {
							if(a!=0) {
								sql+=",";
							}
							a++;
							switch(i)
							{
							case 0: sql+=" seat1="+0; break;
							case 1: sql+=" seat2="+0; break;
							case 2: sql+=" seat3="+0; break;
							case 3: sql+=" seat4="+0; break;
							case 4: sql+=" seat5="+0; break;
							case 5: sql+=" seat6="+0; break;
							case 6: sql+=" seat7="+0; break;
							case 7: sql+=" seat8="+0; break;
							case 8: sql+=" seat9="+0; break;
							case 9: sql+=" seat10="+0; break;
							}
						}
					}
					sql+=" WHERE train='"+getTrain()+"'";
					
			  int cnt = stmt.executeUpdate(sql);
			  
			  //DB연결 종료
			  stmt.close();
			  conn.close();
			  stmt.close();
		}catch (Exception e){
		    //에러
		     e.printStackTrace(); //오류 출력
		}
	}
		
	public void getSeatDB() throws Exception {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/seatdb?characterEncoding=UTF-8&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(url, "root", "mirim");
			Statement st = conn.createStatement();
			String sql = "select * from seat";
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				if(rs.getString("train").equals(getTrain())) {
					for(int i=0; i<seat.length; i++) {
						seat[i] = rs.getInt(i+2);
					}
				}
			}
			st.close();
		}catch (Exception e){
		    //에러
		     e.printStackTrace(); //오류 출력
		}
	}
	public void PayCheckFrame() {
		JFrame paycheckFrame = new JFrame();
		paycheckFrame.setTitle("기차표예매"); // 창 제목 설정
		paycheckFrame.setSize(700,700); // 창 크기 설정
		paycheckFrame.setResizable(false); // T- 창 크기 조절 o F- 창 크기 조절 x
		paycheckFrame.setLocation(600,150); // 창 뜨는 위치 설정
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 창 꺼지는거
		
		
		JPanel panel = new JPanel();
		placeResultFrameButton(panel);
		paycheckFrame.add(panel);
		
		paycheckFrame.setVisible(true);
	}
	public void placeResultFrameButton(JPanel panel) {
		// TODO Auto-generated method stub
		Font f1=new Font("나눔바른고딕",Font.PLAIN,18);//기본폰트
		
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		
		JLabel payEnd = new JLabel("결제가 완료 되었습니다.");//시간선택 출력
		payEnd.setBounds(200, 20, 280, 80);//시간선택 위치, 크기 수정
		panel.add(payEnd);
		payEnd.setFont(new Font("나눔바른고딕",Font.PLAIN,23));//문장 폰트 지정
		
		JLabel bringTiem = new JLabel("기차 시간: "+maketrainTime());//시간선택 출력
		bringTiem.setBounds(80, 80, 250, 80);//시간선택 위치, 크기 수정
		panel.add(bringTiem);
		bringTiem.setFont(f1);//문장 폰트 지정
		
		JLabel bringStart = new JLabel("출발지: "+ s1.getStart());//시간선택 출력
		bringStart.setBounds(80, 120, 250, 80);//시간선택 위치, 크기 수정
		panel.add(bringStart);
		bringStart.setFont(f1);//문장 폰트 지정
		
		JLabel bringArrive = new JLabel("도착지: "+s1.getArrival());//시간선택 출력
		bringArrive.setBounds(80, 160, 250, 80);//시간선택 위치, 크기 수정
		panel.add(bringArrive);
		bringArrive.setFont(f1);//문장 폰트 지정
		
		JLabel seatNumber = new JLabel("좌석 수: " +nowSeatnum);//시간선택 출력
		seatNumber.setBounds(80, 200, 600, 80);//시간선택 위치, 크기 수정
		panel.add(seatNumber);
		seatNumber.setFont(f1);//문장 폰트 지정
		
		String seatplace="";
		for(int i=0; i<myseat.length;i++) {
			if(myseat[i]!=0) {
				seatplace+= (i+1) +"번    ";
			}
		}
		
		JLabel seatXY = new JLabel("좌석 위치: "+seatplace);//시간선택 출력
		seatXY.setBounds(80, 240, 290, 80);//시간선택 위치, 크기 수정
		panel.add(seatXY);
		seatXY.setFont(f1);//문장 폰트 지정
		
		JLabel Buymoney = new JLabel("결제금액: "+getPrice()+"원");//시간선택 출력
		Buymoney.setBounds(80, 280, 250, 80);//시간선택 위치, 크기 수정
		panel.add(Buymoney);
		Buymoney.setFont(f1);//문장 폰트 지정*/
		
		JButton Ok = new JButton("완료");//6시 출력
		Ok.setBounds(480, 500, 80, 30);//6시 버튼 위치, 크기 수정
        panel.add(Ok);
        Ok.setBackground(Color.white);//벼튼 색 지정
        Ok.setFont(f1);//버튼 폰트 지정
        Ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	public String maketrainTime() {
		String result="";
		result+= s1.getMonth()+"월 "+s1.getDay()+"일 ";
		switch(time)
		{
		case "1": result +="6:00"; break;
		case "2": result +="8:00"; break;
		case "3": result +="10:00"; break;
		case "4": result +="12:00"; break;
		case "5": result +="14:00"; break;
		case "6": result +="16:00"; break;
		case "7": result +="18:00"; break;
		case "8": result +="20:00"; break;
		case "9": result +="22:00"; break;
		}
		
		return result;
	}
}