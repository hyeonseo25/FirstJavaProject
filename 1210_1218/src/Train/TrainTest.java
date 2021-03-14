package Train;

public class TrainTest {
	Booking booking;
	Search search;
	
	public static void main(String[] args){
		TrainTest main = new TrainTest();
		
		main.search = new Search(); // �α���â ���̱�
        main.search.setMain(main);
        main.search.SearchFrame();
        
        main.booking = new Booking(main.search); // �α���â ���̱�
        main.booking.setMain(main);
        main.booking.BookingFrame(main.search);
        main.booking.dispose();
	}
	
	public void showBooking() {
		this.search.setVisible(false);
        this.booking.setVisible(true); 
	}
	public void showSearch() {
		this.booking.setVisible(false);
		this.search.setVisible(true);
	}
	
	public void End() {
		System.exit(0);
	}

}