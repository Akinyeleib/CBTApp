package work;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class ExamPage extends JFrame {

	JTextArea questionTxt;
	JRadioButton option1, option2, option3, option4;
	JPanel questionPanel, answerPanel, container;
	ButtonGroup bg;
	JButton submit;
	Connection conn;
	Statement st;
	String correctOption, userOption;
	int points, pointsGained = 0, totalPoints = 0;
	ResultSet rs = null;
	

	ArrayList<JRadioButton> buttons = new ArrayList<>();

	public ExamPage() {

		conn = Loader.loadSql();
		try {
			st = conn.createStatement();
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
		}

		questionTxt = new JTextArea("Question...");

		questionPanel = new JPanel();
		questionPanel.add(questionTxt);

		bg = new ButtonGroup();

		option1 = createRadio();
		option2 = createRadio();
		option3 = createRadio();
		option4 = createRadio();

		answerPanel = new JPanel();
		answerPanel.setLayout(new GridLayout(4, 1));
		answerPanel.add(option1);
		answerPanel.add(option2);
		answerPanel.add(option3);
		answerPanel.add(option4);

		submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (userOption.equals("")) {
					JOptionPane.showMessageDialog(null, "Select an answer");
					return;
				}
				try {
					if (userOption.equals(correctOption)) 
						pointsGained += points;
					populateQuestion(rs);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});

		container = new JPanel();
		container.setLayout(new GridLayout(3, 1));
		container.add(questionPanel);
		container.add(answerPanel);
		container.add(submit);

		add(container);

		loadQuestion();

		setVisible(true);
		setTitle("Exam Page");
		setSize(new Dimension(500, 500));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		new ExamPage();
	}

	private void loadQuestion() {
		
		pointsGained = 0;
		totalPoints = 0;

		String query = "SELECT * FROM Questions";
		try {
			rs = st.executeQuery(query);
			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "Table is empty");
				System.exit(0);
				return;
			}
				

			populateQuestion(rs);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Message: \n" + e.getMessage());
		}

	}

	
	private void populateQuestion(ResultSet rs) throws SQLException {

		if (rs.next()) {

			String question = rs.getString("Questions");
			correctOption = rs.getString("correctOption");
			String option2 = rs.getString("Option2");
			String option3 = rs.getString("Option3");
			String option4 = rs.getString("Option4");
			points = rs.getInt("points");

			totalPoints += points;
			
			questionTxt.setText(question);

			String [] options = { correctOption, option2, option3, option4 };
			ArrayList<String> optionsList = new ArrayList<>(Arrays.asList(options));

			for (JRadioButton btn : buttons) {
				Collections.shuffle(optionsList);
				String option = optionsList.get(0);
				btn.setText(option);
				optionsList.remove(0);
			}
			bg.clearSelection();
			userOption = "";

		} else {
//			String line1 = "";
			double res = (double) pointsGained / totalPoints * 100;
			DecimalFormat format = new DecimalFormat(".00");
			JOptionPane.showMessageDialog(null, "Result is: " + format.format(res) + "%");
			loadQuestion();
		}

	}

	private JRadioButton createRadio() {
		JRadioButton btn = new JRadioButton();
		buttons.add(btn);
		bg.add(btn);
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				userOption = e.getActionCommand();
			}
		});
		return btn;
	}

	public JLabel createLabel() {
		JLabel label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}

}
