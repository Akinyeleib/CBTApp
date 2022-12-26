package work;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class ExamPage extends JFrame {

	JLabel question;
	JRadioButton option1, option2, option3, option4;
	JPanel questionPanel, answerPanel, container;
	ButtonGroup bg;
	JButton submit;

	ArrayList <JRadioButton> buttons = new ArrayList<>();
	
	
	public ExamPage() {

		question = new JLabel("Question...");

		questionPanel = new JPanel();
		questionPanel.add(question);

		option1 = new JRadioButton("Option 1");
		option2 = new JRadioButton("Option 2");
		option3 = new JRadioButton("Option 3");
		option4 = new JRadioButton("Option 4");

		bg = new ButtonGroup();
		bg.add(option1);
		bg.add(option2);
		bg.add(option3);
		bg.add(option4);

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
				JOptionPane.showMessageDialog(null, "Answer: " + bg.getSelection());
			}
		});

		container = new JPanel();
		container.setLayout(new GridLayout(3, 1));
		container.add(questionPanel);
		container.add(answerPanel);
		container.add(submit);

		add(container);

		setVisible(true);
		setTitle("Exam Page");
		setSize(new Dimension(700, 700));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		new ExamPage();
	}

	public void loadQuestion() {
		String question = "What is the capital of Lagos?";
		String optA = "Akure", optB = "Lekki", optC = "Banana Island", optD = "Ikeja";
		String [] options = {optA, optB, optC, optD};
		
		this.question.setText(question);
		ArrayList<String> optionsList = new ArrayList<>(Arrays.asList(options));
		Collections.shuffle(buttons);
		Collections.shuffle(optionsList);
		
		buttons.get(0).setText(optionsList.get(0));
		
		
	}
	
	public JRadioButton createRadio() {
		JRadioButton btn = new JRadioButton();
		buttons.add(btn);
		bg.add(btn);
		return btn;
	}
	
	public JLabel createLabel() {
		JLabel label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}

}