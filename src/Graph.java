import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Graph extends JPanel {

    private static final int padding = 25;
    private static final int labelPadding = 25;
    private static final Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private double initX;
    private double finX;
    private double initY;
    private double finY;

    private List<ArrayList<Double>> yValues;

    public Graph(List<ArrayList<Double>> yValues, double initX, double finX, double initY, double finY) {
        this.initX = initX;
        this.initY = initY;
        this.finX = finX;
        this.finY = finY;
        this.yValues = yValues;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - 2 * padding - labelPadding) / yValues.get(1).size();
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (finY - initY);

        List<ArrayList<Point>> graphPoints = new ArrayList<>(); // --> list that contains list for each graph
        for(int j = 0; j < yValues.size(); j++) {
            ArrayList<Point> graphPointsSub = new ArrayList<>();
            for (int i = 0; i < yValues.get(j).size(); i++) {
                int x1 = (int) (i * xScale + padding + labelPadding);
                int y1 = (int) ((finY - yValues.get(j).get(i)) * yScale + padding);
                graphPointsSub.add(new Point(x1, y1));
            }
            graphPoints.add(graphPointsSub);
        }

        // draw white background and axis
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        createYAxis(g2);
        createXAxis(g2);

        //lines between points to create curves
        g2.setStroke(GRAPH_STROKE);
        for (int j = 0; j < graphPoints.size() - 1; ++j) {
            g2.setColor(new Color(230, (200-j*30), 55));
            for (int i = 0; i < graphPoints.get(j).size() - 1; ++i) {
                int x1 = graphPoints.get(j).get(i).x;
                int y1 = graphPoints.get(j).get(i).y;;
                int x2 = graphPoints.get(j).get(i).x;;
                int y2 = graphPoints.get(j).get(i).y;;
                if (y1 >= padding && y1 <= getHeight() - padding - labelPadding)
                    g2.drawLine(x1, y1, x2, y2);
            }
        }

    }

    private void createXAxis(Graphics2D g2) {
        //marks for x-axis
        int numberXDivisions = 10;
        for (int i = 0; i <= numberXDivisions; i++) {
            int x0 = i * (getWidth() - padding * 2 - labelPadding) / (numberXDivisions) + padding + labelPadding;
            int x1 = x0;
            int y0 = getHeight() - padding - labelPadding;
            int y1 = y0;
            g2.setColor(gridColor);
            g2.drawLine(x0, getHeight() - padding - labelPadding, x1, padding);
            g2.setColor(Color.BLACK);
            String xLabel = (initX + i * (finX - initX) / numberXDivisions) + "";
            FontMetrics metrics = g2.getFontMetrics();
            int labelWidth = metrics.stringWidth(xLabel);
            g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            g2.drawLine(x0, y0, x1, y1);
        }
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);
    }

    private void createYAxis(Graphics2D g2) {
        //and for y-axis
        int numberDivisions = 10;
        for (int i = 0; i <= numberDivisions; i++) {
            int x0 = padding + labelPadding;
            int x1 = padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberDivisions + padding + labelPadding);
            int y1 = y0;
            if (yValues.get(1).size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = (initY + i * (finY - initY) / numberDivisions) + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
    }
}
