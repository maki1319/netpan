package server;
/**
 * 这是服务器类，用来给网盘客户端提供连接服务，连接之后可以给客户端提供上传和下载服务
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
			System.out.println("服务器已经启动");

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
					System.out.println("上传文件");
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
							"用户[" + message.getFrom().getUsername() + "]的[" + message.getFilename() + "]文件上传成功!");
					break;
				}
				case LISTALLFILES: {
					System.out.println("读取文件列表");
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
					System.out.println("完毕");
					break;
				}
				case DOWNLOAD: {// 用户发过来一个下载文件的消息
					System.out.println("下载文件");
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
					System.out.println("下载文件完毕");
					break;
				}
				case DELETE: {
					System.out.println("删除文件");
					String username = message.getFrom().getUsername();
					String fileName = message.getFilename();
					File file = new File(SocketConfig.FileBasePath + username + "/" + fileName);
                    out.close();
					in.close();
					System.out.println("删除文件完毕");
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
