package dvdmania.windows;

import dvdmania.products.OrderManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.HashMap;
import java.util.Set;

public final class SaleChartWindow extends JFrame {

    public SaleChartWindow() {
        super("Order chart");
        final JFreeChart lineChart = ChartFactory.createLineChart(
                "Imprumuturi pe ani",
                "Ani", "Numar imprumuturi",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        final ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));

        this.setContentPane(chartPanel);
        this.setSize(500, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private DefaultCategoryDataset createDataset() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        final OrderManager ordMan = OrderManager.getInstance();
        final HashMap<String, Integer> orders = ordMan.getOrderTimeline();

        //date existente
        final Set<String> keys = orders.keySet();
        for (String key : keys) {
            dataset.addValue(orders.get(key), "Imprumuturi", key);
        }

        //date de test
//        dataset.addValue(100, "Imprumuturi", "2015");
//        dataset.addValue(500, "Imprumuturi", "2016");
//        dataset.addValue(450, "Imprumuturi", "2017");
//        dataset.addValue(300, "Imprumuturi", "2018");
//        dataset.addValue(1000, "Imprumuturi", "2019");
//        dataset.addValue(1200, "Imprumuturi", "2020");
        return dataset;
    }
}
