package top.eligneast;

import top.eligneast.stats.Stats;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        JFrame jf = new JFrame("Error Calculator");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setSize(800, 600);
        jf.setLocationRelativeTo(null);

        Box box = Box.createVerticalBox();
        jf.setContentPane(box);

        final JTextArea textArea = new JTextArea(10, 10);
        Box inputBox = Box.createVerticalBox();
        JScrollPane jsp = new JScrollPane(textArea);
        textArea.setPreferredSize(new Dimension(800, 100));
        textArea.setLineWrap(true);

        inputBox.setBorder(BorderFactory.createTitledBorder("输入"));
        inputBox.add(new JLabel("同组数据用逗号或空格隔开，多组数据请分行输入。"));
        //inputBox.add(new JLabel("Please separate data within the same group using commas or spaces, and input multiple groups of data on separate lines."));

        inputBox.add(jsp);
        box.add(inputBox);

        Box buttonBox = Box.createHorizontalBox();
        JButton calculateButton = new JButton("计算");
        JButton clearButton = new JButton("清除");
        JButton fillButton = new JButton("填入选中数据");

        clearButton.addActionListener(e -> textArea.setText(""));

        buttonBox.add(calculateButton);
        buttonBox.add(clearButton);
        buttonBox.add(fillButton);
        box.add(buttonBox);

        Object[] header = {"data" , "sum", "mean", "pVariance", "sVariance", "pStdDev", "sStdDev", "pStdErr", "sStdErr", "absDev", "absErr"};
        // Object[] header = {"数据" ,"和", "平均值", "测量列标准方差", "测量列实验方差", "测量列标准误差", "测量列实验标准差", "平均值的标准误差", "平均值的实验标准差", "测量列的平均绝对误差", "测量列的平均绝对误差"};
        Box tablePane = Box.createVerticalBox();
        tablePane.setBorder(BorderFactory.createTitledBorder("结果"));

        final DefaultTableModel dtm = new DefaultTableModel(header, 0);

        final JTable resultTable = new JTable(dtm);
        resultTable.setRowHeight(20);

        tablePane.add(resultTable.getTableHeader(), BorderLayout.NORTH);

        JPanel picture = new JPanel(new GridLayout(1, 11));
        picture.add(new JLabel());
        picture.add(getSymbol("sum"));
        picture.add(getSymbol("mean"));
        picture.add(getSymbol("pVariance"));
        picture.add(getSymbol("sVariance"));
        picture.add(getSymbol("pStdDev"));
        picture.add(getSymbol("sStdDev"));
        picture.add(getSymbol("pStdErr"));
        picture.add(getSymbol("sStdErr"));
        picture.add(getSymbol("absDev"));
        picture.add(getSymbol("absErr"));
        tablePane.add(picture, BorderLayout.CENTER);

        tablePane.add(new JScrollPane(resultTable), BorderLayout.SOUTH);

        calculateButton.addActionListener(actionEvent -> {
            String text = textArea.getText();
            if (!text.isBlank()){
                String[] lines = text.split("\n");
                for (String line : lines) {
                    String[] rawData = line.split("\\s|,");
                    ArrayList<Double> data = new ArrayList<>();
                    for (String rawDatum : rawData) {
                        try {
                            data.add(Double.parseDouble(rawDatum));
                        } catch (Exception ignored) {

                        }
                    }
                    if (data.size() == 0) {
                        return;
                    }
                    Stats stats = new Stats(data);
                    Object[] out = stats.statsArray();
                    Object[] out2 = new Object[out.length + 1];
                    Iterator<Double> iterator = data.iterator();
                    StringBuilder sb = new StringBuilder(Double.toString(iterator.next()));
                    while (iterator.hasNext()) {
                        sb.append(',').append(iterator.next());
                    }
                    out2[0] = sb.toString();
                    System.arraycopy(out, 0, out2, 1, out.length);
                    dtm.addRow(out2);
                }
            }
        });

        fillButton.addActionListener(actionEvent -> {
            int row = resultTable.getSelectedRow();
            int column = resultTable.getSelectedColumn();
            if (row != -1 && column != -1) {
                textArea.setText(resultTable.getValueAt(row, column).toString());
            }
        });

        box.add(tablePane);
        jf.setVisible(true);
    }

    private static JLabel getSymbol(String name) {
        String location =  "symbols/" + name + ".png";
        File file = new File(location);
        ImageIcon icon;
        try {
            icon = new ImageIcon(ImageIO.read(file).getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        } catch (IOException ignored) {
            icon = new ImageIcon();
        }
        return new JLabel(icon);
    }
}
