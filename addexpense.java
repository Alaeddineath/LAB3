import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

import org.hsqldb.result.ResultMetaData;

import java.awt.event.*;
import java.sql.*;

import java.util.Date;
import java.util.Calendar;
 class addexpense extends JFrame {
    private JPanel contentPane;
    private JTable table;
    private JTextField textField;
    private JTextField textField_1;
    private Connection conn = null;
    private Statement smt = null;
    
    //headers for the table
    String[] columns = new String[] {
        "ID", "Price","Category","Date"
    };
     
    //actual data for the table in a 2d array
  //  Object[][] data= new Object[][] {} ;
    Object[][] data= new Object[100][4] ;
    int i = 0;

    /**
     * Launch the application.
     * @throws SQLException 
     */
  /*  public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    addexpense frame = new addexpense();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public addexpense() throws SQLException {
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 0, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));

        setContentPane(contentPane);
        
        JMenuBar mb = new JMenuBar();
        JMenu menu1 = new JMenu("File");
        mb.add(menu1);
        JMenu menu2 = new JMenu("Edit");
        mb.add(menu2);

        JMenu menu3 = new JMenu("Help");
        mb.add(menu3);

        
        setJMenuBar(mb);
        
        contentPane.setLayout(new BorderLayout(0, 0));
        
        JPanel frame = new JPanel();
        FlowLayout flowLayout = (FlowLayout) frame.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        contentPane.add(frame, BorderLayout.NORTH);
        
        JLabel lblNewLabel_2 = new JLabel("Expense ID");
        frame.add(lblNewLabel_2);
        
        textField = new JTextField();
        frame.add(textField);
        textField.setColumns(15);
        
        JLabel lblNewLabel = new JLabel("Price");
        frame.add(lblNewLabel);
        
        textField_1 = new JTextField();
        frame.add(textField_1);
        textField_1.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Category");
        frame.add(lblNewLabel_3);
        
        // Create an array of items for the JComboBox
        String[] items = {"Food", "Transport", "Clothing", "Health Care", "Children", "Beauty","Domiciliary","Car","Leisure","Sport","Supermarket", "Other"};

        // Create a JComboBox and add the items to it
        JComboBox<String> comboBox = new JComboBox<>(items);
        
        // Set the default selected item
        comboBox.setSelectedItem(items[0]);
        
        // Add the JComboBox to the JFrame
        frame.add(comboBox);

        JButton bt_add = new JButton("Add Expense");
        frame.add(bt_add);
       bt_add.addActionListener(new ActionListener() {
          
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 String expense = textField.getText();
		            String price = textField_1.getText();
		            String category = (String) comboBox.getSelectedItem();
		            Calendar calendar = Calendar.getInstance();
		            Date date = calendar.getTime();
		            System.out.println("Current Date and Time: " + date);
		            System.out.println("his categ"+category);
		            ResultSet rs = null;
					try {
			    	    conn = DriverManager.getConnection("jdbc:hsqldb:file:db", "SA", "");

			    		smt = conn.createStatement();
			    		int j=0;
			    		
					    smt.executeUpdate("INSERT INTO EXPENSES VALUES ( " + expense+  "," +price+", 'hello' ,'"+category+"', '2023-03-11');");
					    
					    data[i][0] = expense;
					    data[i][1] = price;
					    data[i][2] = category;
					    data[i][3] = date;
					    i++;
					    table.repaint();
					    /*
					    smt.executeUpdate("INSERT INTO EXPENSES VALUES ( '" + expense+  "','" +price+"', ' ' , " + "'category'"+" , '2023-03-11');");
					    i++;
					    data[i][0] = expense;
					    data[i][1] = price;
					    data[i][2] = category;
					    table.repaint();*/
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					finally
					{
						try {
							if (rs != null) {
						          rs.close();
						        }
						        if (smt != null) {
						          smt.close();
						        }
						        if (conn != null) {
						          conn.close();
						        }
						} catch (Exception e2)
						{
							System.out.println(e2);
						}
					}
			}
            
        });
        JPanel pn_center = new JPanel();
        pn_center.setBorder(new LineBorder(new Color(0, 0, 0)));

        pn_center.setLayout(new BorderLayout(0, 0));
                
        table = new JTable(data, columns);
        pn_center.add(new JScrollPane(table),BorderLayout.CENTER);

        contentPane.add(pn_center, BorderLayout.CENTER);
        
        JPanel pn_bottom = new JPanel();
        pn_bottom.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        contentPane.add(pn_bottom, BorderLayout.SOUTH);
        pn_bottom.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        //THE SHOW TOTAL PART 
        /////////////////////
        JLabel Total = new JLabel("00.00 "+"      ");
        pn_bottom.add(Total);
        JButton St = new JButton( "Show Total");
        pn_bottom.add(St);
       
        
        // the event part 
        St.addActionListener(new ActionListener() 
        
        {
        	
        public void actionPerformed(ActionEvent e) 
        	{
  			Total.setText( ""+getTotalExpensesFromDB());
            }

            private static double getTotalExpensesFromDB() {
            double TotalDB = 0.0;

                try {
                    Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:db", "SA", "");
                    Statement stmt = conn.createStatement();

                    String sql = "SELECT SUM(AMOUNT) AS totalexpenses FROM EXPENSES" ;
                    ResultSet rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                    	TotalDB = rs.getDouble("totalexpenses");
                    }

                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return TotalDB;
            }
        
  				
  			
        }
        
        		);
       
    }

}
