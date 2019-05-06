/**
 * @author Emma Prager
 * @date 05 May 2019
 * @title Final Project
 * @file Tickets.java
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

/**
 * Use to open, view, delete, and edit the tickets in the system. 
*/

public class Tickets extends JFrame implements ActionListener {

	// class level member objects
	Dao dao = new Dao(); // for CRUD operations
	Boolean chkIfAdmin = false;

	public Tickets(boolean isAdmin) {
		if (isAdmin != chkIfAdmin) {
			System.out.println("Admin approved");
			// show the tables at startup
			try {

				// Use JTable built in functionality to build a table model and
				// display the table model off your result set!!!
				JTable jt = new JTable(ticketsJTable.buildTableModel(dao.readRecords()));
					jt.setBounds(30, 40, 200, 400);
					jt.setBackground(Color.red);
			        jt.setForeground(Color.white);
			        jt.getTableHeader().setBackground(Color.BLACK);
			        jt.getTableHeader().setForeground(Color.white);
					JScrollPane sp = new JScrollPane(jt);
					add(sp);
					setVisible(true); // refreshes or repaints frame on screen
					System.out.println("Ticket view sucessfully created.");
					chkIfAdmin = true;
			} catch (SQLException e1) {
				System.out.println("Ticket view failed.");
				e1.printStackTrace();
			}
		}
		
		createMenu();
		prepareGUI();
		
	}
	
	// Main menu object items
	private JMenu mnuFile = new JMenu("File");
	private JMenu mnuAdmin = new JMenu("Admin");
	private JMenu mnuTickets = new JMenu("Tickets");

	// Sub menu item objects for all Main menu item objects
	JMenuItem mnuItemExit;
	JMenuItem mnuItemRefresh;
	JMenuItem mnuItemUpdate;
	JMenuItem mnuItemDelete;
	JMenuItem mnuItemOpenTicket;
	JMenuItem mnuItemViewTicket;
	JMenuItem mnuItemSelectTicket;

	private void createMenu() {

		/* Initialize sub menu items **************************************/

		// initialize sub menu item for File main menu
		mnuItemExit = new JMenuItem("Exit");
		// add to File main menu item
		mnuFile.add(mnuItemExit);
		
		// initialize sub menu item for File main menu
		mnuItemRefresh = new JMenuItem("Refresh");
		// add to File main menu item
		mnuFile.add(mnuItemRefresh);
		
		if (chkIfAdmin == true) {
			// initialize first sub menu items for Admin main menu
			mnuItemUpdate = new JMenuItem("Update Ticket");
			// add to Admin main menu item
			mnuAdmin.add(mnuItemUpdate);
		
			// initialize second sub menu items for Admin main menu
			mnuItemDelete = new JMenuItem("Delete Ticket");
			// add to Admin main menu item
			mnuAdmin.add(mnuItemDelete);
		}

		// initialize first sub menu item for Tickets main menu
		mnuItemOpenTicket = new JMenuItem("Open Ticket");
		// add to Ticket Main menu item
		mnuTickets.add(mnuItemOpenTicket);

		// initialize second sub menu item for Tickets main menu
		mnuItemViewTicket = new JMenuItem("View Ticket");
		// add to Ticket Main menu item
		mnuTickets.add(mnuItemViewTicket);
		
		// initialize second sub menu item for Tickets main menu
		mnuItemSelectTicket = new JMenuItem("Select Ticket");
		// add to Ticket Main menu item
		mnuTickets.add(mnuItemSelectTicket);

		/* Add action listeners for each desired menu item *************/
		mnuItemExit.addActionListener(this);
		mnuItemRefresh.addActionListener(this);
		if (chkIfAdmin == true) { //on show these on admin
			mnuItemUpdate.addActionListener(this);
			mnuItemDelete.addActionListener(this);
		}
		mnuItemOpenTicket.addActionListener(this);
		mnuItemViewTicket.addActionListener(this);
		mnuItemSelectTicket.addActionListener(this);
	}

