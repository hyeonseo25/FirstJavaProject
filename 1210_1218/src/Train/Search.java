package Train;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Search extends JFrame {
		private TrainTest main;
		private Booking booking;
    
		private String start="����"; //�����
		private String arrival="����"; //������
		private int month=1; //��
		private int day=1; //��
		private String train=""; //db �����׸�
		private String warningMessage; //���޼���
		private boolean check=false;
		private int today_month; //���� ��
		private int today_day; //���� ��¥
		private int next_month; // ���� ��
    
		Date date = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM"); //��
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd"); //��
    
		Font f1=new Font("�����ٸ����",Font.PLAIN,18);//�⺻��Ʈ
    
		public Search() {
			
		}

		public String getStart() {
			return start;
		}

		public void setStart(String start) {
			this.start = start;
		}

		public String getArrival() {
			return arrival;
		}

		public void setArrival(String arrival) {
			this.arrival = arrival;
		}

		public int getMonth() {
			return month;
		}

		public void setMonth(int month) {
			this.month = month;
		}

		public int getDay() {
			return day;
		}

		public void setDay(int day) {
			this.day = day;
		}
		
		public String getTrain() {
			return train;
		}
		

		public String getWarningMessage() {
			return warningMessage;
		}

		public void setWarningMessage(String warningMessage) {
			this.warningMessage = warningMessage;
		}

		public void SearchFrame() {
			Frame searchframe = new Frame();
			setTitle("����ǥ����"); // â ���� ����
			setSize(500,500); // â ũ�� ����
			setResizable(false); // T- â ũ�� ���� o F- â ũ�� ���� x
			setLocation(600,150); // â �ߴ� ��ġ ����
			setDefaultCloseOperation(EXIT_ON_CLOSE); // â �����°�
			
			JPanel panel = new JPanel();
			placeButton(panel);
			add(panel);
			checkTodayDB();
			setVisible(true);
		}
		public void placeButton(JPanel panel) {
			
			today_month=Integer.parseInt(sdf1.format(date));
			today_day=Integer.parseInt(sdf2.format(date));
			if(today_month==12) {
				next_month=1;
			}else {
				next_month=Integer.parseInt(sdf1.format(date))+1;
			}
			
			
			
			//����(1),�뱸(2),����(3),�λ�(4),����(5),���(6),��õ(7)
			//��¥
			//8:00(1) 10:00(2) 12:00(3) 14:00(4) 16:00(5) 18:00(6) 20:00(7) 22:00(8) 
			//ppmdt
			String[] station = new String[] {"����","�뱸","����","�λ�","����","���","��õ"};
			panel.setLayout(null);
			panel.setBackground(Color.WHITE);//������
				
			String[] monthChoice = new String[] {"1","2","3","4","5","6","7","8","9","10","11","12"};
			panel.setLayout(null);
			
			String[] dayChoice1 = new String[] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"
					,"21","22","23","24","25","26","27","28","29","30","31"};
			
			panel.setLayout(null);
			
			JLabel startL = new JLabel("�����");
			startL.setBounds(30, 20, 70, 80);
			panel.add(startL);
			startL.setFont(f1);
			
			JComboBox startBox = new JComboBox(station);
			startBox.setBounds(100, 40, 80, 30);
			panel.add(startBox);
			startBox.setBackground(Color.white);
			startBox.setFont(f1);
			startBox.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	setStart((String)startBox.getSelectedItem());
				}
			});
			
			JLabel arrive = new JLabel("������");
			arrive.setBounds(210, 20, 70, 80);
			panel.add(arrive);
			arrive.setFont(f1);
			
			JComboBox arriveBox = new JComboBox(station);
			arriveBox.setBounds(280, 40, 80, 30);
			panel.add(arriveBox);
			arriveBox.setBackground(Color.white);
			arriveBox.setFont(f1);
			arriveBox.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	setArrival((String)arriveBox.getSelectedItem());
				}
			});
			
			JLabel date = new JLabel("��¥");
			date.setBounds(30, 80, 50, 80);
			panel.add(date);
			date.setFont(f1);
			
		    JComboBox dayBox = new JComboBox(dayChoice1);
			JComboBox monthBox = new JComboBox(monthChoice);
			monthBox.setBounds(100, 100, 80, 30);
			panel.add(monthBox);
			monthBox.setBackground(Color.white);
			monthBox.setFont(f1);
			monthBox.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	setMonth(monthBox.getSelectedIndex()+1);
			    	if(month!=today_month && month!=next_month) {
				    		warningMessage= today_month+"�� "+today_day+"�� ����"+next_month+"��"+today_day+"�� ������ ���Ű� �����մϴ�.";
							warningFrame();
				    }
				}
			});
			
			dayBox.setBounds(250, 100, 80, 30);
			panel.add(dayBox);
			dayBox.setBackground(Color.white);
			dayBox.setFont(f1);
			dayBox.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	setDay(dayBox.getSelectedIndex()+1);
			    	if((month==2 && day>28)||((month==4||month==6||month==9||month==11)&&day>30)) {
			    		warningMessage="                   �ùٸ��� ���� ��¥�Դϴ�";
						warningFrame();
			    	}else if((day>=today_day&&month==today_month)||(day<=today_day)&&month==next_month) {
			    		
			    	}else{
			    		warningMessage= today_month+"�� "+today_day+"�� ����"+next_month+"�� "+today_day+"�� ������ ���Ű� �����մϴ�.";
						warningFrame();
					}
				}
			});
			
			JLabel monthL = new JLabel("��");
			monthL.setBounds(200, 80, 50, 80);
			panel.add(monthL);
			monthL.setFont(f1);
		
			JLabel dayL = new JLabel("��");
			dayL.setBounds(350, 80, 50, 80);
			panel.add(dayL);
			dayL.setFont(f1);
			
			JButton next = new JButton("������");
			next.setBounds(350, 400, 100, 40);
			panel.add(next);
		    next.setBackground(Color.white);
		    next.setFont(f1);
		    
		    next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((day>=today_day&&month==today_month)||(day<=today_day&&month==next_month))&&(start.equals(arrival)==false)) {
					makeDB();
					main.showBooking();
				}else {
					if(start.equals(arrival)==true) {
						warningMessage = "                   ������� �������� �����մϴ�";
						warningFrame();
					}else {
						warningMessage = today_month+"�� "+today_day+"�� ����"+next_month+"��"+today_day+"�� ������ ���Ű� �����մϴ�.";
						warningFrame();
					}
				}	
			}
			});
		    
		}
		public void makeDB(){
			train="";
			
			train+=changeStationToNum(start)+changeStationToNum(arrival);
			
			if(month<10) {train+="0"+month;}
			else {train+=month;}
			
			if(day<10) {train+="0"+day;}
			else {train+=day;}
	    }  
		public String changeStationToNum(String station) {
			switch(station)
			{
			case "����" : station = "1"; break;
			case "�뱸" : station = "2"; break;
			case "����" : station = "3"; break;
			case "�λ�" : station = "4"; break;
			case "����" : station = "5"; break;
			case "���" : station = "6"; break;
			case "��õ" : station = "7"; break;
			default: break;
			}
			return station;
		}
		public void setMain(TrainTest main) {
	        this.main = main;
	    }
		public void warningFrame() {
			
			JFrame warning = new JFrame();
			warning.setTitle("���"); // â ���� ����
			warning.setSize(400,200); // â ũ�� ����
			warning.setResizable(false); // T- â ũ�� ���� o F- â ũ�� ���� x
			warning.setLocation(800,350); // â �ߴ� ��ġ ����
			
			
			
			JPanel panel = new JPanel();
			panel.setLayout(null);
			JLabel warningMessageL = new JLabel();
			warningMessageL.setText(warningMessage);
			warningMessageL.setBounds(40, 40,400, 60);
	        panel.add(warningMessageL);
	        warningMessageL.setFont(new Font("�����ٸ����", Font.PLAIN, 15));
	        
	        JButton yes = new JButton("Ȯ��");
	        yes.setBounds(160, 100, 80, 30);
	        panel.add(yes);
	        yes.setBackground(Color.white);
	        yes.setFont(f1);
	        
	        yes.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	warning.setVisible(false);
			    	check=false;
				}
			});
	        warning.add(panel);
	        warning.setVisible(true);
	        check = true;
			
		}
		
		public void checkTodayDB() {
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				String url = "jdbc:mysql://localhost/seatdb?characterEncoding=UTF-8&serverTimezone=UTC";
				Connection conn = DriverManager.getConnection(url, "root", "mirim");
				Statement stmt = conn.createStatement();
				String sql = "select month from date";
				ResultSet rs = stmt.executeQuery(sql);
				int month = 0; 
				while(rs.next())
					month = Integer.valueOf(rs.getInt(1));
				//month = 5;
				if(today_month!=month) {
					sql = "Truncate seat";
					int cnt = stmt.executeUpdate(sql);
					addDB();
				}
//				for(int k=1;k<=7;k++) {
//					for (int i=1;i<=7;i++) {
//						if(i==k)continue;
//						for(int j=1; j<=9; j++) {
//							String sql = "insert into seat (train) values ("+k+i+month+day+j+");";
//							
//							ResultSet rs = stmt.executeQuery(sql);
//									
//							int cnt = stmt.executeUpdate(sql);
//						}
//					}
//				}  
				//DB���� ����
				stmt.close();
				conn.close();
				stmt.close();
			}catch (Exception e){
			//����
			e.printStackTrace(); //���� ���
						
			}
		}
		
		public void addDB() { //db�߰�
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				String url = "jdbc:mysql://localhost/seatdb?characterEncoding=UTF-8&serverTimezone=UTC";
				Connection conn = DriverManager.getConnection(url, "root", "mirim");
				Statement stmt = conn.createStatement();
				String sqlDay=""; String sqlMonth=""; String sqlNextMonth="";
				if(today_month<10) {sqlMonth = "0"+today_month;} // �ڸ������߱�
				if(next_month<10) {sqlNextMonth = "0"+next_month;} //�ڸ������߱�
				for(int i=1; i<=31; i++) {
					if (i<10) {sqlDay = "0"+i;}// �ڸ������߱�
					else{sqlDay = Integer.toString(i);} // �ڸ������߱�
					for(int j=1;j<=7;j++) {
						for (int k=1;k<=7;k++) {
							if(j==k)continue;
							for(int l=1; l<=9; l++) {
								String sql = "insert into seat (train) values ("+j+k+sqlMonth+sqlDay+l+")";
								System.out.println(sql);
								int cnt = stmt.executeUpdate(sql);
								sql="insert into seat (train) values ("+j+k+sqlNextMonth+sqlDay+l+")";
								cnt = stmt.executeUpdate(sql);
								//System.out.println(sql);
							}
						}
					} 
				}
				String sql = "UPDATE date SET month = " + today_month;
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

}
