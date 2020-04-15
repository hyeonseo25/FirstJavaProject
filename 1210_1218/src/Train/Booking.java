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
    private String time=""; //�ð���
    private int seatnum=1; //������ �� �ִ� �¼� ��
    private int seat[] = new int[10]; //���� db�� �����ִ� �¼�
    private int myseat[] = new int[10]; //���� ������ �¼�
    private String train; //db �����׸�
    private int nowSeatnum; //���� ������ �� �ִ� �¼� ��
    private int price=25000; //���� ����
    private final int seatPrice = 25000; //�¼� 1���� ����
    Date date = new Date();
	SimpleDateFormat sdf1 = new SimpleDateFormat("MM"); //��
	SimpleDateFormat sdf2 = new SimpleDateFormat("dd"); //��
	SimpleDateFormat sdf3 = new SimpleDateFormat("HH"); //��
    
    Font f1=new Font("�����ٸ�����",Font.PLAIN,18);//�⺻��Ʈ
    
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
		
		setTitle("����ǥ����"); // â ���� ����
		setSize(800,800); // â ũ�� ����
		setResizable(false); // T- â ũ�� ���� o F- â ũ�� ���� x
		setLocation(600,150); // â �ߴ� ��ġ ����
		setDefaultCloseOperation(EXIT_ON_CLOSE); // â �����°�
		
		
		
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
		seatPanel.setBounds(280, 40, 420, 640); //��� ���
		panel.add(seatPanel);
		
		JLabel timeL = new JLabel();
		timeL.setBounds(400, 5, 500, 30);
        panel.add(timeL);
        
        JLabel timeChoice = new JLabel("�� �ð� ����");
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
					s1.setWarningMessage("���Ű� �����Ǿ����ϴ�");
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
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"�� "+s1.getMonth()+"��"+s1.getDay()+"��"+timeButton1.getText());
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
					s1.setWarningMessage("���Ű� �����Ǿ����ϴ�");
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
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"�� "+s1.getMonth()+"��"+s1.getDay()+"��"+timeButton2.getText());
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
					s1.setWarningMessage("���Ű� �����Ǿ����ϴ�");
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
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"�� "+s1.getMonth()+"��"+s1.getDay()+"��"+timeButton3.getText());
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
					s1.setWarningMessage("���Ű� �����Ǿ����ϴ�");
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
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"�� "+s1.getMonth()+"��"+s1.getDay()+"��"+timeButton4.getText());
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
					s1.setWarningMessage("���Ű� �����Ǿ����ϴ�");
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
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"�� "+s1.getMonth()+"��"+s1.getDay()+"��"+timeButton5.getText());
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
					s1.setWarningMessage("���Ű� �����Ǿ����ϴ�");
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
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"�� "+s1.getMonth()+"��"+s1.getDay()+"��"+timeButton6.getText());
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
					s1.setWarningMessage("���Ű� �����Ǿ����ϴ�");
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
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"�� "+s1.getMonth()+"��"+s1.getDay()+"��"+timeButton7.getText());
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
					s1.setWarningMessage("���Ű� �����Ǿ����ϴ�");
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
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"�� "+s1.getMonth()+"��"+s1.getDay()+"��"+timeButton8.getText());
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
					s1.setWarningMessage("���Ű� �����Ǿ����ϴ�");
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
					timeL.setText(s1.getStart()+"->"+s1.getArrival()+"�� "+s1.getMonth()+"��"+s1.getDay()+"��"+timeButton9.getText());
					placeSeatpanel(seatPanel);
					repaint();
					
				}
				
			}
		});
        
        JLabel num = new JLabel("�� �¼� �� ����");
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
        
        JLabel priceL = new JLabel("�� ����ǥ ����");
        priceL.setBounds(20, 440, 120, 30);
        panel.add(priceL);
        priceL.setFont(f1);
        
        JLabel priceL_2 = new JLabel("��� "+Integer.toString(seatPrice)+"��");
        priceL_2.setBounds(20, 470, 120, 30);
        panel.add(priceL_2);
        priceL_2.setFont(f1);
        
        plusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(seatnum>=4) {
					s1.setWarningMessage("                    �¼��� 4�¼� ���� ���ð����մϴ�.");
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
					s1.setWarningMessage("                      �ּ� �¼��� 1�¼� �����Դϴ�.");
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
        
        
		JButton before = new JButton("�� ����");
		before.setBounds(600, 700, 80, 30);
        panel.add(before);
        before.setBackground(Color.white);
        before.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				main.showSearch();
			}
		});
        
		JButton next = new JButton("���� ��");
		next.setBounds(700, 700, 80, 30);
        panel.add(next);        
        next.setBackground(Color.white);
        
        next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nowSeatnum==seatnum) {
					updateDB();
					PayCheckFrame();
				}else {
					s1.setWarningMessage("                                  �¼��� �����ϼ���.");
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
						s1.setWarningMessage("                            �¼� ���� �ʰ� �Ͽ����ϴ�.");
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
						s1.setWarningMessage("                            �¼� ���� �ʰ� �Ͽ����ϴ�.");
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
						s1.setWarningMessage("                            �¼� ���� �ʰ� �Ͽ����ϴ�.");
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
						s1.setWarningMessage("                            �¼� ���� �ʰ� �Ͽ����ϴ�.");
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
						s1.setWarningMessage("                            �¼� ���� �ʰ� �Ͽ����ϴ�.");
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
						s1.setWarningMessage("                            �¼� ���� �ʰ� �Ͽ����ϴ�.");
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
						s1.setWarningMessage("                            �¼� ���� �ʰ� �Ͽ����ϴ�.");
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
						s1.setWarningMessage("                            �¼� ���� �ʰ� �Ͽ����ϴ�.");
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
						s1.setWarningMessage("                            �¼� ���� �ʰ� �Ͽ����ϴ�.");
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
						s1.setWarningMessage("                            �¼� ���� �ʰ� �Ͽ����ϴ�.");
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
        		case 0: seat1.setEnabled(false);  seat1.setBackground(Color.LIGHT_GRAY); break;
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
			Connection conn = DriverManager.getConnection(url, "root", "1125");
			String sql = "select * from seat";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
					sql = "UPDATE seat SET";
					int a=0;
					
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
			  
			  //DB���� ����
			  stmt.close();
			  conn.close();
			  stmt.close();
		}catch (Exception e){
		    //����
		     e.printStackTrace(); //���� ���
		}
	}
	
	public void addDB() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/seatdb?characterEncoding=UTF-8&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(url, "root", "1125");
			Statement stmt = conn.createStatement();
			String month="04";
			String day="16";
			for(int k=1;k<=7;k++) {
				for (int i=1;i<=7;i++) {
					if(i==k)continue;
					for(int j=1; j<=9; j++) {
						String sql = "insert into seat (train) values ("+k+i+month+day+j+");";
						
						ResultSet rs = stmt.executeQuery(sql);
								
						 int cnt = stmt.executeUpdate(sql);
					}
				}
			}
						  
			//DB���� ����
			stmt.close();
			conn.close();
			stmt.close();
		}catch (Exception e){
		//����
		e.printStackTrace(); //���� ���
					
		}
	}
		
	public void getSeatDB() throws Exception {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/seatdb?characterEncoding=UTF-8&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(url, "root", "1125");
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
		    //����
		     e.printStackTrace(); //���� ���
		}
	}
	public void PayCheckFrame() {
		JFrame paycheckFrame = new JFrame();
		paycheckFrame.setTitle("����ǥ����"); // â ���� ����
		paycheckFrame.setSize(700,700); // â ũ�� ����
		paycheckFrame.setResizable(false); // T- â ũ�� ���� o F- â ũ�� ���� x
		paycheckFrame.setLocation(600,150); // â �ߴ� ��ġ ����
		setDefaultCloseOperation(EXIT_ON_CLOSE); // â �����°�
		
		
		JPanel panel = new JPanel();
		placeResultFrameButton(panel);
		paycheckFrame.add(panel);
		
		paycheckFrame.setVisible(true);
	}
	public void placeResultFrameButton(JPanel panel) {
		// TODO Auto-generated method stub
		Font f1=new Font("�����ٸ�����",Font.PLAIN,18);//�⺻��Ʈ
		
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		
		JLabel payEnd = new JLabel("������ �Ϸ� �Ǿ����ϴ�.");//�ð����� ���
		payEnd.setBounds(200, 20, 250, 80);//�ð����� ��ġ, ũ�� ����
		panel.add(payEnd);
		payEnd.setFont(new Font("�����ٸ�����",Font.PLAIN,23));//���� ��Ʈ ����
		
		JLabel bringTiem = new JLabel("���� �ð�: "+maketrainTime());//�ð����� ���
		bringTiem.setBounds(80, 80, 250, 80);//�ð����� ��ġ, ũ�� ����
		panel.add(bringTiem);
		bringTiem.setFont(f1);//���� ��Ʈ ����
		
		JLabel bringStart = new JLabel("�����: "+ s1.getStart());//�ð����� ���
		bringStart.setBounds(80, 120, 250, 80);//�ð����� ��ġ, ũ�� ����
		panel.add(bringStart);
		bringStart.setFont(f1);//���� ��Ʈ ����
		
		JLabel bringArrive = new JLabel("������: "+s1.getArrival());//�ð����� ���
		bringArrive.setBounds(80, 160, 250, 80);//�ð����� ��ġ, ũ�� ����
		panel.add(bringArrive);
		bringArrive.setFont(f1);//���� ��Ʈ ����
		
		JLabel seatNumber = new JLabel("�¼� ��: " +nowSeatnum);//�ð����� ���
		seatNumber.setBounds(80, 200, 600, 80);//�ð����� ��ġ, ũ�� ����
		panel.add(seatNumber);
		seatNumber.setFont(f1);//���� ��Ʈ ����
		
		String seatplace="";
		for(int i=0; i<myseat.length;i++) {
			if(myseat[i]!=0) {
				seatplace+= (i+1) +"��    ";
			}
		}
		
		JLabel seatXY = new JLabel("�¼� ��ġ: "+seatplace);//�ð����� ���
		seatXY.setBounds(80, 240, 250, 80);//�ð����� ��ġ, ũ�� ����
		panel.add(seatXY);
		seatXY.setFont(f1);//���� ��Ʈ ����
		
		JLabel Buymoney = new JLabel("�����ݾ�: "+getPrice()+"��");//�ð����� ���
		Buymoney.setBounds(80, 280, 250, 80);//�ð����� ��ġ, ũ�� ����
		panel.add(Buymoney);
		Buymoney.setFont(f1);//���� ��Ʈ ����*/
		
		JButton Ok = new JButton("�Ϸ�");//6�� ���
		Ok.setBounds(480, 500, 80, 30);//6�� ��ư ��ġ, ũ�� ����
        panel.add(Ok);
        Ok.setBackground(Color.white);//��ư �� ����
        Ok.setFont(f1);//��ư ��Ʈ ����
        Ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	public String maketrainTime() {
		String result="";
		result+= s1.getMonth()+"�� "+s1.getDay()+"�� ";
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