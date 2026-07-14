import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class notepad extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, viewMenu, helpMenu;
    private JMenuItem newItem, openItem, saveItem, exitItem;
    private JMenuItem cutItem, copyItem, pasteItem, selectAllItem;
    private JScrollPane scrollPane;
    private JFileChooser fileChooser;
    private File currentFile;
    private JMenuItem aboutItem;

    public notepad(){
        setTitle("Notepad");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        fileChooser = new JFileChooser();
        createMenuBar();

        setVisible(true);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        newItem = new JMenuItem("New");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        editMenu = new JMenu("Edit");
        cutItem = new JMenuItem("Cut");
        copyItem = new JMenuItem("Copy");
        pasteItem = new JMenuItem("Paste");
        selectAllItem = new JMenuItem("Select All");

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.addSeparator();
        editMenu.add(selectAllItem);

        viewMenu = new JMenu("View");
        helpMenu = new JMenu("Help");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        aboutItem = new JMenuItem("About Notepad");
        aboutItem.addActionListener(this);
        helpMenu.add(aboutItem);

        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        cutItem.addActionListener(this);
        copyItem.addActionListener(this);
        pasteItem.addActionListener(this);
        selectAllItem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == newItem) {
            textArea.setText("");
            setTitle("Untitled - Java Notepad");
            currentFile = null;
        } else if (source == openItem) {
            openFile();
        } else if (source == saveItem) {
            saveFile();
        } else if (source == exitItem) {
            System.exit(0);
        } 
        else if (source == cutItem) {
            textArea.cut();
        } else if (source == copyItem) {
            textArea.copy();
        } else if (source == pasteItem) {
            textArea.paste();
        } else if (source == selectAllItem) {
            textArea.selectAll();
        } 
        else if (source == aboutItem) {
            JOptionPane.showMessageDialog(this, "Java Notepad Clone\nBuilt using Swing & AWT.", "About", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void openFile() {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                textArea.read(reader, null);
                setTitle(currentFile.getName() + " - Java Notepad");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Could not open file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            int returnValue = fileChooser.showSaveDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                currentFile = fileChooser.getSelectedFile();
            } else {
                return;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
            textArea.write(writer);
            setTitle(currentFile.getName() + " - Java Notepad");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not save file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Run GUI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new notepad());
    }
}