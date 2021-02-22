package org.shiftone.jrat.ui;

import org.shiftone.jrat.ui.action.ExitAction;
import org.shiftone.jrat.ui.action.RemoveAllTabsAction;
import org.shiftone.jrat.ui.action.RemoveCurrentTabAction;
import org.shiftone.jrat.ui.action.ResizeAction;
import org.shiftone.jrat.ui.action.SpawnFrameAction;

import org.shiftone.jrat.ui.help.AboutAction;
import org.shiftone.jrat.ui.help.ShowDocsAction;

import org.shiftone.jrat.ui.inject.InjectDirAction;
import org.shiftone.jrat.ui.inject.InjectJarAction;
import org.shiftone.jrat.ui.inject.InjectionManager;

import org.shiftone.jrat.ui.util.StatusBar;

import org.shiftone.jrat.ui.viewer.OpenOutputFileAction;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;


/**
 * Class DesktopFrame
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.11 $
 */
public class DesktopFrame extends JFrame implements UIConstants, ContainerListener {

    private static final Log LOG              = LogFactory.getLogger(DesktopFrame.class);
    private InjectionManager injectionManager = new InjectionManager(this);
    private JMenuBar         menuBar          = new JMenuBar();
    private JMenu            fileMenu         = new JMenu(MENU_FILE);
    private JMenu            windowMenu       = new JMenu(MENU_WINDOW);
    private JMenu            injectMenu       = new JMenu(MENU_INSTRUMENT);
    private JMenu            helpMenu         = new JMenu(MENU_HELP);
    private StatusBar        statusBar        = new StatusBar();
    private JTabbedPane      tabbedPane       = new JTabbedPane();
    private JMenuItem        open             = new JMenuItem(MENU_OPEN);
    private JMenuItem        close            = new JMenuItem(MENU_CLOSE);
    private JMenuItem        closeAll         = new JMenuItem(MENU_CLOSE_ALL);
    private JMenuItem        spawn            = new JMenuItem(MENU_SPAWN);
    private JMenuItem        exit             = new JMenuItem(MENU_EXIT);
    private JMenuItem        injectFile       = new JMenuItem(MENU_INJECT_JAR);
    private JMenuItem        injectDir        = new JMenuItem(MENU_INJECT_DIR);
    private JMenuItem        docs             = new JMenuItem(MENU_DOCS);
    private JMenuItem        about            = new JMenuItem(MENU_ABOUT);
    private JMenuItem        resize640x480    = new JMenuItem(MENU_640X480);
    private JMenuItem        resize800x600    = new JMenuItem(MENU_800x600);

    /**
     * Constructor DesktopFrame
     */
    public DesktopFrame() {

        Container pane = getContentPane();

        pane.setLayout(new BorderLayout());
        pane.add(statusBar, BorderLayout.SOUTH);
        pane.add(tabbedPane, BorderLayout.CENTER);
        addMenuBar();
        setDimension();
        setTitle(UI_TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    /**
     * Method addMenuBar
     */
    public void addMenuBar() {

        fileMenu.add(open);
        fileMenu.add(close);
        fileMenu.add(closeAll);
        fileMenu.addSeparator();
        fileMenu.add(exit);
        fileMenu.setMnemonic('F');
        open.setMnemonic('O');
        exit.setMnemonic('X');

        //
        windowMenu.add(spawn);
        windowMenu.add(resize640x480);
        windowMenu.add(resize800x600);
        windowMenu.setMnemonic('W');

        //
        injectMenu.add(injectFile);
        injectMenu.add(injectDir);
        injectMenu.setMnemonic(KeyEvent.VK_I);

        //
        helpMenu.add(docs);
        helpMenu.add(about);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        about.setMnemonic(KeyEvent.VK_A);
        docs.setMnemonic(KeyEvent.VK_H);

        //
        open.addActionListener(new OpenOutputFileAction(this, tabbedPane, statusBar.getProgressBar()));
        close.addActionListener(new RemoveCurrentTabAction(tabbedPane));
        closeAll.addActionListener(new RemoveAllTabsAction(tabbedPane));
        spawn.addActionListener(new SpawnFrameAction(tabbedPane));
        exit.addActionListener(new ExitAction());
        injectFile.addActionListener(new InjectJarAction(injectionManager, this));
        injectDir.addActionListener(new InjectDirAction(injectionManager, this));
        docs.addActionListener(new ShowDocsAction());
        about.addActionListener(new AboutAction(this));
        resize640x480.addActionListener(new ResizeAction(this, 640, 480));
        resize800x600.addActionListener(new ResizeAction(this, 800, 600));
        menuBar.add(fileMenu);
        menuBar.add(windowMenu);
        menuBar.add(injectMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        //
        tabbedPane.addContainerListener(this);
        tabbedCountChanged(0);

    }

    /**
     * Method componentAdded
     *
     * @param e .
     */
    public void componentAdded(ContainerEvent e) {

        tabbedCountChanged(tabbedPane.getTabCount());

    }

    /**
     * Method componentRemoved
     *
     * @param e .
     */
    public void componentRemoved(ContainerEvent e) {

        tabbedCountChanged(tabbedPane.getTabCount() - 1);

    }

    /**
     * Method tabbedPaneChanged
     *
     * @param tabCount .
     */
    private void tabbedCountChanged(int tabCount) {

        boolean hasTabs = (tabCount > 0);

        close.setEnabled(hasTabs);
        closeAll.setEnabled(hasTabs);
        spawn.setEnabled(hasTabs);

    }

    /**
     * Method setDimension
     */
    private void setDimension() {

        Toolkit   tk         = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int       width      = (int) (screenSize.getWidth() * FRAME_WIDTH_PCT);
        int       height     = (int) (screenSize.getHeight() * FRAME_HEIGHT_PCT);
        int       x          = (int) ((screenSize.getWidth() / 2) - (width / 2));
        int       y          = (int) ((screenSize.getHeight() / 2) - (height / 2));

        setLocation(x, y);
        setSize(width, height);

    }

    /**
     * Method setEnabled
     *
     * @param isEnable .
     */
    public void setEnabled(boolean isEnable) {

        if (isEnable) {

            menuBar.setEnabled(true);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } else {

            menuBar.setEnabled(false);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        }

        // super.setEnabled(enable);
    }

}
