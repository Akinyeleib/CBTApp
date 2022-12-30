package work;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.Caret;

public class AddQuestion extends JFrame implements ActionListener {

	JTextField qst, correctOption, option2, option3, option4, points;
	JTextArea question;
	JPanel questionPanel, answerPanel, container;
	ButtonGroup bg;
	JButton submit;
	Statement st;
	PreparedStatement pt;
	Connection conn;
	Font font;

	public AddQuestion() {
		
		font = new Font("MV Boli", Font.PLAIN, 20);

		conn = Loader.loadSql();

		qst = MyTextField();
		qst.setText("ertyuiooiuyt");
		
		question = new JTextArea(5, 25);
		question.setBackground(qst.getBackground());
		question.setForeground(Color.white);
		question.setFont(font);
		Caret caret = qst.getCaret();
		question.setCaret(caret);
		question.setCaretColor(Color.GREEN);

		JLabel questionLabel = new JLabel("Question:");

		questionPanel = new JPanel();
		questionPanel.add(questionLabel);
		questionPanel.add(question);
//		questionPanel.add(qst);

		JLabel correctOptionLabel = new JLabel("Correct option:");
		correctOption = MyTextField();

		JLabel optionLabel1 = new JLabel("Option 1:");
		option2 = MyTextField();

		JLabel optionLabel2 = new JLabel("Option 2:");
		option3 = MyTextField();

		JLabel optionLabel3 = new JLabel("Option 3:");
		option4 = MyTextField();

		JLabel pointsLabel = new JLabel("Points:");
		points = MyTextField();

		answerPanel = new JPanel();
		answerPanel.setLayout(new GridLayout(5, 2));

		answerPanel.add(correctOptionLabel);
		answerPanel.add(correctOption);

		answerPanel.add(optionLabel1);
		answerPanel.add(option2);

		answerPanel.add(optionLabel2);
		answerPanel.add(option3);

		answerPanel.add(optionLabel3);
		answerPanel.add(option4);

		answerPanel.add(pointsLabel);
		answerPanel.add(points);

		submit = new JButton("Submit");
		submit.setFocusable(false);
		submit.addActionListener(this);

		container = new JPanel();
		container.setLayout(new GridLayout(3, 1));
		container.add(questionPanel);
		container.add(answerPanel);
		container.add(submit);

		add(container);

		setVisible(true);
		setTitle("Add Question");
		setSize(new Dimension(500, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}

	public JTextField MyTextField() {
		JTextField txtField = new JTextField();
		txtField.setBackground(Color.black);
		txtField.setForeground(Color.green);
		txtField.setCaretColor(Color.pink);
		txtField.setFont(font);
		return txtField;
	}

	public static void main(String[] args) {
		new AddQuestion();
	}

	private void insertIntoTable(String the_question, String optA, String optB, String optC, String optD,
			String the_point, String query) {
		try {
			pt = conn.prepareStatement(query);
			pt.setString(1, the_question);
			pt.setString(2, optA);
			pt.setString(3, optB);
			pt.setString(4, optC);
			pt.setString(5, optD);
			pt.setInt(6, Integer.parseInt(the_point));
			pt.execute();

//				query = "INSERT INTO Questions VALUES ('A', 'B', 'C', 'D', 'E', 3);";
//				Statement st = conn.createStatement();
//				st.execute(query);

			question.setText("");
			correctOption.setText("");
			option2.setText("");
			option3.setText("");
			option4.setText("");
			points.setText("");

			JOptionPane.showMessageDialog(null, "Added successfully...");
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String the_question = question.getText();
		String optA = correctOption.getText();
		String optB = option2.getText();
		String optC = option3.getText();
		String optD = option4.getText();
		String the_point = points.getText();

		if (the_question.isBlank() || optA.isBlank() || optB.isBlank() || optC.isBlank() || optD.isBlank()
				|| the_point.isBlank()) {
			JOptionPane.showMessageDialog(null, "All fields should be filled");
		} else if (!Pattern.matches("[1-5]", the_point)) {
			JOptionPane.showMessageDialog(null, "Only values 1 - 5 alloweds");
		} else if (optA.equals(optB) || optA.equals(optC) || optA.equals(optD) || optB.equals(optC) || optB.equals(optD)
				|| optC.equals(optD)) {
			JOptionPane.showMessageDialog(null, "Duplicate options not allowed");
		} else {

			String query = "INSERT INTO Questions VALUES (?, ?, ?, ?, ?, ?);";
			insertIntoTable(the_question, optA, optB, optC, optD, the_point, query);

		}

	}

}
