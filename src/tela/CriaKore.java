package tela;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;

import mslinks.ShellLink;

public class CriaKore {

	private JFrame frmCriakore;
	private JTextField txf;
	
	private BufferedReader buffread;

	public void OpenCriaKore() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CriaKore window = new CriaKore();
					window.frmCriakore.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CriaKore() {
		initialize();
	}

	private void initialize() {
		frmCriakore = new JFrame();
		frmCriakore.setTitle("CriaKore");
		frmCriakore.setIconImage(Toolkit.getDefaultToolkit().getImage(CriaKore.class.getResource("/img/yoshi.png")));
		frmCriakore.setResizable(false);
		frmCriakore.setBounds(100, 100, 265, 110);
		frmCriakore.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCriakore.getContentPane().setLayout(null);
		
		JLabel lbl = new JLabel("Nome");
		lbl.setBounds(10, 11, 46, 14);
		frmCriakore.getContentPane().add(lbl);
		
		txf = new JTextField();
		txf.setBounds(66, 8, 173, 20);
		frmCriakore.getContentPane().add(txf);
		txf.setColumns(10);
		
		JButton btn = new JButton("Contemple o ovo!");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Iniciar();
			}
		});
		btn.setBounds(10, 36, 229, 23);
		frmCriakore.getContentPane().add(btn);
		frmCriakore.setLocationRelativeTo(null);
	}
	
	public void Iniciar() {
		if (Pattern.compile("^[A-Za-z0-9]+$").matcher(txf.getText()).matches() == false) {
			JOptionPane.showMessageDialog(null, "V�lido somente letras mai�sculas e min�sculas de \"A\" at� \"Z\", e n�meros");
			return;
		}
		
		if (new File("ordemkore").exists()) {
			File[] files = new File("ordemkore").listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					if (file.getName().equals(txf.getText())) {
						JOptionPane.showMessageDialog(null, "A pasta \"" + txf.getText() + "\" j� existe dentro da pasta \"ordemkore\"");
						return;
					}
				}
			}
		}
		
		try {
			FileUtils.copyFileToDirectory(new File("control\\config.txt"), new File("ordemkore\\" + txf.getText()));
			FileUtils.copyFileToDirectory(new File("control\\seller\\config.txt"), new File("ordemkore\\" + txf.getText() + "\\seller"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao criar nova pasta \"control\" dentro da pasta \"ordemkore\"");
			return;
		}
		
		try {
			Path path = Paths.get("ordemkore\\"+ txf.getText() +"\\config.txt");
			List<String> linhas = new ArrayList<String>();
			buffread = new BufferedReader(
			new FileReader("control\\config.txt"));
			while (buffread.ready()) {
				String linha = buffread.readLine();
				if (linha.length() >= 8 && linha.substring(0, 8).equals("username")) 
					linhas.add("username " + txf.getText());
				else
					linhas.add(linha);
			}
			Files.write(path, linhas);
			buffread.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao modificar o \"ordemkore\\"+ txf.getText() +"\\config.txt\"");
			return;
		}
		
		try {
			Path path = Paths.get("ordemkore\\"+ txf.getText() +"\\seller\\config.txt");
			List<String> linhas = new ArrayList<String>();
			buffread = new BufferedReader(
			new FileReader("control\\seller\\config.txt"));
			while (buffread.ready()) {
				String linha = buffread.readLine();
				if (linha.length() >= 8 && linha.substring(0, 8).equals("username")) 
					linhas.add("username " + txf.getText());
				else
					linhas.add(linha);
			}
			Files.write(path, linhas);
			buffread.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao modificar o \"ordemkore\\"+ txf.getText() +"\\seller\\config.txt\"");
			return;
		}
		
		
		try {
			String caminho = CriaKore.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			caminho = caminho.substring(1, caminho.lastIndexOf('/') + 1);
			ShellLink sl = ShellLink.createLink(caminho + "openkore.pl");
			sl.setCMDArgs("--config=\"" + caminho + "ordemkore/" + txf.getText() +"/config.txt");
			//sl.setCMDArgs("--control=\"" + caminho + "ordemkore/" + txf.getText() +"\" --logs=\"" + caminho + "ordemkore/" + txf.getText() + "/logs\"");
			sl.saveTo(caminho + "ordemkore//" + txf.getText() + "//" + txf.getText() + ".lnk");
		} catch (URISyntaxException | IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao gerar o arquivo \"CriaKore " + txf.getText() + "\"");
		}
		
		JOptionPane.showMessageDialog(null, "Pasta criada com sucesso");
	}
}