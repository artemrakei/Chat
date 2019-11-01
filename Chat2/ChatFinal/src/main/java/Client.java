import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame {
    private static DataInputStream dis;
    private static OutputStream os;
    private static DataOutputStream dos;
    private static ObjectOutputStream oOs;
    private static BufferedWriter bos;
    private static BufferedReader bis;
    private final JTextArea jTextArea = new JTextArea();
    private final JScrollPane jScrollPane = new JScrollPane(jTextArea);
    private final JTextField nick = new JTextField();
    private final JTextField message = new JTextField("");
    private final JButton jButton = new JButton("Choose file");
    private final JButton jButton1 = new JButton("Download");
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    public static void main(String[] args) throws IOException {
        new Client();
    }


    public Client() throws IOException {
        final Socket socket = new Socket("127.0.0.1", 8006);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        add(jScrollPane);
        add(nick, BorderLayout.NORTH);
        Box box = Box.createHorizontalBox();
        box.add(message);
//        message.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (!message.getText().isEmpty() || !message.getText().equals("")){
//                    try {
//                        final Socket socket = new Socket("127.0.0.1", 8006);
//
//                        oOs = new ObjectOutputStream(socket.getOutputStream());
//                        dos = new DataOutputStream(oOs);
//                        bos = new BufferedWriter(new OutputStreamWriter(dos));
//                        bos.write(message.getText());
//                        message.setText("");
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
//                    try {
//                        bos.flush();
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//        });
        box.add(jButton);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    byte[] bytes = new byte[(int) file.length()];
                    try {

                        dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
                        os = socket.getOutputStream();
                        dos = new DataOutputStream(os);
                        dos.writeUTF(file.getName());
                        dos.writeLong(bytes.length);
                        int read;
                        while ((read = dis.read(bytes)) != -1) {
                            dos.write(bytes, 0, read);
                        }
                        dos.flush();
                        socket.close();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        box.add(jButton1);
        add(box, BorderLayout.SOUTH);

        setVisible(true);

    }


}
