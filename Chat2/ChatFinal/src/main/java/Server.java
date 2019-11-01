import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static DataInputStream inputStream;
    private static InputStream is;
    private static OutputStream os;
    private static ObjectInputStream oIs;
    private static BufferedReader bis;
    private static BufferedWriter bos;


    public static void main(String[] args) throws IOException {
        long buffSize;
        String fileName;
        ServerSocket serverSocket = new ServerSocket(8006);
        System.out.println("Server running");
        while (true) {
            Socket clientSocket = serverSocket.accept();
                is = clientSocket.getInputStream();
                inputStream = new DataInputStream(is);
                if (inputStream.readUTF().isEmpty()) {
                    bis = new BufferedReader(new InputStreamReader(inputStream));
                    String str = bis.readLine();
                    System.out.println(str);
                } else {
                    fileName = inputStream.readUTF();
                    buffSize = inputStream.readLong();
                    os = new FileOutputStream("Download\\" + fileName);
                    byte[] bytes = new byte[(int) buffSize];
                    int read;
                    while ((read = inputStream.read(bytes)) != -1) {
                        os.write(bytes, 0, read);
                    }
                    os.flush();
                }
            clientSocket.close();
        }
    }
}
