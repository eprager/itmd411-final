/**
 * @author Emma Prager
 * @date 05 May 2019
 * @title Final Project
 * @file Login.java
 */

import java.awt.Color;
import java.awt.GridLayout; //useful for layouts
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JButton; //controls-label text fields, button
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Login GUI authenticates both admin and regular users in the system.
 * USER LOGIN: joe, user
 * ADMIN LOGIN: admin, admin1
*/

public class Login extends JFrame {

	Dao conn = new Dao();

	public Login() {

		super("IIT HELP DESK LOGIN"); //title
		conn = new Dao();
		setSize(400, 210);
		setLayout(new GridLayout(4, 2));
		setLocationRelativeTo(null); // centers window
		
		//set background color
		getContentPane().setBackground(Color.red);
		
		//create border
		((JComponent) getContentPane()).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white), BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder())));
		
		JLabel title = new JLabel("IIT HELP ", JLabel.RIGHT);
		JLabel title1 = new JLabel("DESK LOGIN", JLabel.LEFT);
		//set text color
		title.setForeground(Color.black);
		title1.setForeground(Color.black);
		
		// SET UP CONTROLS
		JLabel lblUsername = new JLabel("Username", JLabel.LEFT);
		JLabel lblPassword = new JLabel("Password", JLabel.LEFT);
		JLabel lblStatus = new JLabel(" ", JLabel.CENTER);
		// JLabel lblSpacer = new JLabel(" ", JLabel.CENTER);
		
		//set text color
		lblUsername.setForeground(Color.white);
		lblPassword.setForeground(Color.white);
		
		//space for username and password	
		JTextField txtUname = new JTextField(10);
		JPasswordField txtPassword = new JPasswordField();
		
		//color for text boxes
		txtUname.setBackground(Color.white);
		txtPassword.setBackground(Color.white);
		txtUname.setForeground(Color.black);
		txtPassword.setForeground(Color.black);
		
		//button to login and exit
		JButton btnExit = new JButton("Exit");
		JButton btn = new JButton("Log In");
		
		//make exit button blend in so login is the focus
		btnExit.setBackground(Color.red);
		btn.setBackground(Color.black);
		btnExit.setForeground(Color.white);
		btn.setForeground(Color.white);

		// constraints
		lblStatus.setToolTipText("Contact help desk to unlock password");
		lblUsername.setHorizontalAlignment(JLabel.CENTER);
		lblPassword.setHorizontalAlignment(JLabel.CENTER);
 
		// ADD OBJECTS TO FRAME
		add(title);
		add(title1);
		add(lblUsername);// 1st row filler
		add(txtUname);
		add(lblPassword); // 2nd row
		add(txtPassword);// 3rd row
		add(btnExit);
		add(btn); 
		
		//listen for button to be clicked
		btn.addActionListener(new ActionListener() {
			int count = 0; // count agent
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Login button clicked.");
				boolean adminFlag = false;
				int admin = 0;
				count = count + 1;
				// verify credentials of user (MAKE SURE TO CHANGE YOUR TABLE NAME BELOW)

				String query = "SELECT * FROM eprag_users WHERE uname = ? and upass = ?;";
				try (PreparedStatement stmt = conn.connect().prepareStatement(query)) {
					stmt.setString(1, txtUname.getText());
					stmt.setString(2, txtPassword.getText());
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						//admin = rs.getInt("admin"); // get table column value
						int isAdmin = rs.getInt("admin");
						if (isAdmin == 1) 
						{
							adminFlag = true;
							System.out.println("Login of admin success.");
						} 
						else {
							System.out.println("Login of user success.");
						}
						new Tickets(adminFlag);
						setVisible(false); // HIDE THE FRAME
						dispose(); // CLOSE OUT THE WINDOW
					} else {
						lblStatus.setText("Try again! " + (3 - count) + " / 3 attempts left");
						System.out.println("Try again! " + (3 - count) + " / 3 attempts left");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
 			 
			}
		});
		btnExit.addActionListener(e -> System.exit(0));

		setVisible(true); // SHOW THE FRAME
	}

	public static void main(String[] args) {

		new Login();
		
		// code for time stamp 
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		System.out.println("\nTimestamp=" + timeStamp + " Emma Prager\n");
	}
}