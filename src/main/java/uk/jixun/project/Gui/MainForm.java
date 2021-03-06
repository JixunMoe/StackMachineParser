package uk.jixun.project.Gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.commons.text.StringEscapeUtils;
import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.Simulator.DispatchRecord.IDispatchRecord;
import uk.jixun.project.Simulator.ISmHistory;
import uk.jixun.project.Simulator.SmSimulator;
import uk.jixun.project.SimulatorConfig.ISimulatorConfigFormValue;
import uk.jixun.project.StackMachineInstParser;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainForm extends JFrame {
  private JPanel contentPane;
  private JButton buttonCancel;
  private GraphCanvas graphCanvas1;
  private JButton btnLoad;
  private OptionsForm panelOptions;
  private JTextArea decompileCode;
  private JTabbedPane tabbedPane1;
  private JButton btnRun;
  private JFXPanel jfxPanel;
  private JLabel lbPerformance;
  private JScrollPane flowPanel;
  private ISimulatorConfigFormValue config;
  private ISmProgram program = null;
  private WebView webView;


  public MainForm() {
    $$$setupUI$$$();
    setTitle("Stack Machine Parser - Jixun");
    setContentPane(contentPane);

    Platform.runLater(() -> {
      webView = new WebView();
      jfxPanel.setScene(new Scene(webView));
    });


    pack();
    setResizable(true);
    setMinimumSize(getSize());

    buttonCancel.addActionListener(e -> onCancel());
    btnLoad.addActionListener(this::loadCode);
    btnRun.addActionListener(this::runCode);

    // call onCancel() when cross is clicked
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onCancel();
      }
    });

    panelOptions.setCallback(this::setConfig);

    // call onCancel() on ESCAPE
    contentPane.registerKeyboardAction(
      e -> onCancel(),
      KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
      JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
    );
  }

  private void setConfig(ISimulatorConfigFormValue config) {
    this.config = config;
    graphCanvas1.setConfig(config);
  }

  private void loadCode(ActionEvent e) {
    JFileChooser chooser = new JFileChooser();

    FileNameExtensionFilter asmFilter = new FileNameExtensionFilter(
      "Assembly File (*.asm; *.s)",
      "s", "asm", "txt"
    );
    FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(
      "Text File (*.txt)",
      "txt"
    );

    FileFilter allFilter = chooser.getAcceptAllFileFilter();
    chooser.setFileFilter(asmFilter);
    chooser.addChoosableFileFilter(txtFilter);
    chooser.addChoosableFileFilter(new WrapFileFilter(allFilter, "All Files (*.*)"));
    chooser.removeChoosableFileFilter(allFilter);


    int returnVal = chooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File f = chooser.getSelectedFile();
      if (!f.canRead()) {
        errorMsg("Could not read the file provided.");
        return;
      }

      beginSimulate(f);
    }
  }

  private void errorMsg(String msg) {
    JOptionPane.showMessageDialog(
      this,
      msg,
      getTitle(),
      JOptionPane.ERROR_MESSAGE
    );
  }

  private void beginSimulate(File f) {
    // f.getAbsolutePath();
    Scanner scanner;
    try {
      scanner = new Scanner(f);
    } catch (FileNotFoundException e) {
      errorMsg("Could not read the file: " + e.getMessage());
      e.printStackTrace();
      return;
    }

    decompileCode.setFont(GuiManager.getFontMono());

    StackMachineInstParser parser = new StackMachineInstParser(scanner);
    program = parser.toProgram();
    decompileCode.setText(program.decompile());
    updateHistory(null);
  }

  private void updateHistory(ISmHistory history) {
    graphCanvas1.setHistory(history);
    Platform.runLater(() -> {
      String table = FlowTable.fromHistory(history);
      webView.getEngine().loadContent(table);
    });
  }

  private void runCode(ActionEvent e) {
    if (program == null) {
      errorMsg("Code not loaded, load it first!");
      return;
    }

    SmSimulator sim = new SmSimulator();
    sim.setProgram(program);
    sim.setConfig(config);

    int zeroCount = 0;
    while (!sim.isHalt()) {
      if (sim.dispatch().size() == 0) {
        zeroCount++;

        if (zeroCount > 30) {
          errorMsg("Simulator failed to terminate, incorrect implemented simulator?");
          break;
        }
      } else {
        zeroCount = 0;
      }
    }

    if (sim.isHalt()) {
      int cycles = sim.getContext()
        .getHistory()
        .getAllRecords()
        .stream()
        .mapToInt(IDispatchRecord::getInstEndCycle)
        .max().orElse(0);
      int instructionExecuted = sim.getContext().getHistory().getAllRecords().size();
      double cpi = (double) cycles / instructionExecuted;
      lbPerformance.setText(String.format(
        "The simulation completed in %d cycles, CPI is %.3f",
        cycles, cpi
      ));

      updateHistory(sim.getContext().getHistory());
    } else {
      updateHistory(null);
      lbPerformance.setText("Performance data unavailable.");
    }
  }

  private void createUIComponents() {
    graphCanvas1 = new GraphCanvas();
    // imagePane1 = new ImagePane();
  }

  private void onOK() {
    // add your code here
    dispose();
  }

  private void onCancel() {
    // add your code here if necessary
    dispose();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer
   * >>> IMPORTANT!! <<<
   * DO NOT edit this method OR call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    createUIComponents();
    contentPane = new JPanel();
    contentPane.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
    final JSplitPane splitPane1 = new JSplitPane();
    splitPane1.setDividerSize(3);
    contentPane.add(splitPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(640, 400), null, 0, false));
    final JPanel panel1 = new JPanel();
    panel1.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
    splitPane1.setLeftComponent(panel1);
    panelOptions = new OptionsForm();
    panel1.add(panelOptions.$$$getRootComponent$$$(), new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    final Spacer spacer1 = new Spacer();
    panel2.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    final JPanel panel3 = new JPanel();
    panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel2.add(panel3, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    buttonCancel = new JButton();
    buttonCancel.setText("Exit");
    panel3.add(buttonCancel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel4 = new JPanel();
    panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel2.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    btnLoad = new JButton();
    btnLoad.setText("Load Code");
    panel4.add(btnLoad, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    btnRun = new JButton();
    btnRun.setText("Run");
    panel2.add(btnRun, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel5 = new JPanel();
    panel5.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    final JScrollPane scrollPane1 = new JScrollPane();
    panel5.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(180, -1), null, null, 0, false));
    decompileCode = new JTextArea();
    decompileCode.setEditable(false);
    scrollPane1.setViewportView(decompileCode);
    final JLabel label1 = new JLabel();
    label1.setText("Log / Data");
    panel5.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel6 = new JPanel();
    panel6.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
    splitPane1.setRightComponent(panel6);
    tabbedPane1 = new JTabbedPane();
    panel6.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    final JPanel panel7 = new JPanel();
    panel7.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    tabbedPane1.addTab("Dependency", panel7);
    final JScrollPane scrollPane2 = new JScrollPane();
    panel7.add(scrollPane2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    scrollPane2.setViewportView(graphCanvas1);
    final JPanel panel8 = new JPanel();
    panel8.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    tabbedPane1.addTab("Flow", panel8);
    jfxPanel = new JFXPanel();
    panel8.add(jfxPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    lbPerformance = new JLabel();
    lbPerformance.setText("Performance data unavailable.");
    panel6.add(lbPerformance, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return contentPane;
  }

}
