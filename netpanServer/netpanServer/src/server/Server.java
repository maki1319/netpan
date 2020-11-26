package server;
/**
 * ���Ƿ������࣬���������̿ͻ����ṩ���ӷ�������֮����Ը��ͻ����ṩ�ϴ������ط���
 * @author Lenovo
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import config.SocketConfig;
import model.Message;

public class Server {
	private ServerSocket server;

	public Server() {
		try {
			server = new ServerSocket(SocketConfig.ServerPort);
			System.out.println("�������Ѿ�����");

			while (true) {
				Socket client = server.accept();
				ClientThread c = new ClientThread(client);
				c.start();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server();
	}

	class ClientThread extends Thread {
		private Socket client;
		private ObjectInputStream in;
		private ObjectOutputStream out;

		public ClientThread(Socket client) {
			this.client = client;
			try {
				in = new ObjectInputStream(client.getInputStream());
				out = new ObjectOutputStream(client.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void run() {
			try {
				Message message = (Message) in.readObject();
				System.out.println(message);
				switch (message.getType()) {
				case UPLOAD: {
					System.out.println("�ϴ��ļ�");
					File dir = new File(SocketConfig.FileBasePath + message.getFrom().getUsername());
					if (!dir.exists())
						dir.mkdirs();
					FileOutputStream fileOut = new FileOutputStream(dir + "/" + message.getFilename());
					byte[] bs = new byte[1024];
					int length = -1;
					while ((length = in.read(bs)) != -1) {
						fileOut.write(bs, 0, length);
						fileOut.flush();
						while (in.available() != 0) {
							break;
						}

					}
					fileOut.close();
					System.out.println(
							"�û�[" + message.getFrom().getUsername() + "]��[" + message.getFilename() + "]�ļ��ϴ��ɹ�!");
					break;
				}
				case LISTALLFILES: {
					System.out.println("��ȡ�ļ��б�");
					File userDir = new File(SocketConfig.FileBasePath + message.getFrom().getUsername());
					if (!userDir.exists())
						userDir.mkdirs();
					File[] files = userDir.listFiles();

					Set<File> fs = new HashSet<File>();
					for (File f : files) {
						System.out.println(f.getAbsolutePath());
						fs.add(f);
					}
					Message allFilesMessage = new Message();
					allFilesMessage.setFiles(fs);
					out.writeObject(allFilesMessage);
					out.flush();
					System.out.println("���");
					break;
				}
				case DOWNLOAD: {// �û�������һ�������ļ�����Ϣ
					System.out.println("�����ļ�");
					String username = message.getFrom().getUsername();
					String fileName = message.getFilename();
					File file = new File(SocketConfig.FileBasePath + username + "/" + fileName);
					FileInputStream fileIn = new FileInputStream(file);

					byte[] bs = new byte[1024];
					int length = -1;
					while ((length = fileIn.read(bs)) != -1) {
						out.write(bs, 0, length);
						out.flush();
					}
					fileIn.close();
					out.close();
					in.close();
					System.out.println("�����ļ����");
					break;
				}
				case DELETE: {
					System.out.println("ɾ���ļ�");
					String username = message.getFrom().getUsername();
					String fileName = message.getFilename();
					File file = new File(SocketConfig.FileBasePath + username + "/" + fileName);
                    out.close();
					in.close();
					System.out.println("ɾ���ļ����");
					break;

				}

				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
