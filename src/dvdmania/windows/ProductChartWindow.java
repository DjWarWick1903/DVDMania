package dvdmania.windows;

import dvdmania.products.OrderManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.util.HashMap;

public final class ProductChartWindow extends JFrame {

    private static ProductChartWindow instance = null;
    private static final DefaultPieDataset date = new DefaultPieDataset();

    private ProductChartWindow() {
        super("Tipuri produse");
        this.setContentPane(initializareGrafic());
        this.setSize(500, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    public static ProductChartWindow getInstance() {
        if (instance == null) {
            instance = new ProductChartWindow();
        }

        return instance;
    }

    public ChartPanel initializareGrafic() {
        final JFreeChart chart = ChartFactory.createPieChart("Procentaj tipuri produse imprumutate", date, true, true, false);

        final OrderManager ordMan = OrderManager.getInstance();
        final HashMap<String, Integer> prods = ordMan.getProductOrderTimeline();

        //date reale
        date.setValue("Filme", prods.get("Filme"));
        date.setValue("Jocuri", prods.get("Jocuri"));
        date.setValue("Albume", prods.get("Albume"));

        //date test
//        date.setValue("Filme", 100);
//        date.setValue("Jocuri", 570);
//        date.setValue("Albume", 300);
        return new ChartPanel(chart);
    }
}