	private void prepareGUI() {

		// create jmenu bar
		JMenuBar bar = new JMenuBar();
		bar.add(mnuFile); // add main menu items in order, to JMenuBar
		if (chkIfAdmin == true) { //only show this to admin
			bar.add(mnuAdmin);
		}
		bar.add(mnuTickets);
		// add menu bar components to frame
		setJMenuBar(bar);

		addWindowListener(new WindowAdapter() {
		// define a window close operation
		public void windowClosing(WindowEvent wE) {
		    System.exit(0);
		}
		});
		// set frame options
		setSize(400, 400);
		getContentPane().setBackground(Color.RED);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		UIManager.put("OptionPane.background", (Color.red)); 
		UIManager.put("Panel.background", (Color.red));
		UIManager.put("OptionPane.foreground", (Color.white)); 
		UIManager.put("Panel.foreground", (Color.white));
		
		// implement actions for sub menu items
		if (e.getSource() == mnuItemExit) {
			System.out.println("Ticket system sucessfully exited.");
			System.exit(0);
		} else if (e.getSource() == mnuItemOpenTicket) {

		// get ticket information
		//UIManager.put("
		
		//ImageIcon icon = new ImageIcon("icon.jpg");
		String ticketName = JOptionPane.showInputDialog(null, "Enter your name");
		String ticketDesc = JOptionPane.showInputDialog(null, "Enter a ticket description");
		
		if(ticketName == null || (ticketName != null && ("".equals(ticketName))) || ticketDesc == null || (ticketDesc != null && ("".equals(ticketDesc))))   
		{
			JOptionPane.showMessageDialog(null, "Ticket creation failed: empty name / description.");
			System.out.println("Ticket creation failed: empty name / description.");
		}
		else {
			// insert ticket information to database
			
			int id = dao.insertRecords(ticketName, ticketDesc);
	
			// display results if successful or not to console / dialog box
			if (id != 0) {
			System.out.println("Ticket ID : " + id + " created successfully.");
			JOptionPane.showMessageDialog(null, "Ticket id: " + id + " created");
			
			try {

				// Use JTable built in functionality to build a table model and
				// display the table model off your result set!!!
				JTable jt = new JTable(ticketsJTable.buildTableModel(dao.readRecords()));
					jt.setBounds(30, 40, 200, 400);
					jt.setBackground(Color.red);
			        jt.setForeground(Color.white);
			        jt.getTableHeader().setBackground(Color.BLACK);
			        jt.getTableHeader().setForeground(Color.white);
					JScrollPane sp = new JScrollPane(jt);
					add(sp);
					setVisible(true); // refreshes or repaints frame on screen
					System.out.println("Ticket view sucessfully created.");
				} catch (SQLException e1) {
					System.out.println("Ticket view failed.");
					e1.printStackTrace();
				}
			} else
				System.out.println("Ticket creation failed.");
			}
		}

		else if (e.getSource() == mnuItemViewTicket || e.getSource() == mnuItemRefresh) {

		// retrieve all tickets details for viewing in JTable
		try {

			// Use JTable built in functionality to build a table model and
			// display the table model off your result set!!!
			JTable jt = new JTable(ticketsJTable.buildTableModel(dao.readRecords()));
				jt.setBounds(30, 40, 200, 400);
				jt.setBackground(Color.red);
		        jt.setForeground(Color.white);
		        jt.getTableHeader().setBackground(Color.BLACK);
		        jt.getTableHeader().setForeground(Color.white);
				JScrollPane sp = new JScrollPane(jt);
				add(sp);
				setVisible(true); // refreshes or repaints frame on screen
				System.out.println("Ticket view sucessfully created.");
			} catch (SQLException e1) {
				System.out.println("Ticket view failed.");
				e1.printStackTrace();
			}
		} 
		
		//select specific ticket to view
		else if (e.getSource() == mnuItemSelectTicket) {
			
			String ticketId = JOptionPane.showInputDialog(null, "Enter the ticket ID");
			
			if(ticketId == null || (ticketId != null && ("".equals(ticketId))))   
			{
				JOptionPane.showMessageDialog(null, "Ticket view failed: empty id.");
				System.out.println("Ticket view failed: empty id.");
			}
			else {
				// retrieve tickets details for viewing in JTable
				int tid = Integer.parseInt(ticketId);
				try {
	
					// Use JTable built in functionality to build a table model and
					// display the table model off your result set!!!
					JTable jt = new JTable(ticketsJTable.buildTableModel(dao.selectRecords(tid)));
						jt.setBounds(30, 40, 200, 400);
						jt.setBackground(Color.red);
				        jt.setForeground(Color.white);
				        jt.getTableHeader().setBackground(Color.BLACK);
				        jt.getTableHeader().setForeground(Color.white);
						JScrollPane sp = new JScrollPane(jt);
						add(sp);
						setVisible(true); // refreshes or repaints frame on screen
						System.out.println("Ticket view sucessfully created.");
					} catch (SQLException e1) {
						System.out.println("Ticket view failed.");
						e1.printStackTrace();
					}
				}
			} 
		
		/*
		 * continue implementing any other desired sub menu items 
		 * (like for update and delete sub menus for example) with similar  
		 * syntax & logic as shown above
		 * // Sub menu item objects for all Main menu item objects
			
			JMenuItem mnuItemUpdate;
			JMenuItem mnuItemDelete;
		 */
		
		
		
		else if (e.getSource() == mnuItemUpdate) 
		{
			// get ticket information
			String ticketId = JOptionPane.showInputDialog(null, "Please enter id of ticket to update");
			String ticketDesc = JOptionPane.showInputDialog(null, "Append to the ticket description");
			String ticketStatus = JOptionPane.showInputDialog(null, "Update the ticket status");
			
			if(ticketId == null || (ticketId != null && ("".equals(ticketId))) || ticketStatus == null || (ticketStatus != null && ("".equals(ticketStatus))))   
			{
				JOptionPane.showMessageDialog(null, "Ticket update failed: empty id / status.");
				System.out.println("Ticket update failed: empty id / status.");
			}
			else {
				// insert ticket information to database
				int tid = Integer.parseInt(ticketId);
				
				//go to dao to update records
				dao.updateRecords(ticketId, ticketDesc, ticketStatus);
		
				// display results if successful or not to console / dialog box
				if (tid != 0) {
				System.out.println("Ticket ID : " + tid + " updated successfully.");
				JOptionPane.showMessageDialog(null, "Ticket id: " + tid + " updated");
				} else
					System.out.println("Ticket update failed.");
				
				try {

					// Use JTable built in functionality to build a table model and
					// display the table model off your result set!!!
					JTable jt = new JTable(ticketsJTable.buildTableModel(dao.selectRecords(tid)));
						jt.setBounds(30, 40, 200, 400);
						jt.setBackground(Color.red);
				        jt.setForeground(Color.white);
				        jt.getTableHeader().setBackground(Color.BLACK);
				        jt.getTableHeader().setForeground(Color.white);
						JScrollPane sp = new JScrollPane(jt);
						add(sp);
						setVisible(true); // refreshes or repaints frame on screen
						System.out.println("Ticket view sucessfully created.");
					} catch (SQLException e1) {
						System.out.println("Ticket view failed.");
						e1.printStackTrace();
					}
				
			}
		}
		
		else if (e.getSource() == mnuItemDelete) 
		{
			// get ticket information
			String ticketId = JOptionPane.showInputDialog(null, "Enter the ticket id to delete");
			
			if(ticketId == null || (ticketId != null && ("".equals(ticketId))))   
			{
				JOptionPane.showMessageDialog(null, "Ticket deletion failed: empty tid.");
				System.out.println("Ticket deletion failed: empty tid.");
			}
			else {
				// check ticket information to database
				int tid = Integer.parseInt(ticketId);
				
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete ticket " + tid + "?", "Warning!", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	int id = dao.deleteRecords(tid);
		    		
					// display results if successful or not to console / dialog box
					if (id != 0) {
					System.out.println("Ticket ID : " + id + " deleted successfully.");
					JOptionPane.showMessageDialog(null, "Ticket id: " + id + " deleted");
					} else
						System.out.println("Ticket cannot be deleted!!!");
					}
		        else {
		           JOptionPane.showMessageDialog(null, "Ticket " + tid + " was not deleted.");
		        }
			}
		}
	}
}
