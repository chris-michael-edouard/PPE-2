package PPE2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JPasswordField;
import java.awt.Color;

public class Testlogin extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	public String priviledge;
	public String myname;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Testlogin frame = new Testlogin();
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
	public Testlogin() {
		
		////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUser = new JLabel("Nom d'utilisateur");
		lblUser.setForeground(Color.BLACK);
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUser.setBounds(209, 126, 131, 16);
		contentPane.add(lblUser);
		
		JLabel lblPassword = new JLabel("Mot de passe");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassword.setBounds(209, 206, 131, 16);
		contentPane.add(lblPassword);
		
		txtUsername = new JTextField();
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(420, 203, 150, 33);
		contentPane.add(txtPassword);
		
		txtUsername.setBounds(420, 124, 150, 33);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
////////////////////Login///////////////////////////////////////
		JButton btnLogin = new JButton("Connexion");
		Image login=new ImageIcon(this.getClass().getResource("/Login1.png")).getImage();
		btnLogin.setIcon(new ImageIcon(login));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String username = txtUsername.getText();
				String password = txtPassword.getText();
				
//				System.out.println(username);
//				System.out.println(password);
				
				JLabel alertUser = new JLabel("");
				alertUser.setBounds(222, 75, 56, 16);
				contentPane.add(alertUser);
				
				JLabel alertPass = new JLabel("");
				alertPass.setBounds(222, 132, 56, 16);
				contentPane.add(alertPass);
				
				txtUsername.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent arg0) {
						alertUser.setText("");
					}
				});
				
				txtPassword.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent arg0) {
						alertPass.setText("");
					}
				});
				
				///////////////Validation//////////////////
				
                 if(username.trim().length()==0 && password.trim().length()==0) {
					
					alertUser.setText("Username Is Empty!");
					alertPass.setText("Password Is Empty!");
					JOptionPane.showMessageDialog(null,"Les deux champs sont vides, remplissez-les s'il vous plaît!");
					
				}else if(username.trim().length()==0) {
					
					alertUser.setText("Username Is Empty!");
					JOptionPane.showMessageDialog(null,"Le champ nom d'utilisateur est vide, remplissez-le avec le bon nom d'utilisateur s'il vous plaît.");
				}else if(password.trim().length()==0) {
					
					alertPass.setText("Password Is Empty!");
					JOptionPane.showMessageDialog(null,"Le champ mot de passe est vide, remplissez-le avec le bon mot de passe s'il vous plaît.");
				}else {
					
					connectdb data = new connectdb();
					
					try{
						
						Connection con = data.db_connect();
						PreparedStatement stmt = con.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
						stmt.setString(1,username);
						stmt.setString(2,password);
						ResultSet rs = stmt.executeQuery();
						
	//////////////////////////////Pour faire choix User ou administrateur//////////////////////////////////////////////					
						if (rs.next()) {
							
							String role = rs.getString(4);
							priviledge = role;
							
							String prenom = rs.getString(2);
								myname = prenom;
							
							JOptionPane.showMessageDialog(null,"Bienvenue "+ prenom);
						
							
							    dispose();
							    AppGestion welcome = new AppGestion(priviledge, myname);
							    welcome.setVisible(true);
							    
							
							}
						
						else {
							JOptionPane.showMessageDialog(null,"Nom utilisateur ou Mot de passe incorrect");
						}
						
						
						
					}catch(Exception error) {
						
						System.out.println(error);
						
					}
				}	
				
			}
		});
		btnLogin.setBounds(174, 300, 131, 33);
		contentPane.add(btnLogin);
/////////////////////Exit//////////////////////////////////////
		JButton btnExit = new JButton("Deconnexion");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		Image exit=new ImageIcon(this.getClass().getResource("/Logout1.png")).getImage();
		btnExit.setIcon(new ImageIcon(exit));
		btnExit.setBounds(420, 300, 163, 33);
		contentPane.add(btnExit);
		
		JLabel lblLogin = new JLabel("Identification de l'utilisateur");
		lblLogin.setForeground(Color.BLACK);
		lblLogin.setBackground(Color.WHITE);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblLogin.setBounds(187, 13, 257, 45);
		contentPane.add(lblLogin);
		
		JLabel lblbackground = new JLabel("");
		lblbackground.setIcon(new ImageIcon("C:\\Users\\CHRISTOBAL\\eclipse-workspace\\PPE2\\image\\back.jpeg"));
		lblbackground.setBounds(0, 0, 606, 406);
		contentPane.add(lblbackground);
	
		
		
		
		
		
////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
		
		
		
		
	}
}
