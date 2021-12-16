package com.glg.study.gui;

import com.glg.study.bio.udp.UDPClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author glg
 * @version 1.0
 * @date 2021/12/16 11:22
 */
public class ClientUI extends JFrame {

    private JPanel contentPanel;

    private JTextField ipField = new JTextField();

    private JTextField portField = new JTextField();

    private JButton connect = new JButton("连接");

    private JTextArea info = new JTextArea();

    private JTextField msg = new JTextField();

    private JButton send = new JButton("发送");

    private JButton exit = new JButton("退出");

    private UDPClient udpClient;

    public ClientUI() throws HeadlessException {

        this.setSize(800,450);
        // 把窗口位置设置到屏幕中心
        this.setLocationRelativeTo(null);
        // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 2. 创建中间容器（面板容器）,使用默认的布局管理器
        contentPanel = getContentPanel();
        this.addBtnAction();
        // 4. 把面板容器作为窗口的内容面板设置到窗口
        this.setContentPane(contentPanel);
        // 5. 显示窗口，前面创建的信息都在内存中，通过 this.setVisible(true) 把内存中的窗口显示在屏幕上。
        this.setVisible(true);
    }

    private JPanel getContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(5,5));

        JPanel connectPanel = getConnectPanel();
        JPanel infoPanel = getConnectInfoPanel();
        JPanel msgPanel = getMessagePanel();

        panel.add(connectPanel,BorderLayout.NORTH);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(msgPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel getConnectPanel() {
        JPanel panel = new JPanel();
        JLabel ipLabel = new JLabel("IP地址：");
        ipField.setColumns(20);

        JLabel portLabel = new JLabel("端口：");
        portField.setColumns(10);

        panel.add(ipLabel);
        panel.add(ipField);
        panel.add(portLabel);
        panel.add(portField);
        panel.add(connect);
        return panel;
    }

    private JPanel getConnectInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        // 设置自动换行
        info.setLineWrap(true);
        panel.add(info, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getMessagePanel() {
        JPanel panel = new JPanel(new BorderLayout(5,5));

        msg.setColumns(20);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        send.setEnabled(false);
        btnPanel.add(send);
        btnPanel.add(exit);

        panel.add(msg, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void addBtnAction() {
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipField.getText().trim();
                String port = portField.getText().trim();
                if(!"".equals(ip) && !"".equals(port)) {
                    try {
                        udpClient = new UDPClient(ip, port);
                        send.setEnabled(true);
                        connect.setEnabled(false);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(contentPanel,"连接属性不能为空","警告",JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = msg.getText().trim();
                if ("".equals(message)) {
                    JOptionPane.showMessageDialog(contentPanel, "信息不能为空","警告",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                udpClient.send(message);
                String text = "".equals(info.getText()) ? message : info.getText() + "\n" + message;
                text += "\n" + udpClient.receive();
                info.setText(text);
                msg.setText("");
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                udpClient.send("bye");
                udpClient.close();
                System.exit(0);
            }
        });
    }
}
