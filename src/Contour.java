import org.matheclipse.core.eval.ExprEvaluator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Contour extends JFrame implements ActionListener {
    private JLabel contourlbl, pointLbl;
    private JLabel firstX, lastX, firstY, lastY, firstStep, finalStep;
    private JTextField equationtxt, pointTxt;
    private JTextField minX, maxX, minY, maxY, firstStepTxt, finalStepTxt;
    private JButton calculate;
    double initX, initY, finX, finY, initC, finC, stepSizes;
    String equation, point;

    private static ExprEvaluator eval = new ExprEvaluator(false, 100);

    //window for f(x,y) function inputs
    public Contour() {
        Panel panel1 = new Panel(new FlowLayout());
        contourlbl = new JLabel("Enter the function: z = f(x,y) =  ");
        panel1.add(contourlbl);
        equationtxt = new JTextField("", 23);
        panel1.add(equationtxt);


        Panel panel2 = new Panel(new FlowLayout());
        firstX = new JLabel("Minimum x = ");
        panel2.add(firstX);
        minX = new JTextField("", 7);
        panel2.add(minX);
        lastX = new JLabel("Maximum x = ");
        panel2.add(lastX);
        maxX = new JTextField("", 7);
        panel2.add(maxX);


        Panel panel3 = new Panel(new FlowLayout());
        firstY = new JLabel("Minimum y = ");
        panel3.add(firstY);
        minY = new JTextField("", 7);
        panel3.add(minY);
        lastY = new JLabel("Maximum y = ");
        panel3.add(lastY);
        maxY = new JTextField("", 7);
        panel3.add(maxY);

        Panel panel4 = new Panel(new FlowLayout());
        firstStep = new JLabel("Initial c = ");
        panel4.add(firstStep);
        firstStepTxt = new JTextField("", 7);
        panel4.add(firstStepTxt);
        finalStep = new JLabel("Final c = ");
        panel4.add(finalStep);
        finalStepTxt = new JTextField("", 7);
        panel4.add(finalStepTxt);

        Panel panel5 = new Panel(new FlowLayout());
        pointLbl = new JLabel("(Optional) Starting point as (x,y) coordinate: ");
        panel5.add(pointLbl);
        pointTxt = new JTextField("", 8);
        panel5.add(pointTxt);

        Panel panel6 = new Panel(new FlowLayout());
        calculate = new JButton("Graph");
        panel6.add(calculate);
        calculate.addActionListener(this);

        setLayout(new FlowLayout());
        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);
        add(panel5);
        add(panel6);

        setTitle("Contour Graphing");
        setSize(600, 350);
        setVisible(true);

    }

    public static void main(String[] args) {
        JFrame f = new Contour();
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        initX = Double.parseDouble(minX.getText());
        initY = Double.parseDouble(minY.getText());
        finX = Double.parseDouble(maxX.getText());
        finY = Double.parseDouble(maxY.getText());
        initC = Double.parseDouble(firstStepTxt.getText());
        finC = Double.parseDouble(finalStepTxt.getText());
        equation = equationtxt.getText();
        point = pointTxt.getText();

        JFrame frame = new JFrame("DrawGraph");

        List<ArrayList<Double>> yVals = new ArrayList<>();  //  ---> list contains lists of y values (each is its own contour)
        stepSizes = (finC-initC)/6.0;
        for (double i = initC; i <= finC; i += stepSizes) {
            ArrayList<Double> yVal = new ArrayList<>();
            String equation2 = "Solve(" + equation + "==" + i + ",y)";
            for (double x = initX; x <= finX; x += 0.01) {

                String res = eval.eval(equation2.replace("x", x + "")).toString();
                yVal.add(Double.parseDouble(res.substring(5, res.indexOf("}"))));
            }
            yVals.add(yVal);
        }

        Graph mainPanel = new Graph(yVals, initX, finX, initY, finY);
        mainPanel.setPreferredSize(new Dimension(800, 600));
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
