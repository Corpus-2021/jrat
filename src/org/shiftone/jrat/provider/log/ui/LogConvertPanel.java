package org.shiftone.jrat.provider.log.ui;

import org.shiftone.jrat.core.spi.RuntimeOutput;

import org.shiftone.jrat.provider.log.io.Converter;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * class
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public class LogConvertPanel extends JPanel implements ActionListener
{
    private static final Log    LOG                 = LogFactory.getLogger(LogConvertPanel.class);
    private static final String DEFAULT_DATE_FORMAT = "yyyy.MM.dd HH:mm:ss,SSS";
    private RuntimeOutput       runtimeOutput;
    private JFileChooser        chooser;
    private final JCheckBox     seqBox;
    private final JCheckBox     depthBox;
    private final JCheckBox     indentBox;
    private final JCheckBox     qualifyBox;
    private final JTextField    dateText;
    private final JTextField    fileText;
    private final JButton       convert;

    /**
     * Constructor for LogConvertPanel
     *
     * @param runtimeOutput .
     */
    public LogConvertPanel(RuntimeOutput runtimeOutput)
    {
        this.runtimeOutput     = runtimeOutput;
        this.chooser           = new JFileChooser();

        chooser.setControlButtonsAreShown(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        seqBox         = new JCheckBox("include message sequence identifier", true);
        depthBox       = new JCheckBox("include thread stack depth", true);
        indentBox      = new JCheckBox("indent thread stack depth", false);
        qualifyBox     = new JCheckBox("use fully qualified class names", false);
        dateText       = new JTextField(DEFAULT_DATE_FORMAT);
        fileText       = new JTextField(getDefaultLogName());
        convert        = new JButton("Convert " + getFileName() + " Now!");
        convert.setBackground(new Color(150, 255, 150));

        //setLayout(new GridLayout(0, 1));
        setLayout(new BorderLayout());

        add(chooser, BorderLayout.CENTER);
        add(getOptionsPanel(), BorderLayout.SOUTH);
    }

    /**
     * method
     *
     * @return .
     */
    private String getFileName()
    {
        return runtimeOutput.getInputFile().getName();
    }

    /**
     * method
     *
     * @return .
     */
    private String getDefaultLogName()
    {
        String name     = getFileName();
        int    firstDot = name.indexOf('.');

        if (firstDot > 1)
        {
            name = name.substring(0, firstDot) + ".log";
        }

        return name;
    }

    /**
     * method
     *
     * @return .
     */
    public JPanel getOptionsPanel()
    {
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(seqBox);
        panel.add(depthBox);
        panel.add(indentBox);
        panel.add(qualifyBox);
        panel.add(new JLabel("Date Format :"));
        panel.add(dateText);
        panel.add(new JLabel("Log File Name :"));
        panel.add(fileText);
        panel.add(Box.createVerticalStrut(1));
        panel.add(convert);
        convert.addActionListener(this);

        return panel;
    }

    /**
     * method
     *
     * @param e .
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == convert)
        {
        	
        	Converter converter = new Converter();
			
			converter.setDateFormat(dateText.getText());
			converter.setPrintQualifiedClasses(qualifyBox.isSelected());
			converter.setPrintThreadDepth(depthBox.isSelected());
			converter.setPrintSeqId(seqBox.isSelected());
			
			
			
            LOG.info("actionPerformed:convert");
        }
    }
}
