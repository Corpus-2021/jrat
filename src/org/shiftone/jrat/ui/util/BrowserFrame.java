package org.shiftone.jrat.ui.util;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;


/**
 * Class BrowserFrame
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.5 $
 */
public class BrowserFrame extends JFrame implements HyperlinkListener, ActionListener
{

    private static final Log LOG        = LogFactory.getLogger(BrowserFrame.class);
    private String           homePage   = null;
    private JToolBar         toolBar    = new JToolBar();
    private JEditorPane      editorPane = new JEditorPane();
    private JScrollPane      scrollPane = new JScrollPane(editorPane);
    private StatusBar        statusBar  = new StatusBar();
    private JButton          home       = new JButton("Home");
    private JButton          close      = new JButton("Close");
    private List             linkList   = new ArrayList();

    /**
     * Constructor BrowserFrame
     *
     *
     * @param homePage
     * @param title
     */
    public BrowserFrame(String homePage, String title)
    {

        this.homePage = homePage;

        //
        Container     pane = getContentPane();
        HTMLEditorKit kit  = new HTMLEditorKit();

        kit.setLinkCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        //
        editorPane.setEditorKit(kit);
        editorPane.setEditable(false);
        editorPane.addHyperlinkListener(this);
        editorPane.setBorder(new EmptyBorder(20, 10, 10, 10));
        editorPane.setBackground(new Color(0x006666));

        //
        pane.setLayout(new BorderLayout());
        pane.add(toolBar, BorderLayout.NORTH);
        pane.add(scrollPane, BorderLayout.CENTER);
        pane.add(statusBar, BorderLayout.SOUTH);

        //
        toolBar.add(home);
        toolBar.add(close);
        home.addActionListener(this);
        close.addActionListener(this);

        //
        setTitle(title);

        //
        setSize(700, 700);
        setLocation(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setHyperLink(homePage);
    }

    /**
     * Method hyperlinkUpdate
     */
    public void hyperlinkUpdate(HyperlinkEvent e)
    {

        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
        {
            setHyperLink(e.getURL());
        }
    }

    /**
     * Method setHyperLink
     */
    public void setHyperLink(String string)
    {

        try
        {
            setHyperLink(new URL(string));
        }
        catch (Exception e)
        {
            error(e);
        }
    }

    /**
     * Method setHyperLink
     */
    public void setHyperLink(URL url)
    {

        try
        {
            editorPane.setPage(url);
            statusBar.setText(url.toString());
        }
        catch (Exception e)
        {
            error(e);
        }
    }

    /**
     * Method error
     */
    private void error(Exception e)
    {

        StringWriter stringWriter = new StringWriter();
        PrintWriter  out          = new PrintWriter(stringWriter);

        out.println("<b>Unable to open Documentation</b><p>");
        e.printStackTrace(out);
        out.flush();
        editorPane.setText(stringWriter.toString());
    }

    /**
     * Method actionPerformed
     */
    public void actionPerformed(ActionEvent e)
    {

        if (e.getSource() == home)
        {
            setHyperLink(homePage);
        }
        else if (e.getSource() == close)
        {
            dispose();
        }
    }
}
